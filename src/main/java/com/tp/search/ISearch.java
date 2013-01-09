package com.tp.search;

import java.io.IOException;
import java.util.List;

import org.apache.lucene.search.IndexSearcher;

public interface ISearch<E> {

	public void createIndex(List<E> list) throws Exception;

	public Long countByMethod(IndexSearcher searcher, String method, String sdate, String edate) throws IOException;

	public Long countByMethodAndParams(IndexSearcher searcher, String method, String sdate, String edate,
			String... params) throws IOException;

	public Long countByParam(IndexSearcher searcher, String sdate, String edate, String... params) throws IOException;

}
