package com.smallchill.game.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.elasticsearch.action.search.ClearScrollRequest;
import org.elasticsearch.action.search.ClearScrollResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchScrollRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.script.Script;
import org.elasticsearch.search.Scroll;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.BucketOrder;
import org.elasticsearch.search.aggregations.PipelineAggregatorBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedLongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.ParsedSum;
import org.elasticsearch.search.aggregations.metrics.SumAggregationBuilder;
import org.elasticsearch.search.aggregations.pipeline.BucketSelectorPipelineAggregationBuilder;
import org.elasticsearch.search.aggregations.pipeline.BucketSortPipelineAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smallchill.core.constant.ConstConfig;
import com.smallchill.core.toolbox.grid.BladePage;
import com.smallchill.game.model.request.AaZzLogPropchangeRequest;
import com.smallchill.game.model.request.OrderByRequest;
import com.smallchill.game.newmodel.Gameroomitem;
import com.smallchill.game.newmodel.result.WinLoseStatistics;
import com.smallchill.game.service.PlayerElasticService;

@Service
public class PlayerElasticServiceImpl implements PlayerElasticService {

	@Autowired
	private RestHighLevelClient client;

	@Override
	public SearchHits searchGoldChangeLog(AaZzLogPropchangeRequest request) throws IOException {

		SearchRequest searchRequest = new SearchRequest();
		searchRequest.indices(ConstConfig.ELASTIC_INDICES);

		// 查询条件
		SearchSourceBuilder searchSourceBuilder = this.builder(request);
		String[] includeFields = new String[] { "logtime", "aftamount", "amount", "preamount", "cheatrate",
				"cheatlimit", "serverid", "changetype_id", "kindid","exgamemsg" };
//		String[] includeFields = new String[] {"logtime", "aftamount","amount","preamount","cheatrate","cheatlimit","typeremark","roomname"};

		searchSourceBuilder.fetchSource(includeFields, null);
		// 获取条数
		searchSourceBuilder.from(request.getFrom());
		searchSourceBuilder.size(request.getSize());
		// 排序
		for (OrderByRequest order : request.getOrderBy()) {
			searchSourceBuilder.sort(new FieldSortBuilder(order.getFieldName()).order(order.getOrder()));
		}

		searchRequest.source(searchSourceBuilder);

		// 获取查询结果
		SearchResponse search = client.search(searchRequest, RequestOptions.DEFAULT);
		return search.getHits();
	}

	private SearchSourceBuilder builder(AaZzLogPropchangeRequest request) {
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

		// 查询条件
		BoolQueryBuilder booleanQueryBuilder = QueryBuilders.boolQuery();
		if (request.getUser_Id() != null) {
			booleanQueryBuilder.must(QueryBuilders.termQuery("user_id", request.getUser_Id()));
		}
		if (request.getServerId() != null) {
			booleanQueryBuilder.must(QueryBuilders.termQuery("serverid", request.getServerId()));
		}
		if (request.getChangeType_Id() != null) {
			booleanQueryBuilder.must(QueryBuilders.termQuery("changetype_id", request.getChangeType_Id()));
		}
		if (request.getProp_Id() != null) {
			booleanQueryBuilder.must(QueryBuilders.termQuery("prop_id", request.getProp_Id()));
		}

		/*if (request.getStartTime() != null && request.getEndTime() != null) {
			RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery("logtime");
			if (request.getStartTime() != null) {
				rangeQuery.gte(request.getStartTime());
			}
			if (request.getEndTime() != null) {
				rangeQuery.lte(request.getEndTime());
			}
			booleanQueryBuilder.must(rangeQuery);
		}*/

		if (request.getStartTimeId() != null && request.getEndTimeId() != null) {
			RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery("time_id");
			rangeQuery.gte(request.getStartTimeId());
			rangeQuery.lte(request.getEndTimeId());
			booleanQueryBuilder.must(rangeQuery);
		}

		searchSourceBuilder.query(booleanQueryBuilder);

		return searchSourceBuilder;
	}

