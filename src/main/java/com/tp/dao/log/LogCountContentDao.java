package com.tp.dao.log;

import java.text.DecimalFormat;
import java.util.List;

import com.tp.cache.MemcachedObjectType;
import com.tp.cache.SpyMemcachedClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tp.entity.log.LogCountContent;
import com.tp.orm.hibernate.HibernateDao;

@Component
public class LogCountContentDao extends HibernateDao<LogCountContent, Long> {

    private static final String SUM_EACH_DOWNLOAD = "select sum(c.totalDown) from LogCountContent c where c.themeName=?";

    private SpyMemcachedClient spyMemcachedClient;

    @SuppressWarnings("unchecked")
    public List<LogCountContent> getByContentOrDate(String theme, String date) {
        String hql = "select c from LogCountContent  c ";

        if (theme != null && !theme.isEmpty() && (date == null || date.isEmpty())) {
            hql += "where c.themeName=?";
            return createQuery(hql, theme).list();
        } else if (date != null && !date.isEmpty() && (theme == null || theme.isEmpty())) {
            hql += "where c.logDate=?";
            return createQuery(hql, date).list();
        } else if (theme != null && !theme.isEmpty() && date != null && !date.isEmpty()) {
            hql += "where c.themeName=? and c.logDate=?";
            return createQuery(hql, theme, date).list();
        } else {
            return createQuery(hql).list();
        }
    }

    public String queryTotalDownload(String fname,String language) {
        String prefix = MemcachedObjectType.THEME_COUNT_DOWNLOAD.getPrefix();
        String key=StringUtils.deleteWhitespace(prefix + fname);
        Long count = spyMemcachedClient.get(key);
        if (count == null) {
            count = (Long) createQuery(SUM_EACH_DOWNLOAD, fname).uniqueResult();
            spyMemcachedClient.set(key, MemcachedObjectType.THEME_COUNT_DOWNLOAD.getExpiredTime(), count);
        }
        return convert(count,language);
    }

    private String convert(long count, String language) {
        count = count * 10;

        if (count < 10000) {
            if (count < 1000) {
                if (StringUtils.equalsIgnoreCase(language, "zh"))
                    return "1000以下";
                else
                    return "0-1000";
            } else {
                String number = StringUtils.substring(String.valueOf(count), 0, 1);
                for (int i = 1; i < 10; i++) {
                    if (Integer.valueOf(number).equals(i)) {
                        return i + "000+";
                    }
                }
            }
        } else {
            String number = StringUtils.substring(String.valueOf(count), 0, 1);
            String tenThousandPosition = StringUtils.substring(String.valueOf(count), 0,
                    String.valueOf(count).length() - 4);

            for (int i = 1; i < 10; i++) {
                if (Integer.valueOf(number).equals(i)) {
                    if (StringUtils.equalsIgnoreCase(language, "zh"))
                        return tenThousandPosition + "万+";
                    else {
                        long result = Long.valueOf(tenThousandPosition + "0000");

                        return new DecimalFormat(",###").format(result) + "+";
                    }
                }
            }

        }
        return "";
    }

    @Autowired
    public void setSpyMemcachedClient(SpyMemcachedClient spyMemcachedClient) {
        this.spyMemcachedClient = spyMemcachedClient;
    }
}
