package com.evcas.ddbuswx.controller;

import com.evcas.ddbuswx.service.PlatformAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Created by noxn on 2018/6/13.
 */
@Controller
@RequestMapping(value = "platformAccount")
@ApiIgnore
public class PlatformAccountController {

    @Autowired
    private PlatformAccountService platformAccountService;

    @RequestMapping(value = "test")
    @ResponseBody
    public String test() {
        platformAccountService.findAllPlatformAccount();
        return "123";
    }
}
