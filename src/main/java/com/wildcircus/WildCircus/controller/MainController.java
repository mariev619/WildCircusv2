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
import org.springframework.web.bind.annotation.PostMapping;
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
    public String index(HttpSession session, Model out) {
        boolean isUser = false;
        if (session.getAttribute("userId") != null) {
            isUser = true;
        }
        List<Circus> circusList = new ArrayList<>();
        List<Circus> circuses = circusRepository.findAll();
        for (Circus circus : circuses) {
            if (circus.getEvents() != null) {
                circusList.add(circus);
            }
        }

        out.addAttribute("circus", circusList);
        out.addAttribute("user", isUser);
        return "visit";
    }

    @GetMapping("/cirques")
    public String showAllCircuses(Model out) {

        List<Circus> circuses = circusRepository.findAll();

        out.addAttribute("circuses", circuses);
        return "all-circus";
    }

    @GetMapping("/shows")
    public String showEventsByCircus(Model out, @RequestParam Long circusId, HttpSession session) {

        boolean isUser = false;
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
        if (session.getAttribute("userId") != null) {
            isUser = true;
        }

        out.addAttribute("user", isUser);
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

        boolean isUser = false;
        boolean isPassed = false;
        if (session.getAttribute("userId") != null) {
            isUser = true;
        }
        Event event = eventRepository.findById(eventId).get();
        List<Event> passedEvents = eventRepository.findAll();
        for (Event passedEvent : passedEvents) {
            if (passedEvent.getDate().before(new Date())) {
                isPassed = true;
            }
        }

        out.addAttribute("user", isUser);
        out.addAttribute("event", event);
        out.addAttribute("isPassed", isPassed);
        return "event-details";
    }

    @GetMapping("/mon-profil")
    public String userProfil(Model out, HttpSession session) {
        if (session.getAttribute("userId") == null) {
            return "/visit";
        }
        User user = userRepository.findById((Long) session.getAttribute("userId")).get();

        out.addAttribute("user", user);
        return "user-profil";
    }

    @GetMapping("/modifier-profil")
    public String updateUser(Model out, HttpSession session, @RequestParam Long userId) {
        if (session.getAttribute("userId") != null) {
            return "/visit";
        }
        User user = userRepository.findById((Long) session.getAttribute("userId")).get();

        out.addAttribute("user", user);
        return "update-user";
    }

    @PostMapping("/modifier-profil")
    public String updateUserPost(@RequestParam String name,
                                 @RequestParam String firstname,
                                 @RequestParam String email,
                                 @RequestParam String password,
                                 @RequestParam Long userId,
                                 HttpSession session) {
        User user = userRepository.findById((Long) session.getAttribute("userId")).get();
        user.setName(name);
        user.setFirstname(firstname);
        user.setEmail(email);
        user.setPassword(password);

        userRepository.save(user);
        return "redirect:/mon-profil";
    }

    @GetMapping("/supprimer-profil")
    public String deleteProfil(HttpSession session) {
        User user = userRepository.findById((Long) session.getAttribute("userId")).get();

        userRepository.delete(user);
        return "redirect:/";
    }
}
