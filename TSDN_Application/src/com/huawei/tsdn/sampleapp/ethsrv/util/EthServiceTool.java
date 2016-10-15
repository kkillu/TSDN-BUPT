package com.huawei.tsdn.sampleapp.ethsrv.util;


import org.apache.logging.log4j.LogManager;

import com.huawei.tsdn.sampleapp.ethsrv.model.EthTunnel;
import com.huawei.tsdn.sampleapp.ethsrv.model.EthTunnel.EthTunnelPoint;
import com.huawei.tsdn.sampleapp.ethsrv.model.PhysicalPort;
import com.huawei.tsdn.sampleapp.ethsrv.model.ServiceDataStruct;

/**
 * encode and decode tool class
 * 
 * @author tWX301955
 *
 */
public class EthServiceTool {
	
    // define user input structs, such as service node and link struct
    private static final org.apache.logging.log4j.Logger log = LogManager.getLogger();
    private static final int BANDWIDTH_THOUSAND = 1000;
    private static final int DEFAULT_SUB_CARD = 0;
    private static final int DEFAULT_SHELF_NO = 0;
    private static final int PHYSICAL_PORT_MIN_LEN = 2;

    public static EthTunnel translateServiceData(ServiceDataStruct serviceData) {
        EthTunnel entity = new EthTunnel();
        entity.setBandWidth(serviceData.getBandWidth() * BANDWIDTH_THOUSAND);
        entity.setPir(serviceData.getPir() * BANDWIDTH_THOUSAND);
        entity.setSrvName(serviceData.getServiceName());
        entity.setSrvType(serviceData.getServiceType());
        entity.setSla(serviceData.getServiceType());
        entity.setSrcTunnelPoint(buildTunnelPoint(serviceData.getIngress(), serviceData.getSrcPort()));
        entity.setDistTunnelPoint(buildTunnelPoint(serviceData.getEgress(), serviceData.getDistPort()));
        return entity;
    }
    
    public static EthTunnel translateEditServiceData(ServiceDataStruct serviceData) {
        EthTunnel entity = new EthTunnel();
        entity.setBandWidth(serviceData.getBandWidth() * BANDWIDTH_THOUSAND);
        entity.setSrvName(serviceData.getServiceName());
        entity.setSrvType(serviceData.getServiceType());
        entity.setSla(serviceData.getServiceType());
        entity.setSrvId(serviceData.getSrvId());
        return entity;
    }

    private static PhysicalPort getPortInfoFromString(String portString) {
        PhysicalPort physicalPort = new PhysicalPort();
        String[] tmp = portString.split("-");
        
        if (tmp.length < PHYSICAL_PORT_MIN_LEN) {
            return null;
        }
        int index = 0;
        if (tmp.length > PHYSICAL_PORT_MIN_LEN) {
            physicalPort.setShelfId(Integer.parseInt(tmp[index++]));
        } else {
            physicalPort.setShelfId(DEFAULT_SHELF_NO);
        }

        physicalPort.setSubCardId(DEFAULT_SUB_CARD);
        physicalPort.setBoardId(Integer.parseInt(tmp[index++]));
        physicalPort.setPortId(Integer.parseInt(tmp[index]));
        return physicalPort;
    }

    private static EthTunnelPoint buildTunnelPoint(long neId, String portString) {
        EthTunnelPoint buildPoint = new EthTunnelPoint();
        buildPoint.setNeId(neId);
        buildPoint.setPhysicalPort(getPortInfoFromString(portString));
        return buildPoint;
    }
}
