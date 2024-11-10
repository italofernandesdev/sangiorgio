package dev.italofernandes.desafio.sangiorgio.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "billing")
public class Billing {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @ManyToOne
  @JoinColumn(name = "seller_id")
  private Seller seller;

  @Column(name = "original_amount", nullable = false)
  private BigDecimal originalAmount;
}