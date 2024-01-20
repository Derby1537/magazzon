package CRUD.Magazzino.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import CRUD.Magazzino.data.ProdottiRepository;
import CRUD.Magazzino.models.Prodotto;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/prodotti")
public class ProdottiController {
    final ProdottiRepository prodottiRepository;
    ProdottiController(ProdottiRepository prodottiRepository) {
        this.prodottiRepository = prodottiRepository;
    }
    @GetMapping
    public String index(Model model, @RequestParam(required = false) String search) {
        List<Prodotto> list = prodottiRepository.findByGiacenzaGreaterThan(0);
        if(search != null && search.length() > 0) {
            list.retainAll(prodottiRepository.findByNomeProdottoContainingIgnoreCase(search));
        }
        model.addAttribute("body", "prodotti/index");
        model.addAttribute("prodotti", list);
        model.addAttribute("searchValue", (search != null ? search : ""));
        return "main";
    }

    @GetMapping("/{id}")
    public String show(Model model, @PathVariable Long id) {
        Prodotto prodotto = prodottiRepository.getReferenceById(id);
        if(prodotto == null) {
            return "redirect:/prodotti";
        }
        model.addAttribute("body", "prodotti/show");
        model.addAttribute("prodotto", prodotto);
        return "main";
    }

    @GetMapping("/inserisci")
    public String create(Model model) {
        model.addAttribute("body", "prodotti/create");
        model.addAttribute("prodotto", new Prodotto());
        return "main";
    }
    @PostMapping("/inserisci")
    public String save(Model model, @Valid Prodotto prodotto, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            System.out.println("There was an error");
            model.addAttribute("body", "prodotti/create");
            model.addAttribute("prodotto", prodotto);
            return "main";
        }
        prodottiRepository.save(prodotto);
        model.addAttribute("body", "success");
        model.addAttribute("azione1", new String("Inserisci"));
        model.addAttribute("azione2", new String("inserito"));
        return "main";
    }

    @GetMapping("/{id}/modifica")
    public String edit(Model model, @PathVariable Long id) {
        if(id == null) {
            return "redirect:/prodotti";
        }
        Prodotto prodotto = prodottiRepository.getReferenceById(id);
        if(prodotto == null) {
            return "redirect:/prodotti";
        }
        model.addAttribute("body", "prodotti/edit");
        model.addAttribute("prodotto", prodotto);
        return "main";
    }
    @PostMapping("/{id}/modifica")
    public String patch(Model model, @PathVariable long id, @Valid Prodotto prodotto, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            model.addAttribute("body", "prodotti/edit");
            model.addAttribute("prodotto", prodotto);
            return "main";
        }
        Prodotto prod = prodottiRepository.getReferenceById(id);
        if(prod == null) {
            return "redirect:/";
        }
        prod.setNome_prodotto(prodotto.getNome_prodotto());
        prod.setGiacenza(prodotto.getGiacenza());
        prod.setPrezzo(prodotto.getPrezzo());
        prodottiRepository.save(prod);
        model.addAttribute("body", "success");
        model.addAttribute("azione1", "Modifica");
        model.addAttribute("azione2", "modificato");
        return "main";
    }

    @GetMapping("/{id}/acquista")
    public String acquista(Model model, @PathVariable Long id) {
        if(id == null) {
            return "redirect:/prodotti";
        }
        Prodotto prodotto = prodottiRepository.getReferenceById(id);
        if(prodotto == null) {
            return "redirect:/prodotti";
        }
        model.addAttribute("body", "prodotti/acquista");
        model.addAttribute("prodotto", prodotto);
        model.addAttribute("quantita", 1);
        return "main";
    }
    @PostMapping("/{id}/acquista")
    public String patchAcquista(Model model, @PathVariable Long id, @RequestParam(required = true) @Valid Integer quantita) {
        if(id == null) {
            return "redirect:/";
        }
        Prodotto prodotto = prodottiRepository.getReferenceById(id);
        if(prodotto == null) {
            return "redirect:/prodotti";
        }
        String error = "";
        if(quantita == null || quantita <= 0) {
            error = "La quantit\u00E0 deve essere presente e maggiore di 0";
        }
        if(prodotto.getGiacenza() < quantita) {
            error = "La quanti\u00E0 deve essere minore della giacenza del prodotto";
        }
        if(error.length() > 0) {
            model.addAttribute("body", "prodotti/acquista");
            model.addAttribute("prodotto", prodotto);
            model.addAttribute("quantita", quantita);
            model.addAttribute("errore", error);
            return "main";
        }
        prodotto.setGiacenza(prodotto.getGiacenza() - quantita);
        prodottiRepository.save(prodotto);
        model.addAttribute("body", "success");
        model.addAttribute("azione1", "Acquista");
        model.addAttribute("azione2", "acquistato");
        String azione3 = "Prezzo finale: \u20AC" + prodotto.getPrezzo() * quantita;
        model.addAttribute("azione3", azione3);
        return "main";
    }

    @GetMapping("/{id}/elimina")
    public String delete(Model model, @PathVariable Long id) {
        if(id == null) {
            return "redirect:/";
        }
        Prodotto prodotto = prodottiRepository.getReferenceById(id);
        if(prodotto == null) {
            return "redirect:/";
        }
        model.addAttribute("body", "prodotti/delete");
        model.addAttribute("prodotto", prodotto);
        return "main";
    }
    @PostMapping("/{id}/elimina")
    public String destroy(Model model, @PathVariable Long id) {
        if(id == null) {
            return "redirect:/";
        }
        Prodotto prodotto = prodottiRepository.getReferenceById(id);
        if(prodotto == null) {
            return "redirect:/";
        }
        prodottiRepository.delete(prodotto);
        model.addAttribute("body", "success");
        model.addAttribute("azione1", "Elimina");
        model.addAttribute("azione2", "eliminato");
        return "main";
    }
}
