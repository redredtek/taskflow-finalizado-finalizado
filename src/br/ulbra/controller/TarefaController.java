/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ulbra.controller;

import br.ulbra.DAO.TarefaDAO;
import br.ulbra.model.Tarefa;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author vdasi
 */
public class TarefaController {

    private TarefaDAO dao = new TarefaDAO();

    public void salvar(Tarefa t) throws SQLException {
        dao.salvar(t);
    }

    public Tarefa buscar(int id) throws SQLException {
        return dao.buscarPorId(id);
    }

    public List<Tarefa> listar() throws SQLException {
        return dao.listar();
    }
    public List<Tarefa> listarOrdemPrioridade() throws SQLException {
        return dao.listarOrdemPrioridade();
    }

    public void atualizar(Tarefa t) throws SQLException {
        dao.atualizar(t);
    }

    public void remover(int id) throws SQLException {
        dao.remover(id);
    }
}
