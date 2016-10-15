/*
 *this is tool class.this class can read config from
 *the file 
 */
package com.huawei.tsdn.sampleapp.util;

import java.io.FileInputStream;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.apache.logging.log4j.LogManager;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XmlLoaderUtil {

    final static org.apache.logging.log4j.Logger log = LogManager.getLogger();
    private static XPath path;
    private static DocumentBuilder docmentBuilder = null;  

    static {
        try {
            docmentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            log.error("ParserConfigurationException" + e.getMessage());
        }
        path = XPathFactory.newInstance().newXPath();
    }

    /**
     * load xml info to storage and parse config info
     * 
     * @param configXmlPath
     */
    public static Document LoaderXmlToStorage(String configXmlPath) {
        FileInputStream fileInputStream = null;
        Document doc = null;
        try {
            fileInputStream = new FileInputStream(configXmlPath);
            doc = docmentBuilder.parse(fileInputStream);
        } catch (SAXException e) {
            log.error("SAXException" + e.getMessage());
        } catch (IOException e) {
            log.error("io error" + e.getMessage());
        }

     //   parseServerConfig();
        if (fileInputStream != null) {
            try {
                fileInputStream.close();
            } catch (IOException e) {
                log.error(e.getMessage());
            } finally {
                fileInputStream = null;
            }
        }
        return doc;
    }

    /**
     * get the node list by expression
     * 
     * @param node
     * @param expression
     * @return
     */
    public static NodeList getlist(Object node, String expression) {
        try {
            return (NodeList) path.evaluate(expression, node, XPathConstants.NODESET);
        } catch (XPathExpressionException e) {
            log.error("get node list failed." + e.getMessage());
        }
        return null;
    }

    /**
     * get node content
     * 
     * @param node
     * @param expression
     * @return
     */
    public static String getString(Object node, String expression) {
        try {
            return (String) path.evaluate(expression, node, XPathConstants.STRING);
        } catch (XPathExpressionException e) {
            log.error("get node string failed." + e.getMessage());
        }
        return null;
    }

  
}
