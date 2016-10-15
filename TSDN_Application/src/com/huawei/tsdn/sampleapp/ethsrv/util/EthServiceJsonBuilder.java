package com.huawei.tsdn.sampleapp.ethsrv.util;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.huawei.tsdn.sampleapp.ethsrv.model.EthTunnel;
import com.huawei.tsdn.sampleapp.ethsrv.model.PhysicalPort;
import com.huawei.tsdn.sampleapp.ethsrv.model.EthTunnel.EthTunnelPoint;
import com.huawei.tsdn.sampleapp.util.JsonCodec;

public class EthServiceJsonBuilder extends JsonCodec<EthTunnel> {
	
    private final String SHELFT_STRING = "shelf_id";
    private final String BOARD_STRING = "board_id";
    private final String CARD_STRING = "sub_card_id";
    private final String PORT_STRING = "port_id";
    private final String PHSICAL_PORT_STRING = "physical_port";
    private final String UNI_TYPE = "type";
    private final String NE_ID = "ne_id";
    private final String SLA = "sla";
    private final Logger log = LogManager.getLogger();
    private final String SRV_TYPE = "srv_type";
    private final String SRV_NAME = "srv_name";
    private final String BW = "bw";
    private final String PIR = "pir";
    private final String SRV_LIST = "srv_list";
    private final String ETH_UNI = "uni";
    private final String SRV_ID = "srv_id";
    private final int BANDWIDTH_THOUSAND = 1000;
    
    private final String UUID = "uuid";
    private final String CFG_STATUS = "cfg_status";
    private final String CTRL_OWNER = "ctrl_owner";

    public ObjectNode encode(EthTunnel entity) {
        ObjectNode srvBody = mapper().createObjectNode();

        /**********************************
         * Program tips
         *********************************/
        /**
         * 1��Put service type,service name,SLA��bw��pir into srvBody according to
         * their sequences in API 
         * 2��Get service type(default 1),service name,SLA��bw,you can get these from serviceData 
         * 3��BW,PIR should be amplified 1000 times, such as serviceData.getBandWidth() * 1000��
         * 4��Example:srvBody.put("srv_type", 1);service type in default is 1
         */
        srvBody.put(SRV_TYPE, 1);
        srvBody.put(SRV_NAME, entity.getSrvName());
        srvBody.put(SLA, entity.getSla());
        srvBody.put(BW, entity.getBandWidth());
        srvBody.put(PIR, entity.getPir());
        srvBody.putArray(ETH_UNI).addAll(encodeUniNode(entity));
        if (entity.getSrvId() != 0) {
            srvBody.put(SRV_ID, entity.getSrvId());
        }
        return srvBody;
    }

    public ObjectNode encodeModify(EthTunnel entity) {
        ObjectNode srvBody = mapper().createObjectNode();
        srvBody.put(SRV_NAME, entity.getSrvName());
        srvBody.put(SLA, entity.getSla());
        srvBody.put(BW, entity.getBandWidth());
        srvBody.put(PIR, entity.getBandWidth());
        return srvBody;
    }
    
    public ObjectNode encodeCreateTunnelJson(EthTunnel entity) {
        ObjectNode jsonNode = mapper().createObjectNode();
        ArrayNode srvList = mapper().createArrayNode();
        srvList.add(encode(entity));
        jsonNode.putArray(SRV_LIST).addAll(srvList);
        return jsonNode;
    }

    public ObjectNode encodeModifyTunnelJson(EthTunnel entity) {
        ObjectNode jsonNode = mapper().createObjectNode();
        ArrayNode srvList = mapper().createArrayNode();
        srvList.add(encodeModify(entity));
        jsonNode.putArray(SRV_LIST).addAll(srvList);
        return jsonNode;
    }
    
    public String encodeUpdateTunnelJson(EthTunnel entity) {
        return null;
    }

    /**
     * decode physicalPort from json String
     * 
     * @param jsonNode
     * @return
     */
    private PhysicalPort decodePhysicalPort(JsonNode jsonNode) {

        int shelfId = jsonNode.get(SHELFT_STRING).asInt();
        int boardId = jsonNode.get(BOARD_STRING).asInt();
        int subCardId = jsonNode.get(CARD_STRING).asInt();
        int portId = jsonNode.get(PORT_STRING).asInt();

        PhysicalPort physicalPort = new PhysicalPort(shelfId, boardId, subCardId, portId);
        return physicalPort;
    }

    /**
     * encode eth uni array
     * 
     * @param entity
     *            entity to encode
     * @return uni array node
     */
    public ArrayNode encodeUniNode(EthTunnel entity) {
        ArrayNode uni = mapper().createArrayNode();
        ObjectNode srcUniNode = encodeEthUniNode(entity.getSrcTunnelPoint());
        ObjectNode dstUniNode = encodeEthUniNode(entity.getDistTunnelPoint());
        uni.add(srcUniNode);
        uni.add(dstUniNode);
        return uni;
    }

