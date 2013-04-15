package com.tp.dao.log;

import com.tp.utils.DateUtil;
import org.springframework.stereotype.Component;

import com.tp.entity.log.LogCountClientInstallWithContent;
import com.tp.orm.hibernate.HibernateDao;

import java.util.List;

@Component
public class LogCCInstallWithContentDao extends HibernateDao<LogCountClientInstallWithContent, Long> {

    private static final String QUERY_MARKET = "select l.marketName from LogCountClientInstallWithContent l where l.date between ? and ? group by l.marketName";
    private static final String QUERY_LOG = "select l from LogCountClientInstallWithContent l where l.date between ? and ? order by l.installed desc";


    public List<String> getMarketByDate(String sdate, String edate) {

        return createQuery(QUERY_MARKET, sdate, edate).list();
    }

    public List<LogCountClientInstallWithContent> getByDate(String sdate, String edate) {
        return createQuery(QUERY_LOG, sdate, edate).list();
    }
}
