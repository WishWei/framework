package com.wish.dao.es.client;

import com.google.gson.Gson;
import com.wish.model.vo.Employee;
import com.wish.model.vo.PageInfo;
import org.elasticsearch.action.admin.indices.exists.types.TypesExistsRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wish on 2018/2/5.
 */
@Repository
public class TestEsDao {
    @Autowired
    private TransportClient client;

    private static final String INDEX_NAME = "megacorp";
    private static final String TYPE_NAME = "employee";

    /**
     * 验证type是否存在
     *
     * @param index 索引名称
     * @param type  索引类型
     * @return
     */
    public boolean isTypeExists(String index, String type) {
        return client.admin().indices()
                .typesExists(new TypesExistsRequest(new String[]{index}, type))
                .actionGet()
                .isExists();
    }

    public Employee findEmployeeById(String docId) {
        GetResponse response = client.prepareGet(INDEX_NAME, TYPE_NAME, docId).execute().actionGet();
        if(!response.isExists()){
            return null;
        }
        Gson gson=new Gson();
        Employee employee = gson.fromJson(response.getSourceAsString(), Employee.class);
        return employee;
    }

    public PageInfo<Employee> searchByInterests(String key, int page, int pageSize) {
        int from = (page - 1) * pageSize;
        SearchResponse response = client.prepareSearch(INDEX_NAME)
                .setTypes(TYPE_NAME)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(QueryBuilders.matchQuery("interests", key))                 // Query
//                .setPostFilter(QueryBuilders.rangeQuery("age").from(12).to(18))     // Filter
                .setFrom(from).setSize(pageSize).setExplain(true)
                .get();

        SearchHits searchHits = response.getHits();
        searchHits.getTotalHits();
        SearchHit[] searchHitArray = searchHits.getHits();
        Gson gson=new Gson();
        List<Employee> employees = new ArrayList<>();
        for (SearchHit searchHit : searchHitArray) {
            Employee employee = gson.fromJson(searchHit.getSourceAsString(), Employee.class);
            employees.add(employee);
        }
        PageInfo<Employee> pageInfo = new PageInfo();
        pageInfo.setPage(page);
        pageInfo.setPageSize(pageSize);
        pageInfo.setItems(employees);
        pageInfo.setTotalCount(searchHits.getTotalHits());
        return pageInfo;
    }

}
