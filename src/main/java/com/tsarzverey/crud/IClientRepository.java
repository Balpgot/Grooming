package com.tsarzverey.crud;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IClientRepository extends JpaRepository<ClientDAO, Long> {
}
