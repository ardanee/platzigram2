package com.rubik.platzigram.login.repository;

import com.rubik.platzigram.login.presenter.LoginPresenter;

/**
 * Created by lramirez on 24/07/2017.
 */

public class LoginRepository implements IloginRepository {
   LoginPresenter presenter ;

    public LoginRepository(LoginPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void signIn(String username, String password) {
        boolean success = true;
        if(success){
            presenter.loginSuccess();
        }
        else{
            presenter.loginError("Usuario o contraseña inválidos");
        }
    }
}
