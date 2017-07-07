package com.kevinm416;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("controller2")
public class Controller2 {

    @RequestMapping(
            value = "/endpoint1",
            method = RequestMethod.GET)
    @ResponseBody
    public void getMyClass(@RequestBody MyClass request) {

    }

}
