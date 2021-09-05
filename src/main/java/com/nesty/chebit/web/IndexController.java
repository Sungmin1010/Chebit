package com.nesty.chebit.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/")
    public String index(){
        return "login";
    }

    //@GetMapping("/main")
    public String getMainSection(){
        return "index2";
    }

    @GetMapping("/join")
    public String join(){
        return "joinForm";
    }
}
