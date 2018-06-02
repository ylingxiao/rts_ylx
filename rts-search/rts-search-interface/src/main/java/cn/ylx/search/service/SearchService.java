package cn.ylx.search.service;

import cn.ylx.common.pojo.SearchResult;

public interface SearchService {
	
	SearchResult search(String keyword, int page, int rows) throws Exception;
	
}
