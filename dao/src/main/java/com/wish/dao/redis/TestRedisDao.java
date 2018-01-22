package com.wish.dao.redis;

import com.wish.model.vo.TestVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

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

    public void addToQueen(String key, TestVO testVO) {
        ListOperations<String, TestVO> valueOperations = redisTemplate.opsForList();
        valueOperations.rightPush(key,testVO);

    }

    public List<TestVO> getQueenElements(String key) {
        ListOperations<String, TestVO> valueOperations = redisTemplate.opsForList();
        List<TestVO> results = valueOperations.range(key, 0, valueOperations.size(key) - 1);
        return results;
    }

    public void addToZSet(String key,TestVO testVO, double score){
        ZSetOperations<String,TestVO> zSetOperations = redisTemplate.opsForZSet();
        zSetOperations.add(key,testVO, score);
    }

    public Set<ZSetOperations.TypedTuple<TestVO>> findInZSet(String key) {
        ZSetOperations<String,TestVO> zSetOperations = redisTemplate.opsForZSet();
        Set<ZSetOperations.TypedTuple<TestVO>> typedTupleSet = zSetOperations.rangeWithScores(key, 0 , zSetOperations.size(key) - 1);
        return typedTupleSet;
    }

}
