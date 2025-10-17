package spring.service.aop.handler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/*
 * FileName : LoggingHandler.java
 *  :: 선 / 후 처리를 추상화한 InvocationHandler 를 구현한 Bean
 *  :: InvocationHandler.invoke() O/R 하여 선/후 처리 구현
 *  :: Proxy 객체는 Target Object Method 를 호출 전/후  Handler 를 이용 전/후 처리
 */
public class LoggingHandler implements InvocationHandler {
	
	///Field
	//==> 타겟객체 선언 
	private Object targetObject;
	
	///Constructor
	public LoggingHandler(){
	}
	public LoggingHandler(Object tagetObject) {
		this.targetObject = tagetObject;
	}
	
	// :: invoke() Method O/R  : Target Object 메서드 호출 선/후처리 할 내용 구현 (API 확인)
	public Object invoke(Object proxy, Method method, Object[] args) 
																								throws Throwable {
		System.out.println("\n=====================================");
		System.out.println("[LOG] "+getClass()+".invoke() start.....");
		System.out.println("[LOG] Target Object 호출 할 method :"+method);
		if(args != null){
			System.out.println("[LOG] Target Object 호출 할 method에 전달되는 인자 : "+args[0]);
		}
		
		try {
			//==> 타겟 객체의 Method 를 호출 하는 부분 
			return method.invoke(targetObject, args);
		} catch (Exception e) {
			throw e;
		}finally{
			System.out.println("[LOG] "+getClass()+".invoke() end.....");
			System.out.println("=====================================\n");
		}
	}
	
}//end of class