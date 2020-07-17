/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.projeto.dao;

import br.com.projeto.jdbc.ConnectionFactory;
import br.com.projeto.model.ItemVenda;
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
public class ItemVendaDAO {
    
    //Criar conexão

    private Connection con;

    public ItemVendaDAO() {
        this.con = new ConnectionFactory().getConnection();
    }
    
    //Cadastra itens vendidos
    
    public void cadastraItem(ItemVenda obj){
    
       try {
            String sql = "insert into tb_itensvendas(venda_id, produto_id, qtd, subtotal) values (?,?,?,?)";
            //2 passo - conectar o banco de dados e organizar o comando sql
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, obj.getVenda().getId());
            stmt.setInt(2, obj.getProduto().getId());
            stmt.setInt(3, obj.getQtd());
            stmt.setDouble(4, obj.getSubtotal());

            //3 passo - exacutar comando sql  
            stmt.execute();
            stmt.close();
          //  JOptionPane.showMessageDialog(null, "Item registrado com sucesso!");

        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro:" + erro);
        }
    }
   
    //Metodo que lista uma venda por id
    
       //Metodo que filtra vendas por datas
    
    public List<ItemVenda> listarItensPorVenda(int venda_id) {
        try {
            //1 passo criar a lista
            List<ItemVenda> lista = new ArrayList<>();

            //2 passo - criar o comando sql, organizar e exacutar
            String sql = "select i.id, p.descricao, i.qtd, p.preco, i.subtotal from tb_itensvendas as i "
                    + "inner join tb_produtos as p on (i.produto_id = p.id) where i.venda_id = ?";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1,venda_id);
  
            
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ItemVenda obj = new ItemVenda();
                Produtos prod = new Produtos();

                obj.setId(rs.getInt("i.id"));
                prod.setDescricao(rs.getString("p.descricao"));
                obj.setQtd(rs.getInt("i.qtd"));       
                prod.setPreco(rs.getDouble("p.preco"));
                obj.setSubtotal(rs.getDouble("i.subtotal"));
                
                obj.setProduto(prod);
                
                lista.add(obj);
            }

            return lista;

        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, "Erro:" + erro);
            return null;
        }

    }
    
}
