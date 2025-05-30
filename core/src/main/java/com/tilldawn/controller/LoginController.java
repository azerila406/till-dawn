package com.tilldawn.controller;

import com.badlogic.gdx.Screen;
import com.tilldawn.Main;
import com.tilldawn.model.language.Result;
import com.tilldawn.model.language.error.MenuErrors;
import com.tilldawn.model.user.User;
import com.tilldawn.model.user.UserRepository;
import com.tilldawn.view.ForgetPasswordScreen;
import com.tilldawn.view.LoginScreen;

public class LoginController {
    private final Main game = Main.getInstance();
    private final Screen screen;

    public LoginController(Screen screen) {
        this.screen = screen;
    }

    public Result<User> handleLogin(String username, String password) {
        User user = UserRepository.getInstance().getByUsername(username);
        if (user == null)
            return Result.failure(MenuErrors.USER_DOESNT_EXIST);
        if (!user.getPassword().equals(password))
            return Result.failure(MenuErrors.PASSWORD_DONT_MATCH);
        return Result.success(user);
    }

    public Result<Void> handleResetPassword(String username, String securityQuestion, String securityAnswer, String newPassword, String repeatPassword) {
        User user = UserRepository.getInstance().getByUsername(username);
        if (user == null)
            return Result.failure(MenuErrors.USER_DOESNT_EXIST);
        if (!user.getSecurityQuestion().equals(securityQuestion))
            return Result.failure(MenuErrors.WRONG_SECURITY_QUESTION);
        if (!user.getSecurityAnswer().equals(securityAnswer))
            return Result.failure(MenuErrors.WRONG_ANSWER);
        if (!newPassword.equals(repeatPassword))
            return Result.failure(MenuErrors.PASSWORDS_DIFFER);
        if (!RegisterController.isPasswordStrong(newPassword))
            return Result.failure(MenuErrors.WEAK_PASS);
        user.setPassword(newPassword);
        return Result.success(null);
    }
}
