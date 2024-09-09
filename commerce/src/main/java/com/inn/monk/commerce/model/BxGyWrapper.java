package com.inn.monk.commerce.model;

import lombok.Data;

@Data
public class BxGyWrapper {
private double discount;
private double price;
private Integer quantity;
private Product item;
}