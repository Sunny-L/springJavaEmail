package com.sunyson;

import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.velocity.VelocityEngineUtils;

public class SpringJavaEmail {
	
	private JavaMailSenderImpl javaMailSenderImpl;
	private VelocityEngine velocityEngine;
	
	public SpringJavaEmail() {
		javaMailSenderImpl = new JavaMailSenderImpl();
		javaMailSenderImpl.setHost("smtp.qq.com"); //smtp服务器地址
		javaMailSenderImpl.setUsername("******@qq.com");	//发送方邮箱帐号
		javaMailSenderImpl.setPassword("**********");		//发送方邮箱密码
	}
	
	@Test
	public void templateSend() throws MessagingException{
		ApplicationContext ctx = new FileSystemXmlApplicationContext("/src/applicationContext.xml");
		velocityEngine  = (VelocityEngine)ctx.getBean("velocityEngine");
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("user", "sunyson");
		model.put("content", "velocity集成Srping javaemail 测试...");
		String emailText = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "pswdRecover.vm","utf-8",model);
		
		System.out.println(emailText);
		MimeMessage msg = javaMailSenderImpl.createMimeMessage();
		MimeMessageHelper messageHelper = new MimeMessageHelper(msg,true,"utf-8");
		messageHelper.setFrom(javaMailSenderImpl.getUsername());
		messageHelper.setTo("******@126.com");		//接收方邮箱账号
		messageHelper.setSubject("spring email 集成测试");	//接收方邮箱密码
		messageHelper.setText(emailText,true);
		javaMailSenderImpl.send(msg);
	}
}
