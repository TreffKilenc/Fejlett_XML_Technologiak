/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.inf.megfizethetobutorok.model;

import java.time.YearMonth;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 *
 * @author Alnitak
 */
@javax.xml.bind.annotation.XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@javax.xml.bind.annotation.XmlType(
        propOrder = {
            "title",
            "product_number",
            "price",
            "delivery_time",
            "properties",
            "features",
            "delivery_conditions",
            "deliveries",
            "descriptions"
        }
)

public class Furniture {

    @XmlAttribute(required = true)
    private String uri;

    @XmlElement(name = "title", required = true)
    private String title;

    @XmlElement(name = "product_number", required = true)
    private String product_number;

    @XmlElement(name = "price", required = true)
    private Integer price;

    @XmlElement(name = "delivery_time", required = false)
    @XmlJavaTypeAdapter(YearMonthAdapter.class)
    private YearMonth delivery_time;

    @XmlElementWrapper
    @XmlElement(name = "property", required = true)
    private ArrayList<String> properties;

    @XmlElementWrapper
    @XmlElement(name = "feature", required = true)
    private ArrayList<Feature> features;

    @XmlElementWrapper
    @XmlElement(name = "delivery_condition", required = true)
    private ArrayList<Service> delivery_conditions;

    @XmlElementWrapper
    @XmlElement(name = "delivery", required = true)
    private ArrayList<Service> deliveries;

    @XmlElementWrapper
    @XmlElement(name = "description", required = false)
    private ArrayList<Description> descriptions;

    public Furniture() {
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
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

    public YearMonth getDelivery_time() {
        return delivery_time;
    }

    public void setDelivery_time(YearMonth delivery_time) {
        this.delivery_time = delivery_time;
    }

    public ArrayList<String> getProperties() {
        return properties;
    }

    public void setProperties(ArrayList<String> properties) {
        this.properties = properties;
    }

    public ArrayList<Feature> getFeatures() {
        return features;
    }

    public void setFeatures(ArrayList<Feature> features) {
        this.features = features;
    }

    public ArrayList<Service> getDelivery_conditions() {
        return delivery_conditions;
    }

    public void setDelivery_conditions(ArrayList<Service> delivery_conditions) {
        this.delivery_conditions = delivery_conditions;
    }

    public ArrayList<Service> getDeliveries() {
        return deliveries;
    }

    public void setDeliveries(ArrayList<Service> deliveries) {
        this.deliveries = deliveries;
    }

    public ArrayList<Description> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(ArrayList<Description> descriptions) {
        this.descriptions = descriptions;
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @javax.xml.bind.annotation.XmlType(namespace = "http://www.inf.unideb.hu/Furniture")
    public static class Description {
        
        @XmlValue
        private String name;

        @XmlAttribute(required = false)
        private String unit;

        @XmlAttribute(required = false)
        private String description_value;

        public Description() {
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getDescription_value() {
            return description_value;
        }

        public void setDescription_value(String description_value) {
            this.description_value = description_value;
        }

        public static ArrayList<String> getUnits() {
            ArrayList<String> units = new ArrayList<>();
            units.add("kg");
            units.add("cm");
            return units;
        }

        public String toString() {
            return ReflectionToStringBuilder.toString(this);
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @javax.xml.bind.annotation.XmlType(namespace = "http://www.inf.unideb.hu/Furniture")
    public static class Feature {

        @XmlElement(required = true)
        private String name;

        @XmlElementWrapper
        @XmlElement(name = "option", required = true)
        private ArrayList<Service> options;

        public Feature() {
        }

        public Feature(String name, ArrayList<Service> options) {
            this.name = name;
            this.options = options;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public ArrayList<Service> getOptions() {
            return options;
        }

        public void setOptions(ArrayList<Service> options) {
            this.options = options;
        }

        public String toString() {
            return ReflectionToStringBuilder.toString(this);
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @javax.xml.bind.annotation.XmlType(namespace = "http://www.inf.unideb.hu/Furniture")
    public static class Service {

        @XmlValue
        private String name;

        @XmlAttribute(required = true)
        private Integer price;

        public Service() {
        }

        public Service(String name, Integer price) {
            this.name = name;
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getPrice() {
            return price;
        }

        public void setPrice(Integer price) {
            this.price = price;
        }

        public String toString() {
            return ReflectionToStringBuilder.toString(this);
        }
    }
}
