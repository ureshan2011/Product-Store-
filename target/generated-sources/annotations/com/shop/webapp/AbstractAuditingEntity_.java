package com.shop.webapp;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-07-09T21:38:28")
@StaticMetamodel(AbstractAuditingEntity.class)
public abstract class AbstractAuditingEntity_ { 

    public static volatile SingularAttribute<AbstractAuditingEntity, Date> createdDate;
    public static volatile SingularAttribute<AbstractAuditingEntity, String> createdBy;
    public static volatile SingularAttribute<AbstractAuditingEntity, Date> lastModifiedDate;
    public static volatile SingularAttribute<AbstractAuditingEntity, String> lastModifiedBy;

}