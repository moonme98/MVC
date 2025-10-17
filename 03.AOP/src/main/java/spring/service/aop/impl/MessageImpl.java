package spring.service.aop.impl;

import spring.service.aop.Message;

/*
 * FileName : MessageImpl.java
 * :: Message Interface 구현 Bean 
 */
public class MessageImpl implements Message {
	
	///Field
	private String message;
	
	///Constructor
	public MessageImpl(){
	}
	
	///Method
	public String getMessage() throws Exception {
		System.out.println("\n:: "+getClass()+".getMessage() start / end ...\n ");
		return "Hi "+message;
	}
	
	public void setMessage(String message) throws Exception {
		
		this.message = message;
		
		System.out.println("\n:: "+getClass()+".setMessage() start... ");
		
		//==> 전달받은 message 가 null 인 경우 NullPointerException 발생...
		if(message ==null ){
			System.out.println(":: NullPointerException 발생");
			throw new NullPointerException();
		}
		
		System.out.println(":: "+getClass()+".setMessage() end...\n");
	}
	
}//end of class