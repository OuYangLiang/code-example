package com.personal.test.demo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.personal.test.demo.mod1.dao.Test1Dao;
import com.personal.test.demo.mod2.dao.Test2Dao;

@RestController
public class TestController {
    
    @Autowired
    private Test1Dao dao1;
    
    @Autowired
    private Test2Dao dao2;
    
    @RequestMapping("/hello")
    public String hello() {
        return "hello";
    }
    
    @RequestMapping("/db1/{id}")
    public String db1(@PathVariable("id") int id) {
        return dao1.queryByKey(id);
    }
    
    @RequestMapping("/db2/{id}")
    public String db2(@PathVariable("id") int id) {
        return dao2.queryByKey(id);
    }
}
