package com.margulan.uniproject.Exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

//    @ExceptionHandler(DuplicateResourceException.class)
//    public ModelAndView handleCustomException(DuplicateResourceException ex, Model model) {
//        model.addAttribute("errorMessage", ex.getMessage());
//        return new ModelAndView("errorPage", model.asMap());
//    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleGeneralException(Exception ex) {
        return new ModelAndView("error_page");
    }

//    @ExceptionHandler(DuplicateEmailException.class)
//    public ModelAndView handleDuplicateEmailException(DuplicateEmailException ex, Model model) {
//        model.addAttribute("duplicateEmailError", "An unexpected error occurred.");
//        return new ModelAndView("register_page", model.asMap());
//    }
}
