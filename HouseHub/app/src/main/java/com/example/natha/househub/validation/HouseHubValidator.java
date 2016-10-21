package com.example.natha.househub.validation;

/**
 * Created by natha on 10/20/2016.
 */
public class HouseHubValidator {


    public static boolean isEmpty(String string) {
        if (string == null || string.replaceAll("\\s", "").length() == 0) {
            return true;
        }
        return false;
    }

    public static boolean exceedsLength(String string, int length) {
        if(string.length() > length) {
            return true;
        }
        return false;
    }

    public static boolean lengthIsBetween(String string, int lower, int higher) {
        if(string.length() >= lower && string.length() <= higher) {
            return true;
        }
        return false;
    }

    public static boolean isNotANumber(String string) {
        try {
            Integer.parseInt(string.trim());
            return false;
        }
        catch(NumberFormatException e) {
            return true;
        }
    }

    public static boolean isNegative(String string) {
        if(!isNotANumber(string)) {
            if(Integer.parseInt(string) < 0)
                return true;
        }
        return false;
    }
}
