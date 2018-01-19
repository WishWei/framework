package com.wish.web.controller;

import com.wish.model.vo.ResponseBean;
import com.wish.model.vo.TestVO;
import com.wish.service.TestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private TestService testService;

    @RequestMapping(value = "/test.do", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "测试", notes = "测试")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "a", value = "测试参数", required = true, paramType = "query", dataType = "String")
    })
    public ResponseBean<String> createBuyOrder(@RequestParam("a") String a) throws Exception{
        return ResponseBean.responseSuccess(a);
    }

    @RequestMapping(value = "/saveTestVO.do", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "保存", notes = "保存")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "name", value = "名称", required = true, paramType = "query", dataType = "String")
    })
    public ResponseBean<TestVO> saveTestVO(@RequestParam("id") String id, @RequestParam("name") String name) throws Exception{
        TestVO testVO = new TestVO();
        testVO.setId(id);
        testVO.setName(name);
        testService.saveToRedis(testVO);
        return ResponseBean.responseSuccess(testVO);
    }

    @RequestMapping(value = "/findTestVO.do", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "根据id查询", notes = "根据id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", required = true, paramType = "query", dataType = "String")
    })
    public ResponseBean<TestVO> findTestVO(@RequestParam("id") String id) throws Exception{
        TestVO testVO = testService.findById(id);
        return ResponseBean.responseSuccess(testVO);
    }

}
