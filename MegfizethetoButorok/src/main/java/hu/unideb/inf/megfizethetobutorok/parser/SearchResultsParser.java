/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.inf.megfizethetobutorok.parser;

import hu.unideb.inf.megfizethetobutorok.model.SearchResultItem;
import hu.unideb.inf.megfizethetobutorok.model.SearchResults;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Alnitak
 */
public class SearchResultsParser {

    private static Logger logger = LoggerFactory.getLogger(SearchResultsParser.class);

    public static final int MAX_ITEMS = 5;

    private int maxItems = MAX_ITEMS;

    public SearchResultsParser() {
    }

    public SearchResultsParser(int maxItems) {
        setMaxItems(maxItems);
    }

    public int getMaxItems() {
        return maxItems;
    }

    public void setMaxItems(int maxItems) {
        this.maxItems = maxItems;
    }

    private List<SearchResultItem> extractItems(Document doc) throws IOException {
        List<SearchResultItem> items = new LinkedList<SearchResultItem>();
        int i = 0;
        for (Element element : doc.select("section#advanced-search > div#product-list div[itemprop=itemListElement] > div[itemprop=itemListElement]")) {
            if(i==MAX_ITEMS){
                break;
            }

            SearchResultItem searchResultItem = new SearchResultItem();
            String uri = null;
            
            try {
                uri = element.select("a[itemprop=url]").get(0).attr("href").trim();
            } catch (Exception e) {
                System.out.println("Az uri nem található!");
            }
            
            searchResultItem.setUri(uri);
            String image = null;
            
            try {
                image = element.select("div.product-image img").get(0).attr("src").trim();
            } catch (Exception e) {
                System.out.println("A kép nem található!");
            }

            searchResultItem.setImage(image);
            String title = null;
            
            try {
                title = element.select("div[itemprop=name]").get(0).text().trim();
            } catch (Exception e) {
                System.out.println("A cím nem található!");
            }
            
            searchResultItem.setTitle(title);
            Integer price = null;
            
            try{
                price = Integer.parseInt(element.select("div.price span").get(0).text().replaceAll("[^-?0-9]+", ""));
            } catch (Exception e) {
                System.out.println("Az ár nem található!.");
            }
            
            searchResultItem.setPrice(price);
            ArrayList<String> product_properties = new ArrayList<>();
            
            try {
                for (Element e : element.select("div.product-info ul.check > li.checked")) {
                    String product_property = e.text().trim();
                    product_properties.add(product_property);
                }
            } catch (Exception e) {
                System.out.println("Hiba történt a termékjellemzők kiolvasa közben!");
            }
            
            searchResultItem.setProperties(product_properties);
            String product_number = null;
            
            try {
                Document sub_doc = Jsoup.connect(uri).userAgent("Mozilla").get();
                product_number = new FurnitureParser().parse(sub_doc,true).getProduct_number();
            } catch (Exception e) {
                System.out.println("Hiba történt a termékkód kiolvasása közben!");
            }
            
            searchResultItem.setProduct_number(product_number);
            items.add(searchResultItem);
            ++i;
        }
        return items;
    }

    private Document getNextPage(Document doc) throws IOException {
        String nextPage = null;
        try {
            nextPage = doc.select("div.pagination a[title~=Következő]").get(0).attr("href");
            logger.info("Next page: {}", nextPage);
        } catch (Exception e) {
            // no more pages
        }        
        return nextPage != null ? Jsoup.connect(nextPage).userAgent("Mozilla").get() : null;
    }

    public SearchResults parse(Document doc) throws IOException {
        List<SearchResultItem> items = new LinkedList<SearchResultItem>();
        loop:
        while (doc != null) {
            for (SearchResultItem item : extractItems(doc)) {
                if (0 <= maxItems && maxItems <= items.size()) {
                    break loop;
                }
                items.add(item);
            }
            if (0 <= maxItems && maxItems <= items.size()) {
                break;
            }
            doc = getNextPage(doc);
        }
        return new SearchResults(1, items.size(), items);
    }
}