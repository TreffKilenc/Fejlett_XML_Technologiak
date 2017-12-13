/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.inf.megfizethetobutorok.model;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 *
 * @author Alnitak
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
        propOrder = {
            "title",
            "product_number",
            "price",
            "properties"
        }
)
public class SearchResultItem {

    @XmlAttribute(required = true)
    private String uri;

    @XmlAttribute(required = false)
    private String image;

    @XmlElement(name = "title", required = true)
    private String title;

    @XmlElement(name = "product_number", required = true)
    private String product_number;

    @XmlElement(name = "price", required = true)
    private Integer price;

    @XmlElementWrapper
    @XmlElement(name = "property", required = true)
    private ArrayList<String> properties;

    public SearchResultItem() {
    }

    public SearchResultItem(String uri, String image, String title, String product_number, Integer price, ArrayList<String> properties) {
        this.uri = uri;
        this.image = image;
        this.title = title;
        this.product_number = product_number;
        this.price = price;
        this.properties = properties;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProduct_number() {
        return product_number;
    }

    public void setProduct_number(String product_number) {
        this.product_number = product_number;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public ArrayList<String> getProperties() {
        return properties;
    }

    public void setProperties(ArrayList<String> properties) {
        this.properties = properties;
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}