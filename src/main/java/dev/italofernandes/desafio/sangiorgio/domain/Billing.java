package dev.italofernandes.desafio.sangiorgio.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "billing")
public class Billing {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "code", nullable = false)
  private String code;

  @Column(name = "original_amount", nullable = false)
  private BigDecimal originalAmount;
}