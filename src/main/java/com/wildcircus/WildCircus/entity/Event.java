package com.wildcircus.WildCircus.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String comment;

    @Column(name = "url_picture")
    private String urlPicture;

    private Date date;

    private int price;

    @ManyToOne
    @JoinColumn(name = "id_circus")
    private Circus circus;

    public Event() {
    }

    public Event(String title, String comment, Date date, int price, Circus circus, String urlPicture) {
        this.title = title;
        this.comment = comment;
        this.date = date;
        this.price = price;
        this.circus = circus;
        this.urlPicture = urlPicture;
    }

    public String getFormattedDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return sdf.format(this.date);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUrlPicture() {
        return urlPicture;
    }

    public void setUrlPicture(String urlPicture) {
        this.urlPicture = urlPicture;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Circus getCircus() {
        return circus;
    }

    public void setCircus(Circus circus) {
        this.circus = circus;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
