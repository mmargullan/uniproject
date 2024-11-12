package com.margulan.uniproject.Service.Impl;

import com.margulan.uniproject.Exception.ResourceNotFoundException;
import com.margulan.uniproject.Model.PasswordResetToken;
import com.margulan.uniproject.Model.User;
import com.margulan.uniproject.Repository.PasswordResetTokenRepository;
import com.margulan.uniproject.Service.PasswordResetTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PasswordResetTokenServiceImpl implements PasswordResetTokenService {

    private static final int EXPIRATION_TIME_IN_MINUTES = 30;

    @Autowired
    PasswordResetTokenRepository tokenRepository;

    @Autowired
    private JavaMailSender mailSender;  // Mail sender for sending emails

    @Override
    public PasswordResetToken createPasswordResetToken(User user) {

        String token = UUID.randomUUID().toString();
        LocalDateTime expirationDate = LocalDateTime.now().plusMinutes(EXPIRATION_TIME_IN_MINUTES);

        return tokenRepository.save(new PasswordResetToken(token, expirationDate, user));
    }

    @Override
    public PasswordResetToken findByToken(String token) {
        return tokenRepository.findByToken(token).orElseThrow(
                () -> new ResourceNotFoundException("Token not found")
        );
    }

    @Override
    public void delete(PasswordResetToken token) {
        tokenRepository.delete(token);
    }

    @Override
    public void sendPasswordResetEmail(String appUrl, String email, String token) {
        String resetLink = appUrl + "/passwordReset?token=" + token;
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("alikbegzhan@gmail.com");
        message.setTo(email);
        message.setSubject("Password Reset Request");
        message.setText("Click the link below to reset your password:\n" + resetLink);

        mailSender.send(message);
    }
}
