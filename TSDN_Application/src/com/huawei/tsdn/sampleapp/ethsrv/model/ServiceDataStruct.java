package com.huawei.tsdn.sampleapp.ethsrv.model;

/**
 * Eth service form receiving and storing class
 * 
 * @author tWX301955
 *
 */
public final class ServiceDataStruct {
    private String ServiceName;// service name
    private long ingress;// service source node
    private String srcPort;// service source port,expressed in string
    private long egress;// service dest node
    private String distPort;// service dest port,expressed in string
    private long bandWidth;// bandwidth
    private byte serviceType;// service type

    private int srvId;
    private int pir;
    private String uuid;

    public String getServiceName() {
        return ServiceName;
    }

    public void setServiceName(String ServiceName) {
        this.ServiceName = ServiceName;
    }

    public byte getServiceType() {
        return serviceType;
    }

    public void setServiceType(byte serviceType) {
        this.serviceType = serviceType;
    }

    public long getIngress() {
        return ingress;
    }

    public void setIngress(long ingress) {
        this.ingress = ingress;
    }

    public String getSrcPort() {
        return srcPort;
    }

    public void setSrcPort(String srcPort) {
        this.srcPort = srcPort;
    }

    public long getEgress() {
        return egress;
    }

    public void setEgress(long egress) {
        this.egress = egress;
    }

    public String getDistPort() {
        return distPort;
    }

    public void setDistPort(String distPort) {
        this.distPort = distPort;
    }

    public long getBandWidth() {
        return bandWidth;
    }

    public void setBandWidth(long bandWidth) {
        this.bandWidth = bandWidth;
    }

    public int getSrvId() {
        return srvId;
    }

    public void setSrvId(int srvId) {
        this.srvId = srvId;
    }

    public int getPir() {
        return pir;
    }

    public void setPir(int pir) {
        this.pir = pir;
    }

    public ServiceDataStruct() {
    }

    public ServiceDataStruct(String ServiceName, long ingress, String srcPort, long egress, String distPort,
            long bandWidth, byte serviceType) {
        this.ServiceName = ServiceName;
        this.ingress = ingress;
        this.srcPort = srcPort;
        this.egress = egress;
        this.distPort = distPort;
        this.bandWidth = bandWidth;
        this.serviceType = serviceType;
    }

    public String toString() {
        return "ServiceDataStruct [ServiceName=" + ServiceName + ", ingress=" + ingress + ", srcPort=" + srcPort
                + ", egress=" + egress + ", distPort=" + distPort + ", bandWidth=" + bandWidth + ", serviceType="
                + serviceType + ", srv_id=" + srvId + ", pir=" + pir + ",uuid="+uuid+"]";
    }

    public boolean isValid() {
        boolean isValid = false;
        if ((ServiceName == null) || (ingress == 0) || (srcPort == null) || (egress == 0) || (distPort == null)
                || (bandWidth == 0) || (serviceType == 0)) {
            return isValid;
        }
        if ((!checkPortIsValid(srcPort)) || (!checkPortIsValid(distPort))) {
            return isValid;
        }
        return true;
    }
    
    public boolean isModifyValid() {
        boolean isValid = false;
        if ((ServiceName == null)  
                || (bandWidth == 0) || "".equals(uuid) ) {
            return isValid;
        }
   
        return true;
    }


    /**
     * check the input port string,input such as(3-1 or 1-3-1)
     * 
     * @param portString
     *            input port string
     * @return isvalid
     */
    private boolean checkPortIsValid(String portString) {
        String[] tmp = portString.split("-");
        int portMinLen = 2;
        int portMaxLen = 3;
        if (tmp.length < portMinLen || tmp.length > portMaxLen) {
            return false;
        }
        for (String value : tmp) {
            if ((value.isEmpty()) || !(value.matches("\\d+"))) {
                return false;
            }
        }
        return true;
    }

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
}
