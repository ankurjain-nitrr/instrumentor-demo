package com.ankur.instrumentordemo.controller;

import com.ankur.instrumentor.TrackMetrics;
import com.ankur.instrumentordemo.model.HelloWorld;
import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.exporter.common.TextFormat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

@RestController
@RequestMapping("/")
@Slf4j
public class HelloWorldController {

    @TrackMetrics
    @GetMapping("hello")
    public ResponseEntity<HelloWorld> hello(@RequestParam String name) {
        return new ResponseEntity<>(new HelloWorld(name), HttpStatus.OK);
    }

    @GetMapping("metrics")
    public ResponseEntity<String> metrics() {
        String response = "";
        Writer writer = new StringWriter();
        try {
            TextFormat.writeFormat(TextFormat.CONTENT_TYPE_004, writer,
                    CollectorRegistry.defaultRegistry.filteredMetricFamilySamples(s -> true));
            response = writer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
