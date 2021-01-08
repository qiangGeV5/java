package com.zq.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RestController
public class HelloController {

    @GetMapping("Hello")
    public Object hello(){
        return "HelloWordTest";
    }

    //
}
