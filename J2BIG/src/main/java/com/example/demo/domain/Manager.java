package com.example.demo.domain;
import javax.persistence.*;
//经理，用于审核，只有id和password用于登录
@Entity
@Table(name="Manager")
public class Manager {
    @Id
    private String id;

    private String password;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
