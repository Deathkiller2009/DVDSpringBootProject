package ru.deathkiller2009.dvdspringbootproject.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDate;
@Entity
@Table(name = "dvd_disk")
public class DVDDisk {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "title")
    @NotEmpty(message = "Название диска не должно быть пустым!")
    private String title;
    @Column(name = "author")
    @NotEmpty(message = "Название диска не должно быть пустым!")
    private String author;
    @Column(name = "release_date")
    @NotEmpty(message = "Название диска не должно быть пустым!")
    @Temporal(TemporalType.DATE)
    private LocalDate releaseDate;
    @Column(name = "annotation")
    @NotEmpty(message = "Название диска не должно быть пустым!")
    private String annotation;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person owner;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }
}
