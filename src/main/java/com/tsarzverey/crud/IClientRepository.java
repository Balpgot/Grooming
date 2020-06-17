package com.tsarzverey.crud;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface IClientRepository extends JpaRepository<ClientDAO, Long> {
    Long countAllByisLocalIsTrue();
    Long countAllByRegistrationBetween(LocalDate start, LocalDate finish);
    Long countAllByRegistration(LocalDate date);
    Optional<ClientDAO> findFirstByMobilePhone(String phone);
}
