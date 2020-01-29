package com.wildcircus.WildCircus.repository;

import com.wildcircus.WildCircus.entity.Circus;
import com.wildcircus.WildCircus.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CircusRepository extends JpaRepository<Circus, Long> {
    List<Circus> findAllByUser(User user);
}
