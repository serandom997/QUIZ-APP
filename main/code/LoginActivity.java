package com.example.csit_assignment_2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.widget.NestedScrollView;

import com.example.csit_assignment_2.User;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

//import com.androidtutorialshub.loginregister.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private final AppCompatActivity activity = LoginActivity.this;
    private NestedScrollView nestedScrollView;
    private TextInputLayout textInputLayoutUsername;
    private TextInputLayout textInputLayoutPassword;
    private TextInputEditText textInputEditTextUsername;
    private TextInputEditText textInputEditTextPassword;
    private Button appCompatButtonLogin;
    private TextView textViewLinkRegister;
    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;
    public User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        actionBar = getSupportActionBar();
//        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
//        //  Remove the redundant calls to getSupportActionBar()
//        //       and use variable actionBar instead
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(true);
//        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().hide();
        initViews();
        initListeners();
        initObjects();
    }

    /**
     * This method is to initialize views
     */
    private void initViews() {
        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);
        textInputLayoutUsername = (TextInputLayout) findViewById(R.id.textInputLayoutUsername);
        textInputLayoutPassword = (TextInputLayout) findViewById(R.id.textInputLayoutPassword);
        textInputEditTextUsername = (TextInputEditText) findViewById(R.id.userbox);
        textInputEditTextPassword = (TextInputEditText) findViewById(R.id.passbox);
        appCompatButtonLogin = (Button) findViewById(R.id.loginbutton);
        textViewLinkRegister = (TextView) findViewById(R.id.textViewLinkRegister);
    }

    /**
     * This method is to initialize listeners
     */
    private void initListeners() {
        appCompatButtonLogin.setOnClickListener(this);
        textViewLinkRegister.setOnClickListener(this);
    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        databaseHelper = new DatabaseHelper(activity);
        inputValidation = new InputValidation(activity);
    }

    /**
     * This implemented method is to listen the click on view
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginbutton:
                verifyFromSQLite();
                break;
            case R.id.textViewLinkRegister:
                // Navigate to RegisterActivity
                Intent intentRegister = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intentRegister);
                break;
        }
    }

    /**
     * This method is to validate the input text fields and verify login credentials from SQLite
     */
    private void verifyFromSQLite() {
        if (!inputValidation.isInputEditTextFilled(textInputEditTextUsername, textInputLayoutUsername, getString(R.string.error_message_email))) {
            return;
        }
//        if (!inputValidation.isInputEditTextEmail(textInputEditTextEmail, textInputLayoutUsername, getString(R.string.error_message_email))) {
//            return;
//        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextPassword, textInputLayoutPassword, getString(R.string.error_message_password))) {
            return;
        }
        if (databaseHelper.checkUser(textInputEditTextUsername.getText().toString().trim()
                , textInputEditTextPassword.getText().toString().trim())) {

            int userDBID = databaseHelper.GetUserID(textInputEditTextUsername.getText().toString().trim());

            User user = new User();
            user.setId(userDBID);
            String username = textInputEditTextUsername.getText().toString().trim();
/*            Intent intentName = new Intent(LoginActivity.this, MainActivity.class);
            Bundle extrasName = new Bundle();
            extrasName.putString("PassLoginUsername", username + "");
            intentName.putExtras(extrasName);*/

            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            Bundle extras = new Bundle();
            extras.putString("PassUserDBID", userDBID + "");
            extras.putString("PassLoginUsername", username + "");
            intent.putExtras(extras);
            startActivity(intent);

            emptyInputEditText();

        } else {
            // Snack Bar to show success message that record is wrong
            Snackbar.make(nestedScrollView, getString(R.string.error_valid_email_password), Snackbar.LENGTH_LONG).show();
            System.out.println("error");
        }
    }

    /**
     * This method is to empty all input edit text
     */
    private void emptyInputEditText() {
        textInputEditTextUsername.setText(null);
        textInputEditTextPassword.setText(null);
    }
}
