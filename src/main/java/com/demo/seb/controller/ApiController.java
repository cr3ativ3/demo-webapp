package com.demo.seb.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.HttpStatusCodeException;

import com.demo.seb.gateway.exception.ApiException;
import com.demo.seb.pojo.CurrencyRateDiff;
import com.demo.seb.pojo.Response;
import com.demo.seb.service.CurrencyRateService;

@Controller
public class ApiController {

    @Autowired
    private CurrencyRateService service;

    @GetMapping(path = "api/rates/{dateStr}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Response> getCurrencyPrices(@PathVariable String dateStr) {
        List<CurrencyRateDiff> result = service.calculateDiffFor(dateStr);
        Collections.sort(result, (d2, d1) -> d1.getDiff().compareTo(d2.getDiff()));
        return new ResponseEntity<Response>(new Response(result), HttpStatus.OK);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Response> handleInvalidRequest(Exception e) {
        return new ResponseEntity<>(new Response(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ ApiException.class, HttpStatusCodeException.class })
    public ResponseEntity<Response> handleGatewayFailure(Exception e) {
        return new ResponseEntity<>(new Response(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
