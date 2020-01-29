package com.wildcircus.WildCircus.repository;

import com.wildcircus.WildCircus.entity.Circus;
import com.wildcircus.WildCircus.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findAllByCircus(Circus circus);

}
