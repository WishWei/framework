package com.wish.service.impl;

import com.wish.dao.redis.TestRedisDao;
import com.wish.model.vo.TestVO;
import com.wish.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by wish on 2018/1/17.
 */
@Service
@Transactional
public class TestServiceImpl implements TestService{

    @Autowired
    private TestRedisDao testRedisDao;

    @Override
    public void saveToRedis(TestVO testVO) {
        testRedisDao.save(testVO);
    }

    @Override
    public TestVO findById(String id){
        return testRedisDao.findById(id);
    }
}
