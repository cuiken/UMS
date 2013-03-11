package com.tp.job;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.beans.factory.annotation.Autowired;

import com.tp.entity.log.LogInHome;
import com.tp.search.Search;
import com.tp.service.LogService;
import com.tp.service.ReportService;
import com.tp.utils.Constants;
import com.tp.utils.DateFormatUtils;

public class ReportCreateJob {

	private LogService logService;
	private ReportService reportService;
	private Search search;

	public void createReport() throws Exception {
		String currDate = DateFormatUtils.convertDate(new Date());
		String perDate = DateFormatUtils.getPerDate(currDate);

		List<LogInHome> logs = logService.queryLogInHomeByDate(perDate, currDate);
		search.createLogStoreIndex(logs);

		Directory dir = FSDirectory.open(new File(Constants.INDEX_LOG_STORE));
		IndexReader reader = IndexReader.open(dir);
		IndexSearcher searcher = new IndexSearcher(reader);
		try {
			reportService.createClientReport(searcher, perDate, currDate);
		} catch (Exception e) {

		}
		try {
			reportService.createContentReport(searcher, perDate, currDate);
		} catch (Exception e) {

		}
		try {
			reportService.createCountContentUnzipReport(perDate, currDate);
		} catch (Exception e) {

		}
		try {

			reportService.createGetClientPerMarketReport(perDate, currDate);
		} catch (Exception e) {

		}
		try {
			reportService.createClientInstallPermarketReport(perDate, currDate);
		} catch (Exception e) {

		}
		try {
			reportService.createClientInstallWithContentReport(perDate, currDate);
		} catch (Exception e) {

		}
	}

	@Autowired
	public void setLogService(LogService logService) {
		this.logService = logService;
	}

	@Autowired
	public void setReportService(ReportService reportService) {
		this.reportService = reportService;
	}

	@Autowired
	public void setSearch(Search search) {
		this.search = search;
	}
}
