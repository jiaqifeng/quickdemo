package com.jack.pinpoint.mongodb.springboot.dao;

import org.springframework.data.annotation.Id;

public class Student {
    @Id
    private String id;
    private String name;
    private String email;
    private int age;

    public String getName() {
       return name;
    }

    public void setName(String name) {
       this.name = name;
    }

    public String getEmail() {
       return email;
    }

    public void setEmail(String email) {
       this.email = email;
    }

    public int getAge() {
       return age;
    }

    public void setAge(int age) {
       this.age = age;
    }

    @Override
    public String toString() {
       return "Student=[id=" + id + ", name=" + name + ", age=" + age + "]";
    }
}

