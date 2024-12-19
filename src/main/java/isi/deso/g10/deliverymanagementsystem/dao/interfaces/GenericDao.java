/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem.dao.interfaces;

import java.util.List;

/**
 *
 * @author gonzalo90fa
 */
public interface GenericDao<T> {
    public T crear(T entidad);
    public T obtenerPorId(int id);
    public List<T> obtenerTodos();
    public T actualizar(T entidad);
    public boolean eliminar(int id);
}
