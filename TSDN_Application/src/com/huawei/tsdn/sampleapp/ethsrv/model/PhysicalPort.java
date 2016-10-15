package com.huawei.tsdn.sampleapp.ethsrv.model;

public class PhysicalPort {
    private int shelfId;
    private int boardId;
    private int subCardId;
    private int portId;

    public int getShelfId() {
        return shelfId;
    }

    public void setShelfId(int shelfId) {
        this.shelfId = shelfId;
    }

    public int getBoardId() {
        return boardId;
    }

    public void setBoardId(int boardId) {
        this.boardId = boardId;
    }

    public int getSubCardId() {
        return subCardId;
    }

    public void setSubCardId(int subCardId) {
        this.subCardId = subCardId;
    }

    public int getPortId() {
        return portId;
    }

    public void setPortId(int portId) {
        this.portId = portId;
    }

    public PhysicalPort(int shelfId, int boardId, int subCardId, int portId) {
        super();
        this.shelfId = shelfId;
        this.boardId = boardId;
        this.subCardId = subCardId;
        this.portId = portId;
    }

    public PhysicalPort() {

    }
}
