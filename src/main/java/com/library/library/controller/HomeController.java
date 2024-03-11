package com.library.library.controller;

import com.library.library.dao.UserDao;
import com.library.library.model.Book;
import com.library.library.model.BorowedItem;
import com.library.library.model.Category;
import com.library.library.model.ReservedItem;
import com.library.library.service.*;
import com.library.library.model.security.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.time.LocalDateTime;

@Controller
public class HomeController {

    //UserService userService;
    private UserDao userService;
    private BookService bookService;
    private BorowedItemService borowedItemService;
    private ReservedItemService reservedItemService;

    private CategoryService categoryService;

    public HomeController() {    }

    @Autowired
    public HomeController(UserDao userService, BookService bookService,
                          BorowedItemService borowedItemService,
                          ReservedItemService reservedItemService,
                          CategoryService categoryService) {
        this.userService = userService;
        this.bookService = bookService;
        this.borowedItemService = borowedItemService;
        this.reservedItemService = reservedItemService;
        this.categoryService = categoryService;
    }


    @RequestMapping({"/"})
    public String index() {
        return "index";
    }

    @RequestMapping({"/hours"})
    public String hours() {
        return "hours";
    }

    @RequestMapping({"/faq"})
    public String faq() {
        return "faq";
    }

    @RequestMapping("/bookshelf")
    public String bookshelf(Model model, Principal principal) {
        if (principal != null) {
            String username = principal.getName();
            User user = userService.findByUsername(username);
            model.addAttribute("user", user);
        }
        // List<Book> bookList = bookService.findAll(Pageable pageable);
        List<Category> categoryList = categoryService.findAll();

        //  model.addAttribute("bookList", bookList);
        model.addAttribute("activeAll", true);
        model.addAttribute("categoryList", categoryList);
        return "bookshelf";
    }

