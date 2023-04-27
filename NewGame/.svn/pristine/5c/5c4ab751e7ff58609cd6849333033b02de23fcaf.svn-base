package com.smallchill.common.config.elasticsearch;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.smallchill.core.constant.ConstConfig;

@Configuration
public class ElasticsConfig {
 
    @Value("${config.elastic.url}")
    private String url;
    @Value("${config.elastic.port}")
    private String port;
    @Value("${config.elastic.protocol}")
    private String protocol;
    @Value("${config.elastic.indices}")
    private String indices;
	
    /**
     * 初始化
     */
    @Bean
    public RestHighLevelClient restHighLevelClient() {
    	String[] elasticUrl = url.split(",");
    	List<HttpHost> list = new ArrayList<HttpHost>();
    	for (String url : elasticUrl) {
			list.add(new HttpHost(url, Integer.parseInt(port), protocol));
		}
    	HttpHost[] hosts = new HttpHost[list.size()];
    	list.toArray(hosts);
    	RestHighLevelClient client = new RestHighLevelClient(
    	        RestClient.builder(hosts));
    	return client;
        /*return getEsClientDecorator().getRestHighLevelClient();*/
    }
 
    @Bean
    @Scope("singleton")
    public ESClientDecorator getEsClientDecorator() {
        //可以配置集群 通过逗号隔开
        return new ESClientDecorator(new HttpHost(ConstConfig.ELASTIC_URL,Integer.parseInt(ConstConfig.ELASTIC_PORT), ConstConfig.ELASTIC_POTOCOL));
    }
 
}
