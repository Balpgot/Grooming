package com.tsarzverey.crud.repositories;

import com.tsarzverey.crud.entities.ClientDAO;
import com.tsarzverey.crud.entities.PetDAO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IPetRepository extends JpaRepository<PetDAO, Long> {
    List<PetDAO> findAllByClient(ClientDAO client);
    Long countAllByType(String type);
    List<PetDAO> findAllByPoroda(String poroda);
}
