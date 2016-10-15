package com.huawei.tsdn.sampleapp.ethsrv.model;

public class EthLinkInfo {
    private int linkKey;
    private int etag;
    private int role;
    private int neId;
    private int linkId;
    private PhysicalPort physicalPort;
    private int maxBw;
    private int reservBw;
    private String localIp;
    private String peerIp;
    private String interfaceModule;

    public int getLinkKey() {
        return linkKey;
    }

    public void setLinkKey(int linkKey) {
        this.linkKey = linkKey;
    }

    public int getEtag() {
        return etag;
    }

    public void setEtag(int etag) {
        this.etag = etag;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public int getNeId() {
        return neId;
    }

    public void setNeId(int neId) {
        this.neId = neId;
    }

    public int getLinkId() {
        return linkId;
    }

    public void setLinkId(int linkId) {
        this.linkId = linkId;
    }

    public PhysicalPort getPhysicalPort() {
        return physicalPort;
    }

    public void setPhysicalPort(PhysicalPort physicalPort) {
        this.physicalPort = physicalPort;
    }

    public int getMaxBw() {
        return maxBw;
    }

    public void setMaxBw(int maxBw) {
        this.maxBw = maxBw;
    }

    public int getReservBw() {
        return reservBw;
    }

    public void setReservBw(int reservBw) {
        this.reservBw = reservBw;
    }

    public String getLocalIp() {
        return localIp;
    }

    public void setLocalIp(String localIp) {
        this.localIp = localIp;
    }

    public String getPeerIp() {
        return peerIp;
    }

    public void setPeerIp(String peerIp) {
        this.peerIp = peerIp;
    }

    public String getInterfaceModule() {
        return interfaceModule;
    }

    public void setInterfaceModule(String interfaceModule) {
        this.interfaceModule = interfaceModule;
    }

    public EthLinkInfo() {
    }
}
