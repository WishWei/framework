package com.wish.web.controller;

import com.wish.model.vo.ResponseBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by wish on 2018/1/19.
 */
@Controller
@RequestMapping("test")
@Api(value = "测试", description = "测试接口")
public class TestController {
    @RequestMapping(value = "/test.do", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "测试", notes = "测试")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "a", value = "测试参数", required = true, paramType = "query", dataType = "String")
    })
    public ResponseBean<String> createBuyOrder(@RequestParam("a") String a) throws Exception{
        return ResponseBean.responseSuccess(a);
    }
}
