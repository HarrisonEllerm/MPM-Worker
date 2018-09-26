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

import java.io.Serializable;

/**
 *
 * @author Harrison Ellerm
 */
public class UserInfo implements Serializable {
    
    private String uuid;
    private String year;
    private String start_Date;
    private String end_Date;
    private String email;

    /**
     * @return the uuid
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * @param uuid the uuid to set
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     * @return the year
     */
    public String getYear() {
        return year;
    }

    /**
     * @param year the year to set
     */
    public void setYear(String year) {
        this.year = year;
    }

    /**
     * @return the start_Date
     */
    public String getStart_Date() {
        return start_Date;
    }

    /**
     * @param start_Date the start date to set
     */
    public void setStart_Date(String start_Date) {
        this.start_Date = start_Date;
    }

    /**
     * @return the end_Date
     */
    public String getEnd_Date() {
        return end_Date;
    }

    /**
     * @param end_Date the end_Month to set
     */
    public void setEnd_Date(String end_Date) {
        this.end_Date = end_Date;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "UserInfo{" + "uuid=" + uuid + ", year=" + year + ", start_Date=" + start_Date + ", end_Date=" + end_Date + ", email=" + email + '}';
    }

}