    /**
     * encode single uni
     * 
     * @param tunnelPoint
     *            entity to encode
     * @return uni json
     */
    public ObjectNode encodeEthUniNode(EthTunnelPoint tunnelPoint) {
        ObjectNode uniNode = mapper().createObjectNode();
        ObjectNode physicalPortNode = encodeEthTunnelPoint(tunnelPoint.getPhysicalPort());
        uniNode.put(UNI_TYPE, 0);
        uniNode.put(NE_ID, tunnelPoint.getNeId());
        uniNode.putPOJO(PHSICAL_PORT_STRING, physicalPortNode);
        return uniNode;
    }

    /**
     * encode to get json node
     * 
     * @param portString
     * @return
     */
    private ObjectNode encodeEthTunnelPoint(PhysicalPort physicalPort) {
        ObjectNode physicalPortJsonNode = mapper().createObjectNode();
        physicalPortJsonNode.put(SHELFT_STRING, physicalPort.getShelfId());
        physicalPortJsonNode.put(BOARD_STRING, physicalPort.getBoardId());
        physicalPortJsonNode.put(CARD_STRING, physicalPort.getSubCardId());
        physicalPortJsonNode.put(PORT_STRING, physicalPort.getPortId());
        return physicalPortJsonNode;
    }

    /**
     * decode to get EthTunnelPoint
     * 
     * @param jsonNode
     * @return
     */
    private EthTunnelPoint decodeEthTunnelPoint(JsonNode jsonNode) {
        long neId = jsonNode.path(NE_ID).asLong();
        JsonNode physPortNode = jsonNode.path(PHSICAL_PORT_STRING);
        PhysicalPort physicalPort = decodePhysicalPort(physPortNode);
        EthTunnelPoint tunnelPoint = new EthTunnelPoint();
        StringBuilder builder = new StringBuilder();        
        builder.append(physicalPort.getShelfId() + "-");
        builder.append(physicalPort.getBoardId() + "-" + physicalPort.getPortId());
        tunnelPoint.setPortIdStr(builder.toString());
        tunnelPoint.setNeId(neId);
        tunnelPoint.setNeName(EthTopologyReadUtil.getNeNameByNeId(neId));
        return tunnelPoint;
    }

    /**
     * parse the struct to send response
     * 
     * @param responseJson
     */
    public List<EthTunnel> parserJSON(String responseJson) {
        List<EthTunnel> result = null;
        JsonNode root = null;
        try {
            root = mapper().readTree(responseJson);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        JsonNode srvList = root.path(SRV_LIST);
        if ((srvList != null) && (srvList.isArray())) {
            result = decode((ArrayNode) srvList);
            log.info(srvList.size());
        }

        return result;
    }

    /**
     * decode one tunnel from json
     */
    public EthTunnel decode(ObjectNode srvBody) {
        if (srvBody == null) {
            return null;
        }
        EthTunnel ethTunnel = new EthTunnel();
        int type = srvBody.path(SRV_TYPE).asInt();
        ethTunnel.setSrvType(type);

        if (srvBody.path(SRV_ID) != null) {
            ethTunnel.setSrvId(srvBody.path(SRV_ID).asInt());
        }
        
        if (srvBody.path(UUID) != null){
        	ethTunnel.setUuid(srvBody.path(UUID).asText());
        }
        
        if (srvBody.path(CFG_STATUS) != null){
        	ethTunnel.setCfgStatus(srvBody.path(CFG_STATUS).asInt());
        }
        
        if (srvBody.path(CTRL_OWNER) != null){
        	ethTunnel.setCtrlOwner(srvBody.path(CTRL_OWNER).asInt());
        }
        
        if (srvBody.path(SRV_NAME) != null) {
            ethTunnel.setSrvName(srvBody.path(SRV_NAME).asText());
        }
        if (srvBody.path(SLA) != null) {
            ethTunnel.setSla(srvBody.path(SLA).asInt());
        }
        if (srvBody.path(BW) != null) {
            ethTunnel.setBandWidth(srvBody.path(BW).asInt() / BANDWIDTH_THOUSAND);
        }

        JsonNode uni = srvBody.path(ETH_UNI);
        if (uni != null) {
            if (uni.isArray()) {
                EthTunnelPoint srcEthPoint = decodeEthTunnelPoint(uni.get(0));
                EthTunnelPoint dstEthPoint = decodeEthTunnelPoint(uni.get(1));
                ethTunnel.setSrcTunnelPoint(srcEthPoint);
                ethTunnel.setDistTunnelPoint(dstEthPoint);
            }
        }

        if (!ethTunnel.isValid()) {
            return null;
        }

        return ethTunnel;
    }
}
