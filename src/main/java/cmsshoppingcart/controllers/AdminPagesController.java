package cmsshoppingcart.controllers;

import cmsshoppingcart.models.PageRepository;
import cmsshoppingcart.models.data.Page;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/pages")
public class AdminPagesController {
  private final PageRepository pageRepository;

  public AdminPagesController(PageRepository pageRepository) {
    this.pageRepository = pageRepository;
  }

  @GetMapping
  public String index(Model model) {
    List<Page> pages = pageRepository.findAll();
    model.addAttribute("pages", pages);

    return "admin/pages/index";
  }

  @GetMapping("/add")
  public String add(@ModelAttribute Page page) {
    return "admin/pages/add";
  }
}
