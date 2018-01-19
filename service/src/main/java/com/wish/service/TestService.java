package com.wish.service;

import com.wish.model.vo.TestVO;

/**
 * Created by wish on 2018/1/17.
 */
public interface TestService {
    void saveToRedis(TestVO testVO);

    TestVO findById(String id);
}
