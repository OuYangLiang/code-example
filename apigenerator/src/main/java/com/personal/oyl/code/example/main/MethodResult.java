package com.personal.oyl.code.example.main;

import java.util.LinkedList;
import java.util.List;

public class MethodResult {
    private String methodName;
    private String methodDesc;
    private String returnType;
    private List<MethodParameter> parameters;
    private List<String> exceptions;

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getMethodDesc() {
        return methodDesc;
    }

    public void setMethodDesc(String methodDesc) {
        this.methodDesc = methodDesc;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public List<MethodParameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<MethodParameter> parameters) {
        this.parameters = parameters;
    }
    
    public void addParameter(MethodParameter parameter) {
        if (null == parameters) {
            parameters = new LinkedList<>();
        }
        parameters.add(parameter);
    }

    public List<String> getExceptions() {
        return exceptions;
    }

    public void setExceptions(List<String> exceptions) {
        this.exceptions = exceptions;
    }
    
    public void addException(String exception) {
        if (null == exceptions) {
            exceptions = new LinkedList<>();
        }
        exceptions.add(exception);
    }

    @Override
    public String toString() {
        return "MethodResult [methodName=" + methodName + ", methodDesc=" + methodDesc + ", returnType=" + returnType
                + ", parameters=" + parameters + ", exceptions=" + exceptions + "]";
    }
    
    public static class MethodParameter {
        private String paramName;
        private String paramDesc;
        private String paramType;
        private boolean mandatory;

        public String getParamName() {
            return paramName;
        }

        public void setParamName(String paramName) {
            this.paramName = paramName;
        }

        public String getParamDesc() {
            return paramDesc;
        }

        public void setParamDesc(String paramDesc) {
            this.paramDesc = paramDesc;
        }

        public String getParamType() {
            return paramType;
        }

        public void setParamType(String paramType) {
            this.paramType = paramType;
        }

        public boolean isMandatory() {
            return mandatory;
        }

        public void setMandatory(boolean mandatory) {
            this.mandatory = mandatory;
        }

        @Override
        public String toString() {
            return "MethodParameter [paramName=" + paramName + ", paramDesc=" + paramDesc + ", paramType=" + paramType
                    + ", mandatory=" + mandatory + "]";
        }

    }

}
