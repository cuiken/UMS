package com.tp.startup;

import java.io.InputStream;

/**
 * User: ken.cui
 * Date: 13-3-22
 * Time: 上午11:19
 */
public class ClasspathDatabaseScriptProvider implements DatabaseScriptProvider {
    public InputStream getDatabaseScript(String path) {
        String resourcePath = "/sql/" + path;
        return this.getClass().getResourceAsStream(resourcePath);
    }

}
