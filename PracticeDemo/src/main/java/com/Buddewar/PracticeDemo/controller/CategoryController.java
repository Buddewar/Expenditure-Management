package com.Buddewar.PracticeDemo.controller;

import com.Buddewar.PracticeDemo.entity.Category;
import com.Buddewar.PracticeDemo.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;
    @GetMapping("/admin/categories")
    public String AllCategories(Model model)
    {
        List<Category> categories=categoryRepository.findAll();
        model.addAttribute("categories",categories);
        return "categories";
    }


    @GetMapping("/admin/addCategory")
    public String addCategory(Model model)
    {
        Category category=new Category();
        model.addAttribute("category",category);
        return "addCategory";
    }

    @PostMapping("/admin/saveCategory")
    public  String saveCategory(@ModelAttribute("category") Category category, RedirectAttributes redirectAttributes)
    {
        categoryRepository.save(category);
//        String message="Category created Successfully!!";
//       // redirectAttributes.addAttribute("message",message);
//        redirectAttributes.addFlashAttribute("message", "Category created successfully!");
//        redirectAttributes.addFlashAttribute("status", "success");

        return "redirect:/admin/addCategory";
    }

    @GetMapping("/admin/editOrDeleteCategory")
    public String editOrDeleteCategory(Model model)
    {
        List<Category> categories=categoryRepository.findAll();
        model.addAttribute("categories",categories);
        return "edit-or-delete-category";
    }

    @GetMapping("/admin/editCategory")
    public String editCategory(@RequestParam("id") int id, Model model)
    {
        Optional<Category> categoryOptional=categoryRepository.findById(id);
        if(categoryOptional.isPresent())
        {
            Category category=categoryOptional.get();
            model.addAttribute(category);
        }
        return "addCategory";
    }

    @GetMapping("/admin/deleteCategory")
    public String deleteCategory(@RequestParam("id") int id)
    {
        categoryRepository.deleteById(id);
        return "redirect:/admin/editOrDeleteCategory";
    }



}
