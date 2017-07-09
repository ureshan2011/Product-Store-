package com.shop.webapp.facade;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.inject.Inject;
import com.shop.webapp.ProductEntity;

@Stateless
@Named("productEntity")
public class ProductEntityFacade extends AbstractFacade<ProductEntity, Long> {

    @Inject
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProductEntityFacade() {
        super(ProductEntity.class);
    }

}
