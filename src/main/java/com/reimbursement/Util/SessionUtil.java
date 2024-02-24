package com.reimbursement.Util;

import org.eclipse.jetty.http.HttpCookie;
import org.eclipse.jetty.server.session.SessionHandler;
import org.eclipse.jetty.server.session.DatabaseAdaptor;
import org.eclipse.jetty.server.session.DefaultSessionCache;
import org.eclipse.jetty.server.session.JDBCSessionDataStoreFactory;
import org.eclipse.jetty.server.session.SessionCache;
import java.util.function.Supplier;
import java.util.Properties;
import java.io.IOException;
import db.Database;

public class SessionUtil {

    private static Properties prop = new Properties();
    private static String url;
    static {
        try {
            prop.load(SessionUtil.class.getClassLoader().getResourceAsStream("db.properties"));
            url = prop.getProperty("url");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Supplier<SessionHandler> supplier = () -> sqlSessionHandler("org.postgresql.ds.PGSimpleDataSource", url);

    public static SessionHandler sqlSessionHandler(String driver, String url) {
        SessionHandler sessionHandler = new SessionHandler();
        SessionCache sessionCache = new DefaultSessionCache(sessionHandler);
        sessionCache.setSessionDataStore(
            jdbcDataStoreFactory(driver, url).getSessionDataStore(sessionHandler)
        );
        sessionHandler.setSessionCache(sessionCache);
        sessionHandler.setHttpOnly(true);
        sessionHandler.setSameSite(HttpCookie.SameSite.STRICT);
        sessionHandler.setSecureRequestOnly(true);
        sessionHandler.setMaxInactiveInterval(1800);
        return sessionHandler;
    }

    private static JDBCSessionDataStoreFactory jdbcDataStoreFactory(String driver, String url) {
        DatabaseAdaptor databaseAdaptor = new DatabaseAdaptor();
        databaseAdaptor.setDriverInfo(driver, url);
        databaseAdaptor.setDatasource(Database.getDataSource());
        JDBCSessionDataStoreFactory jdbcSessionDataStoreFactory = new JDBCSessionDataStoreFactory();
        jdbcSessionDataStoreFactory.setDatabaseAdaptor(databaseAdaptor);
        return jdbcSessionDataStoreFactory;
    }
}
