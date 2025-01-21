package com.example.shop.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Board {
    @Id
    public int id;

    public String title;
    public String date;

}