	@Override
	public BladePage<Map> searchGoldChangeLogByPage(AaZzLogPropchangeRequest request) throws IOException {
		SearchHits hits = this.searchGoldChangeLog(request);

		return new BladePage<Map>(
				Arrays.stream(hits.getHits()).map(hit -> hit.getSourceAsMap()).collect(Collectors.toList()),
				request.getPage(), request.getSize(), hits.getTotalHits().value);
	}

	@Override
	public List<Gameroomitem> searchGoldChangeLogByRoom(AaZzLogPropchangeRequest request) throws IOException {
		// 构建查询请求体
		SearchRequest searchRequest = new SearchRequest();
		searchRequest.indices(ConstConfig.ELASTIC_INDICES);

		SearchSourceBuilder builder = this.builder(request);
		builder.size(0);

		// 聚合
		// 求和字段
		SumAggregationBuilder sum = AggregationBuilders.sum("do_a_sum_on_field_amount");
		sum.field("amount");
		// group by 字段
		TermsAggregationBuilder terms = AggregationBuilders.terms("by_serverid");
		terms.field("serverid");
		terms.order(BucketOrder.aggregation("do_a_sum_on_field_amount", false));
		terms.subAggregation(sum);

		builder.aggregation(terms);

		searchRequest.source(builder);

		SearchResponse search = client.search(searchRequest, RequestOptions.DEFAULT);
		Aggregations aggregation = search.getAggregations();
		Map<String, Aggregation> asMap = aggregation.asMap();
		List<Gameroomitem> sums = new ArrayList<Gameroomitem>();
		Gameroomitem item = null;
		for (String s : asMap.keySet()) {
			ParsedLongTerms a = (ParsedLongTerms) asMap.get(s);
			List<? extends Bucket> list = a.getBuckets();
			for (Terms.Bucket b : list) {
				Aggregations aggregations = b.getAggregations();
				Map<String, Aggregation> asMap2 = aggregations.asMap();
				item = new Gameroomitem();
				item.setServerid(b.getKeyAsNumber().intValue());
				for (String s2 : asMap2.keySet()) {
					ParsedSum ps = (ParsedSum) asMap2.get(s2);
					item.setRealscore(new Double(ps.getValue()).longValue());
				}
				sums.add(item);
			}
		}
		item = null;
		asMap = null;

		return sums;
	}

	@Override
	public List<Map> searchGoldChangeLogByList(AaZzLogPropchangeRequest request) throws IOException {
		final Scroll scroll = new Scroll(TimeValue.timeValueMinutes(1L));
		SearchRequest searchRequest = new SearchRequest(ConstConfig.ELASTIC_INDICES);
		searchRequest.scroll(scroll);

		SearchSourceBuilder searchSourceBuilder = this.builder(request);
		String[] includeFields = new String[] { "logtime", "amount" };
		searchSourceBuilder.fetchSource(includeFields, null);
		for (OrderByRequest order : request.getOrderBy()) {
			searchSourceBuilder.sort(new FieldSortBuilder(order.getFieldName()).order(order.getOrder()));
		}
		searchRequest.source(searchSourceBuilder);

		List<Map> list = null;
		SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
		String scrollId = searchResponse.getScrollId();
		SearchHit[] searchHits = searchResponse.getHits().getHits();

		if (searchHits != null && searchHits.length > 0) {
			list = Arrays.stream(searchHits).map(hit -> hit.getSourceAsMap()).collect(Collectors.toList());
		}
		while (searchHits != null && searchHits.length > 0) {
			SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);
			scrollRequest.scroll(scroll);
			searchResponse = client.scroll(scrollRequest, RequestOptions.DEFAULT);
			scrollId = searchResponse.getScrollId();
			searchHits = searchResponse.getHits().getHits();
			if (searchHits != null && searchHits.length > 0) {
				list.addAll(Arrays.stream(searchHits).map(hit -> hit.getSourceAsMap()).collect(Collectors.toList()));
			} else {
				break;
			}
		}

