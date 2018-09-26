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

import com.mypainmanager.dao.ExceptionDAO;
import com.mypainmanager.domain.ExceptionWrapper;
import org.jooby.Jooby;
import org.jooby.MediaType;
import org.jooby.Status;

/**
 *
 * @author Harrison Ellerm
 */
public class ExceptionResource extends Jooby {

    public ExceptionResource(ExceptionDAO dao) {
        path("/api/mpm/exception", () -> {
            /**
             * Triggers the exception report build process. This is used to
             * notify administrators of an exception that was raised while the
             * application was executing.
             *
             * @param body the details of the exception raised.
             * @return <code>201</code> if the request is successful.
             */
            post((req, rsp) -> {
                ExceptionWrapper wrapper = req.body(ExceptionWrapper.class);
                dao.sendException(wrapper);
                rsp.status(Status.OK);
            });
        }).produces(MediaType.json).consumes(MediaType.json);
    }
}
