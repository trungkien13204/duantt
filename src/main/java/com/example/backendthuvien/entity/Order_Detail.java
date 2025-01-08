package com.example.backendthuvien.entity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "order_detail")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Order_Detail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "order_id",referencedColumnName = "id")
    @JsonBackReference
    private  Order order;

    @ManyToOne
    @JoinColumn(name = "product_id",referencedColumnName = "id")
    private  Product product;

    @Column(name = "price",nullable = false)
    private float price;

    @Column(name = "number_of_products",nullable = false)
    private int numberOfProducts;

    @Column(name = "total_money",nullable = false)
    private Float totalMoney;

    @Column(name = "color")
    private String color;

    @Column(name = "borrow_status", columnDefinition = "NVARCHAR(20) DEFAULT 'not returned'")
    private String borrowStatus = "not returned";


    @Column(name = "penalty_fee", columnDefinition = "FLOAT DEFAULT 0")
    private Float penaltyFee = 0f;



}
