package com.example.instagramclone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.input.InputManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {



    Boolean SignUpModeActive = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ConstraintLayout layoutid = findViewById(R.id.Layoutid);
         final TextView SignInText = findViewById(R.id.SignInText);

      final    Button SignUp = findViewById(R.id.SignUp);
        final EditText Name = findViewById(R.id.name);
         final EditText Password = findViewById(R.id.password);


        layoutid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
            }
        });


            SignInText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (SignUpModeActive) {
                        SignUpModeActive = false;
                        SignInText.setText("SignUp");
                        SignUp.setText("LogIn");
                    } else {
                        SignUp.setText("SignIn");
                        SignInText.setText("SignUp");
                        SignUpModeActive = true;
                    }
                }
            });

        SignUp.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          if (Name.getText().toString().equals("") || Password.getText().toString().equals("")) {
                                              Toast.makeText(MainActivity.this, "Please enter your data", Toast.LENGTH_SHORT).show();
                                          } else {
                                              if (SignUpModeActive){
                                              ParseUser user = new ParseUser();
                                              user.setUsername(Name.getText().toString());
                                              user.setPassword(Password.getText().toString());
                                              user.signUpInBackground(new SignUpCallback() {
                                                  @Override
                                                  public void done(ParseException e) {
                                                      if (e == null) {
                                                          showUserList();
                                                          Toast.makeText(MainActivity.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                                                      } else {
                                                          Toast.makeText(MainActivity.this, "Login Unsucessful", Toast.LENGTH_SHORT).show();
                                                      }
                                                  }
                                              });
                                          }
                                          else {
                                              ParseUser.logInInBackground(Name.getText().toString(), Password.getText().toString(), new LogInCallback() {
                                                  @Override
                                                  public void done(ParseUser user, ParseException e) {
                                                      if (user!=null){
                                                          showUserList();
                                                          Toast.makeText(MainActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                                                      }
                                                      else
                                                      {
                                                          Toast.makeText(MainActivity.this, "Error"+e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                                                      }
                                                  }
                                              });

                                              }
                                          }
                                      }
                                  });

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("zQsg3t4DfofciOteCwcLx4UR1LCZ6VkWJq2UcLls")
                .clientKey("gZyvGpE3f7BpgkiL1Hy43UdHXRIMLYFWpuUx87m5")
                .server("https://parseapi.back4app.com")
                .build()
        );
        createObject();


        if (ParseUser.getCurrentUser() != null){
            showUserList();
        }
    }

    public void createObject() {
        String myCustomKey1Value = "foo";
        Integer myCustomKey2Value = 999;

        ParseObject myNewObject = new ParseObject("MyCustomClassName");
        myNewObject.put("myCustomKey1Name", myCustomKey1Value);
        myNewObject.put("myCustomKey2Name", myCustomKey2Value);

        // Saves the new object.
        // Notice that the SaveCallback is totally optional!
        myNewObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                // Here you can handle errors, if thrown. Otherwise, "e" should be null
            }
        });
    }
    public void showUserList(){
        Intent intent = new Intent(getApplicationContext(),UserListActivity.class);
        startActivity(intent);
    }

}

