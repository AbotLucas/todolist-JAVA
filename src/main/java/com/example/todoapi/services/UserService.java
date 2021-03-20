package com.example.todoapi.services;

import com.example.todoapi.entities.User;
import com.example.todoapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements BaseService<User> {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional //Le decimos a cada metodo que tiene que abrir y cerrar una transacion con la DB y manejar el catch
    public List<User> findAll() throws Exception {
        try {
            List<User> entities = userRepository.findAll();
            return entities;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public User findById(Long id) throws Exception {
        try {
            //Uso un objeto optional por si no encuentro el user
            Optional<User> entityOptional = userRepository.findById(id);
            //Retorna lo que encuentra
            return entityOptional.get();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public User save(User entity) throws Exception {
        try {
            entity = userRepository.save(entity);
            return entity;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public User update(Long id, User entity) throws Exception {
        try {
            //Busco el user recibido por parametro en la database
            Optional<User> entityOptional = userRepository.findById(id);
            //Extraigo lo recibido en userUpdated
            User userUpdated = entityOptional.get();
            //Guardo el user updated en la database
            userUpdated = userRepository.save(userUpdated);
            return userUpdated;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public boolean delete(Long id) throws Exception {
        try {
            //Si existe una entidad con ese id en la database
            if (userRepository.existsById(id)) {
                //La elimino
                userRepository.deleteById(id);
                return true;
            } else {
                //Si no existe, arrojo una exception
                throw new Exception();
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public User findByEmailAndPassword(String email, String password) throws Exception {
        try {
            //Uso un objeto optional por si no encuentro el user
            List<User> entityOptional = userRepository.findByEmailAndPassword(email, password);
            //Retorna lo que encuentra
            return entityOptional.get(0);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public Optional<User> findByEmail(String email) throws Exception {
        try {
            Optional<User> oUser = userRepository.findByEmail(email);
            return oUser;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}