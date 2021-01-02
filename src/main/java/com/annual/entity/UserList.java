package com.annual.entity;

import java.util.List;

/**
 * @author xhb
 * @Description:
 * @date 2020/12/21 13:28
 */
public class UserList {
    private List<User> list;
    private Integer lastId;

    public List<User> getList() {
        return list;
    }

    public void setList(List<User> list) {
        this.list = list;
    }

    public Integer getLastId() {
        return lastId;
    }

    public void setLastId(Integer lastId) {
        this.lastId = lastId;
    }
}
