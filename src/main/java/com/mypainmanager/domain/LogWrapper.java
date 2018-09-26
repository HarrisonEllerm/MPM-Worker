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
package com.mypainmanager.domain;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

/**
 *
 * @author Harrison Ellerm
 */
public class LogWrapper implements Serializable {

    @SerializedName("medsDescription")
    private String medsDescription;
    @SerializedName("notesDescription")
    private String notesDescription;
    @SerializedName("day_in_month")
    private String dayInMonth;
    @SerializedName("date_string")
    private String dateString;
    @SerializedName("ranking")
    private String ranking;
    @SerializedName("month_num")
    private String monthNumber;
    @SerializedName("time_string")
    private String timeString;
    @SerializedName("type")
    private String type;

    public LogWrapper() {
    }

    /**
     * @return the medsDescription
     */
    public String getMedsDescription() {
        return medsDescription;
    }

    /**
     * @param medsDescription the medsDescription to set
     */
    public void setMedsDescription(String medsDescription) {
        this.medsDescription = medsDescription;
    }

    /**
     * @return the notesDescription
     */
    public String getNotesDescription() {
        return notesDescription;
    }

    /**
     * @param notesDescription the notesDescription to set
     */
    public void setNotesDescription(String notesDescription) {
        this.notesDescription = notesDescription;
    }

    /**
     * @return the dayInMonth
     */
    public String getDayInMonth() {
        return dayInMonth;
    }

    /**
     * @param dayInMonth the dayInMonth to set
     */
    public void setDayInMonth(String dayInMonth) {
        this.dayInMonth = dayInMonth;
    }

    /**
     * @return the dateString
     */
    public String getDateString() {
        return dateString;
    }

    /**
     * @param dateString the dateString to set
     */
    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

    /**
     * @return the ranking
     */
    public String getRanking() {
        return ranking;
    }

    /**
     * @param ranking the ranking to set
     */
    public void setRanking(String ranking) {
        this.ranking = ranking;
    }

    /**
     * @return the monthNumber
     */
    public String getMonthNumber() {
        return monthNumber;
    }

    /**
     * @param monthNumber the monthNumber to set
     */
    public void setMonthNumber(String monthNumber) {
        this.monthNumber = monthNumber;
    }

    /**
     * @return the timeString
     */
    public String getTimeString() {
        return timeString;
    }

    /**
     * @param timeString the timeString to set
     */
    public void setTimeString(String timeString) {
        this.timeString = timeString;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }
    
    public String getDescription() {
        return "Date: " + dateString 
                + ", Time: " + timeString
                + ", Type: " + type
                + ", Rating: " + ranking
                + ", Meds Notes: " + medsDescription == null ? "N/A": medsDescription
                + ", Notes: " + notesDescription == null? "N/A" : notesDescription;
    }

    @Override
    public String toString() {
        return "LogWrapper{" + "medsDescription=" + medsDescription + ", notesDescription=" + notesDescription + ", dayInMonth=" + dayInMonth + ", dateString=" + dateString + ", ranking=" + ranking + ", monthNumber=" + monthNumber + ", timeString=" + timeString + ", type=" + type + '}';
    }

}
