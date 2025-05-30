package com.tilldawn.controller;

import com.tilldawn.Main;
import com.tilldawn.model.language.Result;
import com.tilldawn.model.language.error.MenuErrors;
import com.tilldawn.model.user.User;
import com.tilldawn.model.user.UserRepository;
import com.tilldawn.view.RegisterScreen;

import java.util.regex.Pattern;

public class RegisterController {
    private final Main game = Main.getInstance();
    private final RegisterScreen screen;

    public RegisterController(RegisterScreen screen) {
        this.screen = screen;
    }

    public Result<Void> handleRegister(String username, String password, String repeatedPassword, String securityQuestion, String securityAnswer) {
        User user = UserRepository.getInstance().getByUsername(username);
        if (user != null)
            return Result.failure(MenuErrors.USER_ALREADY_EXIST);
        if (!password.equals(repeatedPassword))
            return Result.failure(MenuErrors.PASSWORDS_DIFFER);
        if (securityAnswer.length() <= 3)
            return Result.failure(MenuErrors.SECURITY_ANSWER_IS_TOO_SHORT);
        if (!isPasswordStrong(password))
            return Result.failure(MenuErrors.WEAK_PASS);
        user = new User(username, password, securityQuestion, securityAnswer);
        UserRepository.getInstance().add(user);
        return Result.success(null);
    }

    public static boolean isPasswordStrong(String password) {
        System.err.println("PASS: " + password);
        if (password.length() < 8)
            return false;

        if (!Pattern.compile("[@_()*&%$#!]").matcher(password).find())
            return false;

        if (!Pattern.compile("\\d").matcher(password).find())
            return false;

        if (!Pattern.compile("[A-Z]").matcher(password).find())
            return false;

        return true;
    }
}
