package com.tsarzverey.crud;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IPetRepository extends JpaRepository<PetDAO, Long> {
    List<PetDAO> findAllByClient(ClientDAO client);
    Long countAllByType(String type);
}
