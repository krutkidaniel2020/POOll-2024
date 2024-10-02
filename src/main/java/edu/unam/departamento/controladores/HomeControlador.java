package edu.unam.departamento.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeControlador extends Object {
    
    public HomeControlador() {

    }

    @RequestMapping(value = {"/", "/home"})
    public String inicio(){
        return "home";
    }
}