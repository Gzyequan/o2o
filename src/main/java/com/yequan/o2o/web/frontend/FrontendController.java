package com.yequan.o2o.web.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/frontend")
public class FrontendController {

    @RequestMapping(value = "/index")
    private String index() {
        return "frontend/index";
    }

}
