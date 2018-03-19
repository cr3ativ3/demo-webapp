package com.demo.seb.service;

import java.util.List;

import com.demo.seb.pojo.CurrencyRateDiff;

public interface CurrencyRateService {

    List<CurrencyRateDiff> calculateDiffFor(String dateStr);

}
