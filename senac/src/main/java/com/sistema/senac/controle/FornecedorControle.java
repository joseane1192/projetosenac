 package com.sistema.senac.controle;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sistema.senac.modelo.Fornecedor;
import com.sistema.senac.repositorio.FornecedorRepositorio;
import com.sistema.senac.repositorio.CidadeRepositorio;  

@Controller
public class FornecedorControle {
	
	@Autowired
	private FornecedorRepositorio fornecedoresRepositorio;
	@Autowired
	private CidadeRepositorio cidadeRepositorio;
	
	@GetMapping("/cadastroFornecedor")
	public ModelAndView cadastrar(Fornecedor fornecedores) { 
		ModelAndView mv = new ModelAndView("administrativo/fornecedores/cadastro");
		mv.addObject("listaCidades", cidadeRepositorio.findAll());
		mv.addObject("fornecedores",fornecedores);
		return mv;
	}
	
    
    @PostMapping("/salvarFornecedor")
    public ModelAndView salvar(Fornecedor fornecedores, BindingResult result) { 
        if(result.hasErrors()) {
        	return cadastrar(fornecedores);
        	
        }
        fornecedoresRepositorio.saveAndFlush(fornecedores);
        return cadastrar(new Fornecedor());
        
  
       
     }
    
    @GetMapping("/listarFornecedor")
    public ModelAndView listar() {
    	ModelAndView mv = new ModelAndView("administrativo/fornecedores/lista");
    	mv.addObject("listaFornecedors",fornecedoresRepositorio.findAll());
    	return mv;
    }
    
    @GetMapping("/editarFornecedor/{id}")
    public ModelAndView editar(@PathVariable("id") Long id) {
    	Optional<Fornecedor> fornecedores = fornecedoresRepositorio.findById(id);  //busca fornecedores por id e armazenar
    	return cadastrar(fornecedores.get());          //chama função cadastro
    }
        
    @GetMapping("/removerFornecedor/{id}")
    public ModelAndView remover(@PathVariable("id") Long id) {
    	Optional<Fornecedor> fornecedores =fornecedoresRepositorio.findById(id);
    	fornecedoresRepositorio.delete(fornecedores.get());
    	return listar();
    }
    
}
