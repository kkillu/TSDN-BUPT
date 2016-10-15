package com.huawei.tsdn.sampleapp.util;

import java.io.File;
import java.net.URL;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.w3c.dom.Document;


public class GlobalResourceLoaderServlet extends HttpServlet {
    private static String serverAddress;
    private static String userName;
    private static String passWord;

    @Override
    /**
     * load log4j and global sdn config
     */
    public void init(ServletConfig config) throws ServletException {
        String log4jConfigPath = config.getInitParameter("log4jConfiguration");
        String tsdnConfig = config.getInitParameter("tsdnConfiguration");
        URL url = GlobalResourceLoaderServlet.class.getResource("/" + log4jConfigPath);
        LoggerContext context = (LoggerContext) LogManager.getContext(false);
        File file = new File(url.getPath());
        // this will force a reconfiguration
        context.setConfigLocation(file.toURI());
        parseServerConfig(GlobalResourceLoaderServlet.class.getResource("/" + tsdnConfig).getPath());
        super.init(config);
    }

    /**
     * pasre the config info from Document
     */
    private void parseServerConfig(String resourcePath) {
        Document doc = XmlLoaderUtil.LoaderXmlToStorage(resourcePath);
        if (doc != null) {
            serverAddress = XmlLoaderUtil.getString(doc, "tsdnconfig/serveradd");
            userName = XmlLoaderUtil.getString(doc, "tsdnconfig/username");
            passWord = XmlLoaderUtil.getString(doc, "tsdnconfig/password");
        }
    }

    public static String getServerAddress() {
        return serverAddress;
    }

    public static String getUserName() {
        return userName;
    }

    public static String getPassWord() {
        return passWord;
    }
}
