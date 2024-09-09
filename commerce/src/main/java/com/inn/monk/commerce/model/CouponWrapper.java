package com.inn.monk.commerce.model;

import lombok.Data;

@Data
public class CouponWrapper {
private Integer couponId;
private Double discount;
private String type;
private Integer productId;

// public CouponWrapper(Integer id, String type, Double discount) {
// }
}