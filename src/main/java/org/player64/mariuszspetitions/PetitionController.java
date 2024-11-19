package org.player64.mariuszspetitions;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/")
public class PetitionController {
    private final PetitionService petitionService;

    @Autowired
    public PetitionController(PetitionService petitionService) {
        this.petitionService = petitionService;
    }

    @GetMapping
    public String index(Model model) {
        List<Petition> petitions = petitionService.getAllPetitions();
        model.addAttribute("petitions", petitions);
        model.addAttribute("pageTitle", "Petitions List");

        return "index";
    }

    @GetMapping("/petition/{id}")
    public String petition(@PathVariable Long id, Model model) {
        Optional<Petition> petition = petitionService.getPetitionById(id);
        if (petition.isPresent()) {
            model.addAttribute("pageTitle", petition.get().getTitle());
            model.addAttribute("petition", petition.get());
            return "petition";
        }
        return "redirect:/not-found";
    }

    @GetMapping("/search")
    public String search(@RequestParam(name = "query", required = false) String query, Model model) {
        List<Petition> petitions = petitionService.getPetitionByTitle(query);
        model.addAttribute("pageTitle", "Search Results");
        model.addAttribute("petitions", petitions);
        model.addAttribute("query",  query);
        return "index";
    }

    @GetMapping("/petition/new")
    public String newPetition(Model model) {
        model.addAttribute("pageTitle", "New Petition");
        model.addAttribute("petition", new Petition());
        return "new";
    }

    @PostMapping("/petition/new")
    public String createPetition(@Valid @ModelAttribute Petition petition, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Please correct the errors and try again.");
            return "redirect:/petition/new";
        }

        // Set the current date for createdAt before saving
        petition.setCreatedAt(java.time.LocalDate.now().toString());

        petitionService.createPetition(petition);
        redirectAttributes.addFlashAttribute("successMessage", "Petition created successfully!");
        return "redirect:/";
    }

    @PostMapping("/petition/sign/{id}")
    public String signPetition(@PathVariable Long id, @Valid @ModelAttribute User user, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Invalid user details provided.");
            return "redirect:/petition/" + id;
        }

        boolean success = petitionService.signPetition(id, user);

        if (!success) {
            redirectAttributes.addFlashAttribute("errorMessage", "You have already signed this petition.");
            return "redirect:/petition/" + id;
        }

        redirectAttributes.addFlashAttribute("successMessage", "You have successfully signed the petition!");
        return "redirect:/petition/" + id;
    }
}
