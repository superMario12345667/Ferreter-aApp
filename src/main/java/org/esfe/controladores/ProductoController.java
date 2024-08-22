package org.esfe.controladores;

import org.esfe.modelos.Categoria;
import org.esfe.modelos.Producto;
import org.esfe.servicios.implementaciones.CategoriaService;
import org.esfe.servicios.interfaces.ICategoriaService;
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

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;



import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.Base64;

@Controller
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private IProductoService productoService;
    @Autowired
    private ICategoriaService categoriaService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setDisallowedFields("imagen");
    }
    @GetMapping
    public String index(Model model, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1) - 1; // si no está seteado se asigna 0
        int pageSize = size.orElse(5); // tamaño de la página, se asigna 5
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        Page<Producto> productos = productoService.buscarTodosPaginados(pageable);
        model.addAttribute("productos", productos);



        int totalPages = productos.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "producto/index";
    }

    @GetMapping("/create")
    public String create(Model model, Producto producto) {

        List<Categoria> categorias = categoriaService.obtenerTodos();
        model.addAttribute("categorias", categorias);

        return "producto/create";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Producto producto,
                       @RequestParam("imagen") MultipartFile file,
                       BindingResult result,
                       Model model,
                       RedirectAttributes redirectAttributes) {
        try {
            if (!file.isEmpty()) {

                String Base64imagen = Base64.getEncoder().encodeToString(file.getBytes());
                producto.setImagen(Base64imagen);
            }

        } catch (Exception e) {
            result.rejectValue("imagen","error.producto","No se pudo procesar la imagen.");
            return "producto/create";
        }
        if (result.hasErrors()) {
            model.addAttribute(producto);
            redirectAttributes.addFlashAttribute("error", "No se pudo guardar debido a un error.");
            return "producto/create";
        }

        productoService.crearOEditar(producto);
        redirectAttributes.addFlashAttribute("msg", "Producto creado correctamente");
        return "redirect:/productos";
    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable("id") Integer id, Model model) {
        Producto producto = productoService.buscarPorId(id).orElse(null);
        model.addAttribute("producto", producto);
        return "producto/details";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        Producto producto = productoService.buscarPorId(id).orElse(null);
        List<Categoria> categorias = categoriaService.obtenerTodos(); // Cargar todas las categorías

        model.addAttribute("producto", producto);
        model.addAttribute("categorias", categorias); // Pasar las categorías al modelo

        return "producto/edit";
    }


    @GetMapping("/remove/{id}")
    public String remove(@PathVariable("id") Integer id, Model model) {
        Producto producto = productoService.buscarPorId(id).orElse(null);
        model.addAttribute("producto", producto);
        return "producto/delete";
    }

    @PostMapping("/delete")
    public String delete(Producto producto, RedirectAttributes attributes) {
        productoService.eliminarPorId(producto.getId());
        attributes.addFlashAttribute("msg", "Producto eliminado correctamente");
        return "redirect:/productos";
    }
    @GetMapping("/changeImage/{id}")
    public String changeImage(@PathVariable("id") Integer id, Model model) {
        Producto producto = productoService.buscarPorId(id).orElse(null);
        model.addAttribute("producto", producto);
        return "producto/changeImage";
    }


}

