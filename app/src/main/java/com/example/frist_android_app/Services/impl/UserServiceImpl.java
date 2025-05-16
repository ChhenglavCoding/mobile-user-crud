package com.example.frist_android_app.Services.impl;

import com.example.frist_android_app.Services.UserService;
import com.example.frist_android_app.models.Role;
import com.example.frist_android_app.models.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserServiceImpl implements UserService {


    private static List<User> userList = new ArrayList<>();
    private static List<Role> roleList = new ArrayList<>();
    @Override
    public List<User> getAllUser() {
        if(userList.isEmpty()){
            userList.add(new User(
               1,"Chhenglav","Chhenglav@gmail.ciom","Male",getRoleById(1)
            ));
            userList.add(new User(
                    2,"Nika","Nika@gmail.ciom","Female",getRoleById(2)
            ));
            userList.add(new User(
                    3,"buna","buna@gmail.ciom","Male",getRoleById(3)
            ));
            userList.add(new User(
                    4,"lina","lina@gmail.ciom","Female",getRoleById(4)
            ));
            userList.add(new User(
                    5,"sokpy","sokpy@gmail.ciom","Female",getRoleById(5)
            ));
        }
        return userList;
    }

    @Override
    public void insertUser(User user) {

        user.setId(userList.size()+1);
        userList.add(user);
    }

    @Override
    public void updateUser(User user) {

// add more
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getId() == user.getId()) {
                userList.set(i, user);
                break;
            }
        }
    }

    @Override
    public User getUserById(int id) {
        for (User user : userList){
            if(user.getId()==id){
                return user;
            }
        }
        return null;
    }

    @Override
    public List<Role> getAllRoles() {
        if (roleList.isEmpty()){
            Role roleAdmin = new Role(1, "Admin");
            roleList.add(roleAdmin);
            Role roleUser = new Role(2,"User");
            roleList.add(roleUser);
            Role roleCashier = new Role(3,"Cashier");
            roleList.add(roleCashier);
            Role roleSupperAdmin = new Role(4,"SuperAdmin");
            roleList.add(roleSupperAdmin);
            Role roleSale = new Role(5,"Sale");
            roleList.add(roleSale);
        }

        return roleList;
    }

    @Override
    public Role getRoleById(int roleId) {
        for (Role role: roleList){
            if(role.getId()==roleId){
                return role;
            }
        }
        return null;
    }

    // add more implement
    @Override
    public void deleteUser(int id) {
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getId() == id) {
                userList.remove(i);
                break;
            }
        }
    }
}
