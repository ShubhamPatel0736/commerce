package com.inn.monk.commerce.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Product {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@JsonIgnore
private Integer id;
private Integer product_id;
private Integer quantity;
private Double price;



}