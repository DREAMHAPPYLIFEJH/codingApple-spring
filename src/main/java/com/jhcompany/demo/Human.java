package com.jhcompany.demo;

import lombok.Getter;
import lombok.Setter;

public class Human {
    @Setter @Getter
    private String name;
    @Setter @Getter
    private int age;

    public void 한살더하기() {
        this.age += 1;
    }
    public void 나이설정(int age) {
        this.age = age;
    }
}
