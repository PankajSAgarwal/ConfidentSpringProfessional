package com.pankaj.myfancypdfinvoices;

import com.pankaj.myfancypdfinvoices.context.ApplicationConfiguration;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.Wrapper;
import org.apache.catalina.startup.Tomcat;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;

public class ApplicationLauncher {

    public static void main(String[] args) throws LifecycleException {

        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);
        tomcat.getConnector();
        Context tomcatCtx = tomcat.addContext("", null);
        WebApplicationContext appCtx = createapplicationContext(tomcatCtx.getServletContext());
        DispatcherServlet dispatcherServlet = new DispatcherServlet(appCtx);
        /*Wrapper servlet = Tomcat.addServlet(ctx, "myFirstServlet", new MyFancyPdfInvoiceServlet());*/
        Wrapper servlet = Tomcat.addServlet(tomcatCtx,"dispatcherServlet",dispatcherServlet);
        servlet.setLoadOnStartup(1);
        servlet.addMapping("/*");
        tomcat.start();
    }

    private static WebApplicationContext createapplicationContext(ServletContext servletContext) {
        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
        ctx.register(ApplicationConfiguration.class);
        ctx.setServletContext(servletContext);
        ctx.refresh();
        ctx.registerShutdownHook();
        return ctx;
    }
}
