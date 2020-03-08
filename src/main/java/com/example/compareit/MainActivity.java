package com.example.compareit;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.Objects;

import dmax.dialog.SpotsDialog;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {

    private Button btnSignIn, btnRegister;
    private FirebaseAuth mauth;
    private FirebaseDatabase db;
    private DatabaseReference users;
    private RelativeLayout root_layout;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/arkhip_font.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());
        setContentView(R.layout.activity_main);

        mauth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        users = db.getReference("Users");

        btnRegister = findViewById(R.id.btnRegister);
        btnSignIn = findViewById(R.id.btnSignIn);
        root_layout = findViewById(R.id.root_layout);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRegisterDialog();
            }
        });
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLoginDialog();
            }
        });
    }


    private void showRegisterDialog() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
        dialog.setTitle("REGISTER ");
        dialog.setMessage("Please use email to register");

        LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
        View register_layout = inflater.inflate(R.layout.register_layout, null);

        final MaterialEditText etEmailRegister = register_layout.findViewById(R.id.etEmailRegister);
        final MaterialEditText etPasswordRegister = register_layout.findViewById(R.id.etPasswordRegister);
        final MaterialEditText etNameRegister = register_layout.findViewById(R.id.etNameRegister);
        final MaterialEditText etPhoneRegister = register_layout.findViewById(R.id.etPhoneRegister);

        dialog.setView(register_layout);

        dialog.setPositiveButton("REGISTER", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();


                //check validation of user
                if (TextUtils.isEmpty(etEmailRegister.getText().toString())) {
                    // here we use a snack bar and show it in root layout(MainActivity)
                    Snackbar.make(root_layout, "Please enter email address", Snackbar.LENGTH_LONG).show();
                    return;
                }
                if (etPasswordRegister.getText().toString().length() < 6) {
                    // here we use a snack bar and show it in root layout(MainActivity)
                    Snackbar.make(root_layout, "Password too short !!!", Snackbar.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(etNameRegister.getText().toString())) {
                    // here we use a snack bar and show it in root layout(MainActivity)
                    Snackbar.make(root_layout, "Please enter name", Snackbar.LENGTH_LONG).show();
                    return;
                }

                if (TextUtils.isEmpty(etPhoneRegister.getText().toString())) {
                    // here we use a snack bar and show it in root layout(MainActivity)
                    Snackbar.make(root_layout, "Please enter phone number", Snackbar.LENGTH_LONG).show();
                    return;
                }

                if (TextUtils.isEmpty(etPasswordRegister.getText().toString())) {
                    // here we use a snack bar and show it in root layout(MainActivity)
                    Snackbar.make(root_layout, "Please enter password", Snackbar.LENGTH_LONG).show();
                    return;
                }

                final AlertDialog waitingDialog = new SpotsDialog(MainActivity.this);
                waitingDialog.show();
                mauth.createUserWithEmailAndPassword(etEmailRegister.getText().toString(), etPasswordRegister.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                User user = new User();
                                user.setEmail(etEmailRegister.getText().toString());
                                user.setPassword(etPasswordRegister.getText().toString());
                                user.setName(etNameRegister.getText().toString());
                                user.setPhone(etPhoneRegister.getText().toString());

                                // users is the database reference
                                //here we have use UID as the key reference for particular user
                                users.child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                                        .setValue(user)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                waitingDialog.dismiss();
                                                Snackbar.make(root_layout, "Registered successfully", Snackbar.LENGTH_LONG).show();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                waitingDialog.dismiss();
                                                Snackbar.make(root_layout, "FAILED :" + e.getMessage(), Snackbar.LENGTH_LONG).show();
                                            }
                                        });
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                waitingDialog.dismiss();
                                Snackbar.make(root_layout, "FAILED :" + e.getMessage(), Snackbar.LENGTH_LONG).show();

                            }
                        });
            }

        });

        dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dialog.show();
    }

    private void showLoginDialog() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("SIGN IN ");
        dialog.setMessage("Please use email to sign in");

        LayoutInflater inflater = LayoutInflater.from(this);
        View login_layout = inflater.inflate(R.layout.login_layout, null);

        final MaterialEditText etEmailRegister = login_layout.findViewById(R.id.etEmailRegister);
        final MaterialEditText etPasswordRegister = login_layout.findViewById(R.id.etPasswordRegister);

        dialog.setView(login_layout);

        dialog.setPositiveButton("SIGN IN", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                //check validation of user
                if (TextUtils.isEmpty(etEmailRegister.getText().toString())) {
                    // here we use a snack bar and show it in root layout(MainActivity)
                    Snackbar.make(root_layout, "Please enter email address", Snackbar.LENGTH_LONG).show();
                    return;
                }

                if (etPasswordRegister.getText().toString().length() < 6) {
                    // here we use a snack bar and show it in root layout(MainActivity)
                    Snackbar.make(root_layout, "Password too short !!!", Snackbar.LENGTH_LONG).show();
                    return;
                }

                if (TextUtils.isEmpty(etPasswordRegister.getText().toString())) {
                    // here we use a snack bar and show it in root layout(MainActivity)
                    Snackbar.make(root_layout, "Please enter password", Snackbar.LENGTH_LONG).show();
                    return;
                }

                dialogInterface.dismiss();

                // disables signIn button to avoid multiple authentications
                btnSignIn.setEnabled(false);
                // Login authentication

                final AlertDialog waitingDialog = new SpotsDialog(MainActivity.this);
                waitingDialog.show();
                mauth.signInWithEmailAndPassword(etEmailRegister.getText().toString(), etPasswordRegister.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                waitingDialog.dismiss();
                                startActivity(new Intent(MainActivity.this, Home.class));
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                waitingDialog.dismiss();
                                Snackbar.make(root_layout, "FAILED " + e.getMessage(), Snackbar.LENGTH_LONG).show();
                                btnSignIn.setEnabled(true);
                            }
                        });
            }
        })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        btnSignIn.setEnabled(true);
                        dialogInterface.dismiss();
                    }
                });
        dialog.show();
    }
}