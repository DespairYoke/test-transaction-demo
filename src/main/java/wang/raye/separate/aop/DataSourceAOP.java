package wang.raye.separate.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import wang.raye.separate.config.DynamicDataSource;

/**
 * 数据源的切入面
 *
 */
@Aspect
@Component
@Slf4j
public class DataSourceAOP {

    @Before("execution(* wang.raye.separate.service..*.select*(..)) || execution(* wang.raye.separate.service..*.get*(..))")
    public void setReadDataSourceType() {
        DynamicDataSource.slave();
        log.info("dataSource切换到：slave");
    }

    @Before("execution(* wang.raye.separate.service..*.insert*(..)) || execution(* wang.raye.separate.service..*.update*(..)) || execution(* wang.raye.separate.service..*.delete*(..)) || execution(* wang.raye.separate.service..*.add*(..))")
    public void setWriteDataSourceType() {
        DynamicDataSource.master();
        log.info("dataSource切换到：master");
    }

    @Before(("execution(* org.springframework.jdbc.datasource.DataSourceTransactionManager.getDataSource*(..))"))
    public void setTransaction(){
        DynamicDataSource.master();
        log.info("事物 dataSource切换到：master");
    }
}
