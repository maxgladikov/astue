package astue.aop;

import org.aspectj.lang.annotation.Pointcut;

public class PointCuts {
    @Pointcut("execution(* astue.service.*.getx*(..))")
    public void allGetMethods(){}

    @Pointcut("execution(* astue.service.*.addx*(..))")
    public void allAddMethods(){}

    @Pointcut("execution(* astue.service.*.putx*(..))")
    public void allUpdateMethods(){}

    @Pointcut("execution(* astue.service.*.deletex*(..))")
    public void allDeleteMethods(){}
   
    @Pointcut("execution(* astue.service.*.readFromField(..))")
    public void allReadFromFieldMethods(){}
}
