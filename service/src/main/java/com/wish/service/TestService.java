package com.wish.service;

import com.wish.model.vo.Employee;
import com.wish.model.vo.PageInfo;
import com.wish.model.vo.TestVO;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.List;
import java.util.Set;

/**
 * Created by wish on 2018/1/17.
 */
public interface TestService {
    void saveToRedis(TestVO testVO);

    TestVO findById(String id);

    void addToQueen(TestVO testVO);

    List<TestVO> getQueenElements();

    void addToZset(TestVO testVO, double score);

    Set<ZSetOperations.TypedTuple<TestVO>> findInZSet();

    Employee findEmployeeById(String id);

    PageInfo<Employee> searchEmployeesByInterest(String key, int page, int pageSize);
}
