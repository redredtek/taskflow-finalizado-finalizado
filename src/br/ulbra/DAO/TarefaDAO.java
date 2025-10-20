/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ulbra.DAO;

import br.ulbra.model.Tarefa;
import br.ulbra.model.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author aluno.saolucas
 */
public class TarefaDAO extends AbstractDAO implements CrudRepository<Tarefa> {

    @Override
    public void salvar(Tarefa t) throws SQLException {
        String sql = "INSERT INTO tarefa (titulo, descricao, prioridade, status, prazo, idusuario) VALUES (?, ?, ?,?,?,?)";
        try (Connection con = getConnection();
                PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, t.getTitulo());
            ps.setString(2, t.getDescricao());
            ps.setString(3, t.getPrioridade());
            ps.setString(4, t.getStatus());
            ps.setDate(5, t.getPrazoSql());
            ps.setInt(6, t.getUsuario().getId_usuario());
            int affected = ps.executeUpdate();
            if (affected == 0) {
                throw new SQLException("Falha ao inserir tarefa.");
            } else {

            }
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    t.setId_tarefa(rs.getInt(1));
                }
            }
        }
    }

    @Override
    public Tarefa buscarPorId(int id) throws SQLException {
        String sql = "SELECT t.idtarefa, t.titulo, t.descricao, t.prioridade, t.status, t.prazo, t.idusuario, u.idusuario, u.nome, u.email, u.cargo"
                + "FROM tarefa t INNER JOIN usuario u ON t.idusuario = u.idusuario WHERE t.idusuario = ?";
        try (Connection con = getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Usuario u = new Usuario(
                            rs.getInt("idusuario"),
                            rs.getString("nome"),
                            rs.getString("email"),
                            rs.getString("cargo")
                    );
                    return new Tarefa(
                            rs.getInt("idtarefa"),
                            rs.getString("titulo"),
                            rs.getString("descricao"),
                            rs.getString("prioridade"),
                            rs.getString("status"),
                            rs.getDate("prazo").toLocalDate(),
                            u
                    );
                }
            }
        }
        return null;
    }

    public List<Tarefa> listarOrdemPrioridade() throws SQLException {
        String sql = "SELECT  t.idtarefa, t.titulo, t.descricao,t.prioridade, t.status,t.prazo, u.idusuario, u.nome FROM tarefa t "
                + "INNER JOIN usuario u ON t.idusuario = u.idusuario "
                + "ORDER BY FIELD(prioridade, 'Alta', 'Média', 'Baixa');";

        List<Tarefa> lista = new ArrayList<>();

        try (Connection con = getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Usuario u = new Usuario(
                        rs.getInt("idusuario"),
                        rs.getString("nome")
                );
                Tarefa t = new Tarefa(
                        rs.getInt("idtarefa"),
                        rs.getString("titulo"),
                        rs.getString("descricao"),
                        rs.getString("prioridade"),
                        rs.getString("status"),
                        rs.getDate("prazo").toLocalDate(),
                        u
                );
                lista.add(t);
            }
        }
        return lista;
    }
    
    
    
    @Override
    public List<Tarefa> listar() throws SQLException {
        String sql = "SELECT  t.idtarefa, t.titulo, t.descricao,t.prioridade, t.status,t.prazo, u.idusuario, u.nome FROM tarefa t "
                + "INNER JOIN usuario u ON t.idusuario = u.idusuario "
                + "ORDER BY t.titulo";

        List<Tarefa> lista = new ArrayList<>();

        try (Connection con = getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Usuario u = new Usuario(
                        rs.getInt("idusuario"),
                        rs.getString("nome")
                );
                Tarefa t = new Tarefa(
                        rs.getInt("idtarefa"),
                        rs.getString("titulo"),
                        rs.getString("descricao"),
                        rs.getString("prioridade"),
                        rs.getString("status"),
                        rs.getDate("prazo").toLocalDate(),
                        u
                );
                lista.add(t);
            }
        }
        return lista;
    }

    @Override
    public void atualizar(Tarefa t) throws SQLException {
        String sql = "UPDATE tarefa SET titulo = ?, descricao=? ,prioridade = ?, status = ?,prazo = ?, idusuario = ? WHERE idtarefa = ?";
        try (Connection con = getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, t.getTitulo());
            ps.setString(2, t.getDescricao());
            ps.setString(3, t.getPrioridade());
            ps.setString(4, t.getStatus());
            ps.setDate(5, t.getPrazoSql());
            ps.setInt(6, t.getUsuario().getId_usuario());
            ps.setInt(7, t.getId_tarefa());
            ps.executeUpdate();
        }
    }

    @Override
    public void remover(int id) throws SQLException {
        String sql = "DELETE FROM tarefa WHERE idtarefa = ?";
        try (Connection con = getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
