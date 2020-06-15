package com.tsarzverey.crud;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface INOrderRepository extends JpaRepository<NOrderDAO, Long> {
    Optional<NOrderDAO> findFirstByClient_MobilePhoneAndDate(String phone, LocalDate date);
    Long countAllByClient_isLocalIsTrue();
    Long countAllByDateBetween(LocalDate start, LocalDate end);
    List<NOrderDAO> findAllByDateBetween(LocalDate start, LocalDate end);
    List<NOrderDAO> findAllByDate(LocalDate date);

}
