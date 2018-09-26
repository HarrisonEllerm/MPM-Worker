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

import com.mypainmanager.domain.UserInfo;
import com.mypainmanager.domain.LogWrapper;
import com.mypainmanager.utils.Emailer;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.mypainmanager.utils.Settings;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import org.apache.log4j.Logger;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Harrison Ellerm
 */
public class ReportDAO {

    private Gson gson;
    private static final Logger LOG = Logger.getLogger(ReportDAO.class);

    public ReportDAO() {
        this.gson = new Gson();
        LOG.info("Setting up connection to Firebase");
        try (FileInputStream serviceAccount = new FileInputStream(Settings.getInstance().
                getServiceAccountPath())) {
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl(Settings.getInstance().getDatabaseUrl())
                    .build();
            FirebaseApp.initializeApp(options);
            LOG.info("Firebase Connection estabished");
        } catch (IOException ex) {
            Emailer.sendExceptionToSupportAndLog(ex, LOG, ReportDAO.class);
        }
    }

    public void buildAndSendReport(UserInfo user) {
        LOG.info("Build And Send Report Triggered");
        LOG.info("User Info: " + user);
        //Grab Data for the year then filter down into month
        //range requested         
        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference("/pain_log_test/" + user.getUuid() + "/" + user.getYear());

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {
                Object object = ds.getValue(Object.class);
                JsonElement element = gson.toJsonTree(object);
                cleanData(element, user);
            }

            @Override
            public void onCancelled(DatabaseError de) {
                throw new UnsupportedOperationException("Not supported.");
            }
        }
        );
    }

    private void cleanData(JsonElement json, UserInfo user) {
        try {
            java.util.List<LogWrapper> wrapperList = new ArrayList<>();
            //Filter for data between the dates the asked for
            Date startDate = new SimpleDateFormat("dd/MM/yyyy").parse(user.getStart_Date());
            Date endDate = new SimpleDateFormat("dd/MM/yyyy").parse(user.getEnd_Date());
            //Cycle through json pulling out logs that exist
            //converting them into LogWrapper objects, only
            //adding the logs to the list if they are within
            //the date range specified
            Date wrapperDate;
            for (Map.Entry<String, JsonElement> entry : json.getAsJsonObject().entrySet()) {
                LogWrapper wrapper = gson.fromJson(entry.getValue(), LogWrapper.class);
                wrapperDate = new SimpleDateFormat("yyyy-MMM-dd").parse(wrapper.getDateString());
                if (wrapperDate.after(startDate) && wrapperDate.before(endDate)) {
                    LOG.info("Adding Wrapper: " + wrapper);
                    wrapperList.add(wrapper);
                }
            }
            produceReport(wrapperList, user);
        } catch (ParseException ex) {
            Emailer.sendExceptionToSupportAndLog(ex, LOG, ReportDAO.class);
        }
    }

    private void produceReport(java.util.List<LogWrapper> data, UserInfo user) {
        try {
            final File tempFile = File.createTempFile("mypainmanager", "report.pdf");
            LOG.info("Created Temporary File for pdf: " + tempFile.getAbsolutePath());
            PdfWriter writer = new PdfWriter(tempFile.getAbsolutePath());
            PdfDocument pdf = new PdfDocument(writer);
            Paragraph spacePara = new Paragraph("\n\n\n\n\n\n\n\n\n\n\n\n\n");
            Paragraph smallSpacePara = new Paragraph("\n\n");
            try (Document document = new Document(pdf)) {

                String imFile = Settings.getInstance().getLogoPath();
                ImageData imgData = ImageDataFactory.create(imFile);
                // Creating an Image object        
                Image image = new Image(imgData);
                image.setFixedPosition(195, 550);
                image.setWidth(200f);
                image.setMaxHeight(200f);
                document.add(image);
                //Add Some Spacing
                document.add(spacePara);
                //Add title
                Paragraph titlePara = new Paragraph("Your My-Pain-Manager Logs");
                titlePara.setFontSize(25);
                titlePara.setTextAlignment(TextAlignment.CENTER);
                document.add(titlePara);
                document.add(smallSpacePara);
                //Add Table
                float[] columnWidths = {150F, 150F, 150F, 150F, 150F, 150F};
                Table table = new Table(columnWidths);
                //Add Headers
                table.addCell(new Cell().add("Date"));
                table.addCell(new Cell().add("Time"));
                table.addCell(new Cell().add("Type"));
                table.addCell(new Cell().add("Rating"));
                table.addCell(new Cell().add("Meds Notes"));
                table.addCell(new Cell().add("Notes"));
                data.forEach((log) -> {
                    table.addCell(new Cell().add(log.getDateString()));
                    table.addCell(new Cell().add(log.getTimeString()));
                    table.addCell(new Cell().add(log.getType()));
                    table.addCell(new Cell().add(log.getRanking()));
                    table.addCell(new Cell().add(log.getMedsDescription()));
                    table.addCell(new Cell().add(log.getNotesDescription()));
                });
                document.add(table);
            }
            Emailer.sendPDFReport(user, tempFile);
            LOG.info("Email Sent Successfully");
            tempFile.deleteOnExit();
        } catch (IOException ex) {
            Emailer.sendExceptionToSupportAndLog(ex, LOG, ReportDAO.class);
        }
    }
}
