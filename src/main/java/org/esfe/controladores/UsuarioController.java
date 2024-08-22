package org.esfe.controladores;

import org.esfe.modelos.Usuario;
import org.esfe.servicios.interfaces.IUsuarioService;
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
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private IUsuarioService usuarioService;
    @GetMapping
    public String index(Model model, @RequestParam("page")Optional<Integer>page,@RequestParam("size")Optional<Integer>size){
        int currentPage= page.orElse(1)- 1; // si no esta sateado se asigna 0
        int pageSize = size.orElse(5); // tamaño de la pagina , se asigna 5
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        Page<Usuario> usuarios = usuarioService.buscarTodosPaginados(pageable);
        model.addAttribute("usuarios", usuarios);

        int totalPages = usuarios.getTotalPages();
        if (totalPages > 0){
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "usuario/index";

    }
    @GetMapping("/create")
    public String create(Usuario usuario){
        return "usuario/create";
    }
    @PostMapping("/save")
    public String save(Usuario usuario, BindingResult result, Model model, RedirectAttributes attributes){
        if (result.hasErrors()){
            model.addAttribute(usuario);
            attributes.addFlashAttribute("error", "No se pudo guardar devido a un error.");
            return "usuario/create";
        }
        usuarioService.crearOEditar(usuario);
        attributes.addFlashAttribute("msg", "Usuario creado correctamente");
        return "redirect:/usuarios";
    }
    @GetMapping("/details/{id}")
    public String details (@PathVariable("id") Integer id, Model model){
        Usuario usuario = usuarioService.buscarPorId(id).get();
        model.addAttribute("usuario", usuario);
        return "usuario/details";
    }
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model){
        Usuario usuario = usuarioService.buscarPorId(id).get();
        model.addAttribute("usuario", usuario);
        return "usuario/edit";
    }
    @GetMapping("/remove/{id}")
    public String remove(@PathVariable("id") Integer id, Model model) {
        Usuario usuario = usuarioService.buscarPorId(id).get();
        model.addAttribute("usuario", usuario);
        return "usuario/delete";
    }
    @PostMapping("/delete")
    public String delete(Usuario usuario, RedirectAttributes attributes) {
        usuarioService.eliminarPorId(usuario.getId());
        attributes.addAttribute("msg", "Usuario eliminado correctamente");
        return "redirect:/usuarios";
    }
}