package com.rubik.platzigram.login.presenter;

import com.rubik.platzigram.login.interactor.ILoginInteractor;
import com.rubik.platzigram.login.interactor.LoginInteractor;
import com.rubik.platzigram.login.view.LoginActivity;

/**
 * Created by lramirez on 24/07/2017.
 */

public class LoginPresenter implements ILoginPresenter {

    private LoginActivity loginActivity;
    private LoginInteractor loginInteractor;

    public LoginPresenter(LoginActivity loginActivity) {
        this.loginActivity = loginActivity;
        loginInteractor = new LoginInteractor(this);
    }

    @Override
    public void signIn(String username, String password) {
        loginActivity.disableInputs();
        loginActivity.showProgressBar();
        loginInteractor.signIn(username, password);

    }

    @Override
    public void loginSuccess() {
        loginActivity.goHome();
        loginActivity.hideProgressBar();
    }

    @Override
    public void loginError(String mensaje) {
        loginActivity.enableInputs();
        loginActivity.hideProgressBar();
        loginActivity.loginError(mensaje);
    }
}
