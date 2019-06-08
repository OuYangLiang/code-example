package com.personal.oyl.code.example.nettydemo.msgpack;

import org.msgpack.annotation.Message;

import java.io.Serializable;

/**
 * @author OuYang Liang
 * @since 2019-06-06
 */
@Message
public class UserInfo implements Serializable {

    private String userName;
    private int age;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "userName='" + userName + '\'' +
                ", age=" + age +
                '}';
    }
}
