package com.Buddewar.PracticeDemo.controller;

import com.Buddewar.PracticeDemo.entity.AdminRequest;
import com.Buddewar.PracticeDemo.entity.Expense;
import com.Buddewar.PracticeDemo.entity.User;
import com.Buddewar.PracticeDemo.entity.UserInfo;
import com.Buddewar.PracticeDemo.repository.AdminRequestRepository;
import com.Buddewar.PracticeDemo.repository.ExpenseRepository;
import com.Buddewar.PracticeDemo.repository.UserInfoRepository;
import com.Buddewar.PracticeDemo.repository.UserRepository;
import com.Buddewar.PracticeDemo.service.ExpenseServiceImpl;
import jakarta.persistence.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdminRequestRepository adminRequestRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private ExpenseServiceImpl expenseService;


    @GetMapping("/user/userProfile")
    public String userProfile(Model model)
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        Optional<UserInfo> optionalUserInfo = userInfoRepository.findByUserUsername(username);

        if (optionalUserInfo.isPresent()) {
            model.addAttribute("userinfo", optionalUserInfo.get());
            }
        return "user-profile";
    }

    @GetMapping("/user/editUserForm")
    public String editUserProfile(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName(); // currently logged-in username

        Optional<UserInfo> optionalUserInfo = userInfoRepository.findByUserUsername(username);

        if (optionalUserInfo.isPresent()) {
            model.addAttribute("userinfo", optionalUserInfo.get());
            return "user-edit-profile";
        } else {
            return "redirect:/error";
        }
    }

    @Transactional
    @PostMapping("/user/saveProfile")
    public String saveProfile(@ModelAttribute("userinfo") UserInfo userInfo,
                              @RequestParam("password") String password)
    {
        Optional<UserInfo> UserInfoOptional=userInfoRepository.findByUserId(
                userInfo.getUser().getId());

        if(UserInfoOptional.isPresent())
        {
            UserInfo existingUserInfo = UserInfoOptional.get();
            existingUserInfo.setName(userInfo.getName());
            existingUserInfo.setIncome(userInfo.getIncome());
            existingUserInfo.setSavings(userInfo.getSavings());
            existingUserInfo.setFinancial_notes(userInfo.getFinancial_notes());
            if(existingUserInfo.getUser()!=null)
            {
                User existingUser=existingUserInfo.getUser();
                existingUser.setUsername(userInfo.getUser().getUsername());
                existingUser.setEmail(userInfo.getUser().getEmail());
                if(!password.isBlank())
                {
                    existingUser.setPassword(encoder.encode(password));
                }
                existingUserInfo.setUser(existingUser);
            }
            userInfoRepository.save(existingUserInfo);
            return "redirect:/user/userProfile";
        }
        else {
            return "redirect:/error";
        }
    }

    @GetMapping("/user/adminRequest")
    public String requestAdminAccess()
    {
        return "requestAccess";
    }

    @PostMapping("/user/processRequest")
    public String processingRequest(@RequestParam("message") String message,Model model)
    {
        Authentication auth=SecurityContextHolder.getContext().getAuthentication();
        String username=auth.getName();

        User user=userRepository.findByUsername(username);
        int user_id=user.getId();

        List<Expense> e=expenseRepository.findAllByUserId(user_id);

        AdminRequest existing = adminRequestRepository.findByUserId(user_id);
        if (existing != null) {
            model.addAttribute("alert", "You have already submitted a request!");
            return "requestAccess";
        }
        if(e.isEmpty())
        {

            AdminRequest adminRequest = new AdminRequest();
            adminRequest.setUser(user);

            adminRequest.setMessage(message);

            adminRequestRepository.save(adminRequest);
            return "redirect:/user";
        }
        else{
            String alert="Can't make admin request!";
            model.addAttribute("alert",alert);
            return "requestAccess";
        }


    }


    @GetMapping("/user/dashboard")
    public String dashboard(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(auth.getName());

        Map<String, Double> categoryData = expenseService.getCategoryTotals(user.getId());
        model.addAttribute("categoryData", categoryData);

        List<String> categoryLabels = new ArrayList<>(categoryData.keySet());
        List<Double> categoryValues = new ArrayList<>(categoryData.values());

        model.addAttribute("categoryLabels", categoryLabels);
        model.addAttribute("categoryValues", categoryValues);

        Map<String, Double> paymentData=expenseService.getPaymentTypeStats(user.getId());

        model.addAttribute("paymentData", paymentData);

        List<String> paymentLabels = new ArrayList<>(paymentData.keySet());
        List<Double> paymentValues = new ArrayList<>(paymentData.values());

        model.addAttribute("paymentLabels", paymentLabels);
        model.addAttribute("paymentValues", paymentValues);

        Map<String,Double> monthlyData=expenseService.getMonthlyStats(user.getId());
        model.addAttribute(monthlyData);

        List<String> monthLabels=new ArrayList<>(monthlyData.keySet());
        List<Double> monthValues=new ArrayList<>(monthlyData.values());

        model.addAttribute("monthLabels", monthLabels);
        model.addAttribute("monthValues", monthValues);

        return "dashboard";
    }



}




/*
  @PostMapping("/user/saveProfile")
    @Transactional
    public String saveProfile(@ModelAttribute("userinfo") UserInfo userInfo,
                              @RequestParam("password") String password) {

        // Retain the existing user reference to prevent detached entity issues
        System.out.println("Id:"+ userInfo.getUser().getId());
        Optional<UserInfo> existingInfoOpt = userInfoRepository.findByUserId(userInfo.getUser().getId());
        if (existingInfoOpt.isPresent()) {
            UserInfo existingInfo = existingInfoOpt.get();

            existingInfo.setName(userInfo.getName());
            existingInfo.setIncome(userInfo.getIncome());
            existingInfo.setSavings(userInfo.getSavings());
            existingInfo.setFinancial_notes(userInfo.getFinancial_notes());

            if (existingInfo.getUser() != null) {
                existingInfo.getUser().setUsername(userInfo.getUser().getUsername());
                existingInfo.getUser().setEmail(userInfo.getUser().getEmail());
                if (!password.isBlank()) {
                    existingInfo.getUser().setPassword(encoder.encode(password));
                }
            }

            userInfoRepository.save(existingInfo);
            return "redirect:/user";
        } else {
            return "redirect:/error";
        }
    }

 */

