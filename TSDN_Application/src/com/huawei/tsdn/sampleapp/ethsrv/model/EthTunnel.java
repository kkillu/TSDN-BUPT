package com.huawei.tsdn.sampleapp.ethsrv.model;

/**
 * 
 * Eth service info storing class
 * 
 * @author tWX301955
 *
 */
public class EthTunnel {

    private int srvType;
    private int srvId;
    private int eTag;
    private String srvName;
    private int sla;
    private long bandWidth;
    private long actBw;
    private int pir;
    private int status;
    private int adminStatus;
    private int planCreate;
    private int planDelete;
    private int realCreate;
    private EthTunnelPoint srcTunnelPoint;
    private EthTunnelPoint distTunnelPoint;
    private int latency;
    private int ethOam;
    private int etag;
    private String uuid;
    private int cfgStatus;
    private int ctrlOwner;
    
    public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public int getCfgStatus() {
		return cfgStatus;
	}

	public void setCfgStatus(int cfgStatus) {
		this.cfgStatus = cfgStatus;
	}

	public int getCtrlOwner() {
		return ctrlOwner;
	}

	public void setCtrlOwner(int ctrlOwner) {
		this.ctrlOwner = ctrlOwner;
	}

	public int getSrvType() {
        return srvType;
    }

    public void setSrvType(int srvType) {
        this.srvType = srvType;
    }

    public int getSrvId() {
        return srvId;
    }

    public void setSrvId(int srvId) {
        this.srvId = srvId;
    }

    public int geteTag() {
        return eTag;
    }

    public void seteTag(int eTag) {
        this.eTag = eTag;
    }

    public String getSrvName() {
        return srvName;
    }

    public void setSrvName(String srvName) {
        this.srvName = srvName;
    }

    public int getSla() {
        return sla;
    }

    public void setSla(int sla) {
        this.sla = sla;
    }

    public long getBandWidth() {
        return bandWidth;
    }

    public void setBandWidth(long bandWidth) {
        this.bandWidth = bandWidth;
    }

    public long getActBw() {
        return actBw;
    }

    public void setActBw(long actBw) {
        this.actBw = actBw;
    }

    public int getPir() {
        return pir;
    }

    public void setPir(int pir) {
        this.pir = pir;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getAdminStatus() {
        return adminStatus;
    }

    public void setAdminStatus(int adminStatus) {
        this.adminStatus = adminStatus;
    }

    public int getPlanCreate() {
        return planCreate;
    }

    public void setPlanCreate(int planCreate) {
        this.planCreate = planCreate;
    }

    public int getPlanDelete() {
        return planDelete;
    }

    public void setPlanDelete(int planDelete) {
        this.planDelete = planDelete;
    }

    public int getRealCreate() {
        return realCreate;
    }

    public void setRealCreate(int realCreate) {
        this.realCreate = realCreate;
    }

    public EthTunnelPoint getSrcTunnelPoint() {
        return srcTunnelPoint;
    }

    public void setSrcTunnelPoint(EthTunnelPoint srcTunnelPoint) {
        this.srcTunnelPoint = srcTunnelPoint;
    }

    public EthTunnelPoint getDistTunnelPoint() {
        return distTunnelPoint;
    }

    public void setDistTunnelPoint(EthTunnelPoint distTunnelPoint) {
        this.distTunnelPoint = distTunnelPoint;
    }

    public int getLatency() {
        return latency;
    }

    public void setLatency(int latency) {
        this.latency = latency;
    }

    public int getEthOam() {
        return ethOam;
    }

    public void setEthOam(int ethOam) {
        this.ethOam = ethOam;
    }

    public int getEtag() {
        return etag;
    }

    public void setEtag(int etag) {
        this.etag = etag;
    }

    public String toString() {
        return srvName + ";" + bandWidth + ";" + srcTunnelPoint.getNeId() + ";" + distTunnelPoint.getNeId();
    }

    public boolean isValid() {
        if ((srvType == 0) || (sla == 0) || (bandWidth == 0) || (srcTunnelPoint == null) || (distTunnelPoint == null) || (srvName == null)) {
            return false;
        }
        return true;
    }

    public static class EthTunnelPoint {
        private int nType;
        private long neId;
        private PhysicalPort physicalPort;
        private String portIdStr;
        private String neName;

        public int getnType() {
            return nType;
        }

        public void setnType(int nType) {
            this.nType = nType;
        }

        public long getNeId() {
            return neId;
        }

        public void setNeId(long neId) {
            this.neId = neId;
        }

        public PhysicalPort getPhysicalPort() {
            return physicalPort;
        }

        public void setPhysicalPort(PhysicalPort physicalPort) {
            this.physicalPort = physicalPort;
        }

        public String getPortIdStr() {
            return portIdStr;
        }

        public void setPortIdStr(String portIdStr) {
            this.portIdStr = portIdStr;
        }

        public String getNeName() {
            return neName;
        }

        public void setNeName(String neName) {
            this.neName = neName;
        }

        public EthTunnelPoint(int nType, int neId, PhysicalPort physicalPort) {
            this.nType = nType;
            this.neId = neId;
            this.physicalPort = physicalPort;
        }

        public EthTunnelPoint() {

        }

    }

}
