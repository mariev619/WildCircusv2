package com.wildcircus.WildCircus.controller;

import com.wildcircus.WildCircus.entity.Event;
import com.wildcircus.WildCircus.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class EventController {

    @Autowired
    private EventRepository eventRepository;

    @GetMapping("/mes-shows")
    public String showMyShows(Model out) {
        List<Event> events = eventRepository.findAll();

        out.addAttribute("events", events);
        return "my-events";
    }
}
