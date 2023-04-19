package astue.aop;

import astue.exception.fieldbus.DeviceNotAvailable;
import astue.exception.fieldbus.ResponseTimeOutException;
import astue.model.BaseEntity;
import astue.model.Device;
import astue.util.CustomResponse;
import astue.util.CustomStatus;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class MyAspect {
    Logger log = LoggerFactory.getLogger(this.getClass());

    // ADD ENTITY
    @Around("PointCuts.allAddMethods()")
    public Object aroundAddingAdvice(ProceedingJoinPoint joinPoint){
        MethodSignature methodSignature=(MethodSignature) joinPoint.getSignature();
        BaseEntity entity=null;
        if (methodSignature.getName().equals("add")){
            Object[] arguments= joinPoint.getArgs();
            for(Object arg:arguments){
                if(arg instanceof BaseEntity){
                    entity=(BaseEntity) arg;
                    log.info("*** Trying to add {} with name {} ***",entity.getClass().getSimpleName(),entity.getName());
                }
            }
        }
        Object result;
        try {
            result=joinPoint.proceed();
        } catch (Throwable e) {
            log.error("*** "+e.getMessage(),e);
            result= new Object();
        }
//        log.info("*** Entity with name {} was added ***",entity.getName());
        log.info("*** Entity with was added ***");
        return result;
    }
    // GETTERS
    @Around("PointCuts.allGetMethods()")
    public Object aroundGettingAdvice(ProceedingJoinPoint joinPoint){
        MethodSignature methodSignature=(MethodSignature) joinPoint.getSignature();
        Class clazz= methodSignature.getDeclaringType();;
        Class returnType = ((MethodSignature) methodSignature).getReturnType();
        Object parameter=null;
        if(methodSignature.getName().equals("getAll")){
            log.info("**** Trying to get all entities ***");
        } else  {
            Object[] arguments=joinPoint.getArgs();
            for(Object arg:arguments){

                    parameter = arg;
                    log.info("*** {} trying to {} {} with parameter {} ***",clazz.getSimpleName(),methodSignature.getName(),returnType.getSimpleName(),parameter.toString());
            }
        }
        Object result=null;
        try{
            result=joinPoint.proceed();
        } catch (Throwable e){
            log.error(e.getMessage(),e);
            result=new CustomResponse<>(null,CustomStatus.EXCEPTION);
        }

        if (methodSignature.getName().equals("getAll")){
            log.info("All entities were received");
        } else   {
            log.info("entity was received");
        }
        return result;
    }

    // UPDATE
    @Around("PointCuts.allUpdateMethods()")
    public Object aroundUpdatingAdvice(ProceedingJoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        BaseEntity entity = null;
        if (methodSignature.getName().toLowerCase().contains("update")) {
            Object[] arguments = joinPoint.getArgs();
            for (Object arg : arguments) {
                if (arg instanceof BaseEntity) {
                    entity = (BaseEntity) arg;
                    log.info("*** Trying to update {} with name {} ***", entity.getClass().getSimpleName(), entity.getName());
                }
            }
        }
        Object result;
        try {
            result=joinPoint.proceed();
        } catch (Throwable e) {
            log.error("*** "+e.getMessage(),e);
            result= new Object();
        }
        log.info("*** Entity with name {} was updated ***",entity.getName());
        return result;
    }

    // DELETE
    @Around("PointCuts.allDeleteMethods()")
    public Object aroundDeletingAdvice(ProceedingJoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Long entityId = null;
        if (methodSignature.getName().toLowerCase().contains("delete")) {
            Object[] arguments = joinPoint.getArgs();
            for (Object arg : arguments) {
                if (arg instanceof Long) {
                    entityId = (Long) arg;
                    log.info("*** Trying to delete with with id {} ***",  entityId.toString());
                }
            }
        }
        Object result;
        try {
            result=joinPoint.proceed();
        } catch (Throwable e) {
            log.error("*** "+e.getMessage(),e);
            result= new Object();
        }
        log.info("*** Entity with id {} was deleted ***",entityId.toString());
        return result;
    }
  
//    @Around("PointCuts.allReadFromFieldMethods()")
//    public Object aroundReadFromFieldAdvice(ProceedingJoinPoint joinPoint)  {
//    	System.out.println("AOP*************");
//    	Device device = null;
//    		Object[] arguments = joinPoint.getArgs();
//    		for (Object arg : arguments) {
//    			if (arg instanceof Device) {
//    				device = (Device) arg;
//    				log.info("*** Trying to read from {} of type {} ***",  device.getName(),device.getId().toString());
//    			}
//    		}
//    		
//    	Object result = null;
//    	try {
//    		result=joinPoint.proceed();
//    	} catch (DeviceNotAvailable e) {
//    		log.error("Device {} with ip {} is unreachable",device.getName(),device.getHostAddress());
//    		result= new Object();
//    	} catch (ResponseTimeOutException e) {
//    		log.error("Device {} with ip {} is exeeded respone timeout",device.getName(),device.getHostAddress());
//    		result= new Object();
//		} catch (Throwable e) {
//			log.error("*** "+e.getMessage(),e);
//            result= new Object();
//		}
//    	log.info("*** Reading from device {} successfully completed ***",device.getName());
//    	return result;
//    }
}
    
  