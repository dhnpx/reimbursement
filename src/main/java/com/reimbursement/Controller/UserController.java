package com.reimbursement.Controller;

import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.reimbursement.Model.User;
import com.reimbursement.Util.Auth;
import com.reimbursement.DAO.UserDao;

public class UserController {
    public static String register(User user) {
        user.setPassword(Auth.hashPassword(user.getPassword()));
        int registered = UserDao.addUser(user);
        if (registered == 1) {
            return "You have successfully registered";
        }
        return "This username is already in use";
    }

    public static int login(User user) {
        User account = UserDao.getUser(user.getUsername());
        if (account.getUsername().equals("") && account.getPassword().equals("")) {
            return 0;
        }        
        boolean same = Auth.checkPassword(user.getPassword(), account.getPassword());
        if (same == true) {
            user.setRole(account.getRole());
            user.setId(account.getId());
            return 1;
        } else {
            return 0;
        }
    }

    public static int changeRole(byte[] jsonData) {
        ObjectMapper objectMapper = new ObjectMapper();
        int user_id = 0;
        String role = "";
        try {
            JsonNode rootNode = objectMapper.readTree(jsonData);
            JsonNode idNode = rootNode.path("user_id");
            user_id = idNode.asInt();
            JsonNode roleNode = rootNode.path("role");
            role = roleNode.asText();
            if (user_id == 0 || (!role.equals("employee") && !role.equals("manager"))) {
                return 0;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
        int updated = UserDao.updateRole(user_id, role);
        System.out.println(updated);
        if (updated == 1) {
            return 1;
        } else {
            return 2;
        }
    }
}
