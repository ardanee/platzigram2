package com.rubik.platzigram.login.view;

/**
 * Created by lramirez on 24/07/2017.
 */

public interface Iloginview {
    void goCreateAccount();
    void goHome();
    void enableInputs();
    void disableInputs();
    void showProgressBar();
    void hideProgressBar();
    void loginError(String error);
}
