package com.ccesun.framework.core.web.listener;

import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.ccesun.framework.core.AppContext;

public class StartupListener implements ServletContextListener {

    private static final Logger log = Logger.getLogger(StartupListener.class);

    @Override
    public void contextInitialized(ServletContextEvent event) {
        if (log.isInfoEnabled()) {
            log.info("Servlet Context initialized...");
        }
        ServletContext context = event.getServletContext();
        //设置数据
        AppContext.getInstance().setServletContext(context);
        //设置当前WEB路径上下文
        String contextPath = context.getContextPath();
        context.setAttribute("contextPath", contextPath);
        Locale.setDefault(Locale.CHINESE);
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
    	AppContext.getInstance().setServletContext(null);
        if (log.isInfoEnabled()) {
            log.info("Servlet Context destroyed...");
        }
    }
}