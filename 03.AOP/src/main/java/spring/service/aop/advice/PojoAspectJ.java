package spring.service.aop.advice;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;


public class PojoAspectJ {
	
	//Constructor
	public PojoAspectJ() {
		System.out.println(":: PojoAspectJ Default Csontructor");
	}
	
	public void before(JoinPoint joinPoint) {
		
		System.out.println("[before LOG]"+getClass()+".brfore() start...");
		System.out.println("[before LOG] Taget Object : " +joinPoint.getTarget().getClass().getName());
		System.out.println("[before LOG] Taget Object method : " +joinPoint.getSignature().getName());
		if( joinPoint.getArgs().length !=0) {
			System.out.println("[before LOG] Taget Object call" +joinPoint.getArgs()[0]);
		}
		System.out.println("[before LOG]"+getClass()+".brfore() end...");
	}
	
	public void afterReturning(JoinPoint joinPoint, Object returnValue) {
		
		System.out.println("[after LOG]"+getClass()+".afterReturning() start...");
		System.out.println("[after LOG] 타겟 객체 :" +joinPoint.getTarget().getClass().getName());
		System.out.println("[after LOG] 타겟 객체의 호출된 method :" + joinPoint.getSignature().getName());
		System.out.println("[after LOG] 타겟 객체의 호출후 return value: " +returnValue);
		System.out.println("[after LOG]"+getClass()+".afterReturning() end...");
	}
	
	public Object invoke(ProceedingJoinPoint joinPoint) throws Throwable {
		
		System.out.println("[Around before]"+getClass()+".invoke() start...");
		System.out.println("[Around before] 타겟 객체 :" +joinPoint.getTarget().getClass().getName());
		System.out.println("[Around before] 타겟 객체의 호출될 method :" + joinPoint.getSignature().getName());
		
		if(joinPoint.getArgs().length !=0) {
			System.out.println("[Around before] 타겟 객체의 호출할"+"method에 전달되는 인자:"+ joinPoint.getArgs()[0]);
		}
		Object obj = joinPoint.proceed();
		
		System.out.println("[after LOG] 타겟 객체의 호출후 return value: " +obj);
		System.out.println("[Around after]" +getClass()+".invoke() end...");
		
		return obj;
	}
	
	public void afterThrowing(JoinPoint joinPoint, Throwable throwable) {
		
		System.out.println("[exception]"+getClass()+".afterThrowing() start...");
		System.out.println("[exception] 타겟 객체 :" +joinPoint.getTarget().getClass().getName());
		System.out.println("[exception] 타겟 객체의 호출된 method :" + joinPoint.getSignature().getName());
		System.out.println("[exception] Exception 발생...");
		System.out.println("[exception] Exception Message : " + throwable.getMessage());
		System.out.println("[exception]"+getClass()+".afterThrowing() end...");
	}
}
