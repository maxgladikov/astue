package astue.aop;

import astue.entity.Book;
import astue.util.CustomResponse;
import astue.util.CustomStatus;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class MyAspect {
    @Around("Pointcuts.allAddMethods()")
    public Object aroundAddingAdvice(ProceedingJoinPoint joinPoint){
        MethodSignature methodSignature=(MethodSignature) joinPoint.getSignature();
        Book book=null;
        if (methodSignature.getName().equals("addBook")){
            Object[] arguments= joinPoint.getArgs();
            for(Object arg:arguments){
                if(arg instanceof Book){
                    book=(Book) arg;
                    log.info("Trying to add book with name {}",book.getTitle());
                }
            }
        }
        Object result;
        try {
            result=joinPoint.proceed();
        } catch (Throwable e) {
            log.error(e.getMessage(),e);
            result= new CustomResponse<Book>(null, CustomStatus.EXCEPTION);
        }
        log.info("Book with name {} was added",book.getTitle());
        return result;
    }

}
