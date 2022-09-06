package com.interview.controller;

import com.interview.model.CustomResponse;
import com.interview.service.DataFetcher;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
public class RestDataController {

    @Autowired
    DataFetcher dataFetcher;


    @GetMapping(value = "/", produces = "text/csv")
    public ResponseEntity<Resource> fetch(){
        log.info("Received the request");
        List<List<String>> csvBody = new ArrayList<>();
        CustomResponse customResponse;
        try {
           customResponse = dataFetcher.fetchData();
            csvBody.add(List.of(customResponse.getLocationName(), customResponse.getTime(),
                    String.valueOf(Double.valueOf(customResponse.getAmount())), customResponse.getStatus()));
        } catch (RuntimeException ex) {
            csvBody.add(List.of(ex.getMessage()));
        }

        String[] csvHeader = {
                "Location", "Time", "Rainfall Amount", "Raining/Not Raining"
        };

        ByteArrayInputStream byteArrayOutputStream;

        // closing resources by using a try with resources
        // https://www.baeldung.com/java-try-with-resources
        try (
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                // defining the CSV printer
                CSVPrinter csvPrinter = new CSVPrinter(
                        new PrintWriter(out),
                        // withHeader is optional
                        CSVFormat.DEFAULT.withHeader(csvHeader)
                );
        ) {
            // populating the CSV content
            for (List<String> record : csvBody)
                csvPrinter.printRecord(record);

            // writing the underlying stream
            csvPrinter.flush();

            byteArrayOutputStream = new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }

        InputStreamResource fileInputStream = new InputStreamResource(byteArrayOutputStream);

        String csvFileName = "rainfall.csv";

        // setting HTTP headers
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + csvFileName);
        // defining the custom Content-Type
        headers.set(HttpHeaders.CONTENT_TYPE, "text/csv");

        return new ResponseEntity<>(
                fileInputStream,
                headers,
                HttpStatus.OK
        );

    }

}
