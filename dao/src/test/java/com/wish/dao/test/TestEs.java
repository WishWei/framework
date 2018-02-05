package com.wish.dao.test;

import com.wish.dao.redis.TestRedisDao;
import junit.framework.TestCase;
import org.elasticsearch.client.transport.TransportClient;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by wish on 2018/2/5.
 */
public class TestEs extends TestCase {

    @Autowired
    private TransportClient transportClient;

    @Autowired
    private TestRedisDao testRedisDao;

    public void test(){
        testRedisDao.findInZSet("1");
    }
}
