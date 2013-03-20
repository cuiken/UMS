package com.tp.cache;

public enum MemcachedObjectType {

    //===========================日志key前缀==============================
    LOG_CLIENT("log_client:", 60 * 60 * 12),

    LOG_CONTENT("log_content:", 60 * 60 * 12),

    LOG_CMCC("log_cmcc:", 60 * 60 * 1),

    LOG_PAGE("log_page:", 60 * 60 * 4),

    LOG_REDIRECT("log_redirect:", 60 * 60 * 1),

    LOG_FEEDBACK("log_feedback:", 60 * 60 * 1),

    LOG_NAV_CLICK("log_click:", 60 * 60 * 1),

    LOG_IMEI("log_imei:", 60 * 3),

    //====================================================================

    POLL_XML("poll_xml:", 0),

    AD_XML("ad_xml:", 0),

    THEME_SORT("theme_sort:", 60 * 60 * 12),

    //=====================计数器key======================================

    COUNTER_CLIENT("counter_client", 60 * 60 * 12),

    COUNTER_CONTENT("counter_content", 60 * 60 * 12),

    COUNTER_CMCC("counter_cmcc", 60 * 60 * 1),

    COUNTER_PAGE("counter_page", 60 * 60 * 4),

    COUNTER_REDIRECT("counter_redirect", 60 * 60 * 1),

    COUNTER_FEEDBACK("counter_feedback", 60 * 60 * 1),

    COUNTER_NAV_CLICK("counter_nav_click", 60 * 60 * 1);

    //=================================================================

    private String prefix;
    private int expiredTime;

    MemcachedObjectType(String prefix, int expiredTime) {
        this.prefix = prefix;
        this.expiredTime = expiredTime;
    }

    public String getPrefix() {
        return prefix;
    }

    public int getExpiredTime() {
        return expiredTime;
    }
}
