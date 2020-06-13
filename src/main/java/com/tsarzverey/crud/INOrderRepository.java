package com.tsarzverey.crud;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface INOrderRepository extends JpaRepository<NOrderDAO, Long> {
    Optional<NOrderDAO> findFirstByClient_MobilePhoneAndDate(String phone, LocalDate date);
}
