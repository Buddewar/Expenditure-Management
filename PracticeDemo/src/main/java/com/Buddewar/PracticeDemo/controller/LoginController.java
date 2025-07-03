package com.Buddewar.PracticeDemo.controller;

import com.Buddewar.PracticeDemo.entity.UserInfo;
import com.Buddewar.PracticeDemo.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
public class LoginController {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @GetMapping("/showLoginForm")
    public String showLoginForm()
    {
        return "login";
    }

    @GetMapping("/user")
    public String userPage(Model theModel)
    {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        String name;
        Optional<UserInfo> userInfoOptional=userInfoRepository.findByUserUsername(username);
        if(!userInfoOptional.isPresent())
        {
            name=username;
            theModel.addAttribute("username", name);
        }
        else {
            UserInfo userInfo=userInfoOptional.get();
            name=userInfo.getName();

            theModel.addAttribute("username", name);
//        // add to the spring MVC model
//        theModel.addAttribute("userinfo",userInfo);
        }
        return "home";

    }

    @GetMapping("/admin")
    public String adminPage()
    {
        return "admin";
    }


}
