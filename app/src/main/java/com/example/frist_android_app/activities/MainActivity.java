package com.example.frist_android_app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.frist_android_app.R;
import com.example.frist_android_app.Services.UserService;
import com.example.frist_android_app.Services.impl.UserServiceImpl;
import com.example.frist_android_app.adapters.CustomUserAdapter;
import com.example.frist_android_app.models.Role;
import com.example.frist_android_app.models.User;

public class MainActivity extends BaseActivity {

    public final static int RESULT_CODE_DATA = 2000;
    private UserService userService;
    private ListView listViewUser;

    private ImageView tvAddUser;
    private CustomUserAdapter customUserAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        tvAddUser = findViewById(R.id.tvAddUser);
        userService = new UserServiceImpl();
        listViewUser = findViewById(R.id.lvUser);
        getUserData();
        tvAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UserFormActivity.class);
                startActivityForResult(intent,RESULT_CODE_DATA);
            }
        });


    }
    private void getUserData(){
        userService.getAllRoles();
        customUserAdapter = new CustomUserAdapter(this, userService.getAllUser(), new CustomUserAdapter.OnClickListener() {
            @Override
            public void onEdit(View view, User user) {
                Intent intent = new Intent(MainActivity.this, UserFormActivity.class);
                intent.putExtra("ID",user.getId());
                intent.putExtra("USERNAME",user.getName());
                intent.putExtra("EMAIL",user.getEmail());
                intent.putExtra("GENDER",user.getGender());
                intent.putExtra("ROLE_ID", user.getRole().getId());
                startActivityForResult(intent,RESULT_CODE_DATA);
            }

            // add more
            @Override
            public void onDelete(View view, User user) {
                userService.deleteUser(user.getId());
                showToastMessage("User deleted");
                getUserData();
            }


        });
        listViewUser.setAdapter(customUserAdapter);
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == RESULT_CODE_DATA && resultCode == RESULT_OK) {
            int id = data.getIntExtra("ID", 0); // check if it's update or insert
            User user = new User();
            user.setId(id); // important for updating
            user.setName(data.getStringExtra("USERNAME"));
            user.setEmail(data.getStringExtra("EMAIL"));
            user.setGender(data.getStringExtra("GENDER"));
            Role role = userService.getRoleById(data.getIntExtra("ROLE_ID", 0));
            user.setRole(role);

            if (user.getId() == 0) {
                userService.insertUser(user);
                showToastMessage("Insert data success");
            } else {
                userService.updateUser(user);
                showToastMessage("Update data success");
            }

            getUserData(); // refresh the list
        }

    }
}