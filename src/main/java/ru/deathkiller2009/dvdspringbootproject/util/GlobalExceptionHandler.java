package ru.deathkiller2009.dvdspringbootproject.util;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public String handleEntityNotFound(EntityNotFoundException exception, Model model) {
        model.addAttribute("message", exception.getMessage());
        return "errors/error-404";
    }

    @ExceptionHandler(IllegalStateException.class)
    public String handleIllegalState(IllegalStateException exception, Model model) {
        model.addAttribute("message", exception.getMessage());
        return "error";
    }

    @ExceptionHandler(AccessDeniedException.class)
    public String handleAccessDenied(AccessDeniedException exception, Model model) {
        model.addAttribute("message", exception.getMessage());
        return "errors/error-403";
    }

    @ExceptionHandler(Exception.class)
    public String handleCommonException(Exception exception, Model model) {
        model.addAttribute("message", exception.getMessage());
        return "errors/error-500";
    }
}
