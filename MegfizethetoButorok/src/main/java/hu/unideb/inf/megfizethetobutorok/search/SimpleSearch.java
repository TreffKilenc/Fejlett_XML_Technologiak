/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.inf.megfizethetobutorok.search;

import hu.unideb.inf.megfizethetobutorok.model.SearchResults;
import hu.unideb.inf.megfizethetobutorok.parser.SearchResultsParser;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 *
 * @author Alnitak
 */
public class SimpleSearch extends SearchResultsParser {

    private static final String SEARCH_URI = "http://megfizethetobutor.hu/keresesi-eredmenyek";

    public SimpleSearch() {
    }

    public SearchResults doSearch(String keyword, String categories_id, String pfrom, String pto) throws IOException {
        Document doc = Jsoup.connect(SEARCH_URI).userAgent("Mozilla")
                .data("main_page", "advanced_search_result")
                .data("inc_subcat", "1")
                .data("keyword", keyword)
                .data("categories_id", categories_id)
                .data("pfrom", pfrom)
                .data("pto", pto).get();
        return parse(doc);
    }
}