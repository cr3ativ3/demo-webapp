package com.demo.seb.gateway;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.demo.seb.gateway.exception.ApiException;
import com.demo.seb.gateway.pojo.LBCurrencyRate;

@Repository
public class LietuvosBankasGatewayImpl implements LietuvosBankasGateway {

    private static final String DATE_PARAM = "Date";
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String URL = "http://old.lb.lt/webservices/ExchangeRates/ExchangeRates.asmx/getExchangeRatesByDate";

    private RestTemplate restTemplate;

    @Autowired
    private List<HttpMessageConverter<?>> messageConverters;

    @Override
    public List<LBCurrencyRate> getCurrencyRatesFor(Calendar date) {
        ResponseEntity<List<LBCurrencyRate>> result = getRestTemplate().exchange(createRequest(date),
                new ParameterizedTypeReference<List<LBCurrencyRate>>(){});
        if (result.getStatusCode() == HttpStatus.OK) {
            return result.getBody();
        }
        throw new ApiException("Backend call responded with: " + result.getStatusCode());
    }

    private RequestEntity<?> createRequest(Calendar date) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        URI location = UriComponentsBuilder.fromHttpUrl(URL)
                .queryParam(DATE_PARAM, sdf.format(date.getTime()))
                .build().encode().toUri();
        return new RequestEntity<>(HttpMethod.GET, location);
    }

    private RestTemplate getRestTemplate() {
        if (restTemplate == null) {
            this.restTemplate = new RestTemplate(messageConverters);
        }
        return restTemplate;
    }
}