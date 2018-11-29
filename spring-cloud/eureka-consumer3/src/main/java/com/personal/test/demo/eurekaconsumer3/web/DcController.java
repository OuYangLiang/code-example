package com.personal.test.demo.eurekaconsumer3.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.personal.test.demo.eurekaconsumer3.service.DcClient;


@RestController
public class DcController {

    @Autowired
    DcClient dcClient;

    @GetMapping("/consumer")
    public String dc() {
        return "From consumer3: " + dcClient.consumer();
    }
}