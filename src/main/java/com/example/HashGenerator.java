package com.example;

import util.PasswordUtil;

public class HashGenerator {
    public static void main(String[] args) {

        System.out.println(PasswordUtil.hashPassword("Admin@123"));
        System.out.println(PasswordUtil.hashPassword("Teacher@123"));
        System.out.println(PasswordUtil.hashPassword("Student@123"));

    }
}
