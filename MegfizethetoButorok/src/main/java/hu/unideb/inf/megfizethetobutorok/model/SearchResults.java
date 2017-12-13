/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.inf.megfizethetobutorok.model;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 *
 * @author Alnitak
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SearchResults {

    @XmlAttribute(required = true)
    private int from;

    @XmlAttribute(required = true)
    private int to;

    @XmlElement(name = "item", required = false)
    private List<SearchResultItem> items;

    public SearchResults() {
    }

    public SearchResults(int from, int to, List<SearchResultItem> items) {
        this.from = from;
        this.to = to;
        this.items = items;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public List<SearchResultItem> getItems() {
        return items;
    }

    public void setItems(List<SearchResultItem> items) {
        this.items = items;
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}