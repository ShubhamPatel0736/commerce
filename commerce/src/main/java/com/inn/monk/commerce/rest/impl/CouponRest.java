package com.inn.monk.commerce.rest.impl;

import com.inn.monk.commerce.exception.BusinessException;
import com.inn.monk.commerce.model.Cart;
import com.inn.monk.commerce.model.CartWrapper;
import com.inn.monk.commerce.model.Coupon;
import com.inn.monk.commerce.model.CouponWrapper;
import com.inn.monk.commerce.rest.ICouponRest;
import com.inn.monk.commerce.service.ICouponService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
public class CouponRest implements ICouponRest {

@Autowired
ICouponService iCouponService;
@Override
public ResponseEntity<Coupon> save(Coupon coupon) {
log.info("coupon {}",coupon);
return iCouponService.save(coupon);
}

@Override
public List<Coupon> getCoupons() {
return iCouponService.getCoupons();
}

@Override
public Optional<Coupon> getCouponById(Integer id) throws Exception {
return iCouponService.getByCouponId(id);
}

@Override
public String deleteById(Integer id) {
return iCouponService.deleteById(id);
}

@Override
public Coupon updateById(Integer id, Coupon coupon) throws Exception {
try{
return iCouponService.updateById(id,coupon);
}catch(Exception e) {
throw new Exception(e.getMessage());
}
}

@Override
public Cart applyCoupon(Integer id, Cart cart) {
log.info("cart {}",cart);
return iCouponService.applyCoupon(id,cart);
}

@Override
public List<CouponWrapper> applicableCoupons(Cart cart) {
try {
return iCouponService.applicableCoupons(cart);
}catch(BusinessException e) {

throw new BusinessException(e.getMessage());
}


}

}