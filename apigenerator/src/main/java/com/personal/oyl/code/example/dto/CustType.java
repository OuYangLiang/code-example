package com.personal.oyl.code.example.dto;

import com.personal.oyl.code.example.annotation.DESC;

@DESC("客户类型")
public enum CustType {
    @DESC("司机")
    type_1("司机"),
    @DESC("货主")
    type_2("货主");
    
    private String desc;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    private CustType(String desc) {
        this.desc = desc;
    }
    
}
