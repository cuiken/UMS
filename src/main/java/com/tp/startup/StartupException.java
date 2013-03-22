package com.tp.startup;

import com.tp.service.ServiceException;

import java.util.Collections;
import java.util.List;

/**
 * User: ken.cui
 * Date: 13-3-22
 * Time: 上午10:24
 */
public class StartupException extends ServiceException {
    private final List<String> startupLog;


    public StartupException(String msg) {
        super(msg);
        this.startupLog = Collections.EMPTY_LIST;
    }

    public StartupException(String msg, Throwable t) {
        super(msg, t);
        this.startupLog = Collections.EMPTY_LIST;
    }

    public StartupException(String msg, List<String> log) {
        super(msg);

        if (log != null) {
            this.startupLog = log;
        } else {
            this.startupLog = Collections.EMPTY_LIST;
        }
    }

    public StartupException(String msg, Throwable t, List<String> log) {
        super(msg, t);

        if (log != null) {
            this.startupLog = log;
        } else {
            this.startupLog = Collections.EMPTY_LIST;
        }
    }


    public List<String> getStartupLog() {
        return startupLog;
    }
}
