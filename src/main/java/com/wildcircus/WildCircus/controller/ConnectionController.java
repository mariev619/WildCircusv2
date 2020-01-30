package com.wildcircus.WildCircus.controller;

import com.wildcircus.WildCircus.entity.User;
import com.wildcircus.WildCircus.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;
import com.google.common.hash.Hashing;

import java.util.Optional;

@Controller
public class ConnectionController {

    @Autowired
    private UserRepository userRepository;

    @Value("${password.salted}")
    private String pass;

    @GetMapping("/inscription")
    public String inscription (Model out,
                               @ModelAttribute User user) {

        out.addAttribute("user", user);
        return "inscription";
    }

    @PostMapping("/inscription")
    public RedirectView inscriptionPost (@ModelAttribute User user) {

        RedirectView redirectView = new RedirectView();
        redirectView.setContextRelative(true);
        String encryptedPassword = Hashing.sha256()
                .hashString(pass + user.getPassword(), StandardCharsets.UTF_8)
                .toString();
        user.setPassword(encryptedPassword);

        userRepository.save(user);
        redirectView.setUrl("/");
        return redirectView;
    }

    @GetMapping("/connexion")
    public String connection (Model out) {

        out.addAttribute("user", new User());
        return "connection";
    }

    @PostMapping("/connexion")
    public RedirectView connectionPost (@RequestParam String email,
                                        @RequestParam String password,
                                        HttpSession session) {

        RedirectView redirectView = new RedirectView();
        redirectView.setContextRelative(true);
        redirectView.setUrl("/");
        String encryptedPassword = Hashing.sha256()
                .hashString(pass + password, StandardCharsets.UTF_8)
                .toString();
        Optional<User> optionalUser = userRepository.findByEmailAndPassword(email, encryptedPassword);
        User user = optionalUser.get();
        if (!optionalUser.isPresent()) {
            redirectView.setUrl("/");
            return redirectView;
        }
        session.setAttribute("userId", user.getId());
        redirectView.setUrl("/mes-cirques");

        return redirectView;
    }

    @GetMapping("/deconnexion")
    public RedirectView deconnection(HttpSession session) {
        session.removeAttribute("userId");
        RedirectView redirectView = new RedirectView();
        redirectView.setContextRelative(true);
        redirectView.setUrl("/");
        return redirectView;

    }
}
