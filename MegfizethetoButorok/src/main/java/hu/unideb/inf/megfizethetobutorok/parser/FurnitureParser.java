/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.inf.megfizethetobutorok.parser;

import hu.unideb.inf.megfizethetobutorok.jaxb.JAXBUtil;
import hu.unideb.inf.megfizethetobutorok.model.Furniture;
import java.io.File;
import java.io.IOException;
import java.text.DateFormatSymbols;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Alnitak
 */
public class FurnitureParser {

    public FurnitureParser() {
    }

    public Furniture parse(String url) throws IOException {
        Document doc = Jsoup.connect(url).userAgent("Mozilla").get();
        Furniture furniture = parse(doc, false);
        furniture.setUri(url);
        return furniture;
    }

    public Furniture parse(File file) throws IOException {
        Document doc = Jsoup.parse(file, null);
        Furniture furniture = parse(doc, false);
        furniture.setUri(file.toURI().toString());
        return furniture;
    }

    public Furniture parse(Document doc, Boolean onlyItemNumber) throws IOException {
        Furniture furniture = new Furniture();
        try {
            furniture.setProduct_number(doc.select("p.item-number").first().text().replace("Cikkszám:", "").trim());
        } catch (Exception e) {
            System.out.println("A cikkszám nem található!");
        }
        if (onlyItemNumber) {
            return furniture;
        }

        try {
            furniture.setTitle(doc.select("h1[itemprop=name]").first().text().trim());
        } catch (Exception e) {
            System.out.println("A cim nem található!");
        }
        try {
            //IDE???
            Integer price = Integer.parseInt(doc.select("link[itemprop=price]").first().attr("content").trim());
            furniture.setPrice(price);
        } catch (Exception e) {
            System.out.println("Az ár nem található!");
        }
        try {
            String delivery_time_element = doc.select("p.delivery-time").first().attr("data-actualdeliverytime").trim();
            String[] delivery_times = delivery_time_element.split(" ");
            Integer year = Integer.parseInt(delivery_times[0]);
            String monthtext = delivery_times[1];
            DateFormatSymbols symbols = new DateFormatSymbols(new Locale("hu"));
            String[] monthNames = symbols.getMonths();
            Integer month = null;
            for (int i = 0; i < monthNames.length; ++i) {
                if (monthNames[i].equals(monthtext.toLowerCase())) {
                    month = ++i;
                    break;
                }
            }
            if (month != null) {
                furniture.setDelivery_time(YearMonth.of(year, month));
            }
        } catch (Exception e) {
            System.out.println("A szállítási idő nem található!");
        }

        ArrayList<String> properties = new ArrayList<String>();

        try {
            for (Element e : doc.select("ul.check > li.checked")) {
                String property = e.text().trim();
                properties.add(property);
            }
        } catch (Exception e) {
            System.out.println("Hiba a termékjellemzők kiolvasásakor!");
        }

        furniture.setProperties(properties);
        ArrayList<Furniture.Feature> features = new ArrayList<>();

        try {
            for (Element e : doc.select("div#productAttributes div.module")) {
                Furniture.Feature feature = new Furniture.Feature();
                feature.setName(e.select("h3.block-title").first().text().trim());
                ArrayList<Furniture.Service> options = new ArrayList<>();
                Elements elements;
                if (e.select("div.module-content li").isEmpty()) {
                    elements = e.select("div.module-content");
                } else {
                    elements = e.select("div.module-content li");
                }
                for (Element sub_e : elements) {
                    String service_element = sub_e.select("label").first().text().trim();
                    Pattern pattern = Pattern.compile("([^\\(]+)( \\( (.+)Ft \\))?");
                    Matcher matcher = pattern.matcher(service_element);
                    matcher.matches();
                    Furniture.Service service = new Furniture.Service();
                    service.setName(matcher.group(1).trim());
                    if (matcher.group(3) != null) {
                        service.setPrice(Integer.parseInt(matcher.group(3).replace(".", "").trim()));
                    }
                    // System.out.println(service);
                    options.add(service);
                }
                feature.setOptions(options);
                features.add(feature);
            }
        } catch (Exception e) {
            System.out.println("Hiba a featuer-ök kiolvasásakor.");
        }

        furniture.setFeatures(features);
        ArrayList<Furniture.Service> delivery_conditions = new ArrayList<>();

        try {
            for (Element e : doc.select("div#productAttributes div#delivery option")) {
                Furniture.Service service = new Furniture.Service();
                int index = e.text().indexOf('(');
                if (index != -1) {
                    service.setName(e.text().substring(0, index).trim());
                } else {
                    service.setName(e.text().trim());
                }
                service.setPrice(Integer.parseInt(e.attr("data-price")));
                delivery_conditions.add(service);
            }
        } catch (Exception e) {
            System.out.println("Hiba a szállítási feltételek kiolvasásakor!");
        }

        furniture.setDelivery_conditions(delivery_conditions);
        ArrayList<Furniture.Service> deliveries = new ArrayList<>();

        try {
            for (Element e : doc.select("div#productAttributes div#housing-delivery option")) {
                Furniture.Service service = new Furniture.Service();
                int index = e.text().indexOf('(');
                if (index != -1) {
                    service.setName(e.text().substring(0, index).trim());
                } else {
                    service.setName(e.text().trim());
                }
                service.setPrice(Integer.parseInt(e.attr("data-price")));
                deliveries.add(service);
            }
        } catch (Exception e) {
            System.out.println("Hiba a házhoz szállítás kiolvasásakor.");
        }

        furniture.setDeliveries(deliveries);
        ArrayList<Furniture.Description> descriptions = new ArrayList<>();
        ArrayList<String> units = Furniture.Description.getUnits();

        try {
            for (Element e : doc.select("div[itemprop=description] > div")) {
                String description_element = e.text().trim();
                Pattern pattern = Pattern.compile("([^:]+)(: ([^ ]+))?( .+)?");
                Matcher matcher = pattern.matcher(description_element);
                if (matcher.matches()) {
                    // System.out.println(descriptionString);                
                    Furniture.Description description = new Furniture.Description();
                    String name = matcher.group(1).trim();
                    if (!name.equals("")) {
                        description.setName(name);
                        if (matcher.group(3) != null) {
                            description.setDescription_value(matcher.group(3).trim());
                        }
                        if (matcher.group(4) != null && units.contains(matcher.group(4).trim())) {
                            description.setUnit(matcher.group(4).trim());
                        }
                        //System.out.println(description);
                        descriptions.add(description);
                    }
                }
            }
            for (Element e : doc.select("div[itemprop=description] > p")) {
                String[] htmls = e.html().split("<br>");
                for (String html : htmls) {
                    String description_element = html.replaceAll("&nbsp;", "").trim();
                    Pattern pattern = Pattern.compile("([^:]+)(: ([^ ]+))?( .+)?");
                    Matcher matcher = pattern.matcher(description_element);
                    if (!matcher.matches()) {
                        Furniture.Description description = new Furniture.Description();
                        String name = matcher.group(1).trim();
                        if (!name.equals("")) {
                            description.setName(name);
                            if (matcher.group(3) != null) {
                                description.setDescription_value(matcher.group(3).trim());
                            }
                            if (matcher.group(4) != null && units.contains(matcher.group(4).trim())) {
                                description.setUnit(matcher.group(4).trim());
                            }
                            //System.out.println(description);
                            descriptions.add(description);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Hiba történt a leírás kiolvasása közben!");
        }

        furniture.setDescriptions(descriptions);

        return furniture;
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.printf("Usage: java %s <url>\n", FurnitureParser.class.getName());
            System.exit(1);
        }
        try {
            Furniture furniture = new FurnitureParser().parse(args[0]);
            System.out.println(furniture);
            JAXBUtil.toXML(furniture, System.out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
