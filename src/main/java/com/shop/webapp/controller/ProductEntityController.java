package com.shop.webapp.controller;

import com.shop.webapp.ProductEntity;
import com.shop.webapp.facade.ProductEntityFacade;
import com.shop.webapp.controller.util.HeaderUtil;
import com.shop.webapp.security.Secured;
import org.slf4j.Logger;
import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 * REST controller for managing ProductEntity.
 */
@Path("/api/product-entity")
@Secured
public class ProductEntityController {

    @Inject
    private Logger log;

    @Inject
    private ProductEntityFacade productEntityFacade;

    /**
     * POST : Create a new productEntity.
     *
     * @param productEntity the productEntity to create
     * @return the Response with status 201 (Created) and with body the new
     * productEntity, or with status 400 (Bad Request) if the productEntity has
     * already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @POST
    public Response createProductEntity(ProductEntity productEntity) throws URISyntaxException {
        log.debug("REST request to save ProductEntity : {}", productEntity);
        productEntityFacade.create(productEntity);
        return HeaderUtil.createEntityCreationAlert(Response.created(new URI("/resources/api/product-entity/" + productEntity.getId())),
                "productEntity", productEntity.getId().toString())
                .entity(productEntity).build();
    }

    /**
     * PUT : Updates an existing productEntity.
     *
     * @param productEntity the productEntity to update
     * @return the Response with status 200 (OK) and with body the updated
     * productEntity, or with status 400 (Bad Request) if the productEntity is
     * not valid, or with status 500 (Internal Server Error) if the
     * productEntity couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PUT
    public Response updateProductEntity(ProductEntity productEntity) throws URISyntaxException {
        log.debug("REST request to update ProductEntity : {}", productEntity);
        productEntityFacade.edit(productEntity);
        return HeaderUtil.createEntityUpdateAlert(Response.ok(), "productEntity", productEntity.getId().toString())
                .entity(productEntity).build();
    }

    /**
     * GET : get all the productEntities.
     *
     * @return the Response with status 200 (OK) and the list of productEntities
     * in body
     *
     */
    @GET
    public List<ProductEntity> getAllProductEntities() {
        log.debug("REST request to get all ProductEntities");
        List<ProductEntity> productEntities = productEntityFacade.findAll();
        return productEntities;
    }

    /**
     * GET /:id : get the "id" productEntity.
     *
     * @param id the id of the productEntity to retrieve
     * @return the Response with status 200 (OK) and with body the
     * productEntity, or with status 404 (Not Found)
     */
    @Path("/{id}")
    @GET
    public Response getProductEntity(@PathParam("id") Long id) {
        log.debug("REST request to get ProductEntity : {}", id);
        ProductEntity productEntity = productEntityFacade.find(id);
        return Optional.ofNullable(productEntity)
                .map(result -> Response.status(Response.Status.OK).entity(productEntity).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    /**
     * DELETE /:id : remove the "id" productEntity.
     *
     * @param id the id of the productEntity to delete
     * @return the Response with status 200 (OK)
     */
    @Path("/{id}")
    @DELETE
    public Response removeProductEntity(@PathParam("id") Long id) {
        log.debug("REST request to delete ProductEntity : {}", id);
        productEntityFacade.remove(productEntityFacade.find(id));
        return HeaderUtil.createEntityDeletionAlert(Response.ok(), "productEntity", id.toString()).build();
    }

}
