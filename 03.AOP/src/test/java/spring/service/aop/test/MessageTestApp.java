package spring.service.aop.test;

import spring.service.aop.Message;
import spring.service.aop.impl.MessageImpl;

/*
 * FileName : MessageTestApp.java
 * ::Message / MessageImpl 를 사용하는 Application
 */
public class MessageTestApp {

	///Main
	public static void  main(String[] args) throws Exception {
		
		Message message = new MessageImpl();
		
		System.out.println("==============================");
		message.setMessage("Hello");
		System.out.println("==============================");
		String messageValue = message.getMessage();
		System.out.println("==============================");
	    
		System.out.println("\n리턴 받은 메세지 : "+messageValue);
	    
	}
}//end of class

/* 	
 * [고려할 사항]
 * :: Method 실행 전 무언가를 하고 싶다면...
 * :: Method 실행 후 무언가를 하고 싶다면...
 */