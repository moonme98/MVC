package spring.service.aop.proxy;

import spring.service.aop.impl.MessageImpl;

/*
 * FileName : MessageImplProxy.java
 * ::ManagerImpl.getMesage() 호출를	대리하는  Proxy 객체 
 */
public class MessageImplProxy extends MessageImpl {
	
	///Field
	
	///Constructor
	public MessageImplProxy() {
	}

	///Method
	public String getMessage() throws Exception {

		System.out.println("\nProxy :: "+getClass()+".getMessage() start... ");

		//==> :: Target Object : 실질적으로 호출되는 Method 를 갖는 Object
		//==> :: Target Object 를 호출은 Proxy 를 통해 호출 됨으로 Proxy 가 호출 전,후 전/후처리 가능.
		//==> ㅇ 예외가 발생한다면 처리
		//==> ㅇ 전달되는 인자 가공
		//==> ㅇ return 되는 값의 가공 Proxy 객체를 통해서 가능하다.
		String result = super.getMessage();
		
		System.out.println("Proxy :: "+getClass()+".getMessage() end...\n ");
		
		return result;
	}

}//end of class