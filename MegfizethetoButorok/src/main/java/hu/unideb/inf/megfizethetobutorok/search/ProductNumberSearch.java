/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.inf.megfizethetobutorok.search;

import hu.unideb.inf.megfizethetobutorok.model.Furniture;
import hu.unideb.inf.megfizethetobutorok.parser.FurnitureParser;
import hu.unideb.inf.megfizethetobutorok.parser.SearchResultsParser;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 *
 * @author Alnitak
 */
public class ProductNumberSearch extends SearchResultsParser {

    private static final String SEARCH_URI = "http://megfizethetobutor.hu/keresesi-eredmenyek";

    public ProductNumberSearch() {
        super(1);
    }

    public Furniture doSearch(String productCode) throws IOException {
        Document doc = Jsoup.connect(SEARCH_URI).userAgent("Mozilla").data("keyword", productCode).data("main_page", "advanced_search_result").get();
        if (!doc.select("div.errors div:containsOwn(Sajnos nincs a keresési feltételnek megfelelő termék.)").isEmpty()) {
            return null;
        }
        Element element = doc.select("section#advanced-search div#product-list div[itemprop=itemListElement]").first();
        String uri = element.select("div[itemprop=name] > a").attr("href");
        Furniture furniture = new FurnitureParser().parse(Jsoup.connect(uri).userAgent("Mozilla").get(), false);
        furniture.setUri(uri);
        return furniture;
    }
}