package com.inn.monk.commerce.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Details {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "ID")
@JsonIgnore
private Integer id;


private Integer product_id;

@Column(name = "THRSHOLD")
private Integer threshold;

@Column(name = "DISCOUNT")
private Double discount;



@OneToMany(cascade = CascadeType.ALL)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
private List<Product> buy_products;

@OneToMany(cascade = CascadeType.ALL)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
private List<Product> get_products;

private Integer repition_limit;




}