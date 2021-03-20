package com.example.todoapi.controllers;

import com.example.todoapi.entities.LoginRequest;
import com.example.todoapi.entities.User;
import com.example.todoapi.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(origins="*")//Le perimitimos el acceso a nuestra api de distintos clients
@RequestMapping(path="api/v1/users") //URI de acceso a los metodos de user
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(userService.findById(id));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error, usuario no encontrado.\"}");
        }
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody User entity) {

        if(entity.getEmail().isEmpty() || entity.getPassword().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Email y/o password invalidos.\"}");
        }

        try {
            return ResponseEntity.status(HttpStatus.OK).body(userService.save(entity));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error, no se pudo registrar al user. Es posible que el usuario ya se encuentre registrado.\"}");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> tryLogIn(@RequestBody LoginRequest loginRequest) {

        if(loginRequest.getEmail().isEmpty() || loginRequest.getPassword().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Email y/o password invalidos.\"}");
        }

        String password = loginRequest.getPassword();
        String email = loginRequest.getEmail();

        Optional<User> userExists;

        try {
           userExists = userService.findByEmail(email);
       } catch (Exception e) {
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Hubo un error en la bd, reintente.\"}");
       }

       if(!userExists.isPresent()) {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"El mail ingresado no esta registrado.\"}");
       }

       try {
           return ResponseEntity.status(HttpStatus.OK).body(userService.findByEmailAndPassword(email,password));
       } catch (Exception e) {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Mail y/o Password incorrect@.\"}" + e.getMessage());
       }

    }
}
