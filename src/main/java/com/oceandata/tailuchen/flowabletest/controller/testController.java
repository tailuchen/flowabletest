package com.oceandata.tailuchen.flowabletest.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;




@Controller
@RequestMapping(value = "/test")
public class testController {

    @GetMapping("/t2")
    public String hello() {
        return "test2";
    }

    @GetMapping("/t3")
    public String hello3() {
        return "test3";
    }

}
