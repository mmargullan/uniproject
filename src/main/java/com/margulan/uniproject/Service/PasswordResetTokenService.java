package com.margulan.uniproject.Service;

import com.margulan.uniproject.Model.PasswordResetToken;
import com.margulan.uniproject.Model.User;

public interface PasswordResetTokenService {

    PasswordResetToken createPasswordResetToken(User user);
    PasswordResetToken findByToken(String token);
    void delete(PasswordResetToken token);
    void sendPasswordResetEmail(String appUrl, String email, String token);
}
