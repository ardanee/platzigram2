package com.rubik.platzigram.login.presenter;

/**
 * Created by lramirez on 24/07/2017.
 */

public interface ILoginPresenter {
    void signIn(String username, String password);

    void loginSuccess();

    void loginError(String mensaje);
}
