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
package com.mypainmanager;

import com.mypainmanager.dao.ReportDAO;
import com.mypainmanager.domain.UserInfo;
import org.jooby.Jooby;
import org.jooby.MediaType;
import org.jooby.Status;

/**
 *
 * @author Harrison Ellerm
 */
public class ReportResource extends Jooby {

    public ReportResource(ReportDAO dao) {
        path("/api/mpm/report", () -> {
            /**
             * Triggers the report build process for a particular user.
             *
             * @param body The users details, including what dates to
             * build a report out for, and what types of pain to include.
             * @return <code>201</code> if the request is successful.
             */
            post((req, rsp) -> {
                UserInfo user = req.body(UserInfo.class);
                dao.buildAndSendReport(user);
                rsp.status(Status.OK);
            });
        }).produces(MediaType.json).consumes(MediaType.json);
    }
}

