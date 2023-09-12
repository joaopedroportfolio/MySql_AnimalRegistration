package Controle;

import Banco.BaseDeDados;
import Objetos.Animal;

public class ControleAnimal {
	BaseDeDados bd;

	public ControleAnimal() {
		try {
			this.bd = new BaseDeDados();
		} catch (Exception e) {
			System.out.println("erro de conexão" + e);
		}
	}

	//adicionar novo animal
	public void adicionarAnimal(String especie, String classe, String dieta) {

		Animal novoAnimal = new Animal(especie, dieta, classe);

		try {
			bd.inserirAnimal(novoAnimal);
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	//listar animais
	public String listarAnimais() {
		return bd.listarAnimais(0);
		
	}

	//tamanho da lista de animais
	public int tamanhoListaAnimais() {
		return bd.tamanhoListaAnimais();
	}

	//tabela de animais
	public String[] tabelaAnimais() {
		return bd.tabelaAnimais();
	}

	//excluir animal
	public void excluirAnimal(String string) {
		bd.excluirAnimal(string);
		
	}

	//pesquisar animal por nome de especie
	public String pesquisarAnimal(String string) {
		return bd.pesquisarAnimal(string);
	}

}
