package com.example.frist_android_app.Services;

import com.example.frist_android_app.models.Role;
import com.example.frist_android_app.models.User;

import java.util.List;

public interface UserService {
    List<User> getAllUser();
    void insertUser (User user);
    void updateUser(User user);
    User getUserById(int id );
    List<Role> getAllRoles();

    Role getRoleById(int roleId);

    void deleteUser(int id);
}
