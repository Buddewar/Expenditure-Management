package com.Buddewar.PracticeDemo.controller;

import com.Buddewar.PracticeDemo.entity.Role;
import com.Buddewar.PracticeDemo.entity.User;
import com.Buddewar.PracticeDemo.entity.UserInfo;
import com.Buddewar.PracticeDemo.repository.RoleRepository;
import com.Buddewar.PracticeDemo.repository.UserInfoRepository;
import com.Buddewar.PracticeDemo.repository.UserRepository;
import jakarta.persistence.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Controller
public class RegisterController {



    @GetMapping("/showRegistrationForm")
    public String registerNewUser()
    {
        return "register";
    }

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    @PostMapping("/Registration")
    public String registerNewUser(@RequestParam("username") String username,
                                  @RequestParam("name")String name,
                                  @RequestParam("email") String email,
                                  @RequestParam("password") String password, Model model)
    {
        if(username==null || username.trim().isEmpty() || name==null || email==null
                || name.trim().isEmpty() ||  email.trim().isEmpty()) {
            String message = "All fields are required";
            model.addAttribute("message", message);
            return "register";
        }

        User user= new User();
        User userdummy=userRepository.findByUsername(username);
        if (userdummy != null) {
            String message = "Username already present!";
            model.addAttribute("message", message);
            return "register";
        }

            user.setUsername(username);
            user.setEmail(email);
            user.setPassword(passwordEncoder.encode(password));
            user.setCreated_at(LocalDateTime.now());
            // setting user as default user role
            Role defaultUser = roleRepository.findByName("User");
            user.setRole(defaultUser);

            userRepository.save(user);

            UserInfo userInfo = new UserInfo();

            userInfo.setUser(user);
            userInfo.setName(name);

            userInfoRepository.save(userInfo);

            return "redirect:/showLoginForm?success";

    }


}
