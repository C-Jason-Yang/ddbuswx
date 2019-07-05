package com.evcas.ddbuswx.controller;

import com.evcas.ddbuswx.service.impl.FileUpLoadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Created by noxn on 2018/9/18.
 */
@Controller
@RequestMapping("fileUpLoad")
@ApiIgnore
public class FileUpLoadController {

    @Autowired
    private FileUpLoadService fileUpLoadService;


    @RequestMapping(value = "base64UpLoad", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin(origins = "*", maxAge = 3600)
    public String base64UpLoad(String fileBase64Code, String fileName) {
       // String tempFilePath = fileUpLoadService.fileBase64UpLoad(fileBase64Code, fileName);

        return "ok";
    }
}
