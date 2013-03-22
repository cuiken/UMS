package com.tp.startup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoaderListener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

/**
 * User: ken.cui
 * Date: 13-3-22
 * Time: 下午1:03
 */
public class UmsContext extends ContextLoaderListener {
    private static Logger logger = LoggerFactory.getLogger(UmsContext.class);

    private static ServletContext servletContext = null;

    public UmsContext() {
        super();
    }

    @Override
    public void contextInitialized(ServletContextEvent event) {
        this.servletContext= event.getServletContext();
        super.contextInitialized(event);
        try{
            UmsStartup.prepare();
        }catch (StartupException e){
            logger.error("ums startup failed during app preparation",e);
            return;
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        super.contextDestroyed(event);
    }

    public ServletContext getServletContext() {
        return servletContext;
    }
}
