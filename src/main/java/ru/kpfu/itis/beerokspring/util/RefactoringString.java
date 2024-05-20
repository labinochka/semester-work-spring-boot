package ru.kpfu.itis.beerokspring.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class RefactoringString {

    public static String capitalizeFirstLetter(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        char firstLetter = input.charAt(0);
        char upperCaseFirstLetter = Character.toUpperCase(firstLetter);
        String remainingLetters = input.substring(1);

        return upperCaseFirstLetter + remainingLetters;
    }
}
