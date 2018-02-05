package com.wish.web.controller;

import com.wish.model.vo.Employee;
import com.wish.model.vo.PageInfo;
import com.wish.model.vo.ResponseBean;
import com.wish.model.vo.TestVO;
import com.wish.service.TestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Set;

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

    @RequestMapping(value = "/addToQueen.do", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "添加到队列", notes = "添加到队列")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "name", value = "名称", required = true, paramType = "query", dataType = "String")
    })
    public ResponseBean<TestVO> addToQueen(@RequestParam("id") String id, @RequestParam("name") String name) throws Exception{
        TestVO testVO = new TestVO();
        testVO.setId(id);
        testVO.setName(name);
        testService.addToQueen(testVO);
        return ResponseBean.responseSuccess("添加到队列成功");
    }


    @RequestMapping(value = "/findQueenList.do", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询的队列", notes = "返回队列元素")
    public ResponseBean<List<TestVO>> findQueenList() throws Exception{
        List<TestVO> testVOList = testService.getQueenElements();
        return ResponseBean.responseSuccess(testVOList);
    }

    @RequestMapping(value = "/addToTop.do", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "添加到排行榜", notes = "添加到排行榜")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "name", value = "名称", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "score", value = "分数", required = true, paramType = "query", dataType = "String")
    })
    public ResponseBean<TestVO> addToTop(@RequestParam("id") String id, @RequestParam("name") String name,  @RequestParam("score")double score) throws Exception{
        TestVO testVO = new TestVO();
        testVO.setId(id);
        testVO.setName(name);
        testService.addToZset(testVO, score);
        return ResponseBean.responseSuccess("添加到排行榜成功");
    }


    @RequestMapping(value = "/findTopElements.do", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询排行榜", notes = "返回排行榜元素")
    public ResponseBean<Set<ZSetOperations.TypedTuple<TestVO>>> findTopElements() throws Exception{
        Set<ZSetOperations.TypedTuple<TestVO>> set = testService.findInZSet();
        return ResponseBean.responseSuccess(set);
    }

    @RequestMapping(value = "/findEmployeeById.do", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "根据id查询员工", notes = "根据id查询员工")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", required = true, paramType = "query", dataType = "String")
    })
    public ResponseBean<Employee> findEmployeeById(String id) throws Exception{
        Employee employee = testService.findEmployeeById(id);
        return ResponseBean.responseSuccess(employee);
    }

    @RequestMapping(value = "/searchEmployeesByInterest.do", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "根据兴趣搜索员工", notes = "根据兴趣搜索员工")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key", value = "关键字", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "page", value = "页", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "pageSize", value = "每页行数", required = true, paramType = "query", dataType = "String")
    })
    public ResponseBean<PageInfo<Employee>> searchEmployeesByInterest(String key, int page, int pageSize) throws Exception{
        PageInfo<Employee> pageInfo = testService.searchEmployeesByInterest(key, page, pageSize);
        return ResponseBean.responseSuccess(pageInfo);
    }



}
