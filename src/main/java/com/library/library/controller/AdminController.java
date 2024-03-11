package com.library.library.controller;

import com.library.library.dao.UserDao;
import com.library.library.model.*;
import com.library.library.model.security.User;
import com.library.library.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class AdminController {

    private UserDao userService;
    private BorowedItemService borowedItemService;
    private ReservedItemService reservedItemService;
    private CategoryService categoryService;
    private AuthorService authorService;
    private PublisherService publisherService;
    private BookService bookService;
    private TransactionService adminService;


    @Autowired
    public AdminController(UserDao userService,
                           BorowedItemService borowedItemService,
                           ReservedItemService reservedItemService,
                           CategoryService categoryService,
                           AuthorService authorService,
                           PublisherService publisherService,
                           BookService bookService,
                           TransactionService adminService

    ) {
        this.userService = userService;
        this.borowedItemService = borowedItemService;
        this.reservedItemService = reservedItemService;
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.publisherService = publisherService;
        this.bookService = bookService;
        this.adminService = adminService;
    }

    @RequestMapping("/admin")
    //   @RequestMapping("/systems")
    public String systemPage(Model model, Principal principal) {
        return "/admin/adminDashboard";
    }

    @RequestMapping("/admin/borowedItemHistory")
    public String adminBorowedItemsFirstPage(Model model, Principal principal) {
        return adminBorowedItemsHistoryListByPage(1, model, "book_title", "asc", null);
    }

    @RequestMapping("/admin/borowedItemHistory/page/{pageNum}")
    public String adminBorowedItemsHistoryListByPage(@PathVariable(name = "pageNum") int pageNum,
                                                     Model model, //Principal principal,
                                                     @Param("sortField") String sortField,
                                                     @Param("sortDir") String sortDir,
                                                     @Param("keyword") String keyword

    ) {
        Page<BorowedItem> page = borowedItemService.listByPage(pageNum, sortField, sortDir, keyword); // selects the elements for the fisrt page from the database
        List<BorowedItem> borowedItemList = page.getContent();

        long startCount = (pageNum - 1) * borowedItemService.PRODUCTS_PER_PAGE + 1;
        long endCount = startCount + borowedItemService.PRODUCTS_PER_PAGE - 1;
        if (endCount > page.getTotalPages()) {
            endCount = page.getTotalElements();
        }

        String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";

        model.addAttribute("borowedItemList", borowedItemList);
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("startCount", startCount);
        model.addAttribute("endCount", endCount);
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", reverseSortDir);
        model.addAttribute("keyword", keyword);

        return "AdminBorowedItemsHistory";
    }

    @GetMapping("/admin/categories")
    public String allCategories(Model model) {
        List<Category> listCategories = categoryService.findAll();
        model.addAttribute("listCategories", listCategories);
        return "admin/categories";
    }

    @GetMapping("/admin/category/newCategory")
    public String newCategory(Model model) {
        List<Category> listCategories = categoryService.findAll();

        Category category = new Category();

        model.addAttribute("category", category);
        model.addAttribute("pageTitle", "Create New Category");

        return "admin/category-form";
    }

    @PostMapping("/admin/category/save")
    public String saveCategory(Category category, RedirectAttributes redirectAttributes, Model model) {
        // To display the success message that the user has been store to the database we use redirect
        // The message will show above users table
        try {
            categoryService.save(category);
        }
        catch (Exception e) {
            model.addAttribute("message", e.getMessage());
        }
        model.addAttribute("message", "The new category has been saved successfully.");
        return "admin/category-form";

    }

    @GetMapping("/admin/authors")
    public String listAllAuthors(Model model) {
        List<Author> authorsList = authorService.findAll();
        model.addAttribute("authorsList", authorsList);
        return "admin/authors";
    }

    @GetMapping("/admin/newAuthor")
    public String newAuthor(Model model) {
        Author author = new Author();

        model.addAttribute("author", author);
        model.addAttribute("pageTitle", "Create New Author");

        return "admin/author-form";
    }

    @PostMapping("/admin/author/save")
    public String saveAuthor(Author author, RedirectAttributes redirectAttributes) {
        // To display the success message that the user has been store to the database we use redirect
        // The message will show above users table
        redirectAttributes.addFlashAttribute("message", "The new author has been saved successfully.");
        authorService.save(author);
        return "redirect:/admin/authors";
    }

    @GetMapping("/admin/publishers")
    public String listAllPublishers(Model model) {
        List<Publisher> publishersList = publisherService.findAll();
        model.addAttribute("publishersList", publishersList);
        return "admin/publishers";
    }

    @GetMapping("/admin/newPublisher")
    public String newPublisher(Model model) {
        Publisher publisher = new Publisher();

        model.addAttribute("publisher", publisher);
        model.addAttribute("pageTitle", "Create New Publisher");

        return "admin/publisher-form";
    }

    @PostMapping("/admin/publisher/save")
    public String savePublisher(Publisher publisher, RedirectAttributes redirectAttributes) {
        // To display the success message that the user has been store to the database we use redirect
        // The message will show above users table
        redirectAttributes.addFlashAttribute("message", "The new publisher has been saved successfully.");
        //publisherService.save(publisher);
        return "redirect:/admin/publishers";
    }

    @GetMapping("/admin/newBook")
    public String newBook(Model model) {
        Book book = new Book();

        List<Author> authorsList = authorService.findAll();

        List<Category> categoriesList = categoryService.findAll();
        //The list must be sorted before printing
        List<Category> categoriesListSorted = categoriesList.stream().
               sorted((c1, c2) -> {return c1.getName().compareTo(c2.getName());
       }).collect(Collectors.toList());


        List<Publisher> publishersList = publisherService.findAll();
        model.addAttribute("authorsList", authorsList);
        model.addAttribute("categoriesList", categoriesListSorted);
        model.addAttribute("publishersList", publishersList);
        model.addAttribute("book", book);
        model.addAttribute("pageTitle", "Create New Book");

        return "admin/book-form";
    }

    @PostMapping("/admin/book/save")
    public String saveBook(Book book, RedirectAttributes redirectAttributes) {
        // To display the success message that the user has been store to the database we use redirect
        // The message will show above users table

        try {
            //book.getAuthors().forEach(author-> System.out.println(author.getBooks().toString()));
            bookService.updateBook(book);
            authorService.saveAll(book.getAuthors());
            redirectAttributes.addFlashAttribute("message", "The new book has been saved successfully.");
        }catch (Exception e){
           System.out.println(e.getMessage());
        }

        return "redirect:/admin/books";
    }

    @GetMapping("/admin/books")
    public String listAllBooks(Model model) {
        List<Book> booksList = new ArrayList<>();  //bookService.findAll();

        model.addAttribute("booksList", booksList);
        return "admin/books";
    }

    @RequestMapping("/admin/adminReservedItems")
    public String adminReservedItems(Model model) {
        model.addAttribute("reservedItemsList", reservedItemService.getAllReservedItems());
        return "admin/adminReservedItems";
    }

    @RequestMapping("/admin/adminBorowedItems")
    public String adminBorowedItems(Model model) {
        model.addAttribute("borowedItemsList", borowedItemService.findAll());
        return "admin/adminBorowedItems";
    }

    @RequestMapping("/admin/collectReservedBooks")
    //@Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    // rollback the entire method in the event of an exception
    public String collectReservedBooks(@RequestParam("cid") Long[] idList,
                                       Model model,
                                       @RequestParam("username") String username,
                                       RedirectAttributes redirectAttributes
                                       //@Param("username") String username
    ) {
        List<ReservedItem> reservedItemsList = reservedItemService.selectCollected(List.of(idList));
        List<BorowedItem> borowedItemsList = new ArrayList<BorowedItem>();
        for (ReservedItem r : reservedItemsList) {
            BorowedItem borowedItem = new BorowedItem();
            // borowedItem.setId(103L);
            borowedItem.setBook(r.getBook());
            borowedItem.setUser(r.getUser());
            //borowedItem.setBorowedDate(Date.valueOf(LocalDate.now()));
            borowedItem.setBorowedDate(LocalDate.now());
            //borowedItem.setDueDate(Date.valueOf(LocalDate.now().plusDays(10)));
            borowedItem.setDueDate(LocalDate.now().plusDays(10));
            borowedItemsList.add(borowedItem);
        }
        try {
            adminService.collectReservedBooks(idList, borowedItemsList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        redirectAttributes.addFlashAttribute("username", username);
        return "redirect:/admin/adminUserBorowedBooks?username=" + username;
    }

    @RequestMapping("/admin/returnBorowedBooks")
    //@Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    // rollback the entire method in the event of an exception
    public String returnReservedBooks(@RequestParam("cid") Long[] idList,
                                       Model model,
                                       @RequestParam("username") String username,
                                       RedirectAttributes redirectAttributes
                                       //@Param("username") String username
    ) {
        List<BorowedItem> returnedBorowedItemsList = borowedItemService.selectCollected(List.of(idList));

        List<BorowedItem> borowedItemsList = new ArrayList<BorowedItem>();
        List<Book> books = new ArrayList<>();
        for (BorowedItem item : returnedBorowedItemsList) {
            item.setReturnDate(LocalDate.now());
            item.setReturned(true);
            item.getBook().setInStockNumber(item.getBook().getInStockNumber() + 1);
        }
        try {
            borowedItemService.saveBorowedItems(returnedBorowedItemsList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        redirectAttributes.addFlashAttribute("username", username);
        return "redirect:/admin/adminUserBorowedBooks?username=" + username;

    }


    @GetMapping("/admin/adminUserReservedBooks")
    public String getUserReservedBooks(@Param("username") String username,
                                        //@ModelAttribute("username") String username,
                                       Model model
                                       //RedirectAttributes redirectAttributes
    ) {
        if (username == null) {
            return "admin/adminUserReservedItems";
        }

        User user = userService.findByUsername(username);
        if (user == null) {
            String message = "The user: " + username + " does not exist";
            model.addAttribute("message", message);
            return "admin/adminUserReservedItems";
        }

        model.addAttribute("username", username);
        if (user != null) {
            // if true, the <div> containing personal data is visible in webpage
            model.addAttribute("usernametrue", true);
            model.addAttribute("user", user);
            List<ReservedItem> reservedItemsList = reservedItemService.findByUser(user);
            if (!reservedItemsList.isEmpty()) {
                model.addAttribute("username", username);
                model.addAttribute("reservedItemsList", reservedItemsList);
            }
            else
                model.addAttribute("emptyList", true);
        }

        return "admin/adminUserReservedItems";
    }

    @GetMapping("/admin/adminUserBorowedBooks")
    public String getUserBorowedBooks(@ModelAttribute("username") String username,
                                      Model model) {

        if (username.isEmpty() ) {
            return "admin/adminUserBorowedItems";
        }

        User user = userService.findByUsername(username);
        if (user == null) {
            String message = "The user: " + username + " does not exist";
            model.addAttribute("message", message);
            return "admin/adminUserBorowedItems";
        }


        if (user != null) {
            model.addAttribute("username", username);
            model.addAttribute("usernametrue", true);
            model.addAttribute("user", user);
            List<BorowedItem> borowedItemsList = borowedItemService.findByUserAndReturnedEquals(user, false);

            if (!borowedItemsList.isEmpty())
                model.addAttribute("borowedItemsList", borowedItemsList);
            else
                model.addAttribute("emptyList", true);
        }

        return "admin/adminUserBorowedItems";
    }

}
