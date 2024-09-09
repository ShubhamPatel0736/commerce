package com.inn.monk.commerce.service;

import com.inn.monk.commerce.model.Cart;
import com.inn.monk.commerce.model.Coupon;
import com.inn.monk.commerce.model.CouponWrapper;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface ICouponService {

public ResponseEntity<Coupon> save(Coupon coupon);

public List<Coupon> getCoupons();

Optional<Coupon> getByCouponId(Integer id) throws Exception;

String deleteById(Integer id);

Coupon updateById(Integer id, Coupon coupon);

Cart applyCoupon(Integer id, Cart cart);

List<CouponWrapper> applicableCoupons(Cart cart);
}