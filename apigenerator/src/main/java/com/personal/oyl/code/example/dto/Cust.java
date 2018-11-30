package com.personal.oyl.code.example.dto;

import com.personal.oyl.code.example.annotation.DESC;

@DESC("客户实体")
public class Cust extends CommonID {
    @DESC("客户名称")
    private String custName;
    @DESC("客户类型")
    private CustType type;
    @DESC("关联客户")
    private Cust relative;

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public CustType getType() {
        return type;
    }

    public void setType(CustType type) {
        this.type = type;
    }

    public Cust getRelative() {
        return relative;
    }

    public void setRelative(Cust relative) {
        this.relative = relative;
    }
}
