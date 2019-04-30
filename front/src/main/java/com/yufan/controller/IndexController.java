package com.yufan.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexController {

    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String page(){
        return "index/index";
    }

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String topage(){
        return "index/index";
    }
}
