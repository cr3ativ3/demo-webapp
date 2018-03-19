package com.demo.seb.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.seb.gateway.LietuvosBankasGateway;
import com.demo.seb.gateway.pojo.LBCurrencyRate;
import com.demo.seb.pojo.CurrencyRateDiff;

@Service
public class CurrencyRateServiceImpl implements CurrencyRateService{

    private static final String DATE_FORMAT_ERROR_MSG = "Cannot parse %s to date. Format should be year-month-day (%s)";

    private static final String DATE_FORMAT = "yyyy-MM-dd";

    @Autowired
    private LietuvosBankasGateway gateway;

    @Override
    public List<CurrencyRateDiff> calculateDiffFor(String dateStr) {
        Calendar date = toCalendar(dateStr);
        validate(date);
        List<LBCurrencyRate> ratesToday = gateway.getCurrencyRatesFor(date);
        date.add(Calendar.DAY_OF_MONTH, -1);
        List<LBCurrencyRate> ratesYesterday = gateway.getCurrencyRatesFor(date);
        return calculateDiff(ratesYesterday, ratesToday);
    }

    private List<CurrencyRateDiff> calculateDiff(List<LBCurrencyRate> yRates,
            List<LBCurrencyRate> tRates) {
        List<CurrencyRateDiff> result = new ArrayList<>(tRates.size());
        for (LBCurrencyRate rate : tRates) {
            CurrencyRateDiff diff = new CurrencyRateDiff();
            diff.setCode(rate.getCurrency());
            diff.setCurrRate(normalize(rate));
            LBCurrencyRate prevRate = getByCode(yRates, rate.getCurrency());
            if (prevRate == null) {
                continue;
            }
            diff.setPrevRate(normalize(prevRate));
            diff.setDiff(diff.getCurrRate().subtract(diff.getPrevRate()));
            result.add(diff);
        }
        return result;
    }

    private LBCurrencyRate getByCode(List<LBCurrencyRate> rates, String currency) {
        for (LBCurrencyRate rate : rates) {
            if (currency.equals(rate.getCurrency())) {
                return rate;
            }
        }
        return null;
    }

    private BigDecimal normalize(LBCurrencyRate rate) {
        return rate.getRate().divide(BigDecimal.valueOf(rate.getQuantity()));
    }

    private Calendar toCalendar(String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty() || !dateStr.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
            throw new IllegalArgumentException(String.format(DATE_FORMAT_ERROR_MSG, dateStr, DATE_FORMAT));
        }
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        Date d = null;
        try {
            sdf.setLenient(false);
            d = sdf.parse(dateStr);
        } catch (ParseException e) {
            throw new IllegalArgumentException(String.format(DATE_FORMAT_ERROR_MSG, dateStr, DATE_FORMAT));
        }
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        cal.setTime(d);
        return cal;
    }

    private void validate(Calendar date) {
        Calendar maxDate = Calendar.getInstance();
        maxDate.clear();
        maxDate.set(2014, 11, 31);
        Calendar minDate = Calendar.getInstance();
        minDate.clear();
        minDate.set(1993, 5, 26);
        if (date.compareTo(minDate) < 0 || date.compareTo(maxDate) > 0) {
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            throw new IllegalArgumentException(
                    String.format("Date must be between %s and %s",
                            sdf.format(minDate.getTime()),
                            sdf.format(maxDate.getTime())));
        }
    }
}