package com.example.seb45pre011.member;

import lombok.Getter;

public class MemberDto {

    @Getter
    public static class post{
        private String id;
        private String name;
        private String email;
        private String password;

        private String gender;
        private String phone;
        private String nick;

    }

    @Getter
    public static class login{
        private String id;
        private String password;
    }
}
