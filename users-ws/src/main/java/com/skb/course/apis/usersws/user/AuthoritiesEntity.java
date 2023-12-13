package com.skb.course.apis.usersws.user;

import javax.persistence.*;

@Entity
@Table(name = "AUTHORITIES")
public class AuthoritiesEntity {

    @Column(name = "Authority_Id")
    @Id
    private Integer authorityId;

    @Column(name = "Authority")
    private String authority;

    @Column(name = "Role")
    private String role;

    public AuthoritiesEntity() {
    }

    public AuthoritiesEntity(Integer authorityId, String authority, String role) {
        this.authorityId = authorityId;
        this.authority = authority;
        this.role = role;
    }

    /*@ManyToOne(fetch = FetchType.EAGER,
        cascade = CascadeType.ALL)
    @JoinColumn(name = "Username", nullable = false)
    private UserEntity userEntity;*/

    public Integer getAuthorityId() {
        return authorityId;
    }

    public void setAuthorityId(Integer authorityId) {
        this.authorityId = authorityId;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
