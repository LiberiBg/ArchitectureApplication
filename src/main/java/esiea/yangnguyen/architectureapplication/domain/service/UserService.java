package esiea.yangnguyen.architectureapplication.domain.service;

import lombok.experimental.UtilityClass;

import static esiea.yangnguyen.architectureapplication.utils.Utils.isNullOrEmpty;

@UtilityClass
public class UserService {
    public static void validate(String firstName, String lastName, String email, String password) {
        if (isNullOrEmpty(firstName)) throw new IllegalArgumentException("First name is missing");
        if (isNullOrEmpty(lastName)) throw new IllegalArgumentException("Last name is missing");
        if (isNullOrEmpty(email) || !email.contains("@")) throw new IllegalArgumentException("Email is invalid");
        if (!UserService.validatePassword(password)) throw new IllegalArgumentException("Password is invalid");
    }

    private static boolean validatePassword(String password) {
        if (password.length() < 8) return false;

        boolean hasUpper = false;
        boolean hasLower = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) hasUpper = true;
            else if (Character.isLowerCase(c)) hasLower = true;
            else if (Character.isDigit(c)) hasDigit = true;
            else hasSpecial = true;
        }

        return hasUpper && hasLower && hasDigit && hasSpecial;
    }

}
