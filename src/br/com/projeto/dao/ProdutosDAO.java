/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.projeto.dao;

import br.com.projeto.jdbc.ConnectionFactory;
import br.com.projeto.model.Clientes;
import br.com.projeto.model.Fornecedores;
import br.com.projeto.model.Produtos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Isaac
 */
public class ProdutosDAO {

    //Criar conexão

    private Connection con;

    public ProdutosDAO() {
        this.con = new ConnectionFactory().getConnection();
    }

    //Metodo cadastrar produtos
    public void cadastrar(Produtos obj) {
        try {
            String sql = "insert into tb_produtos(descricao, preco, qtd_estoque, for_id) values (?,?,?,?)";
            //2 passo - conectar o banco de dados e organizar o comando sql
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, obj.getDescricao());
            stmt.setDouble(2, obj.getPreco());
            stmt.setInt(3, obj.getQtd_estoque());
            stmt.setInt(4, obj.getForcenecedor().getId());

            //3 passo - exacutar comando sql  
            stmt.execute();
            stmt.close();
            JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso!");

        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro:" + erro);
        }

    }

    // Metodo listar todos os produtos
    public List<Produtos> listarProdutos() {
        try {
            //1 passo criar a lista
            List<Produtos> lista = new ArrayList<>();

            //2 passo - criar o comando sql, organizar e exacutar
            String sql = "select p.id, p.descricao, p.preco, p.qtd_estoque, f.nome from tb_produtos as p "
                    + "inner join tb_fornecedores as f on (p.for_id = f.id)";

            PreparedStatement stmt = con.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Produtos obj = new Produtos();
                Fornecedores f = new Fornecedores();
                
                obj.setId(rs.getInt("p.id"));
                obj.setDescricao(rs.getString("p.descricao"));
                obj.setPreco(rs.getDouble("p.preco"));
                obj.setQtd_estoque(rs.getInt("p.qtd_estoque"));

                f.setNome(rs.getString("f.nome"));
                
                obj.setForcenecedor(f);
                
                lista.add(obj);
            }

            return lista;

        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, "Erro:" + erro);
            return null;
        }

    }

    
        //Metodo alterar produto
    public void alterar(Produtos obj) {
        try {
            String sql = "update tb_produtos set descricao = ?, preco = ?, qtd_estoque = ?, for_id = ? where id = ?";
            //2 passo - conectar o banco de dados e organizar o comando sql
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, obj.getDescricao());
            stmt.setDouble(2, obj.getPreco());
            stmt.setInt(3, obj.getQtd_estoque());
            stmt.setInt(4, obj.getForcenecedor().getId());

            stmt.setInt(5, obj.getId());
            //3 passo - exacutar comando sql  
            stmt.execute();
            stmt.close();
            JOptionPane.showMessageDialog(null, "Produto alterado com sucesso!");

        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro:" + erro);
        }

    }
    
    
            //Metodo excluir produtos
    public void excluir(Produtos obj) {
        try {
            String sql = "delete from tb_produtos where id = ?";
            //2 passo - conectar o banco de dados e organizar o comando sql
            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, obj.getId());
            //3 passo - exacutar comando sql  
            stmt.execute();
            stmt.close();
            JOptionPane.showMessageDialog(null, "Produto excluido com sucesso!");

        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro:" + erro);
        }

    }
    
    
    
       // Metodo listar produtos por nome
    public List<Produtos> listarProdutosPorNome(String nome) {
        try {
            //1 passo criar a lista
            List<Produtos> lista = new ArrayList<>();

            //2 passo - criar o comando sql, organizar e exacutar
            String sql = "select p.id, p.descricao, p.preco, p.qtd_estoque, f.nome from tb_produtos as p "
                    + "inner join tb_fornecedores as f on (p.for_id = f.id) where p.descricao like ?";

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, nome);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Produtos obj = new Produtos();
                Fornecedores f = new Fornecedores();
                
                obj.setId(rs.getInt("p.id"));
                obj.setDescricao(rs.getString("p.descricao"));
                obj.setPreco(rs.getDouble("p.preco"));
                obj.setQtd_estoque(rs.getInt("p.qtd_estoque"));

                f.setNome(rs.getString("f.nome"));
                
                obj.setForcenecedor(f);
                
                lista.add(obj);
            }

            return lista;

        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, "Erro:" + erro);
            return null;
        }

    }
    
    
    
        // Metodo listar produtos por nome
    public Produtos buscarPorNome(String nome) {
        try {
            //1 passo criar a lista
            Produtos obj = new Produtos();

            //2 passo - criar o comando sql, organizar e exacutar
            String sql = "select p.id, p.descricao, p.preco, p.qtd_estoque, f.nome from tb_produtos as p "
                    + "inner join tb_fornecedores as f on (p.for_id = f.id) where p.descricao = ?";

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, nome);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
             
                Fornecedores f = new Fornecedores();
                
                obj.setId(rs.getInt("p.id"));
                obj.setDescricao(rs.getString("p.descricao"));
                obj.setPreco(rs.getDouble("p.preco"));
                obj.setQtd_estoque(rs.getInt("p.qtd_estoque"));

                f.setNome(rs.getString("f.nome"));
                
                obj.setForcenecedor(f);                
               
            }

            return obj;

        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, "Erro:" + erro);
            return null;
        }

    }
    
    
          // Metodo listar produtos por nome
    public Produtos buscarPorCodigo(int id) {
        try {
            //1 passo criar a lista
            Produtos obj = new Produtos();

            //2 passo - criar o comando sql, organizar e exacutar
            String sql = "select * from tb_produtos where id = ?";

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                         
                obj.setId(rs.getInt("id"));
                obj.setDescricao(rs.getString("descricao"));
                obj.setPreco(rs.getDouble("preco"));
                obj.setQtd_estoque(rs.getInt("qtd_estoque"));
      
            }

            return obj;

        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, "Erro:" + erro);
            return null;
        }

    }
    
    
    //Metodo que dá baixa no estoque (outra solução é usar triggers)
    
    public void atualizarEstoque(int id, int qtd_nova){
        
        try {

            //1 passo - criar comando sql
            String sql = "update tb_produtos set qtd_estoque=? where id=?";

            //2 passo - conectar o banco de dados e organizar o comando sql
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, qtd_nova);
            stmt.setInt(2, id);
            

            //3 passo - exacutar comando sql  
            stmt.execute();
            stmt.close();
            //JOptionPane.showMessageDialog(null, "Alterado com sucesso!");

        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro:" + erro);
        }
  
    }
    
    
    
    //retorna estoque atual
    
    public int retornaEstoqueAtual(int id){
         try {
            int qtd_estoque=0;
            //1 passo - criar comando sql
            String sql = "select qtd_estoque from tb_produtos where id=?";

            //2 passo - conectar o banco de dados e organizar o comando sql
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, id);
            
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                Produtos p = new Produtos();
                qtd_estoque = (rs.getInt("qtd_estoque"));                
            }
      
            return qtd_estoque;
           

        } catch (SQLException e) {
            //Forçar o programa parar sem retorno
            throw new RuntimeException(e);
        }     
    }
}
