package com.skb.course.apis.usersws.user;

import javax.persistence.*;

@Entity
@Table(name = "USER_ROLE")
public class UserRoleEntity {

    @Column(name = "User_Role_Id")
    @Id
    private Integer userRoleId;

    @Column(name = "Role")
    private String role;

    public UserRoleEntity() {
    }

    public UserRoleEntity(Integer userRoleId, String role) {
        this.userRoleId = userRoleId;
        this.role = role;
    }

    @ManyToOne(fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
    @JoinColumn(name = "Username", nullable = false)
    private UserEntity userEntity;

    public Integer getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(Integer userRoleId) {
        this.userRoleId = userRoleId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }
}
