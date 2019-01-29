package com.sum.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "usuario")
public class Usuario implements UserDetails {

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setEmail2(String email2) {
        this.email2 = email2;
    }

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "UF")
    private String unidadFuncional;

    @Column(name = "password")
    private String password;

    @Column(name = "rol")
    private String rol;

    @Column(name = "piso")
    private String piso;

    @Column(name = "depto")
    private String depto;

    @Column(name = "email")
    private String email;

    @Column(name = "email2")
    private String email2;

    @Override
    public String getUsername() {
        return this.unidadFuncional;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    public String getRol() {
        return rol;
    }

    public String getPiso() {
        return piso;
    }

    public String getDepto() {
        return depto;
    }

    public String getEmail() {
        return email;
    }

    public String getEmail2() {
        return email2;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> auth = new ArrayList<GrantedAuthority>();
        auth.add(new SimpleGrantedAuthority("ROLE_".concat(this.rol)));
        return auth;
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
}
