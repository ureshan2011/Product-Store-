package com.shop.webapp;

import com.shop.webapp.Authority;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-07-09T21:38:28")
@StaticMetamodel(User.class)
public class User_ extends AbstractAuditingEntity_ {

    public static volatile SingularAttribute<User, String> lastName;
    public static volatile SingularAttribute<User, String> firstName;
    public static volatile SingularAttribute<User, String> password;
    public static volatile SingularAttribute<User, Date> resetDate;
    public static volatile SingularAttribute<User, String> langKey;
    public static volatile SingularAttribute<User, Long> id;
    public static volatile SingularAttribute<User, String> login;
    public static volatile SingularAttribute<User, String> activationKey;
    public static volatile SingularAttribute<User, String> resetKey;
    public static volatile SetAttribute<User, Authority> authorities;
    public static volatile SingularAttribute<User, String> email;
    public static volatile SingularAttribute<User, Boolean> activated;

}