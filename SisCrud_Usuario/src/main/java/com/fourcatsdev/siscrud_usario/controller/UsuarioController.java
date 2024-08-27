package com.fourcatsdev.siscrud_usario.controller;

import java.util.List;
import javax.validation.Valid;
import com.fourcatsdev.siscrud_usario.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.fourcatsdev.siscrud_usario.modelo.Papel;
import com.fourcatsdev.siscrud_usario.modelo.Usuario;
import com.fourcatsdev.siscrud_usario.service.PapelService;
import com.fourcatsdev.siscrud_usario.service.UsuarioService;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
	private PapelService papelService;
	
	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	private boolean temAutorizacao(Usuario usuario, String papel) {
		for (Papel pp : usuario.getPapeis()) {
			if (pp.getPapel().equals(papel)) {
				return true;
			}
	    }
		return false;
	}
	
	@GetMapping("/index")
	public String index(@CurrentSecurityContext(expression="authentication.name")
						String login) {
		
		Usuario usuario = usuarioService.buscarUsuarioPorLogin(login);
		
		String redirectURL = "";
		if (temAutorizacao(usuario, "ADMIN")) {
            redirectURL = "/auth/admin/admin-index";
        } else if (temAutorizacao(usuario, "USER")) {
            redirectURL = "/auth/user/user-index";
        } else if (temAutorizacao(usuario, "ENFERMEIRO")) {
            redirectURL = "/auth/enferm/enferm-index";
        }		     
		return redirectURL;
	}
		
	@GetMapping("/novo")
	public String adicionarUsuario(Model model) {
		model.addAttribute("usuario", new Usuario());
		return "/publica-criar-usuario";
	}
	
	@PostMapping("/salvar")
	public String salvarUsuario(@Valid Usuario usuario, BindingResult result, 
				Model model, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return "/publica-criar-usuario";
		}
		
		Usuario usr = usuarioService.buscarUsuarioPorLogin(usuario.getLogin());
		if (usr != null) {
			model.addAttribute("loginExiste", "Login já existe cadastrado");
			return "/publica-criar-usuario";
		}
		
		usuarioService.gravarUsuario(usuario);
		attributes.addFlashAttribute("mensagem", "Usuário salvo com sucesso!");
		return "redirect:/usuario/novo";
	}
	
	@RequestMapping("/admin/listar")
	public String listarUsuario(Model model) {
		List<Usuario> lista = usuarioService.listarUsuario(); 
		model.addAttribute("usuarios", lista);		
		return "/auth/admin/admin-listar-usuario";		
	}
	
	@GetMapping("/admin/apagar/{id}")
	public String deleteUser(@PathVariable("id") long id, Model model) {
		usuarioService.apagarUsuarioPorId(id);
	    return "redirect:/usuario/admin/listar";
	}
	
	@GetMapping("/editar/{id}")
	public String editarUsuario(@PathVariable("id") long id, Model model) {
		Usuario usuario = usuarioService.buscarUsuarioPorId(id);
	    model.addAttribute("usuario", usuario);
	    return "/auth/user/user-alterar-usuario";
	}
	
	@PostMapping("/editar/{id}")
	public String editarUsuario(@PathVariable("id") long id, 
			@Valid Usuario usuario, BindingResult result) {
		if (result.hasErrors()) {
			usuario.setId(id);
			return "/auth/user/user-alterar-usuario";
		}
		usuarioService.alterarUsuario(usuario);
		return "redirect:/usuario/admin/listar";
	}

	@PostMapping("/pesquisar")
	public String pesquisarEnfermeiro(@RequestParam("pesquisa") String pesquisa, Model model) {
		List<Usuario> usuarios = usuarioRepository.findByNomeContaining(pesquisa);
		model.addAttribute("usuarios", usuarios);
		return "/auth/admin/admin-listar-usuario";
	}

	@GetMapping("/editarPapel/{id}")
	public String selecionarPapel(@PathVariable("id") long id, Model model) {
		Usuario usuario = usuarioService.buscarUsuarioPorId(id);
	    model.addAttribute("usuario", usuario);
	    List<Papel> papeis = papelService.listarPapel();
	    model.addAttribute("listaPapeis", papeis);
	    
	    return "/auth/admin/admin-editar-papel-usuario";
	}
	
	@PostMapping("/editarPapel/{id}")
	public String atribuirPapel(@PathVariable("id") long idUsuario, 
			@RequestParam(value = "pps", required=false) int[] pps,
			Usuario usuario,
			RedirectAttributes attributes) {
		if (pps == null) {
			usuario.setId(idUsuario);
			attributes.addFlashAttribute("mensagem", "Pelo menos um papel deve ser informado");
			return "redirect:/usuario/editarPapel/"+idUsuario;
		} else {
			usuarioService.atribuirPapelParaUsuario(idUsuario, pps, usuario.isAtivo());		
		}		
	    return "redirect:/usuario/admin/listar";
	}
}
