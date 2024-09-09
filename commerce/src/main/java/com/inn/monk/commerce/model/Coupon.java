package com.inn.monk.commerce.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.hibernate.annotations.NotFound;
import org.springframework.validation.annotation.Validated;

import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@Data
public class Coupon {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Integer id;

@NotNull(message = "type can not be null")
private String type;

@OneToOne(cascade = CascadeType.ALL)
@JoinColumn(name = "details")
private Details details;

@Column
private LocalDateTime expirationDate=LocalDateTime.now().plusDays(30);

}