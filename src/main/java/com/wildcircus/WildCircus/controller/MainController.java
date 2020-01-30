package com.wildcircus.WildCircus.controller;

import com.wildcircus.WildCircus.entity.Circus;
import com.wildcircus.WildCircus.entity.Event;
import com.wildcircus.WildCircus.entity.User;
import com.wildcircus.WildCircus.repository.CircusRepository;
import com.wildcircus.WildCircus.repository.EventRepository;
import com.wildcircus.WildCircus.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    private CircusRepository circusRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String index() {

        return "visit";
    }

    @GetMapping("/cirques")
    public String showAllCircuses(Model out) {

        List<Circus> circuses = circusRepository.findAll();

        out.addAttribute("circuses", circuses);
        return "all-circus";
    }

    @GetMapping("/shows")
    public String showEventsByCircus(Model out, @RequestParam Long circusId) {

        List<Event> events = new ArrayList<>();
        List<Circus> circuses = circusRepository.findAll();
        List<List<Event>> eventsList = new ArrayList<>();
        for (Circus circus : circuses) {
            if (circus.getId().equals(circusId)) {
                eventsList.add(circus.getEvents());
                for (List<Event> eventList : eventsList) {
                    for (Event event : eventList) {
                        if (event.getDate().after(new Date())) {
                            events.add(event);
                        }
                    }
                }
            }
        }

        out.addAttribute("events", events);
        return "show-events";
    }

    @GetMapping("/tous-shows")
    public String showAllEvents(Model out) {

        List<Event> events = eventRepository.findAllByDateAfter(new Date());

        out.addAttribute("events", events);
        return "all-events";
    }

    @GetMapping("/details-show")
    public String showDetailsEvent(Model out, @RequestParam Long eventId, HttpSession session) {

        Event event = eventRepository.findById(eventId).get();
        boolean isUser = false;
        if (session.getAttribute("userId") != null) {
            isUser = true;
        }

        out.addAttribute("user", isUser);
        out.addAttribute("event", event);
        return "event-details";
    }
}
