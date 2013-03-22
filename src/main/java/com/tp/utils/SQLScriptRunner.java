package com.tp.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * User: ken.cui
 * Date: 13-3-22
 * Time: 上午11:34
 */
public class SQLScriptRunner {
    private List<String> commands = new ArrayList<String>();
    private List<String> messages = new ArrayList<String>();
    private boolean failed = false;
    private boolean errors = false;


    /**
     * Creates a new instance of SQLScriptRunner
     */
    public SQLScriptRunner(InputStream is) throws IOException {

        BufferedReader in = new BufferedReader(new InputStreamReader(is));
        String command = "";
        String line;
        while ((line = in.readLine()) != null) {
            line = line.trim();

            if (!line.startsWith("--")) { // ignore lines starting with "--"

                if (line.indexOf("--") > 0) {
                    // trim comment off end of line
                    line = line.substring(0, line.indexOf("--")).trim();
                }

                // add line to current command
                command += line.trim();
                if (command.endsWith(";")) {
                    // ";" is end of command, so add completed command to list
                    String cmd = command.substring(0, command.length() - 1);
                    String[] cmdArray = StringUtils.split(cmd);
                    cmd = StringUtils.join(cmdArray, " ");
                    commands.add(cmd);
                    command = "";
                } else if (StringUtils.isNotEmpty(command)) {
                    command += " "; // still more command coming so add space
                }
            }
        }
        in.close();
    }


    /**
     * Creates a new instance of SQLScriptRunner
     */
    public SQLScriptRunner(String scriptPath) throws IOException {
        this(new FileInputStream(scriptPath));
    }


    /**
     * Number of SQL commands in script
     */
    public int getCommandCount() {
        return commands.size();
    }


    /**
     * Return messages from last run of script, empty if no previous run
     */
    public List<String> getMessages() {
        return messages;
    }


    /**
     * Returns true if last call to runScript() threw an exception
     */
    public boolean getFailed() {
        return failed;
    }


    /**
     * Returns true if last run had any errors
     */
    public boolean getErrors() {
        return errors;
    }


    /**
     * Run script, logs messages, and optionally throws exception on error
     */
    public void runScript(
            Connection con, boolean stopOnError) throws SQLException {
        failed = false;
        errors = false;
        for (String command : commands) {

            // run each command
            try {
                Statement stmt = con.createStatement();
                stmt.executeUpdate(command);
                if (!con.getAutoCommit()) con.commit();

                // on success, echo command to messages
                successMessage(command);

            } catch (SQLException ex) {
                // add error message with text of SQL command to messages
                errorMessage("ERROR: SQLException executing SQL [" + command
                        + "] : " + ex.getLocalizedMessage());
                // add stack trace to messages
                StringWriter sw = new StringWriter();
                ex.printStackTrace(new PrintWriter(sw));
                errorMessage(sw.toString());
                if (stopOnError) {
                    failed = true;
                    throw ex;
                }
            }
        }
    }


    private void errorMessage(String msg) {
        messages.add(msg);
    }


    private void successMessage(String msg) {
        messages.add(msg);
    }
}
