package com.huawei.tsdn.sampleapp.ethsrv.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.huawei.tsdn.sampleapp.ethsrv.model.EthNodeInfo;
import com.huawei.tsdn.sampleapp.util.XmlLoaderUtil;


public class EthTopologyReadUtil {
	
    private static Map<Long, EthNodeInfo> nodeList = new HashMap<>();

    public static void LoaderTopologyInfo(String absolutePath) {
        Document doc = XmlLoaderUtil.LoaderXmlToStorage(absolutePath);
        if (doc != null) {
            NodeList nodeList = XmlLoaderUtil.getlist(doc, "topologyconfig/neconfig");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node configNode = nodeList.item(i);
                parseNeConfigInfo(configNode);
            }
        }
    }

    /**
     * read from node config info
     * 
     * @param configNode
     */
    private static void parseNeConfigInfo(Node configNode) {
        String neIdStr = XmlLoaderUtil.getString(configNode, "neid");
        long neId = 0;
        if (neIdStr != null) {
            neId = Long.parseLong(neIdStr);
        }

        EthNodeInfo ethNode = new EthNodeInfo(neId, XmlLoaderUtil.getString(configNode, "nename"));

        NodeList portNodeList =XmlLoaderUtil.getlist(configNode, "ports/port");
        for (int i = 0; i < portNodeList.getLength(); i++) {
            ethNode.getPortInfoStr().add(portNodeList.item(i).getTextContent());
        }
        nodeList.put(neId, ethNode);
    }

    /**
     * convert the xml config info to json string
     * 
     * @return json string
     */
    public static String buildConfigJsonString() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode topConfigNode = mapper.createObjectNode();
        ArrayNode neListNode = mapper.createArrayNode();
        for (EthNodeInfo oneNode : nodeList.values()) {
            ObjectNode neNodeObj = mapper.createObjectNode();
            neNodeObj.put("neId", oneNode.getNeId());
            neNodeObj.put("neName", oneNode.getNeName());
            ArrayNode portListNode = mapper.createArrayNode();
            List<String> portInfoList = oneNode.getPortInfoStr();
            for (int j = 0; j < portInfoList.size(); j++) {
                String portStr = portInfoList.get(j);
                ObjectNode portJsonNode = mapper.createObjectNode();
                portJsonNode.put("port", portStr);
                portListNode.add(portJsonNode);
            }
            neNodeObj.putArray("portList").addAll(portListNode);
            neListNode.add(neNodeObj);
        }
        topConfigNode.putArray("nodeList").addAll(neListNode);
        return topConfigNode.toString();
    }

    /**
     * get ne name
     * 
     * @param neId
     * @return
     */
    public static String getNeNameByNeId(long neId) {
        EthNodeInfo nodeInfo = nodeList.get(neId);
        if (nodeInfo != null) {
            return nodeInfo.getNeName();
        }
        return null;
    }
}
