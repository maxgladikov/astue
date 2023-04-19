package astue.aop;

import org.aspectj.lang.annotation.Pointcut;

public class PointCuts {
    @Pointcut("execution(* astue.service.*.get*(..))")
    public void allGetMethods(){}

    @Pointcut("execution(* astue.service.*.add*(..))")
    public void allAddMethods(){}

    @Pointcut("execution(* astue.service.*.put*(..))")
    public void allUpdateMethods(){}

    @Pointcut("execution(* astue.service.*.delete*(..))")
    public void allDeleteMethods(){}
   
//    @Pointcut("execution(* astue.service.task.*.proceed*(..))")
//    public void fieldbus(){}
}
