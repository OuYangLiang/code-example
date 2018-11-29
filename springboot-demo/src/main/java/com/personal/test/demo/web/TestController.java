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

    /**
     * /hello方法
     */
    @RequestMapping("/hello")
    public String hello() {
        return "hello";
    }

    /**
     * /db1方法
     */
    @RequestMapping("/db1/{id}")
    public String db1(final @PathVariable("id") int id) {
        return dao1.queryByKey(id);
    }

    /**
     * /db2方法
     */
    @RequestMapping("/db2/{id}")
    public String db2(final @PathVariable("id") int id) {
        return dao2.queryByKey(id);
    }

    /**
     * /exception方法
     */
    @RequestMapping("/exception")
    public String exception() {
        int a = 1 / 0;

        return Integer.toString(a);
    }
}
