package com.jhcompany.demo.member;

public class MemberDTO { // 장점 : DTO는 타입추론이 쉬움
    public String username;
    public String displayName;

    MemberDTO(String username, String displayName) {
        this.displayName = displayName;
        this.username = username;
    }
}