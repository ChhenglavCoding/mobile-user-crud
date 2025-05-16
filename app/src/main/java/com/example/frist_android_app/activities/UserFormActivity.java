package com.example.frist_android_app.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.frist_android_app.R;
import com.example.frist_android_app.Services.UserService;
import com.example.frist_android_app.Services.impl.UserServiceImpl;
import com.example.frist_android_app.adapters.CustomRoleAdapter;
import com.example.frist_android_app.models.Role;

import java.util.List;

public class UserFormActivity extends BaseActivity {

    private UserService userService;
    private Spinner spinnerRole;
    private Role selectRole;
    private CustomRoleAdapter customRoleAdapter;
    private EditText etUsername, etEmail;
    private RadioButton rbMale,rbFemale;
    private Button btnCreate;
    private ImageView btnBack;
    private int userId = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_form);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        userService = new UserServiceImpl();
        initView();
        customRoleAdapter = new CustomRoleAdapter(this,userService.getAllRoles());
        spinnerRole.setAdapter(customRoleAdapter);
        spinnerRole.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectRole = userService.getAllRoles().get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnCreate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                onCreateUser();
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                finish();
            }
        });

    }

    private void onCreateUser() {
        String username = etUsername.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String gender = rbMale.isChecked() ? "Male" : "Female";

        if(username.isEmpty()){
            showToastMessage("Please enter username");
            etUsername.requestFocus();
            return;
        }
        if(email.isEmpty()){
            showToastMessage("Please enter email");
            etEmail.requestFocus();
            return;
        }

        Intent intent = new Intent();
        intent.putExtra("USERNAME", username);
        intent.putExtra("EMAIL", email);
        intent.putExtra("GENDER", gender);
        intent.putExtra("ROLE_ID", selectRole.getId());

        // ✅ Get ID passed in the intent and pass it back for update
        int id = getIntent().getIntExtra("ID", 0);
        if (id != 0) {
            intent.putExtra("ID", id);
        }

        setResult(RESULT_OK, intent);
        finish();



    }
    @SuppressLint("SetTextI18n")
    private void initView(){
       spinnerRole = findViewById(R.id.spinnerRole);
       etUsername = findViewById(R.id.etUsername);
       etEmail = findViewById(R.id.etEmail);
       rbFemale = findViewById(R.id.rbFemale);
       rbMale = findViewById(R.id.rbMale);
       btnBack = findViewById(R.id.btnBck);
       btnCreate = findViewById(R.id.btnCreate);
       rbMale.setChecked(true);

// add more
        Intent intent = getIntent();
        int id = intent.getIntExtra("ID", 0);
        if(id != 0){
            etUsername.setText(intent.getStringExtra("USERNAME"));
            etEmail.setText(intent.getStringExtra("EMAIL"));
            String gender = intent.getStringExtra("GENDER");
            if (gender != null && gender.equals("Male")) {
                rbMale.setChecked(true);
            } else {
                rbFemale.setChecked(true);
            }

            int roleId = intent.getIntExtra("ROLE_ID", 0);
            List<Role> roles = userService.getAllRoles();
            for (int i = 0; i < roles.size(); i++) {
                if (roles.get(i).getId() == roleId) {
                    spinnerRole.setSelection(i);
                    selectRole = roles.get(i);

                }
            }
            btnCreate.setText("Update");
        }

    }
}