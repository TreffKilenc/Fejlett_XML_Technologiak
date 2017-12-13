/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.inf.megfizethetobutorok.service;

import hu.unideb.inf.megfizethetobutorok.model.SearchResults;
import hu.unideb.inf.megfizethetobutorok.search.SimpleSearch;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

/**
 *
 * @author Alnitak
 */
public class SearchResource extends ServerResource {

    @Get("json|xml")
    public SearchResults represent() throws IOException {
        String keyword = getQueryValue("keyword") != null ? getQueryValue("keyword") : "";
        String categories_id = "";
        if (getQueryValue("category") != null) {
            try {
                Document doc = Jsoup.connect("http://megfizethetobutor.hu/index.php").data("main_page", "advanced_search").get();
                categories_id = doc.select("select[name=categories_id] option:containsOwn(" + getQueryValue("category") + ")").first().attr("value").trim();
            } catch (Exception ex) {
            }
        }
        String pfrom = getQueryValue("pfrom") != null ? getQueryValue("pfrom") : "";
        String pto = getQueryValue("pto") != null ? getQueryValue("pto") : "";
        return new SimpleSearch().doSearch(keyword, categories_id, pfrom, pto);
    }
}