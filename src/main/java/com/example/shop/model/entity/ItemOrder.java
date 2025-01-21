package com.example.shop.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class ItemOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(
            name = "item_id",
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)
    )
    private Item item;
    private double price;
    private int quantity;
    @ManyToOne
    @JoinColumn(
            name = "member_id"
          , foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)
    )
    private Member member;
    @CreationTimestamp
    private LocalDateTime orderDate;

    public void setItemById(int itemId) {
        this.item = new Item();
        this.item.setId(itemId);
    }

    public void setMemberById(int memberId) {
        this.member = new Member();
        this.member.setId(memberId);
    }

}
