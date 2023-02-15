package astue.aop;

import org.aspectj.lang.annotation.Pointcut;

public class PointCuts {
    @Pointcut("execution(* astue.service.*.get*(..))")
    public void allGetMethods(){}

    @Pointcut("execution(* astue.service.*.add*(..))")
    public void allAddMethods(){}
}
