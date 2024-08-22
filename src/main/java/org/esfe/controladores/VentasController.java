package org.esfe.controladores;

import org.esfe.modelos.Ventas;
import org.esfe.servicios.interfaces.IVentasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/ventas")
public class VentasController {

    @Autowired
    private IVentasService ventasService;

    @GetMapping
    public String index(Model model, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1) - 1;
        int pageSize = size.orElse(5);
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        Page<Ventas> ventas = ventasService.buscarTodosPaginados(pageable);
        model.addAttribute("ventas", ventas);

        int totalPages = ventas.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "ventas/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("venta", new Ventas());
        return "ventas/create";
    }

    @PostMapping("/save")
    public String save(Ventas venta) {
        ventasService.crearOEditar(venta);
        return "redirect:/ventas";
    }


}
