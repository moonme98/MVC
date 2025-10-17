package spring.service.aop.advice;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.ThrowsAdvice;

public class TestAdvice implements MethodBeforeAdvice,
										AfterReturningAdvice,
										ThrowsAdvice,
										MethodInterceptor{
	
	public void before( Method method, Object[] args, Object target) {
		
		System.out.println("[before LOG]"+getClass()+".brfore() start...");
		System.out.println("[before LOG] tagetObject call Method :" +method);
		if( args.length !=0) {
			System.out.println("[before LOG] tagetObject method 전달 argument : "+args[0]);
		}
		System.out.println("[before LOG]"+getClass()+".brfore() end...");
	}
	
	public void afterReturning( Object returnValue, Method method,
									Object[] args, Object target) {
		
		System.out.println("[after LOG]"+getClass()+".afterReturning() start...");
		System.out.println("[after LOG] tagetObject call Method :" +method);
		System.out.println("[after LOG] 타겟 객체의 호출후 return value :" +returnValue);
		System.out.println("[after LOG]"+getClass()+".after() end...");
	}
	
	public Object invoke( MethodInvocation invocation) throws Throwable {
		
		System.out.println("[Around before]"+getClass()+".invoke() start...");
		System.out.println("[Around before] tagetObject call Method :" +invocation.getMethod());
		if( invocation.getArguments().length !=0) {
			System.out.println("[Around before] tagetObject method 전달 argument : " + invocation.getArguments()[0]);
		}
		Object obj = invocation.proceed();
		
		System.out.println("[Around after] 타겟 객체의 호출후 return value :" +obj);
		System.out.println("[Around after]" +getClass()+".invoke() end...");
		
		return obj;
	}
	
	public void afterThrowing(Throwable throwable) {
		
		System.out.println("[exception]"+getClass()+".afterThrowing() start...");
		System.out.println("[exception] Exception 발생...");
		System.out.println("[exception] Exception Message : " + throwable.getMessage());
		System.out.println("[exception]"+getClass()+".afterThrowing() end...");
	}
}
