package mybatis.service;

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
}
