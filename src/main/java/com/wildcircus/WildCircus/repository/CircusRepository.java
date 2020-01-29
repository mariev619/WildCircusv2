package com.wildcircus.WildCircus.repository;

import com.wildcircus.WildCircus.entity.Circus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CircusRepository extends JpaRepository<Circus, Long> {
}
