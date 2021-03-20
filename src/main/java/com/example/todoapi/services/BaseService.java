package com.example.todoapi.services;

import java.util.List;

public interface BaseService<E> {
    public List<E> findAll() throws Exception; //Traigo una lista de todas las entidades que pase
    public E findById(Long id) throws Exception; //Traigo el registro a partir del id enviado
    public E save(E entity) throws Exception; //Guardo una entidad en la bd
    public E update(Long id, E entity) throws Exception; //Actualizo una entidad en la db
    public boolean delete(Long id) throws Exception; //Elimino un registro de la db
}
