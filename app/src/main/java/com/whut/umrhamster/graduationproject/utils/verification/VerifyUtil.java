package com.whut.umrhamster.graduationproject.utils.verification;

public class VerifyUtil {
    public enum VerifyStatus{
        USERNAME_ILLEGAL,PASSWORD_ILLEGAL, VERIFICATION_ILLEGAL,
        NICKNAME_AND_USERNAME_ILLEGAL,USERNAME_AND_VERIFICATION_ILLEGAL,NICKNAME_AND_VERIFICATION_ILLEGAL,
        ALL_ILLEGAL,ALL_LEGAL,

        USERNAEM_AND_PASSWORD_LEGAL
    }

    public static VerifyStatus localLoginVerify(String username, String password){
        if (username.isEmpty() && password.isEmpty()){
            return VerifyStatus.NICKNAME_AND_USERNAME_ILLEGAL;
        }else if (username.isEmpty()){
            return VerifyStatus.USERNAME_ILLEGAL;
        }else if (password.isEmpty()){
            return VerifyStatus.PASSWORD_ILLEGAL;
        }else {
            return VerifyStatus.USERNAEM_AND_PASSWORD_LEGAL;
        }
    }

    public static VerifyStatus localSignUpVerify(String nickname, String username, String password){
        if (nickname.isEmpty() && username.isEmpty() && password.isEmpty()){
            return VerifyStatus.ALL_ILLEGAL;
        }else if (nickname.isEmpty() && username.isEmpty()){
            return VerifyStatus.NICKNAME_AND_USERNAME_ILLEGAL;
        }else if (nickname.isEmpty() && password.isEmpty()){
            return VerifyStatus.NICKNAME_AND_USERNAME_ILLEGAL;
        }else {
            return VerifyStatus.USERNAEM_AND_PASSWORD_LEGAL;
        }
    }
}
