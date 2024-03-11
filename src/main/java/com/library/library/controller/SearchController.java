package com.library.library.controller;

import com.library.library.model.Book;
import com.library.library.model.Category;
import com.library.library.model.security.User;
import com.library.library.service.BookService;
import com.library.library.service.CategoryService;
import com.library.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
public class SearchController {
    private UserService userService;
    private BookService bookService;
    private CategoryService categoryService;

    @Autowired
    public SearchController(UserService userService, BookService bookService, CategoryService categoryService) {
        this.userService = userService;
        this.bookService = bookService;
        this.categoryService = categoryService;
    }

    @RequestMapping("/searchByCategory")
    public String searchByCategory(@RequestParam("category") String category, Model model, Principal principal) {

        if (principal != null) {
            String username = principal.getName();
            User user = this.userService.findByUsername(username);
            model.addAttribute("user", user);
        }

        model.addAttribute("clsActiveCategory", category);

     //   Long id = Long.parseLong(categoryId);
        Category selectedCategory = this.categoryService.findByName(category);//categoryId);
        List<Book> bookList = this.bookService.findByCategory(selectedCategory);
        List<Category> categoryList = categoryService.findAll();

        model.addAttribute("categoryList", categoryList);
        if (bookList.isEmpty()) {
            model.addAttribute("emptyList", true);
            //return "bookshelf";
        } else {
            model.addAttribute("bookList", bookList);
        }

        return "bookshelf";
    }
    @RequestMapping({"/searchBook"})
    public String listFirstPage(@ModelAttribute("keyword") String keyword, Principal principal, Model model){
        return listByPage(1, model);

    }
    @RequestMapping("/searchBook/page/{pageNum}")
    public String listByPage(@PathVariable(name = "pageNum") int pageNum, Model model  ){
        Page<Book> page = bookService.listByPage(pageNum); // selects the elements for the fisrt page from the database
        // bookService.listByPage(pageNum);
        List<Book> bookList = page.getContent();

        long startCount = (pageNum - 1) * BookService.PRODUCTS_PER_PAGE + 1;
        long endCount = startCount + BookService.PRODUCTS_PER_PAGE - 1;
        if (endCount > page.getTotalPages()){
            endCount = page.getTotalElements();
        }

        List<Category> categoryList = categoryService.findAll();
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("bookList", bookList);
        model.addAttribute("currentPage", pageNum  );
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("startCount", startCount);
        model.addAttribute("endCount", endCount);
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("bookList", bookList);


        return "bookshelf";
    }


}
