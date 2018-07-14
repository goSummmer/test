package com.kedacom;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 * 邮件发送测试类
 * @author tobeng
 * @date 2018/7/7 23:29
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class EmailTest {

    @Autowired
    private JavaMailSenderImpl javaMailSender;

    /**
     * 发送简单邮件测试方法
     * class: SimpleMailMessage
     */
    @Test
    public void sendSimpleEmailTest(){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        //邮件标题
        simpleMailMessage.setSubject("测试");
        //邮件文本内容
        simpleMailMessage.setText("这是一个简单测试！");
        //发送者
        simpleMailMessage.setTo("yrlezm@163.com");
        //接收者
        simpleMailMessage.setFrom("294571638@qq.com");
        //发送
        javaMailSender.send(simpleMailMessage);
    }

    /**
     * 复杂邮件
     */
    @Test
    public void complexTest() throws MessagingException {
        //1、创建一个复杂的消息邮件
        MimeMessage mimeMailMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMailMessage, true);
        //邮件设置
        mimeMessageHelper.setSubject("测试复杂邮件");
        mimeMessageHelper.setText("<p>内容</p>", true);

        mimeMessageHelper.setTo("yrlezm@163.com");
        mimeMessageHelper.setFrom("294571638@qq.com");

        //上传邮件
        mimeMessageHelper.addAttachment("1.jpg", new File("K:\\github\\test\\src\\main\\resources\\static\\images\\1.jpg"));
        mimeMessageHelper.addAttachment("2.jpg",new File("K:\\github\\test\\src\\main\\resources\\static\\images\\2.jpg"));

        javaMailSender.send(mimeMailMessage);

    }

}
