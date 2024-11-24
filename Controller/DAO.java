/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.Chamado;
import java.sql.*;
import javax.swing.JOptionPane;

/**
 *
 * @author Adriano
 */
public class DAO {

    // CADASTRAR FUNCIONARIO
    public void cadastrar_funcionario(Chamado chamado) throws ClassNotFoundException, SQLException {
        PreparedStatement ps;
        Connection conexao = new Conexao().getConnection();
        ps = conexao.prepareStatement("insert into funcionario (id_cargo,user_funcionario, nome_funcionario, "
                + "email_funcionario, senha_funcionario, salario_funcionario) values (?,?,?,?,?,?)");

        ps.setInt(1, chamado.getId_cargo());
        ps.setString(2, chamado.getUser_funcionario());
        ps.setString(3, chamado.getNome_funcionario());
        ps.setString(4, chamado.getEmail_funcionario());
        ps.setString(5, chamado.getSenha_funcionario());
        ps.setDouble(6, chamado.getSalario_funcionario());

        ps.execute();
    }

    // AUTENTICAÇÃO DE LOGIN
    public boolean login(String user, String senha) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM funcionario WHERE user_funcionario = ? AND senha_funcionario = ?";
        boolean isAuthenticated = false;

        try (Connection conexao = new Conexao().getConnection(); PreparedStatement ps = conexao.prepareStatement(sql)) {

            // Substituindo os placeholders (?) pelos valores
            ps.setString(1, user);
            ps.setString(2, senha);

            // Executa a consulta
            try (ResultSet rs = ps.executeQuery()) {
                // Se houver um resultado, login é válido
                if (rs.next()) {
                    isAuthenticated = true;
                }
            }
        }
        return isAuthenticated;
    }

    // DEMITIR 👾
    public void pesquisar_demitir(String userFuncionario, Chamado chamado) throws SQLException, ClassNotFoundException {
        String sql = "SELECT c.nome_cargo, f.nome_funcionario, f.email_funcionario "
                + "FROM funcionario f "
                + "JOIN cargo c ON f.id_cargo = c.id_cargo "
                + "WHERE f.user_funcionario = ?";

        try (Connection conexao = new Conexao().getConnection(); PreparedStatement st = conexao.prepareStatement(sql)) {

            // Define o valor do parâmetro
            st.setString(1, userFuncionario);

            try (ResultSet rs = st.executeQuery()) {
                // Preenche o objeto chamado com os dados encontrados
                if (rs.next()) {
                    chamado.setNome_cargo(rs.getString("nome_cargo")); // Nome do cargo
                    chamado.setNome_funcionario(rs.getString("nome_funcionario")); // Nome do funcionário
                    chamado.setEmail_funcionario(rs.getString("email_funcionario")); // Email
                } else {
                    JOptionPane.showMessageDialog(null, "Funcionário não encontrado.");
                }
            }
        }
    }

    public void demicao(Chamado chamado) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM funcionario WHERE user_funcionario = ?";
        try (Connection con = new Conexao().getConnection(); PreparedStatement st = con.prepareStatement(sql)) {

            // Define o valor do parâmetro
            st.setString(1, chamado.getUser_funcionario());

            // Executa a exclusão
            int linhasAfetadas = st.executeUpdate();

            // Valida se algo foi excluído
            if (linhasAfetadas == 0) {
                throw new SQLException("Funcionário não encontrado para exclusão.");
            }
        }
    }

    // UPDATE
    public void pesquisar_update(String userFuncionario, Chamado chamado) throws SQLException, ClassNotFoundException {
        String sql = "SELECT c.nome_cargo, f.nome_funcionario, f.email_funcionario, f.salario_funcionario "
                + "FROM funcionario f "
                + "JOIN cargo c ON f.id_cargo = c.id_cargo "
                + "WHERE f.user_funcionario = ?";

        try (Connection conexao = new Conexao().getConnection(); PreparedStatement st = conexao.prepareStatement(sql)) {

            // Define o valor do parâmetro
            st.setString(1, userFuncionario);

            try (ResultSet rs = st.executeQuery()) {
                // Preenche o objeto chamado com os dados encontrados
                if (rs.next()) {
                    chamado.setNome_cargo(rs.getString("nome_cargo")); // Nome do cargo
                    chamado.setNome_funcionario(rs.getString("nome_funcionario")); // Nome do funcionário
                    chamado.setEmail_funcionario(rs.getString("email_funcionario"));

                    double salario = rs.getDouble("salario_funcionario");
                    chamado.setSalario_funcionario(salario);

                } else {
                    JOptionPane.showMessageDialog(null, "Funcionário não encontrado.");
                }
            }
        }
    }

    public void atualizar_funcionario(Chamado chamado) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE funcionario SET id_cargo = ?, salario_funcionario = ? WHERE user_funcionario = ?";

        try (Connection conexao = new Conexao().getConnection(); PreparedStatement st = conexao.prepareStatement(sql)) {

            // Define os valores dos parâmetros
            st.setInt(1, chamado.getId_cargo()); // ID do cargo
            st.setDouble(2, chamado.getSalario_funcionario()); // Salário do funcionário
            st.setString(3, chamado.getUser_funcionario()); // User do funcionário

            // Executa a atualização
            int linhasAfetadas = st.executeUpdate();

            if (linhasAfetadas > 0) {
                JOptionPane.showMessageDialog(null, "Funcionário atualizado com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Funcionário não encontrado para atualização.");
            }
        } catch (SQLException e) {
            // Captura erro de SQL
            JOptionPane.showMessageDialog(null, "Erro ao atualizar funcionário: " + e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // Captura erro de conexão com o banco de dados
            JOptionPane.showMessageDialog(null, "Erro de conexão com o banco de dados: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
