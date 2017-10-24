package com.worldpay.test;

import com.worldpay.test.resource.MerchantResource;
import com.worldpay.test.util.DBUtil;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {
    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) throws Exception {

        // Drop the table if it's already available.
        DBUtil.dropTable(DBUtil.getConnection());

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");

        Server jettyServer = new Server(8080);
        jettyServer.setHandler(context);

        ServletHolder jerseyServlet = context.addServlet(org.glassfish.jersey.servlet.ServletContainer.class, "/*");
        jerseyServlet.setInitOrder(0);

        // Tells the Jersey Servlet which REST service/class to load
        jerseyServlet.setInitParameter("jersey.config.server.provider.classnames", MerchantResource.class.getCanonicalName());

        DBUtil.createTable(DBUtil.getConnection());

        try {
            jettyServer.start();
            jettyServer.join();
        } catch (Exception e) {
            LOGGER.error("error during server starting", e);
            jettyServer.stop();
            jettyServer.destroy();
        }
    }
}
