package com.inn.monk.commerce.service.impl;

import com.inn.monk.commerce.exception.BusinessException;
import com.inn.monk.commerce.model.*;
import com.inn.monk.commerce.repository.ICouponRepository;
import com.inn.monk.commerce.service.ICouponService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CouponService implements ICouponService {

@Autowired
ICouponRepository iCouponRepository;

@Override
public ResponseEntity<Coupon> save(Coupon coupon) {
try {
Coupon coupon1= iCouponRepository.save(coupon);
return ResponseEntity.ok(coupon1);
}
catch (BusinessException e){
throw new BusinessException(e.getMessage());

}
}

@Override
public List<Coupon> getCoupons() {
return iCouponRepository.findAll();
}

@Override
public Optional<Coupon> getByCouponId(Integer id) throws Exception {

Optional<Coupon> coupon = iCouponRepository.findById(id);
if(coupon.isPresent())return coupon;
else throw new BusinessException("No record found");
}

@Override
public String deleteById(Integer id) {

try {
iCouponRepository.deleteById(id);
return "Coupon Deleted!!!";
} catch (BusinessException be) {
throw new BusinessException("Error occured while delete");
}
}
@Override
public Coupon updateById(Integer id, Coupon coupon) {

try {
Optional<Coupon> coupon1 = iCouponRepository.findById(id);
if (coupon1.isPresent()) {
Coupon tempCoupon=coupon1.get();
tempCoupon.setDetails(coupon.getDetails());
tempCoupon.setType(coupon.getType());
tempCoupon.getDetails().setDiscount(coupon.getDetails().getDiscount());
return iCouponRepository.save(tempCoupon);
} else {
return iCouponRepository.save(coupon);
}}
catch(BusinessException be) {

throw new BusinessException(be.getMessage());
}
}


@Override
public Cart applyCoupon(Integer id, Cart cart) {
try {
Optional<Coupon> coupon = iCouponRepository.findById(id);
if(coupon!=null && coupon.get().getExpirationDate().isBefore(LocalDateTime.now()))throw new BusinessException("Coupon Expired!");

String errorMessage;
log.info("cartWrapper {}", cart);
List<Product> list = cart.getCart().getItems();
double Discount = 0;
double totalDiscount = 0;
double finalPrice = 0;
double totalPrice = 0;
double discountPercentage = 0;
totalPrice = list.stream().mapToDouble(product -> product.getQuantity() * product.getPrice()).sum();

log.info("list {}", list);
//Optional<Coupon> coupon = iCouponRepository.findById(id);
log.info("coupon {}", coupon);
if (coupon.get().getType().equals("cart-wise")) {
// List<Product> list = cartWrapper.getItems();
// total = list.stream().mapToDouble(product -> product.getQuantity() * product.getPrice()).sum();
if (totalPrice > coupon.get().getDetails().getThreshold()) {
discountPercentage = coupon.get().getDetails().getDiscount();
Discount = (discountPercentage / 100) * totalPrice;
cart.getCart().setFinalPrice(totalPrice - Discount);
cart.getCart().setTotalDiscount(Discount);
cart.getCart().setTotalPrice(totalPrice);
//cartWrapper.setItems(cartWrapper.getItems());
log.info("inside cart-wise -wrapper {}", cart);
return cart;
} else {
throw new BusinessException("Coupon can't be applied.");
}
}
if (coupon.get().getType().equals("product-wise")) {
log.info("inside product-wise");
List<Product> products = list.stream().filter(x -> x.getProduct_id() == coupon.get().getDetails().getProduct_id()).collect(Collectors.toList());
log.info("inside product-wise products {}", products);

if(products.size()==0)throw new BusinessException("Coupon can't be applied as there are no products for this coupon.");
discountPercentage = coupon.get().getDetails().getDiscount();
//total = list.stream().mapToDouble(product -> product.getQuantity() * product.getPrice()).sum();
double discountedProduct = products.stream().mapToDouble(product -> product.getQuantity() * product.getPrice()).sum();

Discount = ((discountPercentage / 100) * discountedProduct);

cart.getCart().setTotalPrice(totalPrice);
cart.getCart().setTotalDiscount(Discount);
cart.getCart().setFinalPrice(totalPrice - Discount);
//products.stream().map(x->x.setPrice((finalTotalDiscount /100)*x.getPrice())).collect(Collectors.toList());
log.info("inside product-wise -wrapper {}", cart);

return cart;
}
if (coupon.get().getType().equals("bxgy")) {

List<Product> bxgylist = cart.getCart().getItems();

BxGyWrapper bxGyWrapper= appliedCouponData(coupon.get(),bxgylist);
cart.getCart().setFinalPrice(totalPrice);
cart.getCart().setTotalDiscount(bxGyWrapper.getDiscount());
cart.getCart().setTotalPrice(totalPrice+bxGyWrapper.getDiscount());

for(Product item: cart.getCart().getItems()){
if(item.getProduct_id()==bxGyWrapper.getItem().getProduct_id()){
item.setQuantity(bxGyWrapper.getQuantity());
}

}

return cart;









}
return null;
}catch (BusinessException ex){
throw new BusinessException(ex.getMessage());

}
}

@Override
public List<CouponWrapper> applicableCoupons(Cart cart) {
try{
log.info("inside applicableCoupons {}",cart);
List<CouponWrapper> couponWrapperList=new ArrayList<>();
List<Coupon> tempCouponList =iCouponRepository.findAll();
List<Coupon> couponList= tempCouponList.stream().filter(x->x.getExpirationDate().isAfter(LocalDateTime.now())).collect(Collectors.toList());
List<Product> list = cart.getCart().getItems();
double total = list.stream().mapToDouble(product -> product.getQuantity() * product.getPrice()).sum();
log.info("inside applicableCoupons {}",list);
log.info("inside applicableCoupons -- total {}",total);
List<Coupon> couponList1=couponList.stream().filter(x->x.getDetails().getThreshold()!=null && x.getDetails().getThreshold()<total).collect(Collectors.toList());
log.info("inside applicableCoupons -- coupounList1 {}",couponList1);

for(Coupon coup:couponList1){
CouponWrapper couponWrapper=new CouponWrapper();
couponWrapper.setCouponId(coup.getId());
couponWrapper.setType(coup.getType());
couponWrapper.setDiscount((coup.getDetails().getDiscount()/100)*total);
couponWrapperList.add(couponWrapper);
}

// couponList1.stream().forEach(x->couponWrapperList.add(new CouponWrapper(x.getId(),x.getType(),x.getDetails().getDiscount())));
List <Coupon> couponList2= couponList.stream().filter(x->x.getType().equals("product-wise") &&
list.stream().anyMatch(y->y.getProduct_id()==x.getDetails().getProduct_id())).collect(Collectors.toList());
if(couponList2!=null) {
for(Coupon coup :couponList2) {
CouponWrapper cWrapper=new CouponWrapper();
cWrapper.setCouponId(coup.getId());
cWrapper.setType("product-wise");
for(Product item :list){
if(item.getProduct_id()==coup.getDetails().getProduct_id()){
cWrapper.setDiscount((coup.getDetails().getDiscount()/100)*item.getPrice()*item.getQuantity());
}
}
couponWrapperList.add(cWrapper);
}
}

List<Coupon> couponList3 = couponList.stream().filter(x->x.getType().equals("bxgy")).collect(Collectors.toList());

for(Coupon c:couponList3)
{
BxGyWrapper bxGyWrapper= appliedCouponData(c,list);
CouponWrapper couponWrapper = new CouponWrapper();
couponWrapper.setCouponId(c.getId());
couponWrapper.setType(c.getType());
couponWrapper.setDiscount(bxGyWrapper.getDiscount());
couponWrapperList.add(couponWrapper);



}

log.info("inside applicableCoupons -- couponWrapperList {}",couponWrapperList);

return couponWrapperList;

}
catch (BusinessException e)
{
throw new BusinessException("error occured");

}}

public BxGyWrapper appliedCouponData(Coupon c, List<Product> list)
{
List<Product> buyProducts=c.getDetails().getBuy_products();
List<Product> getProducts=c.getDetails().getGet_products();
boolean buyBool = buyProducts.stream()
.allMatch(b -> list.stream().anyMatch(a -> a.getProduct_id() == b.getProduct_id()));
log.info("buyBool {}",buyBool);
boolean getBool = getProducts.stream().allMatch(item->list.stream().anyMatch(product->product.getProduct_id()==item.getProduct_id()));
log.info("getBool {}",getBool);
Product getProduct=getProducts.get(0);

double buyProdQuantity =buyProducts.stream().mapToDouble(Product::getQuantity).sum();
double quantityOfList = list.stream()
.filter(a -> buyProducts.stream().anyMatch(b -> b.getProduct_id() == a.getProduct_id())) // Filter based on presence in list B
.mapToDouble(Product::getQuantity)
.sum();

log.info("buyProdQuantity {}",buyProdQuantity);
log.info("quantityOfList {}",quantityOfList);
log.info("getBool {}",getBool);


if(getBool && buyBool){
if(quantityOfList>buyProdQuantity) {
Optional<Product> item= list.stream().filter(x->x.getProduct_id()==getProduct.getProduct_id()).findFirst();
double discount = item.get().getPrice()* c.getDetails().getRepition_limit();
Integer updateQuantity=c.getDetails().getRepition_limit()*item.get().getQuantity();
double updatedPrice=updateQuantity*item.get().getPrice();
BxGyWrapper bxGyWrapper=new BxGyWrapper();
bxGyWrapper.setDiscount(discount);
bxGyWrapper.setPrice(updatedPrice);
bxGyWrapper.setQuantity(updateQuantity);
Product Product= item.get();
bxGyWrapper.setItem(Product);
return bxGyWrapper;
}
}

return null;
}
}