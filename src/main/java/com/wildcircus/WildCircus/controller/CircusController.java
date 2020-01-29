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
import java.util.Date;
import java.util.List;

@Controller
public class CircusController {

    @Autowired
    private CircusRepository circusRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/mes-cirques")
    public String showMyCircus(Model out, HttpSession session) {
        User user = userRepository.findById((Long) session.getAttribute("userId")).get();

        List<Circus> circuses = circusRepository.findAllByUser(user);

        out.addAttribute("circuses", circuses);
        return "my-circus";
    }

    @GetMapping("/ajout-cirque")
    public String addACircus(Model out, HttpSession session) {
        User user = userRepository.findById((long) session.getAttribute("userId")).get();

        out.addAttribute("circus", new Circus());
        out.addAttribute("user", user);
        return "add-circus";
    }

    @PostMapping("/ajout-cirque")
    public String addACircusPost (@RequestParam String name,
                                  @RequestParam String address,
                                  @RequestParam int phone,
                                  HttpSession session) {

        User user = userRepository.findById((long) session.getAttribute("userId")).get();

        circusRepository.save(new Circus(name, address, phone, user));
        return "redirect:/mes-cirques";
    }
}
