package spring.service.aop.advice;

import java.lang.reflect.Method;

import org.springframework.aop.MethodBeforeAdvice;

/*
 *FileName : BeforeLogAdvice.java
 * :: LoggingHandler.java 와 비교 이해
 * :: Target Object 호출 전 선처리에 사용 될  Handler Bean 은 
 *     MethodBeforeAdvice 구현 Bean  으로 변경
 * =========================================================
 *  :: AOP 용어정리 
 *     ㅇAdvice	:  무엇을 	:: 로그, 예외처리, 트랜젝션....
 * =========================================================               
 */
public class BeforeLogAdvice implements MethodBeforeAdvice {
	
	///Method
	//==>타겟객체의 Method 를 호출 전  호출되는 before() Method 구현   
	public void before(Method method, Object[] args, Object target)
																										throws Throwable {
		
		System.out.println("\n==========================================");
		System.out.println("[before LOG] "+getClass()+".before() start.....");
		System.out.println("[before LOG] tagetObject call Method :"+method);
		if( args.length != 0 ){
			System.out.println("[before LOG] tagetObject method 전달 argument : "+args[0]);
		}
		System.out.println("[before LOG] "+getClass()+".before() end.....");
	}
	
}