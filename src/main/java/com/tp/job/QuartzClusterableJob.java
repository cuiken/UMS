package com.tp.job;

import com.tp.dao.log.LogInHomeDao;
import com.tp.entity.log.LogInHome;
import com.tp.search.Search;
import com.tp.service.LogService;
import com.tp.service.ReportService;
import com.tp.utils.Constants;
import com.tp.utils.DateUtil;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * User: ken.cui
 * Date: 13-4-10
 * Time: 上午11:16
 */
public class QuartzClusterableJob extends QuartzJobBean {

    private IndexReader reader;


    private ApplicationContext applicationContext;

    /**
     * 从SchedulerFactoryBean注入的applicationContext.
     */
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String currDate = DateUtil.convertDate(new Date());
        String perDate = DateUtil.getPerDate(currDate);

        LogService logService=applicationContext.getBean(LogService.class);
        ReportService reportService=applicationContext.getBean(ReportService.class);
        Search search=applicationContext.getBean(Search.class);

        List<LogInHome> logs = logService.queryLogInHomeByDate(perDate, currDate);
        try{
        search.createLogStoreIndex(logs);

        Directory dir = FSDirectory.open(new File(Constants.INDEX_LOG_STORE));
         reader = IndexReader.open(dir);
        }catch(Exception e){

            }
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

}
