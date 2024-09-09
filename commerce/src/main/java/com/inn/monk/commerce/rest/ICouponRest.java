package com.inn.monk.commerce.rest;


import com.inn.monk.commerce.model.Cart;
import com.inn.monk.commerce.model.CartWrapper;
import com.inn.monk.commerce.model.Coupon;
import com.inn.monk.commerce.model.CouponWrapper;
import jakarta.validation.Valid;
import org.apache.hadoop.hdfs.protocol.datatransfer.Op;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/monk")
public interface ICouponRest {

@PostMapping("/coupons")
public ResponseEntity<Coupon> save(@Valid @RequestBody Coupon coupon);

@GetMapping("/coupons")
public List<Coupon> getCoupons();

@GetMapping("/coupons/{id}")
public Optional<Coupon> getCouponById(@PathVariable Integer id) throws Exception;

@DeleteMapping("/coupons/{id}")
public String deleteById(@PathVariable Integer id);

@PutMapping("/coupons/{id}")
public Coupon updateById(@PathVariable Integer id,@RequestBody Coupon coupon) throws Exception;

@PostMapping("/apply-coupon/{id}")
public Cart applyCoupon(@PathVariable Integer id,@RequestBody Cart cart);

@PostMapping("/applicable-coupons")
public List<CouponWrapper> applicableCoupons(@RequestBody Cart cart);
}