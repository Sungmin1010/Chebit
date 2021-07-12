package com.nesty.chebit.web;

import com.nesty.chebit.web.dto.HelloResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello(){
        return "Hello";
    }

    @GetMapping("/hello/dto")
    public HelloResponseDto helloDto(@RequestParam String name, @RequestParam int amount){
        return new HelloResponseDto(name, amount);
    }


}
