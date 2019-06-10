package com.personal.oyl.code.example.nettydemo.marshalling;

import java.io.Serializable;

/**
 * @author OuYang Liang
 * @since 2019-06-10
 */
public class Request implements Serializable {
    private static final long SerialVersionUID = 1L;
    private int subReqID;
    private String userName;
    private String productName;
    private String address;

    public int getSubReqID() {
        return subReqID;
    }

    public void setSubReqID(int subReqID) {
        this.subReqID = subReqID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Request{" +
                "subReqID=" + subReqID +
                ", userName='" + userName + '\'' +
                ", productName='" + productName + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
