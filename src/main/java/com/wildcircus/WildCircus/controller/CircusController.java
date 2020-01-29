package com.wildcircus.WildCircus.controller;

import com.wildcircus.WildCircus.entity.Circus;
import com.wildcircus.WildCircus.entity.Event;
import com.wildcircus.WildCircus.repository.CircusRepository;
import com.wildcircus.WildCircus.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CircusController {

    @Autowired
    private CircusRepository circusRepository;

    @Autowired
    private EventRepository eventRepository;

    @GetMapping("/")
    public String index() {
        return "visit";
    }

    @GetMapping("/mes-cirques")
    public String showMyCircus(Model out) {
        List<Circus> circuses = circusRepository.findAll();

        out.addAttribute("circuses", circuses);
        return "my-circus";
    }

    @GetMapping("/mes-shows")
    public String showMyShows(Model out) {
        List<Event> events = eventRepository.findAll();

        out.addAttribute("events", events);
        return "my-events";
    }
}
