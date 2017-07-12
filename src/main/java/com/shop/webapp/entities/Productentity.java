/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shop.webapp.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Yasas Sri
 */
@Entity
@Table(name = "PRODUCTENTITY")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PRODUCTENTITY.findAll", query = "SELECT p FROM Productentity p")
    , @NamedQuery(name = "PRODUCTENTITY.findById", query = "SELECT p FROM Productentity p WHERE p.id = :id")
    , @NamedQuery(name = "PRODUCTENTITY.findByProductqty", query = "SELECT p FROM Productentity p WHERE p.productqty = :productqty")
    , @NamedQuery(name = "PRODUCTENTITY.findByProductname", query = "SELECT p FROM Productentity p WHERE p.productname = :productname")
    , @NamedQuery(name = "PRODUCTENTITY.findByProductprice", query = "SELECT p FROM Productentity p WHERE p.productprice = :productprice")})
public class Productentity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Long id;
    @Size(max = 255)
    @Column(name = "PRODUCTQTY")
    private String productqty;
    @Size(max = 255)
    @Column(name = "PRODUCTNAME")
    private String productname;
    @Size(max = 255)
    @Column(name = "PRODUCTPRICE")
    private String productprice;

    public Productentity() {
    }

    public Productentity(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductqty() {
        return productqty;
    }

    public void setProductqty(String productqty) {
        this.productqty = productqty;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getProductprice() {
        return productprice;
    }

    public void setProductprice(String productprice) {
        this.productprice = productprice;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Productentity)) {
            return false;
        }
        Productentity other = (Productentity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.shop.webapp.entities.Productentity[ id=" + id + " ]";
    }
    
}
