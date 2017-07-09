package com.shop.webapp.facade;

import com.shop.webapp.Authority;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.inject.Inject;

@Stateless
@Named("authority")
public class AuthorityFacade extends AbstractFacade<Authority, String> {

    @Inject
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AuthorityFacade() {
        super(Authority.class);
    }
}