    @RequestMapping({"/bookDetail"})
    public String bookDetail(@PathParam("id") Long id, Model model, Principal principal) {
        if (principal != null) {
            String username = principal.getName();
            User user = this.userService.findByUsername(username);
            model.addAttribute("user", user);
        }

        Book book = null;
        try {
            book = this.bookService.findById(id);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        model.addAttribute("book", book);
        //List<Integer> qtyList = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        // model.addAttribute("qtyList", qtyList);
        model.addAttribute("qty", 1);
        return "bookDetail";
    }

    @RequestMapping({"/forgetPassword"})
    public String forgetPassword(HttpServletRequest request, @ModelAttribute("email") String email, Model model) {
        return "myAccount";
    }

    @RequestMapping({"/myProfile"})
    public String myProfile(Model model, Principal principal) {
        User user = this.userService.findByUsername(principal.getName());
        model.addAttribute("user", user);
        model.addAttribute("userPersonalData", user);
        model.addAttribute("listOfCreditCards", true);
        model.addAttribute("listOfShippingAddresses", true);
        model.addAttribute("classActiveEdit", true);
        return "myProfile";
    }


    @RequestMapping("/reservedItem")
    public String ReservedItems(Model model, Principal principal) {
        User user = null;
        if (principal != null) {
            String username = principal.getName();
            user = this.userService.findByUsername(username);
            model.addAttribute("user", user);
        }
        model.addAttribute("userPersonalData", user);
        List<ReservedItem> reservedItemsList = user.getReservedList();
        if (reservedItemsList.isEmpty()) {
            model.addAttribute("emptyList", true);
        } else {
            model.addAttribute("reservedItemList", user.getReservedList());
        }

        return "reservedItems";
    }

    @RequestMapping("/reservedItem/delete/{id}")
    public String removeReservedItem(@ModelAttribute("id") Long id,
                                     Principal principal,
                                     RedirectAttributes redirectAttributes) {
        if (principal != null) {
            User user = userService.findByUsername(principal.getName());
            Book book = reservedItemService.findById(id).getBook();
            book.setInStockNumber(book.getInStockNumber() + 1);
            bookService.updateBook(book);
            reservedItemService.deleteById(id);
            redirectAttributes.addFlashAttribute("message", "The reservation with ID: " + id + " has been removed");
            return "redirect:/reservedItem";
        }
        return "redirect:/reservedItem";
    }

    @RequestMapping(value = "/addItem")
    public String updateReservationList(@ModelAttribute("id") Long id,
                                        //@ModelAttribute("book") Book book,
                                        Model model, Principal principal,
                                        RedirectAttributes redirectAttributes) {

        User user = userService.findByUsername(principal.getName());
        if (principal == null) {
            return "redirect:/bookDetail?id=" + id;
        }
        Book book = bookService.findById(id);
        if (book.getInStockNumber() == 0){
            redirectAttributes.addFlashAttribute("message", "Not in stock anymore");
            return "redirect:/bookDetail?id=" + id;
        }

        if (user.getReservedList().stream().anyMatch(item->item.getBook().equals(book))) {
            redirectAttributes.addFlashAttribute("message",
                    "The book has already been added to reservation list");
            return "redirect:/reservedItem";
        }

        if (user.getReservedList().size() < 5) {
            //book = bookService.findById(book.getId());
            LocalDateTime now = LocalDateTime.now();
            // Date today = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            //Date today = Date.valueOf(LocalDate.now());
            LocalDate today = LocalDate.now();
           // System.out.println(" today " + today + " " + " now " + now);
            ReservedItem reservedItem = new ReservedItem();
            reservedItem.setReservedDate(today);
            reservedItem.setBook(book);
            reservedItem.setCollected(false);
            reservedItem.setUser(user);
            reservedItemService.updateReservedItem(reservedItem);
        } else {
            redirectAttributes.addFlashAttribute("message", "The maximum of reserved book has been reached");
            return "redirect:/reservedItem";
        }

        return "redirect:/bookDetail?id=" + id;
    }

    @RequestMapping(value = "/updateUserInfo", method = RequestMethod.POST)
    public String updateUserInfo(
            @ModelAttribute("user") User user, //DTO
            @ModelAttribute("newPassword") String newPassword,
            Model model
    ) throws Exception {
        return "myProfile";
    }

    @RequestMapping("/borowedItemHistory")
    public String BorowedItemsFirstPage( Model model, Principal principal){ //@ModelAttribute("keyword") String keyword,
        User user = null;
        if (principal != null) {
            String username = principal.getName();
            user = this.userService.findByUsername(username);
            model.addAttribute("user", user);
        }

        return borowedItemsHistoryListByPageAndUser(1, model, principal,"book_title", "asc", null);

    }
    @RequestMapping("/borowedItemHistory/page/{pageNum}")
    public String borowedItemsHistoryListByPageAndUser(@PathVariable(name = "pageNum") int pageNum,
                                                       Model model, Principal principal,
                                                       @Param("sortField") String sortField,
                                                       @Param("sortDir")String sortDir,
                                                       @Param("keyword") String keyword
    ){
        User user = null;
        if (principal != null) {
            String username = principal.getName();
            user = this.userService.findByUsername(username);
            model.addAttribute("user", user);
        }

        sortDir = "asc";
        Page<BorowedItem> page = borowedItemService.listByPageAndUser(pageNum, sortField, sortDir, keyword, user); // selects the elements for the fisrt page from the database

        // bookService.listByPage(pageNum);
        List<BorowedItem> borowedItemList = page.getContent();

        long startCount = (pageNum - 1) * borowedItemService.PRODUCTS_PER_PAGE + 1;
        long endCount = startCount + borowedItemService.PRODUCTS_PER_PAGE - 1;
        if (endCount > page.getTotalPages()){
            endCount = page.getTotalElements();
        }

        String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";
        
        model.addAttribute("borowedItemList", borowedItemList);
        model.addAttribute("currentPage", pageNum  );
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("startCount", startCount);
        model.addAttribute("endCount", endCount);
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", reverseSortDir);
        model.addAttribute("keyword", keyword);

        return "borowedItemsHistory";
    }


}
