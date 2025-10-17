package spring.service.aop.test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import spring.service.aop.Message;
import spring.service.aop.handler.LoggingHandler;
import spring.service.aop.impl.MessageImpl;

/*
 * FileName : MessageTestAppUseProxyAPI.java
 * :: 	Proxy Bean 를  개발자가 구현 하지 않고 
 * 		java.lang.reflect.Proxy API 사용 만들주는 Proxy instance  사용 
 * :: 	실행시 java.lang.reflect.Proxy 에의해 만들어 지는 Proxy::Dynamic Proxy
 * 		아래의 Hierarchy 를 갖는다.  
 *      Message
 *        L MessageImpl
 *            L Dynamic Proxy
 * 
 *	:: 위와 같으 Hierarchy 를 갖는 Dynamic Proxy 를 만들기 위해..
 *  -  하나 : interface == > Message 
 *  -  두울 :  TargetObjcet ==>  MessageImpl
 *  -  세엣 : 선,후 처리 수행 정보를 갖는 InvocationHandler를 구현한 Bean
 *     3가지 정보를 가 필요
 */
public class MessageTestAppUseProxyAPI {
	
	///Main Method
	public static void main(String[] args) throws Exception {
		
		System.out.println("::LoggingHandler를 사용 Method 호출 전,후를  log 기록");
		
		//==>1. InvocationHandler객체를 구현한 Handler 객체와 Target Object 연결(?) 
		LoggingHandler logHandler = new LoggingHandler(new MessageImpl());
		
		//==>2. Proxy.newProxyInsanc() 를 이용 Dynamic Proxy 생성 
		//==> java.lang.reflect.Proxy API 참조
		Message  messageProxy 
			= (Message)Proxy.newProxyInstance	(	MessageImpl.class.getClassLoader(),
																					new Class[]{ Message.class}, 
																					logHandler	);
			   
		//==>3. Dynamic Proxy 를 통해 타겟객체 메서드 호출 ::  출력 결과를 확인
		messageProxy.setMessage("Hello");
		
		System.out.println("\n리턴 받은 메세지 : "+messageProxy.getMessage());
		
		
//		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//		//Proxy / Handler 을 이용한 Proxy instance 다른 사용법(참조용)				
//		System.out.println("::LoggingHandler를 사용 Method 호출 전,후를  log 기록");
//		
//		//==>2. Proxy.newProxyInsanc() 를 이용 
//		//==> java.lang.reflect.Proxy API 참조
//	    Class proxyClass =
//	    				Proxy.getProxyClass(	
//	    															MessageImpl.class.getClassLoader(), 
//	    															new Class[] { Message.class }		
//	    														);
//	    
//	    Message messageProxy01 = 
//	    				(Message) proxyClass.
//	    										getConstructor(	
//	    																		new Class[] { InvocationHandler.class }	).
//	    																		newInstance(new Object[] { logHandler }
//	    												                      );
//
//	    //==> Proxy 객체를 통해 타겟객체 메서드 호출 ::  출력 결과를 확인하자...
//	     messageProxy01.setMessage("Hello");
//		
//	    System.out.println("\n리턴 받은 메세지 : "+messageProxy01.getMessage());
	}
}//end of class