package com.wish.dao.redis;

import com.wish.model.vo.TestVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

/**
 * Created by wish on 2018/1/19.
 */
@Repository
public class TestRedisDao {
    @Autowired
    private RedisTemplate<String,TestVO> redisTemplate;

    public void save(TestVO testVO) {
        ValueOperations<String, TestVO> valueOper = redisTemplate.opsForValue();
        valueOper.set(testVO.getId(), testVO);
    }

    public TestVO findById(String id) {
        ValueOperations<String, TestVO> valueOper = redisTemplate.opsForValue();
        return valueOper.get(id);
    }
}
