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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Controller
public class EventController {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private CircusRepository circusRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/mes-shows")
    public String showMyShows(Model out, HttpSession session) {
        if (session.getAttribute("userId") == null) {
            return "redirect:/";
        }
        User user = userRepository.findById((Long) session.getAttribute("userId")).get();
        List<Circus> circuses = circusRepository.findAllByUser(user);
        List<Event> events = new ArrayList<>();

        for (Circus circus : circuses) {
            for (Event event : circus.getEvents()) {
                events.add(event);
            }
        }

        out.addAttribute("events", events);
        return "my-events";
    }

    @GetMapping("ajout-show")
    public String addEvent(Model out, @RequestParam Long circusId, HttpSession session) {
        if (session.getAttribute("userId") == null) {
            return "redirect:/";
        }
        Circus circus = circusRepository.findById(circusId).get();
        out.addAttribute("circus", circus);
        out.addAttribute("event", new Event());
        return "add-event";
    }

    @PostMapping("/ajout-show")
    public String addEventPost(@RequestParam String title,
                               @RequestParam String comment,
                               @RequestParam String dateString,
                               @RequestParam String hour,
                               @RequestParam int price,
                               @RequestParam Long circusId) {

        Date date = null;
        try {
            date = new SimpleDateFormat("MMMM dd, yyyy h:mm a", Locale.FRANCE).parse(dateString + " " + hour);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Circus circus = circusRepository.findById(circusId).get();
        eventRepository.save(new Event(title, comment, date, price, circus));
        return "redirect:/mes-shows";
    }
}
