package com.margulan.uniproject.Controller;

import com.margulan.uniproject.Model.PasswordResetToken;
import com.margulan.uniproject.Model.User;
import com.margulan.uniproject.Service.PasswordResetTokenService;
import com.margulan.uniproject.Service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Controller
public class PasswordResetController {

    @Autowired
    private UsersService usersService;

    @Autowired
    private PasswordResetTokenService tokenService;

    @Value("${app.url}")
    private String appUrl;

    @GetMapping("/passwordResetRequest")
    public String passwordResetRequest() {
        return "password_reset_request";
    }

    @PostMapping("/passwordResetRequest")
    public String processPasswordResetRequest(@RequestParam String email) {
        User user = usersService.findByEmail(email);
        PasswordResetToken token = tokenService.createPasswordResetToken(user);

        // Send email with the reset link
        tokenService.sendPasswordResetEmail(appUrl, user.getEmail(), token.getToken());
        return "password_reset_sent_success";  // A page indicating that the email has been sent
    }

    @GetMapping("/passwordReset")
    public String getResetPassword(@RequestParam("token") String token, Model model) {
        model.addAttribute("token", token);
        return "password_reset";
    }

    @PostMapping("/passwordReset")
    public String resetPassword(@RequestParam String token, @RequestParam String password) {
        PasswordResetToken resetToken = tokenService.findByToken(token);

        // If token expired
        if (resetToken.getExpirationDate().isBefore(LocalDateTime.now())) {
            return "password_reset_expired";
        }

        User user = resetToken.getUser();
        user.setPassword(new BCryptPasswordEncoder().encode(password));
        usersService.resetUserWithNewPassword(user);

        // Delete the token after use
        tokenService.delete(resetToken);

        return "redirect:/users/login";
    }

}

