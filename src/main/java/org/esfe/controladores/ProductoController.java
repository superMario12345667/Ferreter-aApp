package org.esfe.controladores;

import org.esfe.modelos.Producto;
import org.esfe.servicios.interfaces.IProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.util.List;
import java.util.Optional;
import java.util.jar.Attributes;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/productos")
public class ProductoController {
    @Autowired
    private IProductoService productoService;
@GetMapping
    public String index(Model model, @RequestParam("page")Optional<Integer>page,@RequestParam("size")Optional<Integer>size){
        int currentPage= page.orElse(1)- 1; // si no esta sateado se asigna 0
        int pageSize = size.orElse(5); // tamaño de la pagina , se asigna 5
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        Page<Producto> productos = productoService.buscarTodosPaginados(pageable);
        model.addAttribute("productos", productos);

        int totalPages = productos.getTotalPages();
        if (totalPages > 0){
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "producto/index";

    }
    @GetMapping("/create")
    public String create(Producto producto){
        return "producto/create";
    }
    @PostMapping("/save")
    public String save(Producto producto, BindingResult result, Model model, RedirectAttributes attributes){
    if (result.hasErrors()){
        model.addAttribute(producto);
        attributes.addFlashAttribute("error", "No se pudo guardar devido a un error.");
        return "producto/create";
    }
    productoService.crearOEditar(producto);
        attributes.addFlashAttribute("msg", "Grupo creado correctamente");
        return "redirect:/productos";
    }
@GetMapping("/details/{id}")
    public String details (@PathVariable("id") Integer id, Model model){
    Producto producto = productoService.buscarPorId(id).get();
    model.addAttribute("producto", producto);
    return "producto/details";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model){
    Producto producto = productoService.buscarPorId(id).get();
    model.addAttribute("producto", producto );
    return "producto/edit";
    }

    @GetMapping("/remove/{id}")
    public String remove(@PathVariable("id") Integer id, Model model) {
        Producto producto = productoService.buscarPorId(id).get();
        model.addAttribute("producto", producto);
        return "producto/delete";
    }

    @PostMapping("/delete")
    public String delete(Producto producto, RedirectAttributes attributes) {
        productoService.eliminarPorId(producto.getId());
        attributes.addAttribute("msg", "Grupo eliminado correctamente");
        return "redirect:/productos";
    }


}
