package ru.deathkiller2009.dvdspringbootproject.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import ru.deathkiller2009.dvdspringbootproject.util.Role;

import java.util.List;

@Entity
@Table(name = "person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "username")
    @NotEmpty(message = "Имя пользователя не должно быть пустым!")
    private String username;
    @Column(name = "password")
    @NotEmpty(message = "Пароль не должен быть пустым!")
    private String password;
    @OneToMany(mappedBy = "owner")
    private List<DVDDisk> disks;

    @Enumerated(EnumType.STRING)
    private Role role;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<DVDDisk> getDisks() {
        return disks;
    }

    public void setDisks(List<DVDDisk> disks) {
        this.disks = disks;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }
}
