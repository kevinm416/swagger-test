package com.kevinm416;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("controller1")
public class Controller1 {

    @RequestMapping(
            value = "/endpoint1",
            method = RequestMethod.GET)
    @ResponseBody
    public MyClass getMyClass() {
        return ImmutableMyClass.of("abc");
    }

}
