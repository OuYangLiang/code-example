package com.personal.oyl.code.example.service;

import java.sql.SQLException;

import com.personal.oyl.code.example.annotation.DESC;
import com.personal.oyl.code.example.annotation.SERVICE;
import com.personal.oyl.code.example.dto.Cust;
import com.personal.oyl.code.example.dto.CustType;

@SERVICE("会员服务")
public interface CustomerService {
    
    @DESC("根据ID查询客户")
    Cust queryByKey(@DESC(value="客户ID", mandatory=true) long custId) throws SQLException;
    
    @DESC("更改客户名称")
    void updateCustName(@DESC(value="客户ID", mandatory=true) long custId, @DESC(value="新名称", mandatory=true) String newName) throws IllegalArgumentException;
    
    @DESC("更改客户类型")
    void changeType(@DESC(value="客户ID", mandatory=true) long custId, @DESC(value="客户类型", mandatory=true) CustType newType) throws IllegalArgumentException, SQLException;
}
