package com.tungstun.security.domain.user;

import com.tungstun.common.phonenumber.PhoneNumber;
import com.tungstun.common.security.exception.CannotAuthenticateException;
import com.tungstun.common.security.exception.NotAuthorizedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "\"user\"")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "mail", unique = true)
    private String mail;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "phone_number")
    @Embedded
    private PhoneNumber phoneNumber;

    @SuppressWarnings("java:S1948")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Authorization> authorizations;

    public User() {
    }

    public User(String username, String password, String mail, String firstName, String lastName, String phoneNumber, List<Authorization> authorizations) {
        this.username = username;
        this.password = password;
        this.mail = mail;
        this.firstName = firstName;
        this.lastName = lastName;
        this.authorizations = authorizations;
        this.phoneNumber = new PhoneNumber(phoneNumber);
    }

    public void canAuthenticate() {
        if (isAccountNonExpired())
            throw new CannotAuthenticateException("Account expired. An expired account cannot be authenticated.");
        if (isAccountNonLocked())
            throw new CannotAuthenticateException("Account locked. A locked account cannot be authenticated.");
        if (isCredentialsNonExpired())
            throw new CannotAuthenticateException("Account credentials expired. Expired credentials prevent authentication.");
        if (isEnabled())
            throw new CannotAuthenticateException("Account disabled. A disabled account cannot be authenticated.");
    }

    public boolean newBarAuthorization(Long barId) {
        return addAuthorization(barId, Role.OWNER);
    }

    public boolean authorizeUser(User user, Long barId, Role role) {
        if (!isOwner(barId)) throw new NotAuthorizedException("User has to be Owner of bar to authorize other users");
        if (this.equals(user)) throw new IllegalArgumentException("Cannot change your own bar role");
        if (role == Role.OWNER) throw new IllegalArgumentException("Cannot make an other person than yourself owner");
        return user.addAuthorization(barId, role);
    }

    private boolean isOwner(Long barId) {
        return authorizations.stream()
                .filter(authorization -> authorization.getBarId().equals(barId))
                .anyMatch(authorization -> authorization.getRole() == Role.OWNER);
    }

    private boolean addAuthorization(Long barId, Role role) {
        authorizations.stream()
                .filter(authorization -> authorization.getBarId().equals(barId))
                .filter(authorization -> authorization.getRole() != (Role.OWNER)) // Cannot unmake yourself Owner
                .findAny()
                .ifPresentOrElse(
                        authorization -> authorization.updateRole(role),
                        () -> authorizations.add(new Authorization(barId, role)));
        return true;
    }

    public boolean revokeUserAuthorization(User user, Long barId) {
        if (!isOwner(barId)) throw new NotAuthorizedException("User has to be Owner of bar to revoke authorize other users");
        if (this.equals(user)) throw new IllegalArgumentException("Cannot revoke your own bar ownership authorization");
        return user.revokeAuthorization(barId);
    }

    public boolean revokeOwnership(Long barId) {
        if (!isOwner(barId)) throw new NotAuthorizedException("User has to be Owner to revoke ownership");
        return revokeAuthorization(barId);
    }

    private boolean revokeAuthorization(Long barId) {
        return authorizations.removeIf(authorization -> authorization.getBarId().equals(barId));
    }

    public Map<Long, String> getAuthorizations() {
        return authorizations.stream()
                .collect(Collectors.toMap(
                        Authorization::getBarId,
                        authorization -> authorization.getRole().name()));
    }

    public Long getId() {
        return id;
    }

    public String getMail() {
        return mail;
    }

    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = new PhoneNumber(phoneNumber);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User that = (User) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(username, that.username) &&
                Objects.equals(password, that.password) &&
                Objects.equals(mail, that.mail) &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(authorizations, that.authorizations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, mail, firstName, lastName, authorizations);
    }
}