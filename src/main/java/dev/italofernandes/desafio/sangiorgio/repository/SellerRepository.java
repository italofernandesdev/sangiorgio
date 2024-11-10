package dev.italofernandes.desafio.sangiorgio.repository;

import dev.italofernandes.desafio.sangiorgio.domain.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerRepository extends JpaRepository<Seller, Long> {
}
