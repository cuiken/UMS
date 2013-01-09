package com.tp.search;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.LogByteSizeMergePolicy;
import org.apache.lucene.index.LogMergePolicy;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.TermRangeQuery;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.springframework.stereotype.Component;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.tp.entity.LogInHome;
import com.tp.utils.Constants;

@Component
public class Search implements ISearch<LogInHome> {

	@Override
	public void createLogStoreIndex(List<LogInHome> logs) throws Exception {

		File indexDir = new File(Constants.INDEX_LOG_STORE);
		FSDirectory dir = FSDirectory.open(indexDir);
		IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_36, new IKAnalyzer());
		iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
		LogMergePolicy mergePolicy = new LogByteSizeMergePolicy();
		mergePolicy.setMergeFactor(5);
		iwc.setMergePolicy(mergePolicy);
		IndexWriter indexWrite = new IndexWriter(dir, iwc);

		for (LogInHome log : logs) {
			Document doc = new Document();
			doc.add(new Field("id", log.getId().toString(), Field.Store.YES, Field.Index.NOT_ANALYZED));
			doc.add(new Field("imei", StringUtils.defaultString(log.getImei(), ""), Field.Store.YES,
					Field.Index.NOT_ANALYZED));
			doc.add(new Field("imsi", StringUtils.defaultString(log.getImsi(), ""), Field.Store.YES,
					Field.Index.NOT_ANALYZED));
			doc.add(new Field("version", StringUtils.defaultString(log.getClientVersion(), ""), Field.Store.YES,
					Field.Index.NOT_ANALYZED));
			doc.add(new Field("store", StringUtils.defaultString(log.getStoreType(), ""), Field.Store.YES,
					Field.Index.NOT_ANALYZED));
			doc.add(new Field("fm", StringUtils.defaultString(log.getFromMarket(), ""), Field.Store.YES,
					Field.Index.NOT_ANALYZED));
			doc.add(new Field("method", StringUtils.defaultString(log.getRequestMethod(), ""), Field.Store.YES,
					Field.Index.NOT_ANALYZED));
			doc.add(new Field("params", StringUtils.defaultString(log.getRequestParams(), ""), Field.Store.YES,
					Field.Index.ANALYZED));
			doc.add(new Field("time", StringUtils.defaultString(log.getCreateTime(), ""), Field.Store.YES,
					Field.Index.NOT_ANALYZED));
			indexWrite.addDocument(doc);
		}
		indexWrite.commit();
		indexWrite.close();

	}

	@Override
	public Long countByMethod(IndexSearcher searcher, String method, String sdate, String edate) throws IOException {

		BooleanQuery bq1 = new BooleanQuery();
		WildcardQuery w1 = new WildcardQuery(new Term("method", method));
		bq1.add(w1, BooleanClause.Occur.MUST);

		bq1.add(new TermRangeQuery("time", sdate, edate, false, false), BooleanClause.Occur.MUST);
		TopScoreDocCollector res = TopScoreDocCollector.create(10000, false);
		searcher.search(bq1, res);
		Integer hit = res.getTotalHits();
		return hit.longValue();
	}

	@Override
	public Long countByMethodAndParams(IndexSearcher searcher, String method, String sdate, String edate,
			String... params) throws IOException {
		if (params == null || params.length == 0) {
			return countByMethod(searcher, method, sdate, edate);
		}
		BooleanQuery bq1 = new BooleanQuery();
		WildcardQuery w1 = new WildcardQuery(new Term("method", method));
		bq1.add(w1, BooleanClause.Occur.MUST);

		for (int i = 0; i < params.length; i++) {
			WildcardQuery wq = new WildcardQuery(new Term("params", params[i] + "*"));
			bq1.add(wq, BooleanClause.Occur.MUST);
		}
		bq1.add(new TermRangeQuery("time", sdate, edate, false, false), BooleanClause.Occur.MUST);
		TopScoreDocCollector res = TopScoreDocCollector.create(10000, false);
		searcher.search(bq1, res);
		Integer hit = res.getTotalHits();
		return hit.longValue();

	}

	@Override
	public Long countByParam(IndexSearcher searcher, String sdate, String edate, String... params) throws IOException {
		if (params != null) {
			BooleanQuery bq1 = new BooleanQuery();
			for (int i = 0; i < params.length; i++) {

				WildcardQuery w1 = new WildcardQuery(new Term("params", params[i] + "*"));
				bq1.add(w1, BooleanClause.Occur.MUST);
			}

			bq1.add(new TermRangeQuery("time", sdate, edate, false, false), BooleanClause.Occur.MUST);
			TopScoreDocCollector res = TopScoreDocCollector.create(10000, false);
			searcher.search(bq1, res);
			Integer hit = res.getTotalHits();
			return hit.longValue();
		}
		return 0L;
	}

}
