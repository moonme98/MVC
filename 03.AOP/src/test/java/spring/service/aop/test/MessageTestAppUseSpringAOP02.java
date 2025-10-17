package spring.service.aop.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import spring.service.aop.Message;

/*
 * FileName : MessageTestAppUseSpringAOP02.java
*/
public class MessageTestAppUseSpringAOP02 {
	
	///Main Method
	public static void main(String[] args) throws Exception{

		ApplicationContext context = 
		//		new ClassPathXmlApplicationContext("/config/messageservice02.xml");
		//		new ClassPathXmlApplicationContext("/config/messageservice03.xml");
		//		new ClassPathXmlApplicationContext("/config/messageservice04.xml");
		//		new ClassPathXmlApplicationContext("/config/messageservice05.xml");
		//		new ClassPathXmlApplicationContext("/config/messageservice06.xml");
		//		new ClassPathXmlApplicationContext("/config/messageservice07.xml");			
				new ClassPathXmlApplicationContext("/config/messageservice08.xml");
				
		//==> IoC Container 로 부터 Look Up 한 인스턴스는 ProxyFactoryBean 객체가 생성해준 
		//==> Message interface 구현한 Dynamic Proxy
		Message message = (Message)context.getBean("message");

		//==> 실행방법
		//::1. 인자 : String 전달
	    message.setMessage("Hello");
	    //::2. 인자 : null 전달 : ThrowsAdvice 동작확인
		//message.setMessage(null);
	    
	    System.out.println("\n\n+++++++++++++++++++++++++++++++++++++++++++++\n\n");
		
	    System.out.println("\n리턴 받은 메세지 : "+message.getMessage());
	}
}//end of class