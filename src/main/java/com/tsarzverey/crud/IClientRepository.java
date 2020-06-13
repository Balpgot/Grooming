package com.tsarzverey.crud;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Calendar;
import java.util.Optional;

public interface IClientRepository extends JpaRepository<ClientDAO, Long> {
    Long countAllByisLocalIsTrue();
    Long countAllByRegistrationBetween(Calendar start, Calendar finish);
    Optional<ClientDAO> findFirstByMobilePhone(String phone);
}
