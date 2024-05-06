package com.example.HourPortal.controllers;

import com.example.HourPortal.models.User;
import com.example.HourPortal.repositories.UserRepository;
import com.example.HourPortal.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
public class RegistroController {

    //Se importan los servicios del usuario para poder manejar solicitudes a la base de datos
    private final UserService userService;
    private final UserRepository userRepository;


    //El controlador importa el UserService con userService
    @Autowired
    public RegistroController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }



    @GetMapping("/Registro")

    //Se crea un modelo User vacio para llenarlo con el forms del thymeleaf y devuelve la vista registro
    public String registro(Model model) {
        model.addAttribute("user", new User());


        return "Registro";
    }



    @PostMapping("/Registro")

    //Metodo post que llama el atributo del modelo user y se guarda un usuario en la base de datos con el metodo "registroUser", luego se devuelve la vista login
    public String UserRegistrado(@ModelAttribute("user") User user, Model model) {


        User existingUser = userRepository.findByCorreoUnab(user.getCorreoUnab());


        if(user.getCorreoUnab().isEmpty() || user.getContraseña().isEmpty()){

            log.info("Falto un campo requerido");

            model.addAttribute("errorCampo", "Porfavor rellene todos los campos");
            return "Registro";
        }
        if(existingUser != null){

            log.info("El usuario ya existe en la base de datos");

            model.addAttribute("errorExiste", "Ya ha sido usado este correo");


            return "Registro";
        }
        else{
            log.info("El usuario se ha registrado exitosamente");

            userService.registroUser(user);

            model.addAttribute("validacion", "Se ha registrado correctamente");


            return "Login";
        }

    }
}
