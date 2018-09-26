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
import com.mypainmanager.dao.ReportDAO;
import com.mypainmanager.utils.Settings;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import org.apache.log4j.Logger;
import org.jooby.Jooby;
import org.jooby.json.Gzon;

/**
 *
 * @author Harrison Ellerm
 */
public class App extends Jooby {
    
   static final Logger LOG = Logger.getLogger(App.class);

   public App() {
       use(new Gzon());
       use(new ReportResource(new ReportDAO()));
       use(new ExceptionResource(new ExceptionDAO()));
    }

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        App server = new App();
        server.port(Settings.getInstance().getPort());
        CompletableFuture.runAsync(() -> {
            server.start();
        });
        server.onStarted(() -> {
            LOG.info("Server Live...");
            LOG.info("Press Enter from CLI to stop the service...");
        });
        System.in.read();
        System.exit(0);
    }
}
