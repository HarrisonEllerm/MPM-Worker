/*
 * Copyright 2018 Harrison Ellerm.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mypainmanager.utils;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;

/**
 *
 * @author Harrison Ellerm
 */
public class HtmlContent {

    private final StringBuilder SB = new StringBuilder();
    private final String generatingClass;
    private Exception exception;
    private String exceptionString;
    private boolean send = false;
    
    public HtmlContent(Object obj){
        this.generatingClass = obj.getClass().getName();
    }
    
    public HtmlContent(Class obj){
        this.generatingClass = obj.getName();
    }
    
    public HtmlContent(String clazz) {
        this.generatingClass = clazz;
    }
    
    public StringBuilder getHtmlContent(){
        return SB;
    }
    
    public String getGeneratingClass(){
        return generatingClass;
    }
    
    public Exception getException(){
        return exception;
    }
    
    public void addLine(String line, Logger log, boolean send) {
        this.send = send;
        addLine(line, log);
    }
 
    public void addLine(String line, Logger log) {
        SB.append(line).append("<br>");
        log.info("NOTIFY: "+line);
    }
    
    public void addBoldLine(String line, Logger log){
        addLine("<b>"+line+"</b>", log);
    }
    
    public void addWarningLine(String line, Logger log, boolean send){
        this.send = send;
        addWarningLine(line, log);
    }
    
    public void addWarningLine(String line, Logger log){
        SB.append("<font color=\"#0066ff\">");
        SB.append(line).append("<br></font>");
        log.info("NOTIFY: "+line);
    }
    
    public void addErrorLine(String line, Logger log, boolean send){
        this.send = send;
        addErrorLine(line, log);
    }
    
    public void addErrorLine(String line, Logger log){
        SB.append("<font color=\"#A93226\">");
        SB.append(line).append("<br></font>");
        log.info("NOTIFY: "+line);
    }
    
    public void addHR(){
        SB.append("<HR>");
    }
    
    public void addException(Exception ex, Logger log, boolean send){
        this.send = send;
        addException(ex, log);
    }
  
    public void addException(Exception ex, Logger log) {
        this.exception = ex;
        log.info("NOTIFY: " +ex.getLocalizedMessage());
    }
    
    public void addException(String ex, Logger log) {
        this.exceptionString = ex;
        log.info("NOTIFY: " + ex);
    }

    public String getHtml() {
        if (exception != null) {
            String header = "<html><body><font size=\"2\" face=\"Verdana,Times New Roman\">";
            header = header + "Workflow error notification<br><hr><br>";
            header = header + "<bold>Generator: " + generatingClass + "</bold><br>";
            if (SB.length() > 0) {
                header = header + "Message: " + SB.toString();
            }
            header = header + "<hr><br><font color=\"#A93226\">";
            header = header + "Exception: " + exception.getMessage() + "<br><br>";
            String stackTrace = ExceptionUtils.getStackTrace(exception);
            header = header + "Stacktrace: " + stackTrace + "<br>";
            String footer = "</font></font></body></html>";
            return header + footer;
        } else if (exceptionString != null && !exceptionString.isEmpty()){
           String header = "<html><body><font size=\"2\" face=\"Verdana,Times New Roman\">";
            header = header + "Workflow error notification<br><hr><br>";
            header = header + "<bold>Generator: " + generatingClass + "</bold><br>";
            if (SB.length() > 0) {
                header = header + "Message: " + SB.toString();
            }
            header = header + "<hr><br><font color=\"#A93226\">";
            header = header + "Exception: " + exceptionString + "<br><br>";
            String footer = "</font></font></body></html>";
            return header + footer;
        } else {
            String header = "<html><body><font size=\"2\" face=\"Verdana,Times New Roman\">";
            header = header + "Workflow notification: <bold>Generator: " + generatingClass + "</bold><br><hr><br>";
            String footer = "</font></body></html>";
            return header + SB.toString() + footer;
        }
    }
    
    public String getHtmlWithoutHeader() {
        if (exception != null) {
            String header = "<html><body><font size=\"2\" face=\"Verdana,Times New Roman\">";
            header = header + "Workflow error notification<br><hr><br>";
            header = header + "<bold>Generator: " + generatingClass + "</bold><br>";
            if (SB.length() > 0) {
                header = header + "Message: " + SB.toString();
            }
            header = header + "<hr><br><font color=\"#A93226\">";
            header = header + "Exception: " + exception.getMessage() + "<br><br>";
            String stackTrace = ExceptionUtils.getStackTrace(exception);
            header = header + "Stacktrace: " + stackTrace + "<br>";
            String footer = "</font></font></body></html>";
            return header + footer;
        } else {
            String header = "<html><body><font size=\"2\" face=\"Verdana,Times New Roman\">";
            String footer = "</font></body></html>";
            return header + SB.toString() + footer;
        }
    }
    
    public boolean isSend() {
        return send;
    }

    public void setSend(boolean send) {
        this.send = send;
    }
}
