package cmsshoppingcart.controllers;

import cmsshoppingcart.models.PageRepository;
import cmsshoppingcart.models.data.Page;
import java.util.List;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

  @PostMapping("/add")
  public String add(
      @Valid Page page, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
    if (bindingResult.hasErrors()) {
      return "admin/pages/add";
    }

    redirectAttributes.addFlashAttribute("message", "Page added successfully.");
    redirectAttributes.addFlashAttribute("alertClass", "alert-success");

    String slug =
        page.getSlug().equals("")
            ? page.getTitle().toLowerCase().replace(" ", "-")
            : page.getSlug().toLowerCase().replace(" ", "-");

    Page slugExists = pageRepository.findBySlug(slug);

    if (slugExists != null) {
      redirectAttributes.addFlashAttribute(
          "message", "The slug you selected already exists. Please choose another.");
      redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
      redirectAttributes.addFlashAttribute("page", page);

      return "redirect:/admin/pages/add";
    }

    page.setSlug(slug);
    page.setSorting(100);

    pageRepository.save(page);

    return "redirect:/admin/pages";
  }

  @GetMapping("/edit/{id}")
  public String edit(@PathVariable int id, Model model) {
    Page page = pageRepository.getOne(id);
    model.addAttribute("page", page);

    return "admin/pages/edit";
  }

  @PostMapping("/edit")
  public String edit(
      @Valid Page page,
      BindingResult bindingResult,
      RedirectAttributes redirectAttributes,
      Model model) {
    Page pageCurrent = pageRepository.getOne(page.getId());

    if (bindingResult.hasErrors()) {
      model.addAttribute("pageTitle", pageCurrent.getTitle());

      return "admin/pages/edit";
    }

    redirectAttributes.addFlashAttribute("message", "Page edited successfully.");
    redirectAttributes.addFlashAttribute("alertClass", "alert-success");

    String slug =
        page.getSlug().equals("")
            ? page.getTitle().toLowerCase().replace(" ", "-")
            : page.getSlug().toLowerCase().replace(" ", "-");

    Page slugExists = pageRepository.findBySlugAndIdNot(slug, page.getId());

    if (slugExists != null) {
      redirectAttributes.addFlashAttribute(
          "message", "The slug you selected already exists. Please choose another.");
      redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
      redirectAttributes.addFlashAttribute("page", page);

      return "redirect:/admin/pages/edit/" + page.getId();
    }

    page.setSlug(slug);

    pageRepository.save(page);

    return "redirect:/admin/pages";
  }
}
