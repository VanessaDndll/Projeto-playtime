/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.Chamado;
import java.sql.*;

/**
 *
 * @author Adriano
 */
public class DAO {
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
    
    public void login(Chamado chamado) throws ClassNotFoundException, SQLException {
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
}
