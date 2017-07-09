package com.shop.webapp;

import java.util.Date;
import javax.inject.Inject;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import com.shop.webapp.security.SecurityUtils;

/**
 * Entity listener class for audit info
 */
public class AuditListner {

    //Issue : https://issues.jboss.org/browse/WFLY-2387
    //@Inject
    //private SecurityUtils securityUtils;
    @PrePersist
    void onCreate(AbstractAuditingEntity entity) {
        entity.setCreatedDate(new Date());
        //entity.setCreatedBy(securityUtils.getCurrentUserLogin());
    }

    @PreUpdate
    void onUpdate(AbstractAuditingEntity entity) {
        entity.setLastModifiedDate(new Date());
        //entity.setLastModifiedBy(securityUtils.getCurrentUserLogin());
    }
}
