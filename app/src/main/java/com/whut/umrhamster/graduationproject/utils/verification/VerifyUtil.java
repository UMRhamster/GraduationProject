package com.whut.umrhamster.graduationproject.utils.verification;

import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VerifyUtil {
    public static boolean localEmailVerify(String email){
        String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Log.d(email,""+email.matches(REGEX_EMAIL));
        return email.matches(REGEX_EMAIL);
    }
}
