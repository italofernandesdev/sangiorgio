package dev.italofernandes.desafio.sangiorgio.repository;

import dev.italofernandes.desafio.sangiorgio.domain.Billing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BillingRepository extends JpaRepository<Billing, Long> {
    Optional<Billing> findByCode(String code);
}
