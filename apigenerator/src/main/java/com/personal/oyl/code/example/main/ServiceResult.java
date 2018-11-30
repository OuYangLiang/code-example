package com.personal.oyl.code.example.main;

import java.util.LinkedList;
import java.util.List;

public class ServiceResult {
    private String serviceName;
    private String serviceDesc;
    private List<MethodResult> methods;

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceDesc() {
        return serviceDesc;
    }

    public void setServiceDesc(String serviceDesc) {
        this.serviceDesc = serviceDesc;
    }

    public List<MethodResult> getMethods() {
        return methods;
    }

    public void setMethods(List<MethodResult> methods) {
        this.methods = methods;
    }
    
    public void addMethodResult(MethodResult result) {
        if (null == methods) {
            methods = new LinkedList<>();
        }
        
        methods.add(result);
    }

    @Override
    public String toString() {
        return "ServiceResult [serviceName=" + serviceName + ", serviceDesc=" + serviceDesc + ", methods=" + methods
                + "]";
    }

}
