package com.junlong.windseeker.web.controller;

import com.junlong.windseeker.utils.VirtuaMachineUtils;
import com.junlong.windseeker.web.response.BaseResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by niujunlong on 17/9/6.
 */
@RestController
@RequestMapping("/vm")
public class VirtuaMachineController {
    @GetMapping(value = "/attach")
    public BaseResponse attachTargetServer(int port){
        VirtuaMachineUtils.loadAgent(port);
        return BaseResponse.build(null);
    }

    @GetMapping(value = "/close")
    public BaseResponse closeTargetServer(int port){
        VirtuaMachineUtils.close(port);
        return BaseResponse.build(null);
    }
}
