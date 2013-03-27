package com.tp.utils;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static junit.framework.Assert.assertEquals;

/**
 * User: ken.cui
 * Date: 13-3-26
 * Time: 下午4:30
 */
public class DateUtilTest {
    String dateString = "2013-03-25";

    Date date;

    @Before
    public void setUp() throws Exception {
        date = DateUtil.format(dateString);
    }

    @Test
    public void convertTest() throws Exception {
        String friendlyTimestamp = DateUtil.convert(date);
        String iso86010DateFormat = DateUtil.convertDate(date);
        String perDate = DateUtil.getPerDate(dateString);
        String nextDate = DateUtil.getNextDate(dateString);
        String perMonthDate = DateUtil.getPerMonthDate(dateString);
        String d6charDateString = DateUtil.get6charDateString(dateString);
        String nextMonthDate = DateUtil.getNextMonthDate(dateString);

        assertEquals("2013-03-25 00:00:00", friendlyTimestamp);
        assertEquals("2013-03-25", iso86010DateFormat);
        assertEquals("2013-03-24", perDate);
        assertEquals("2013-03-26", nextDate);
        assertEquals("2013-02-25", perMonthDate);
        assertEquals("201303", d6charDateString);
        assertEquals("2013-04-25", nextMonthDate);
    }
}
