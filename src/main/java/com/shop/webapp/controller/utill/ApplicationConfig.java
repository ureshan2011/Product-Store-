/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shop.webapp.controller.utill;

import java.util.HashSet;
import java.util.Set;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import javax.ws.rs.core.Application;

/**
 *
 * @author Yasas Sri
 */
@javax.ws.rs.ApplicationPath("resources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }


    @Override
    public Set<Object> getSingletons() {
        final Set<Object> instances = new HashSet<>();
        instances.add(new JacksonJsonProvider());
        return instances;
    }

    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(com.shop.webapp.controller.AccountController.class);
        resources.add(com.shop.webapp.controller.ProductEntityController.class);
        resources.add(com.shop.webapp.controller.UserController.class);
        resources.add(com.shop.webapp.controller.UserJWTController.class);
        resources.add(com.shop.webapp.entities.service.ProductentityFacadeREST.class);
        resources.add(com.shop.webapp.security.SecurityUtils.class);
        resources.add(com.shop.webapp.security.jwt.JWTAuthenticationFilter.class);
    }
    
}
