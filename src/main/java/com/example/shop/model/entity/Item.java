package com.example.shop.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@ToString
@Getter
@Setter
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private double price;

    @Column(nullable = true, length = 2048) // 최대 길이 설정
    private String imageURL;

    @Column(nullable = true, length = 2048)
    private String description;

    @ManyToOne
    @JoinColumn(name = "writer_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Member writer;

    @CreationTimestamp
    private LocalDateTime createdDate;
}
