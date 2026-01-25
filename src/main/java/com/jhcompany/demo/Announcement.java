package com.jhcompany.demo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Date;

@Entity
public class Announcement {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long Id;
    String title;
    Date date;
}
