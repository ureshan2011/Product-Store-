package com.shop.webapp.service;

import com.shop.webapp.facade.AuthorityFacade;
import com.shop.webapp.facade.UserFacade;
import com.shop.webapp.security.AuthoritiesConstants;
import com.shop.webapp.security.PasswordEncoder;
import com.shop.webapp.User;
import com.shop.webapp.Authority;
import com.shop.webapp.security.SecurityUtils;
import com.shop.webapp.util.RandomUtil;
import com.shop.webapp.security.AuthenticationException;
import com.shop.webapp.security.UserAuthenticationToken;
import com.shop.webapp.controller.dto.UserDTO;
import java.time.Instant;
import java.time.ZoneId;
import org.slf4j.Logger;
import java.time.ZonedDateTime;
import javax.inject.Inject;
import java.util.*;
import javax.ejb.Stateless;
import static java.util.stream.Collectors.toSet;

/**
 * Service class for managing users.
 */
@Stateless
public class UserService {

    @Inject
    private Logger log;

    @Inject
    private SecurityUtils securityUtils;

    @Inject
    private PasswordEncoder passwordEncoder;

    @Inject
    private UserFacade userFacade;

    @Inject
    private AuthorityFacade authorityFacade;

    public Optional<User> activateRegistration(String key) {
        log.debug("Activating user for activation key {}", key);
        return userFacade.findOneByActivationKey(key)
                .map(user -> {
                    // activate given user for the registration key.
                    user.setActivated(true);
                    user.setActivationKey(null);
                    userFacade.edit(user);
                    log.debug("Activated user: {}", user);
                    return user;
                });
    }

    public Optional<User> completePasswordReset(String newPassword, String key) {
        log.debug("Reset user password for reset key {}", key);

        return userFacade.findOneByResetKey(key)
                .filter(user -> {
                    ZonedDateTime oneDayAgo = ZonedDateTime.now().minusHours(24);
                    ZonedDateTime resetDate = ZonedDateTime.ofInstant(Instant.ofEpochMilli(user.getResetDate().getTime()), ZoneId.systemDefault());
                    return resetDate.isAfter(oneDayAgo);
                })
                .map(user -> {
                    user.setPassword(passwordEncoder.encode(newPassword));
                    user.setResetKey(null);
                    user.setResetDate(null);
                    userFacade.edit(user);
                    return user;
                });
    }

    public Optional<User> requestPasswordReset(String mail) {
        return userFacade.findOneByEmail(mail)
                .filter(User::getActivated)
                .map(user -> {
                    user.setResetKey(RandomUtil.generateResetKey());
                    user.setResetDate(new Date());
                    userFacade.edit(user);
                    return user;
                });
    }

    public User createUser(String login, String password, String firstName, String lastName, String email,
            String langKey) {

        User newUser = new User();
        Authority authority = authorityFacade.find(AuthoritiesConstants.USER);
        Set<Authority> authorities = new HashSet<>();
        String encryptedPassword = passwordEncoder.encode(password);
        newUser.setLogin(login);
        // new user gets initially a generated password
        newUser.setPassword(encryptedPassword);
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setEmail(email);
        newUser.setLangKey(langKey);
        // new user is not active
        newUser.setActivated(false);
        // new user gets registration key
        newUser.setActivationKey(RandomUtil.generateActivationKey());
        authorities.add(authority);
        newUser.setAuthorities(authorities);
        String currentLogin = securityUtils.getCurrentUserLogin();
        newUser.setCreatedBy(currentLogin != null ? currentLogin : AuthoritiesConstants.ANONYMOUS);
        userFacade.create(newUser);
        log.debug("Created Information for User: {}", newUser);
        return newUser;
    }

    public User createUser(UserDTO userDTO) {
        User user = new User();
        user.setLogin(userDTO.getLogin());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        if (userDTO.getLangKey() == null) {
            user.setLangKey("en"); // default language
        } else {
            user.setLangKey(userDTO.getLangKey());
        }
        if (userDTO.getAuthorities() != null) {
            user.setAuthorities(userDTO.getAuthorities().stream().map(authorityFacade::find).collect(toSet()));
        }
        String encryptedPassword = passwordEncoder.encode(RandomUtil.generatePassword());
        user.setPassword(encryptedPassword);
        user.setResetKey(RandomUtil.generateResetKey());
        user.setResetDate(new Date());
        user.setActivated(true);
        userFacade.create(user);
        log.debug("Created Information for User: {}", user);
        return user;
    }

    public void updateUser(String firstName, String lastName, String email, String langKey) {
        userFacade.findOneByLogin(securityUtils.getCurrentUserLogin()).ifPresent(u -> {
            u.setFirstName(firstName);
            u.setLastName(lastName);
            u.setEmail(email);
            u.setLangKey(langKey);
            userFacade.edit(u);
            log.debug("Changed Information for User: {}", u);
        });
    }

    public void deleteUserInformation(String login) {
        userFacade.findOneByLogin(login).ifPresent(u -> {
            userFacade.remove(u);
            log.debug("Deleted User: {}", u);
        });
    }

    public void changePassword(String password) {
        userFacade.findOneByLogin(securityUtils.getCurrentUserLogin()).ifPresent(u -> {
            String encryptedPassword = passwordEncoder.encode(password);
            u.setPassword(encryptedPassword);
            userFacade.edit(u);
            log.debug("Changed password for User: {}", u);
        });
    }

    public Optional<User> getUserWithAuthoritiesByLogin(String login) {
        return userFacade.findOneWithAuthoritiesByLogin(login);
    }

    public User getUserWithAuthorities(Long id) {
        return userFacade.findOneWithAuthoritiesById(id).orElse(null);
    }

    public User getUserWithAuthorities() {
        if (securityUtils.getCurrentUserLogin() == null) {
            return null;
        }
        return userFacade.findOneWithAuthoritiesByLogin(securityUtils.getCurrentUserLogin()).orElse(null);
    }

    public User authenticate(UserAuthenticationToken authenticationToken) throws AuthenticationException {
        Optional<User> userOptional = userFacade.findOneWithAuthoritiesByLogin(authenticationToken.getPrincipal());
        if (userOptional.isPresent() && userOptional.get().getActivated() && userOptional.get().getPassword().equals(passwordEncoder.encode(authenticationToken.getCredentials()))) {
            return userOptional.get();
        } else {
            throw new AuthenticationException();
        }
    }

}
