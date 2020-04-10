package com.tsarzverey.crud;

import org.springframework.data.jpa.repository.JpaRepository;

public interface INOrderRepository extends JpaRepository<NOrderDAO, Long> {
}
