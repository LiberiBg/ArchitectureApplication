package esiea.yangnguyen.architectureapplication.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Utils {
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.isBlank();
    }
}
