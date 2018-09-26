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
package com.mypainmanager.dao;

import com.mypainmanager.domain.ExceptionWrapper;
import com.mypainmanager.utils.Emailer;
import org.apache.log4j.Logger;

/**
 *
 * @author Harrison Ellerm
 */
public class ExceptionDAO {

    private static final Logger LOG = Logger.getLogger(ExceptionDAO.class);

    public void sendException(ExceptionWrapper wrapper) {
        LOG.info("Exception Report Build Triggered...");
        Emailer.sendApplicationExceptionToSupportAndLog(wrapper.getExceptionMessage(),
                wrapper.getClazz(), LOG);
        LOG.info("Exception Report Sent...");
    }
}
