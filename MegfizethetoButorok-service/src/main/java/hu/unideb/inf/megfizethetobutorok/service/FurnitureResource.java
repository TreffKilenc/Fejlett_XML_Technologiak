/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.inf.megfizethetobutorok.service;

import hu.unideb.inf.megfizethetobutorok.model.Furniture;
import hu.unideb.inf.megfizethetobutorok.search.ProductNumberSearch;
import java.io.IOException;
import org.restlet.data.Status;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

/**
 *
 * @author Alnitak
 */
public class FurnitureResource extends ServerResource {

    @Get("json|xml")
    public Furniture represent() throws IOException {
	String product_number = getAttribute("product_number");
	Furniture furniture = new ProductNumberSearch().doSearch(product_number);
	if (furniture == null)
            throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND);
	return furniture;
    }
}