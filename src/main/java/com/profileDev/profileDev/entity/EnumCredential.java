package com.profileDev.profileDev.entity;

import lombok.AllArgsConstructor;


@AllArgsConstructor
public enum EnumCredential {
    ENUMCRED1("name1","pwd1","role"),
    ENUMCRED2("name2","pwd2","role"),
    ENUMCRED3("name3","pwd3","role"),
    ENUMCRED4("name4","pwd4","role");

    // need to check how we can print all variables in ResponseBody from controller
    String name;
    String password;
    String role;
}