		ClearScrollRequest clearScrollRequest = new ClearScrollRequest();
		clearScrollRequest.addScrollId(scrollId);
		ClearScrollResponse clearScrollResponse = client.clearScroll(clearScrollRequest, RequestOptions.DEFAULT);
		boolean succeeded = clearScrollResponse.isSucceeded();

		return list;
	}

	@Override
	public List<WinLoseStatistics> searchWinLoseStatistics(AaZzLogPropchangeRequest request) throws IOException {
		// 构建查询请求体
		SearchRequest searchRequest = new SearchRequest();
		searchRequest.indices(ConstConfig.ELASTIC_INDICES);

		SearchSourceBuilder builder = this.builder(request);
		// 获取条数
		/*builder.from(request.getFrom());
		if(request.getLimit()!=null && request.getLimit()>0) {
			builder.size(request.getLimit());
		} else {
			builder.size(request.getSize());
		}*/
		builder.size(0); //不会有 hits 搜索结果返回

		// 聚合
		// 求和字段
		SumAggregationBuilder sum = AggregationBuilders.sum("win_score");
		sum.field("amount");

		// group by 字段
		TermsAggregationBuilder terms = AggregationBuilders.terms("by_user_id");
		terms.field("user_id");
		terms.order(BucketOrder.aggregation("win_score", false));
		terms.subAggregation(sum);
		// having条件
		Map<String, String> bucketsPathsMap = new HashMap<>();
		bucketsPathsMap.put("winScore", "win_score");
		Script script = null;
		
		if(request.getStartScore()!=null && request.getStartScore()>0 && request.getEndScore()!=null && request.getEndScore()>0) {
			script = new Script("params.winScore >= "+request.getStartScore()+" && params.winScore <= "+request.getEndScore());
		}else if(request.getStartScore()!=null && request.getStartScore()>0) {
			script = new Script("params.winScore >= "+request.getStartScore());
		}else if(request.getEndScore()!=null && request.getEndScore()>0) {
			script = new Script("params.winScore <= "+request.getEndScore());
		} else {
		}
		if(script!=null) {
			BucketSelectorPipelineAggregationBuilder bs =  PipelineAggregatorBuilders.bucketSelector("win_score_filter", bucketsPathsMap, script);
			terms.subAggregation(bs);
		}
		// 排序
		BucketSortPipelineAggregationBuilder sb = PipelineAggregatorBuilders.bucketSort("win_score_sort", Arrays.asList(new FieldSortBuilder("win_score").order(SortOrder.DESC)));
/*		// 获取条数
		sb.from(request.getFrom());
		if(request.getLimit()!=null && request.getLimit()>0) {
			sb.size(request.getLimit());
		} else {
			sb.size(request.getSize());
		}*/
		terms.subAggregation(sb);
		builder.aggregation(terms);

		searchRequest.source(builder);

		SearchResponse search = client.search(searchRequest, RequestOptions.DEFAULT);
		Aggregations aggregation = search.getAggregations();
		Map<String, Aggregation> asMap = aggregation.asMap();
		List<WinLoseStatistics> sums = new ArrayList<WinLoseStatistics>();
		WinLoseStatistics item = null;
		for (String s : asMap.keySet()) {
			ParsedLongTerms a = (ParsedLongTerms) asMap.get(s);
			List<? extends Bucket> list = a.getBuckets();
			for (Terms.Bucket b : list) {
				Aggregations aggregations = b.getAggregations();
				Map<String, Aggregation> asMap2 = aggregations.asMap();
				item = new WinLoseStatistics();
				item.setUserId(b.getKeyAsNumber().intValue());
				for (String s2 : asMap2.keySet()) {
					ParsedSum ps = (ParsedSum) asMap2.get(s2);
					item.setDayWaste(new Double(ps.getValue()).longValue());
				}
				sums.add(item);
			}
		}
		item = null;
		asMap = null;

		return sums;
	}

}
