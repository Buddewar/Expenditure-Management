package com.Buddewar.PracticeDemo.controller;

import com.Buddewar.PracticeDemo.entity.AdminRequest;
import com.Buddewar.PracticeDemo.entity.Role;
import com.Buddewar.PracticeDemo.entity.User;
import com.Buddewar.PracticeDemo.repository.AdminRequestRepository;
import com.Buddewar.PracticeDemo.repository.RoleRepository;
import com.Buddewar.PracticeDemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdminRequestRepository adminRequestRepository;

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/admin/users")
    public String getAllusers(Model theModel)
    {
        List<User> users=userRepository.findAll();
        theModel.addAttribute("users",users);

        return "list-all-users";
    }


    @GetMapping("/admin/deleteUsers")
    public String showUsersForDeletion(Model theModel) {
        List<User> users = userRepository.findAll();
        List<User> tempUsers = new ArrayList<>();
        Authentication auth= SecurityContextHolder.getContext().getAuthentication();
        String username=auth.getName();
        for(User temp:users)
        {
            if(!temp.getUsername().equals(username))
            {
                tempUsers.add(temp);
            }
        }
        theModel.addAttribute("users", tempUsers);
        return "delete-users";
    }


    @GetMapping("/admin/removeUser")
    public String deleteUserById(@RequestParam("id") int id) {
        userRepository.deleteById(id);
        return "redirect:/admin/deleteUsers";
    }

    @GetMapping("/admin/accessRequests")
    public String accessRequests(Model model)
    {
        List<AdminRequest> dummy=adminRequestRepository.findAll();
        List<AdminRequest> adminRequestList=new ArrayList<>();
        Authentication auth= SecurityContextHolder.getContext().getAuthentication();
        String username=auth.getName();
        User user=userRepository.findByUsername(username);
        for(AdminRequest temp:dummy)
        {
            if(temp.getUser().getId()!=(user.getId()))
            {
                adminRequestList.add(temp);
            }
        }
        model.addAttribute("adminrequests",adminRequestList);
        return "admin-requests";
    }

    @GetMapping("/admin/assignAdmin")
    public String assignAdmin(@RequestParam("id") int id)
    {
        User user=userRepository.findById(id).orElseThrow();
        Role adminRole=roleRepository.findByName("Admin");

        user.setRole(adminRole);
        userRepository.save(user);
        return "redirect:/admin/accessRequests";
    }

    @GetMapping("/admin/revokeAdmin")
    public String revokeAdmin(@RequestParam("id") int id)
    {
        User user=userRepository.findById(id).orElseThrow();
        Role userRole=roleRepository.findByName("User");

        user.setRole(userRole);
        userRepository.save(user);
        return "redirect:/admin/accessRequests";
    }

}
