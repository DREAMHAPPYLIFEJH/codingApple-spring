package com.jhcompany.demo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@ToString
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Getter @Setter
    String userid;

    @Getter @Setter
    String password;

    @Getter @Setter
    String displayName;
}
