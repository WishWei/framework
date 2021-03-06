package com.wish.service.impl;

import com.wish.common.exception.CommonException;
import com.wish.common.util.ZookeeperClientUtil;
import com.wish.dao.es.client.TestEsDao;
import com.wish.dao.redis.TestRedisDao;
import com.wish.model.vo.Employee;
import com.wish.model.vo.PageInfo;
import com.wish.model.vo.TestVO;
import com.wish.service.TestService;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * Created by wish on 2018/1/17.
 */
@Service
@Transactional
public class TestServiceImpl implements TestService{

    @Autowired
    private TestRedisDao testRedisDao;

    @Autowired
    private TestEsDao testEsDao;

    @Autowired
    private ZookeeperClientUtil zookeeperClientUtil;

    private final static String QUEEN_NAME = "testQueen";

    private final static String TOP_NAME = "testTop";

    @Override
    public void saveToRedis(TestVO testVO) {
        testRedisDao.save(testVO);
    }

    @Override
    public TestVO findById(String id){
        return testRedisDao.findById(id);
    }

    @Override
    public void addToQueen(TestVO testVO) {
        testRedisDao.addToQueen(QUEEN_NAME, testVO);
    }

    @Override
    public List<TestVO> getQueenElements() {
        return testRedisDao.getQueenElements(QUEEN_NAME);
    }

    @Override
    public void addToZset(TestVO testVO, double score) {
        testRedisDao.addToZSet(TOP_NAME, testVO, score);
    }

    @Override
    public Set<ZSetOperations.TypedTuple<TestVO>> findInZSet() {
        return testRedisDao.findInZSet(TOP_NAME);
    }

    @Override
    public Employee findEmployeeById(String id) {
        return testEsDao.findEmployeeById(id);
    }

    public PageInfo<Employee> searchEmployeesByInterest(String key, int page, int pageSize) {
        return testEsDao.searchByInterests(key, page, pageSize);
    }

    public void addChild(String path, byte[] data) throws Exception{
        ZooKeeper zk =  zookeeperClientUtil.getAliveZk();
        try {
            zk.create(path, data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        } catch (KeeperException e) {
            e.printStackTrace();
            throw new CommonException("创建节点失败");
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new CommonException("创建节点失败");
        }finally {
            zk.close();
        }
    }

}
