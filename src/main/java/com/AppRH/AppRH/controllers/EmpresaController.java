package com.AppRH.AppRH.controllers;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.AppRH.AppRH.models.Empresa;
import com.AppRH.AppRH.repository.EmpresaRepository;

@Controller
public class EmpresaController {

	
	  @Autowired private EmpresaRepository er;
	  
	  // GET que chama o FORM que cadastra vaga
	  
		@RequestMapping("/cadastrarEmpresa")
		public String form() {
			return "empresa/form-empresa";
		}
		
		@RequestMapping(value="/cadastrarEmpresa", method=RequestMethod.POST)
		public String form(@Valid Empresa empresa, BindingResult result, RedirectAttributes attributes) {
			
			if (result.hasErrors()) {
				attributes.addFlashAttribute("mensagem", "Verifique os campos...");
				return "redirect:/cadastrarEmpresa";
			}

			er.save(empresa);
			attributes.addFlashAttribute("mensagem", "Empresa cadastrada com sucesso!");
			return "redirect:/cadastrarEmpresa";
		}

	  // GET que lista as vagas

		@RequestMapping("/empresas")
		public ModelAndView listaEmpresas() {
			ModelAndView mv = new ModelAndView("empresa/lista-empresa");
			Iterable<Empresa> empresas = er.findAll();
			mv.addObject("empresas", empresas);
			return mv;
		}
		
		@RequestMapping("/deletarEmpresa")
		public String deletarEmpresa(long empid) {
			Empresa emp = new Empresa();
			emp = er.findByEmpid(empid);
			er.delete(emp);
			return "redirect:/empresas";
		}

		@RequestMapping("/editar-empresa")
		public ModelAndView editarEmpresa(long empid) {
			Empresa emp = new Empresa();
			emp = er.findByEmpid(empid);
			ModelAndView mv = new ModelAndView("empresa/update-empresa");
			mv.addObject("emp", emp);
			return mv;
		}
		
		@RequestMapping(value="/editar-empresa", method=RequestMethod.POST)
		public String updateEmpresa(@Valid Empresa emp, BindingResult result, RedirectAttributes attributes) {
			er.save(emp);
			attributes.addFlashAttribute("mensagem", "Empresa alterara com sucesso!");
			return "redirect:/editar-empresa?empid=" + emp.getEmpid();
		}

		 /* 
		 * // Métodos que atualizam vaga // GET que chama o formulário de edição da vaga
		 * 
		 * @RequestMapping("/editar-vaga") public ModelAndView editarVaga(long codigo) {
		 * Vaga vaga = vr.findByCodigo(codigo); ModelAndView mv = new
		 * ModelAndView("vaga/update-vaga"); mv.addObject("vaga", vaga); return mv; }
		 * 
		 * // POST do FORM que atualiza a vaga
		 * 
		 * @RequestMapping(value = "/editar-vaga", method = RequestMethod.POST) public
		 * String updateVaga(@Valid Vaga vaga, BindingResult result, RedirectAttributes
		 * attributes) { vr.save(vaga); attributes.addFlashAttribute("success",
		 * "Vaga alterada com sucesso!");
		 * 
		 * long codigoLong = vaga.getCodigo(); String codigo = "" + codigoLong; return
		 * "redirect:/vaga/" + codigo; }
		 */
	 

}
