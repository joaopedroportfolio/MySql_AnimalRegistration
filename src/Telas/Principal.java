package Telas;

//import java.util.Iterator;

import javax.swing.JOptionPane;

import Controle.ControleAnimal;

public class Principal {

	public static void main(String[] args) {
		ControleAnimal ca = new ControleAnimal();
		int menu;
		do {
			menu = numeroInteiro(
					"1- Adicionar animal\n2- Listar animais\n3- Quantidade de animais\n4- Excluir animal\n5- Pesquisar animal\n0- Sair");

			switch (menu) {

			//adicionar animal
			case 1:
				String especie = JOptionPane.showInputDialog("Espécie").toLowerCase();

				String[] opcoesClasse = { "mamífero", "ave", "réptil", "anfíbio", "peixe" };
				Object selecionadoClasse = JOptionPane.showInputDialog(null, "Escolha uma classe", "Adicionar animal",
						JOptionPane.INFORMATION_MESSAGE, null, opcoesClasse, opcoesClasse[0]);
				String classe = selecionadoClasse.toString();

				String[] opcoesDieta = { "carnívoro", "herbívoro", "onívoro" };
				Object selecionadoDieta = JOptionPane.showInputDialog(null, "Escolha uma dieta", "Adicionar animal",
						JOptionPane.INFORMATION_MESSAGE, null, opcoesDieta, opcoesDieta[0]);
				String dieta = selecionadoDieta.toString();

				ca.adicionarAnimal(especie, classe, dieta);
				break;
				
			//listar animais
			case 2:
				System.out.println(ca.listarAnimais());
				break;
				
			//tamanho lista de animais
			case 3:
				JOptionPane.showMessageDialog(null, ca.tamanhoListaAnimais());
				break;
				
			//excluir animal
			case 4:
				try {
				Object selecionadoAnimalExcluir = JOptionPane.showInputDialog(null, "Escolha um animal",
						"Excluir animal", JOptionPane.INFORMATION_MESSAGE, null, ca.tabelaAnimais(),
						ca.tabelaAnimais()[0]);
				ca.excluirAnimal(selecionadoAnimalExcluir.toString());
				break;
				}catch(Exception e) {
					JOptionPane.showMessageDialog(null, "erro", "Erro", JOptionPane.ERROR_MESSAGE);
					System.out.println(e);
				}
				
			//pesquisar animal por nome de especie
			case 5:
				Object selecionadoAnimal = JOptionPane.showInputDialog(null, "Escolha um animal", "Selecionar animal",
						JOptionPane.INFORMATION_MESSAGE, null, ca.tabelaAnimais(), ca.tabelaAnimais()[0]);
				JOptionPane.showMessageDialog(null, ca.pesquisarAnimal(selecionadoAnimal.toString()));
				break;

			}
		} while (menu != 0);
	}

	private static int numeroInteiro(String string) {
		int numero;
		try {
			numero = Integer.parseInt(JOptionPane.showInputDialog(string));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Valor invalido.", "Alerta", JOptionPane.WARNING_MESSAGE);
			numero = numeroInteiro(string);
		}
		return numero;
	}

}
