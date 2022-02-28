package com.example.csit_assignment_2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.widget.NestedScrollView;
import com.google.android.material.snackbar.Snackbar;


import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import android.view.View;
//import com.androidtutorialshub.loginregister.R;

import com.example.csit_assignment_2.User;
import com.example.csit_assignment_2.InputValidation;
import com.example.csit_assignment_2.DatabaseHelper;
//public class changePassword extends AppCompatActivity implements View.OnClickListener {
public class changePassword extends AppCompatActivity {
    private final AppCompatActivity activity = changePassword.this;
    private NestedScrollView nestedScrollView;

    private TextInputLayout textInputLayoutChangePassword;
    private TextInputLayout textInputLayoutConfirmChangePassword;
    private TextInputEditText textInputEditTextName;
    private TextInputEditText textInputEditTextEmail;
    private TextInputEditText textInputEditTextChangePassword;
    private TextInputEditText textInputEditTextConfirmChangePassword;
    private AppCompatButton appCompatButtonConfirmChangePassword;

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;
    private User user;
    private int intVariableName;
    private AppCompatTextView textViewUsername;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepassword);

        Bundle extrasChange = getIntent().getExtras();

        String stringVariableNameTo = extrasChange.getString("PassToChangeID");
        //int user_idToChange = Integer.parseInt(stringVariableNameTo);
        intVariableName= Integer.parseInt(stringVariableNameTo);

        //Bundle extrasUsername = getIntent().getExtras();
        //String stringUsername = extrasUsername.getString("PassLoginUsername"
        String stringUsername = extrasChange.getString("PassChangeUsername");
        textViewUsername = (AppCompatTextView) findViewById(R.id.textViewUsername);
        textViewUsername.setText(stringUsername);

        final AppCompatButton change=findViewById(R.id.appCompatButtonConfirmChangePassword);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postDataToSQLite();
            }
        });
        initViews();
        //initListeners();
        initObjects();
    }
    /**
     * This method is to initialize views
     */
    private void initViews() {
        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);

        textInputLayoutChangePassword = (TextInputLayout) findViewById(R.id.textInputLayoutChangePassword);
        textInputLayoutConfirmChangePassword = (TextInputLayout) findViewById(R.id.textInputLayoutConfirmChangePassword);

        textInputEditTextChangePassword = (TextInputEditText) findViewById(R.id.textInputEditTextChangePassword);
        textInputEditTextConfirmChangePassword = (TextInputEditText) findViewById(R.id.textInputEditTextConfirmChangePassword);
        appCompatButtonConfirmChangePassword = (AppCompatButton) findViewById(R.id.appCompatButtonConfirmChangePassword);

    }
    /**
     * This method is to initialize listeners
     //     */
/*    private void initListeners() {
        appCompatButtonConfirmChangePassword.setOnClickListener(this);
    }*/

    /*
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        inputValidation = new InputValidation(activity);
        databaseHelper = new DatabaseHelper(activity);
        user = new User();
    }
    /**
     * This implemented method is to listen the click on view
     *
     * @param v
     */
/*    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.appCompatButtonConfirmChangePassword:
                postDataToSQLite();
                break;

        }
    }*/
    /**
     * This method is to validate the input text fields and post data to SQLite
     */
    private void postDataToSQLite() {

        if (!inputValidation.isInputEditTextFilled(textInputEditTextChangePassword, textInputLayoutChangePassword, getString(R.string.error_message_password))) {
            return;
        }
        if (!inputValidation.isInputEditTextMatches(textInputEditTextChangePassword, textInputEditTextConfirmChangePassword,
                textInputLayoutConfirmChangePassword, getString(R.string.error_password_match))) {
            return;
        }
        else {
//            user.setName(textInputEditTextName.getText().toString().trim());
//            user.setEmail(textInputEditTextEmail.getText().toString().trim());


            user.setPassword(textInputEditTextChangePassword.getText().toString().trim());

/*            Bundle extras = getIntent().getExtras();

            String stringVariableNameTo = extras.getString("PassToChangeID");
            //int user_idToChange = Integer.parseInt(stringVariableNameTo);
            intVariableName= Integer.parseInt(stringVariableNameTo);*/
            String password=textInputEditTextChangePassword.getText().toString().trim();
            databaseHelper.updateUser(intVariableName,password);


            // Snack Bar to show success message that record saved successfully
            Snackbar.make(nestedScrollView, getString(R.string.success_updateMessage), Snackbar.LENGTH_LONG).show();
            emptyInputEditText();

            Intent accountsIntent = new Intent(activity, newfile.class);
            startActivity(accountsIntent);
        }

    }
    /**
     * This method is to empty all input edit text
     */
    private void emptyInputEditText() {

        //textInputEditTextEmail.setText(null);
        textInputEditTextChangePassword.setText(null);
        textInputEditTextConfirmChangePassword.setText(null);
    }


/*    public void ChangePassword(User usr) {
        intVariableName=usr.getId();
    }*/
}