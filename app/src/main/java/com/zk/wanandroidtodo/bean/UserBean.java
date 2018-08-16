package com.zk.wanandroidtodo.bean;

import java.util.List;

/**
 * @description: 用户信息实体类
 * @author: zhukai
 * @date: 2018/3/12 16:25
 */
public class UserBean {

    /**
     * collectIds : []
     * email :
     * icon :
     * id : 3749
     * password : 123456
     * type : 0
     * username : 拉塞尔威斯布鲁克
     */
    private String email;
    private String icon;
    private int id;
    private String password;
    private int type;
    private String username;
    private List<Integer> collectIds;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<?> getCollectIds() {
        return collectIds;
    }

    public void setCollectIds(List<Integer> collectIds) {
        this.collectIds = collectIds;
    }
}
