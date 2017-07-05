package com.konex.netty.entity;

import javax.persistence.*;

/**
 * Created by vitaliy on 05.07.2017.
 */
@Entity
@Table(name="Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="user_id")
    private Long id;

    @Column(name = "user_PIB")
    private String userPIB;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserPIB() {
        return userPIB;
    }

    public void setUserPIB(String userPIB) {
        this.userPIB = userPIB;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!id.equals(user.id)) return false;
        return userPIB != null ? userPIB.equals(user.userPIB) : user.userPIB == null;

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (userPIB != null ? userPIB.hashCode() : 0);
        return result;
    }
}
