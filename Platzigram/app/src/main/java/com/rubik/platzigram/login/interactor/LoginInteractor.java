package com.rubik.platzigram.login.interactor;

import com.rubik.platzigram.login.presenter.LoginPresenter;
import com.rubik.platzigram.login.repository.LoginRepository;

/**
 * Created by lramirez on 24/07/2017.
 */

public class LoginInteractor implements ILoginInteractor {
    private LoginPresenter loginPresenter;
    private LoginRepository repository;

    public LoginInteractor(LoginPresenter loginPresenter) {
        this.loginPresenter = loginPresenter;
        this.repository = new LoginRepository(loginPresenter);
    }

    @Override
    public void signIn(String username, String password) {
        repository.signIn(username,password);
    }
}
