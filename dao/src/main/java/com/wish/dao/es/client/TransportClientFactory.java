package com.wish.dao.es.client;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import java.net.InetSocketAddress;

/**
 * Created by wish on 2018/2/5.
 */
public class TransportClientFactory implements FactoryBean<TransportClient>,InitializingBean,DisposableBean {


    private String clusterName;
    private String hosts;
    private TransportClient client;


    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public void setHosts(String hosts) {
        this.hosts = hosts;
    }

    @Override
    public void destroy() throws Exception {
        if(client!=null)
            client.close();

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Settings settings=Settings.builder().put("cluster.name",this.clusterName).build();
        client = new PreBuiltTransportClient(settings);
        String[] split = hosts.split(",");
        for (String ipport : split) {
            String[] sp = ipport.split(":");
            String ip = sp[0];
            Integer port = Integer.valueOf(sp[1]);
            client.addTransportAddress(
                    new InetSocketTransportAddress(
                            new InetSocketAddress(ip, port)));
        }
    }

    @Override
    public TransportClient getObject() throws Exception {
        return client;
    }

    @Override
    public Class<?> getObjectType() {
        return TransportClient.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

}