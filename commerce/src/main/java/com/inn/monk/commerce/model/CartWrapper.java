package com.inn.monk.commerce.model;

import lombok.Data;

import java.util.List;

@Data
public class CartWrapper {
List<Product> items;
Double totalPrice;
Double finalPrice;
Double totalDiscount;
}