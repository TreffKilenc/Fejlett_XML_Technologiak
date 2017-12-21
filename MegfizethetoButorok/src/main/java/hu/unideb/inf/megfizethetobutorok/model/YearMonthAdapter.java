/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.inf.megfizethetobutorok.model;

import java.time.YearMonth;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 *
 * @author Alnitak
 */
public class YearMonthAdapter extends XmlAdapter<String, YearMonth> {

    @Override
    public String marshal(YearMonth v) throws Exception {
        return String.format("%d-%02d", v.getYear(), v.getMonthValue());
    }

    @Override
    public YearMonth unmarshal(String v) throws Exception {
        String[] yearAndMonth = v.split("-");
        return YearMonth.of(Integer.parseInt(yearAndMonth[0]), Integer.parseInt(yearAndMonth[1]));
    }
}
