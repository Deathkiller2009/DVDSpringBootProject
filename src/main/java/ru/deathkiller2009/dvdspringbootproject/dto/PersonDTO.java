package ru.deathkiller2009.dvdspringbootproject.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;

public class PersonDTO {

    @NotEmpty(message = "Имя пользователя не должно быть пустым!")
    private String username;

    @NotEmpty(message = "Пароль не должен быть пустым!")
    private String password;

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
}
