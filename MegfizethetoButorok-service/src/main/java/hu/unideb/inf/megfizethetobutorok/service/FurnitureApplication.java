/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.inf.megfizethetobutorok.service;

import org.restlet.Component;
import org.restlet.Restlet;
import org.restlet.Server;
import org.restlet.data.Protocol;
import org.restlet.routing.Router;

/**
 *
 * @author Alnitak
 */
public class FurnitureApplication extends org.restlet.Application {

    static {
        System.setProperty("org.restlet.engine.loggerFacadeClass", "org.restlet.ext.slf4j.Slf4jLoggerFacade");
    }

    public static void main(String[] args) {
        Component component = new Component();
        component.getDefaultHost().attach("/furniture", new FurnitureApplication());
        Server server = new Server(Protocol.HTTP, 8111, component);
        try {
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Restlet createInboundRoot() {
        Router router = new Router(getContext());
        router.setDefaultMatchingQuery(true);
        router.attach("/product/{product_number}", FurnitureResource.class);
        router.attach("/search?{query}", SearchResource.class);
        return router;
    }
}