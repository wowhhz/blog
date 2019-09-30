package com.acefet.blog.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Data
@Entity
public class User {
    /**编号*/
    @Id
    private String id;
    /**名字*/
    private String name;
    /**用户名*/
    private String username;
    /**密码*/
    private String password;
    /**账户状态*/
    private String status;
    /**头像*/
    private String icon;
    /**email*/
    private String email;
    /**手机号*/
    private String phone;
    /**创建时间*/
    private Timestamp createTime;

//    @ManyToMany(cascade = {CascadeType.REFRESH},fetch = FetchType.EAGER)
//    private List<Role> roles;
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        List<GrantedAuthority> auths = new ArrayList<>();
//        List<Role> roles = this.getRoles();
//        for (Role role : roles) {
//            auths.add(new SimpleGrantedAuthority(role.getName()));
//        }
//        return auths;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return "enable".equals(this.getStatus());
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return "enable".equals(this.getStatus());
//    }
}
