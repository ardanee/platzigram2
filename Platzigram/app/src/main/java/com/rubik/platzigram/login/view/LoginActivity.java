package com.rubik.platzigram.login.view;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.rubik.platzigram.ContainerActivity;

import com.rubik.platzigram.R;
import com.rubik.platzigram.createAccountActivity;
import com.rubik.platzigram.login.presenter.LoginPresenter;

public class LoginActivity extends AppCompatActivity implements Iloginview {
    static String TAG="LoginActivity";
    private TextInputEditText etUserName, etPassword;
    private Button btnLogin;
    private ProgressBar progressBarLogin;
    private LoginPresenter presenter;
    TextView tvCreateAccount;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    ProgressDialog loginDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUserName = (TextInputEditText) findViewById(R.id.etUserName);
        etPassword = (TextInputEditText) findViewById(R.id.etPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        progressBarLogin = (ProgressBar) findViewById(R.id.progressBarLogin);
        tvCreateAccount = (TextView) findViewById(R.id.tvCreateAccount);
        loginDialog = new ProgressDialog(this);

        //Autenticación
        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    goHome();

                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
        // ...


        presenter = new LoginPresenter(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autenticar();
               // presenter.signIn(etUserName.getText().toString(), etPassword.getText().toString());
            }
        });

        tvCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goCreateAccount();
            }
        });

    }

private void autenticar(){
    loginDialog.setTitle("Autenticando");
    loginDialog.setMessage("Espere un momento...");
    loginDialog.show();

    mAuth.signInWithEmailAndPassword(etUserName.getText().toString(), etPassword.getText().toString())
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                    // If sign in fails, display a message to the user. If sign in succeeds
                    // the auth state listener will be notified and logic to handle the
                    // signed in user can be handled in the listener.
                    if (!task.isSuccessful()) {
                        Log.w(TAG, "signInWithEmail:failed", task.getException());
                        Toast.makeText(LoginActivity.this, "Login Incorrecto",
                                Toast.LENGTH_SHORT).show();
                    }

                    // ...
                }
            });

    loginDialog.dismiss();
}

    @Override
    public void goCreateAccount() {
        Intent iCreateAccount = new Intent(this,createAccountActivity.class);
        startActivity(iCreateAccount);
    }



    @Override
    public void goHome() {
        enableInputs();
        Toast.makeText(this,"Login correcto",Toast.LENGTH_SHORT).show();
        Intent iHome = new Intent(this,ContainerActivity.class);
        startActivity(iHome);
            }

    @Override
    public void enableInputs() {
        etUserName.setEnabled(true);
        etPassword.setEnabled(true);
        btnLogin.setEnabled(true);
    }

    @Override
    public void disableInputs() {
        etUserName.setEnabled(false);
        etPassword.setEnabled(false);
        btnLogin.setEnabled(false);
    }

    @Override
    public void showProgressBar() {
        progressBarLogin.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBarLogin.setVisibility(View.GONE);
    }

    @Override
    public void loginError(String error) {
        Toast.makeText(this,"Ocurrió el siguiente error: " + error,Toast.LENGTH_SHORT).show();
    }






    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {

    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }




}
