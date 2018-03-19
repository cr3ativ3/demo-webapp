package com.demo.seb.gateway;

import java.util.Calendar;
import java.util.List;

import com.demo.seb.gateway.pojo.LBCurrencyRate;

public interface LietuvosBankasGateway {

    List<LBCurrencyRate> getCurrencyRatesFor(Calendar date);
}
