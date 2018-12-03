package com.personal.test.demo.web;

import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.personal.test.demo.mod1.dao.Test1Dao;
import com.personal.test.demo.mod2.dao.Test2Dao;

@RestController
public class TestController {

    @Autowired
    private Test1Dao dao1;

    @Autowired
    private Test2Dao dao2;

    @RequestMapping("/hello/{id}")
    public Result getResult(@PathVariable long id, @RequestParam(required = false, value = "name") String name,
            HttpServletRequest req, HttpServletResponse resp) {

        Result rlt = new Result();
        rlt.setId(id);
        rlt.setUserName("欧阳亮");
        rlt.setEmail("ouyanggod@gmail");
        rlt.setDate(new Date());

        if (name != null) {
            rlt.setUserName(name);
        }

        resp.addHeader("addedHeader", "abcdefg");
        resp.addCookie(new Cookie("addedCookie", "12345"));

        return rlt;
    }

    @RequestMapping("/people")
    public People getResult(@RequestBody People p) {
        People rlt = new People();
        rlt.setName(p.getName());
        rlt.setAge(1 + p.getAge());
        return rlt;
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

    static class Result {
        private Long id;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        private String userName;
        private String email;
        private Date date;
    }
}
