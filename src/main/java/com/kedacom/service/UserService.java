package com.kedacom.service;

import com.kedacom.entity.QUserEntity;
import com.kedacom.repository.UserRepository;
import com.kedacom.entity.UserEntity;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * created by yr on 2018/06/27
 * 将UserRepository中的update、delete方法封装，添加上事物。
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    public void updateUserAge(Integer id, Integer age){
        userRepository.updateUserAge(age, id);
    }

    public void deleteUserById(Integer id){
        userRepository.deleteUserById(id);
    }


    @Autowired
    @PersistenceContext
    private EntityManager entityManager;

    private JPAQueryFactory jpaQueryFactory;

    /**
     * @PostConstruct 注解作用：在servlet初始化使用该注解的方法，只会初始化一次。
     */
    @PostConstruct
    public void init(){
        jpaQueryFactory = new JPAQueryFactory(entityManager);
    }

    /**
     * redis服务器缓存：
     *      用于查询的注解，第一次查询的时候返回该方法返回值，并向redis服务器保存数据，
     *      以后的查询将不再执行方法体内的代码，而是直接查询redis服务器获取数据并返回。
     *      value属性做键，key属性则可以看作为value的子键，
     *      一个value可以有多个key组成不同值存在redis服务器，
     *      这里再介绍一个属性是condition，用法condition="#key<10"，
     *      就是说当key小于10的时候才将数据保存到redis服务器
     */

    /**
     * 使用QueryDSL方法
     * 查询用户信息
     * @param name
     * @return
     */
    @Cacheable(value = "user", key = "#name")
    public UserEntity findOne(final String name){
        logger.info("查询用户信息：{}", name);
        QUserEntity userEntity = QUserEntity.userEntity;
        Predicate predicate = userEntity.name.eq(name);
        return userRepository.findOne(predicate);
    }

    /**
     * 查询表中所有记录
     */
    @Cacheable(value = "user", keyGenerator = "keyGenerator", sync = true)
    public List<UserEntity> findAll(){
        logger.info("查询所有用户信息");
        QUserEntity userEntity = QUserEntity.userEntity;
        return jpaQueryFactory.selectFrom(userEntity)
                .fetch();
    }

    /**
     * 单表单条件查询
     * @param name
     * @return
     */
    @Cacheable(value = "user", key = "#name")
    public UserEntity findOneByUserName(final String name){
        logger.info("查询用户：{}信息", name);
        QUserEntity userEntity = QUserEntity.userEntity;
        return jpaQueryFactory.selectFrom(userEntity)
                .where(userEntity.name.eq(name))
                .fetchOne();
    }

    @Cacheable(value = "user", keyGenerator = "keyGenerator")
    public UserEntity findOneById(final Integer id){
        logger.info("查询用户：{}信息", id);
        return userRepository.findOne(id);
    }

    /**
     * 单表多条件查询
     *  使用：使用and()方法将条件关联
     * @param name
     * @param address
     * @return
     */
    @Cacheable(value = "user", key = "#name")
    public UserEntity findOneByUserNameAndAddress(final String name, final String address){
        logger.info("查询姓名：{}，地址：{} 的信息", name, address);
        QUserEntity userEntity = QUserEntity.userEntity;
        return jpaQueryFactory.selectFrom(userEntity)
                .where(userEntity.name.eq(name).and(userEntity.address.eq(address)))
                .fetchOne();
    }

    /**
     * 将查询结果进行排序
     * 使用：JpaQueryFactory的orderBy()方法，配合生成类的desc()、asc()
     */
    @Cacheable(value = "user", key = "findUserAndOrder", sync = true)
    public List<UserEntity> findUserAndOrder(){
        logger.info("查询所有信息并进行排序。");
        QUserEntity userEntity = QUserEntity.userEntity;
        return jpaQueryFactory.selectFrom(userEntity)
                .orderBy(userEntity.name.desc())
                .fetch();
    }

    /**
     * group by的使用
     */
    @Cacheable(value = "user", key = "findUserByGroup", sync = true)
    public List<UserEntity> findUserByGroup(){
        logger.info("查询所有信息，并进行group by处理。");
        QUserEntity userEntity = QUserEntity.userEntity;
        return jpaQueryFactory.selectFrom(userEntity)
                .groupBy(userEntity.age)
                .fetch();
    }

    /**
     * 分页查询
     * @param offset   页码
     * @param pageSize  每页数量
     * @return
     */
    @Cacheable(value = "user", keyGenerator = "keyGenerator")
    public Page<UserEntity> findAllAndPager(final int offset, final int pageSize){
        logger.info("查询页面：{}，每页数量{}。", offset, pageSize);
        Predicate predicate = QUserEntity.userEntity.id.lt(10);
        Sort sort = new Sort(new Sort.Order(Sort.Direction.ASC, "id"));
        PageRequest pageRequest = new PageRequest(offset, pageSize, sort);
        return userRepository.findAll(predicate, pageRequest);
    }

    /**
     * 更新用户信息
     * @param userEntity
     * @return
     */
    @CachePut(value = "user", key = "#userEntity.name")
    public long update(final UserEntity userEntity){
        logger.info("更新用户：" + userEntity.toString());
        QUserEntity qUserEntity = QUserEntity.userEntity;
        return jpaQueryFactory.update(qUserEntity)
                .set(qUserEntity.name, userEntity.getName())
                .set(qUserEntity.id, userEntity.getId())
                .set(qUserEntity.age, userEntity.getAge())
                .set(qUserEntity.address, userEntity.getAddress())
                .execute();
    }

    /**
     * 新增用户信息
     * @param userEntity
     */
    @CacheEvict(value = "user")
    public void insert(UserEntity userEntity){
        logger.info("新增用户："+ userEntity.toString());
        userRepository.save(userEntity);
    }


    /**
     * 删除用户信息
     * @param id
     * @return
     */

    public long delete(final Integer id){
        logger.info("删除用户id= "+ id);
        QUserEntity qUserEntity = QUserEntity.userEntity;
        return jpaQueryFactory.delete(qUserEntity)
                .where(qUserEntity.id.eq(id))
                .execute();
    }

}
