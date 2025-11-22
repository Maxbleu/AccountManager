package com.juanfran.accountsmanager.di;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public final class OrchestratorProyectDependences {
    private static ApplicationContext context;
    private static Logger logger4j;

    private OrchestratorProyectDependences() {}
    public static void loadProyectDependences(Logger logger, String beanFilePath){
        logger4j = logger;
        context = new ClassPathXmlApplicationContext(beanFilePath);
    }
    public static Object getService(Class<?> c){
        return context.getBean(c);
    }
    public static Logger getLogger(){
        return logger4j;
    }
}
