package com.tp.job;

import com.tp.dao.log.LogInHomeDao;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * User: ken.cui
 * Date: 13-4-10
 * Time: 下午2:21
 */
public class CreateTableClusterableJob extends QuartzJobBean{

    private ApplicationContext applicationContext;

    /**
     * 从SchedulerFactoryBean注入的applicationContext.
     */
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        LogInHomeDao logInHomeDao=applicationContext.getBean(LogInHomeDao.class);
        try{
            logInHomeDao.createTable();
        }catch(Exception e){

        }
    }
}
