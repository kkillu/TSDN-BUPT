package com.huawei.tsdn.sampleapp.ethsrv.model;

import java.util.ArrayList;
import java.util.List;

public class EthNodeInfo {
    private long neId;
    private int status;
    private String neName;
    private List<String> portInfoStr;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public EthNodeInfo(long neId, String neName) {
        this.neId = neId;
        this.neName = neName;
        this.portInfoStr = new ArrayList<String>();
    }

    public EthNodeInfo() {

    }

    public long getNeId() {
        return neId;
    }

    public void setNeId(long neId) {
        this.neId = neId;
    }

    public String getNeName() {
        return neName;
    }

    public void setNeName(String neName) {
        this.neName = neName;
    }

    public List<String> getPortInfoStr() {
        return portInfoStr;
    }

}
