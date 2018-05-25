package com.wish.common.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by wish on 2018/5/14.
 */
public class ZookeeperClientUtil {
    private ZooKeeper zk;
    //zookeeper地址
    private String servers;
    //链接超时时间
    private int sessionTimeout = 40000;

    private String mainPath;

    public ZooKeeper getAliveZk() {
        ZooKeeper aliveZk = zk;
        if (aliveZk != null && aliveZk.getState().isAlive()) {
            return aliveZk;
        } else {
            zkReconnect();
            return zk;
        }
    }
    public synchronized void zkReconnect() {
        close();
        try {
            connect();
        } catch (IOException e) {
        }
    }
    public synchronized void close() {
        if (zk != null) {
            try {
                zk.close();
            } catch (InterruptedException e) {
            }
            zk = null;
        }
    }
    private synchronized void connect() throws IOException {
        if (zk == null  && !StringUtils.isBlank(servers))
            zk = new ZooKeeper(servers, sessionTimeout, null);
    }
    public String getData(String path) {
        String result = null;
        try {
            byte [] data = getAliveZk().getData(path, Boolean.TRUE,null);
            if(null != data){
                result = new String(data, "UTF-8");
            }
        } catch (KeeperException e) {
        } catch (InterruptedException e) {
        } catch (UnsupportedEncodingException e) {
        }
        return result ;
    }
    public List<String> getChildren(){
        List<String> data = null;
        try {
            data = getAliveZk().getChildren(mainPath, Boolean.TRUE);
        } catch (KeeperException e) {
        } catch (InterruptedException e) {
        }
        return data;
    }
    public void setSessionTimeout(int sessionTimeout) {
        this.sessionTimeout = sessionTimeout;
    }
    public String getMainPath() {
        return mainPath;
    }
    public void setMainPath(String mainPath) {
        this.mainPath = mainPath;
    }
    public void setServers(String servers) {
        this.servers = servers;
    }
}
