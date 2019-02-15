package com.wayne.springbootjdbc.model;

import com.wayne.springbootjdbc.enums.UserSexEnum;

import java.io.Serializable;

public class User implements Serializable {
    private Long id;
    private String userName;
    private String password;
    private int age;

    private UserSexEnum userSex;
    private String nickName;

    public User(String name, String password, int age) {
        this.userName = name;
        this.password = password;
        this.age = age;
    }

    public User() {

    }

    public User(String userName, String passWord, UserSexEnum userSex) {
        super();
        this.password = passWord;
        this.userName = userName;
        this.userSex = userSex;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public UserSexEnum getUserSex() {
        return userSex;
    }

    public void setUserSex(UserSexEnum userSex) {
        this.userSex = userSex;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", age=" + age +
                ", userSex=" + userSex +
                ", nickName='" + nickName + '\'' +
                '}';
    }
}
