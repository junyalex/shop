package com.example.shop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="cart")
@Getter @Setter @ToString
public class Cart extends BaseEntity{

    @Id @GeneratedValue
    @Column(name="cart_id")
    private Long id;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;
}
