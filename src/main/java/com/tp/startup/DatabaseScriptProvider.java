package com.tp.startup;

import java.io.InputStream;

/**
 * User: ken.cui
 * Date: 13-3-22
 * Time: 上午11:01
 */
public interface DatabaseScriptProvider {
    public InputStream getDatabaseScript(String path) throws Exception;
}
