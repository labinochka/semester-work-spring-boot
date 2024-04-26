package ru.kpfu.itis.beerokspring.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {

    public final static String USERNAME_REGEX = "^[a-zA-Z0-9]+$";

    public final static String EMAIL_REGEX = "[A-z0-9_-]+@[A-z0-9_-]+.[a-z]+";
}
