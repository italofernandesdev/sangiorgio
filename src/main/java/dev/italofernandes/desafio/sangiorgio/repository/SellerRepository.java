package dev.italofernandes.desafio.sangiorgio.repository;

import dev.italofernandes.desafio.sangiorgio.domain.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SellerRepository extends JpaRepository<Seller, Long> {
    Optional<Seller> findByCode(String code);
}
