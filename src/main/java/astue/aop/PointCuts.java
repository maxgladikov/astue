package astue.aop;

import org.aspectj.lang.annotation.Pointcut;

public class PointCuts {
    @Pointcut("execution(* astue.service.BookService.get*(..))")
    public void allGetMethods(){}

    @Pointcut("execution(* astue.service.BookService.add*(..))")
    public void allAddMethods(){}
}
