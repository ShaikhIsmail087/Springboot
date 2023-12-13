package com.skb.course.apis.authorsws.user;
import javax.persistence.*;
@Entity
@Table(name = "USER_ROLE")
public class UserRoleEntity {

    @Column(name = "User_Role_ID")
    @Id
    private Integer userRoleId;
    @Column(name = "Role")
    private String role;
    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "username",nullable = false)
    private UserEntity userEntity;

    public UserRoleEntity() {
    }

    public UserRoleEntity(Integer userRoleId, String role) {
        this.userRoleId = userRoleId;
        this.role = role;
    }

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
