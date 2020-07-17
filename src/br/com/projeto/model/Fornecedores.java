/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.projeto.model;

/**
 *
 * @author Isaac
 */
public class Fornecedores extends Clientes {

    private String cnpj;

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
    
   
    /*
        Permite que combobox exiba o nome no lugar do endereço do objeto
        toString() como padrão exibe o endereço do objeto
        sobrescrevendo o método toString() ele exibe o nome ao invés do endereço do eobjeto
    */
    
    @Override
    public String toString(){
        return this.getNome();
    }

}
