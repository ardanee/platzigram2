package com.rubik.platzigram;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class createAccountActivity extends AppCompatActivity {
    private static final String TAG = "CreateAccountActivity";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private Button btnUnete;
    private TextInputEditText etEmail;
    private TextInputEditText etPassword;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        //Muestra Toolbar
        showToolbar(getResources().getString(R.string.TittleToolbarCreateAccount), true);

        dialog = new ProgressDialog(createAccountActivity.this);

        btnUnete = (Button) findViewById(R.id.btnJoinUs);
        etEmail = (TextInputEditText) findViewById(R.id.etEmail);
        etPassword = (TextInputEditText) findViewById(R.id.etPasswordCreateAccount);

        btnUnete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount();
            }
        });


        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d(TAG, "Usuario Logeado " + user.getEmail());
                } else {
                    Log.d(TAG, "Usuario No Logeado ");
                }
            }
        };
    }

    public void showToolbar(String tittle, boolean showUpButton) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(tittle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(showUpButton);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(mAuthListener);
    }

    private void createAccount() {
        String email = etEmail.getText().toString();
        String pass = etPassword.getText().toString();
        dialog.setTitle("Guardando...");
        dialog.setMessage("Subiendo foto a Servidor, espere...");
        dialog.setCancelable(false);
        dialog.show();

        mAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(createAccountActivity.this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        dialog.dismiss();

                        if (task.isSuccessful()) {
                            Toast.makeText(createAccountActivity.this, "Cuenta creada exitosamente", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(createAccountActivity.this, "Ocurrió un error", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

}
