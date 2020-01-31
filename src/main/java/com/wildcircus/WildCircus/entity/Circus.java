package com.wildcircus.WildCircus.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Circus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String adress;

    private int phone;

    @Column(name = "url_picture")
    private String urlPicture;

    @OneToMany(mappedBy = "circus", cascade = CascadeType.ALL)
    private List<Event> events;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    public Circus() {
    }

    public Circus (String name, String adress, int phone, User user, String urlPicture) {
        this.name = name;
        this.adress = adress;
        this.phone = phone;
        this.user = user;
        this.urlPicture = urlPicture;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getUrlPicture() {
        return urlPicture;
    }

    public void setUrlPicture(String urlPicture) {
        this.urlPicture = urlPicture;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
