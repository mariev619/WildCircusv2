package com.wildcircus.WildCircus.controller;

import com.wildcircus.WildCircus.entity.Circus;
import com.wildcircus.WildCircus.repository.CircusRepository;
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
}
