package com.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author eric
 * @description
 * @date 2023/4/17
 */
@RestController
public class IndexController {
    @RequestMapping("/")
    public String index() {
        return "Hello WebRtc!";
    }
}
