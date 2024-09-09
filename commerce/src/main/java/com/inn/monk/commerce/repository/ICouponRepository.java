package com.inn.monk.commerce.repository;

import com.inn.monk.commerce.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICouponRepository extends JpaRepository<Coupon,Integer> {
}