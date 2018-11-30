package com.personal.oyl.code.example.dto;

import com.personal.oyl.code.example.annotation.DESC;

public class CommonID {
    @DESC("客户ID")
    private Long custId;

    public Long getCustId() {
        return custId;
    }

    public void setCustId(Long custId) {
        this.custId = custId;
    }

}
