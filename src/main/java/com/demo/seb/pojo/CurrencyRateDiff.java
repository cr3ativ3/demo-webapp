package com.demo.seb.pojo;

import java.math.BigDecimal;

public class CurrencyRateDiff {

    private String code;
    private BigDecimal prevRate;
    private BigDecimal currRate;
    private BigDecimal diff;
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public BigDecimal getPrevRate() {
        return prevRate;
    }
    public void setPrevRate(BigDecimal prevRate) {
        this.prevRate = prevRate;
    }
    public BigDecimal getCurrRate() {
        return currRate;
    }
    public void setCurrRate(BigDecimal currRate) {
        this.currRate = currRate;
    }
    public BigDecimal getDiff() {
        return diff;
    }
    public void setDiff(BigDecimal diff) {
        this.diff = diff;
    }
}
