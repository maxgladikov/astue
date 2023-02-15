package astue.aop;

import astue.model.BaseEntity;
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
        log.info("*** Entity with name {} was added ***",entity.getName());
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

}
