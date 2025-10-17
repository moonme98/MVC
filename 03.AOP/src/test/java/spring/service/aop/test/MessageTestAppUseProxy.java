package spring.service.aop.test;

import spring.service.aop.Message;
import spring.service.aop.proxy.MessageImplProxy;

/*
 * FileName : MessageTestAppUseProxy.java
 * 
 * ::Proxy 객체를 이용하여 Target Object 메서드를 호출
 * 
 * ::Target Object 메서드 호출하는 Proxy Bean 의  Hierarchy   
 *      Manager
 *          L ManagerImpl
 *                   L ManagerImplProxy class 
 *  
 * :: Proxy 를 이용하면 타겟객체의 메서드 호출시 선/후 처리 가능
 */
public class MessageTestAppUseProxy {
	
	///Main Method
	public static void main(String[] args) throws Exception {
		
		Message message = new MessageImplProxy();
		
		System.out.println("==============================");
		message.setMessage("Hello");
		System.out.println("==============================");
		String messageValue = message.getMessage();
		System.out.println("==============================");
	    
		System.out.println("\n리턴 받은 메세지 : "+messageValue);
		
	}
}//end of class