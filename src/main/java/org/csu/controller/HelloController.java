package org.csu.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lixiang on 2017 07 09 下午11:09.
 */
@RestController
public class HelloController {

    @GetMapping("hello")
    public Object hello() {
        return "hello world, csu-cache";
    }
}
