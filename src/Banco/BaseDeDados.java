package Banco;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import Objetos.Animal;

public class BaseDeDados {

	Connection conexao;

	public BaseDeDados() throws Exception {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			this.conexao = DriverManager.getConnection("jdbc:mysql://localhost/bancoAnimal", "root", "");
		} catch (Exception e) {
			throw new Exception("Ocorreu um erro na conexão");
		}
	}

	public void fecharConexao() {
		try {
			this.conexao.close();
		} catch (Exception e) {
			throw new RuntimeException("Ocorreu um erro no encerramento da conexão");
		}
	}

	public void liberar(PreparedStatement ps) {
		try {
			if (ps != null) {
				ps.close();
			}
		} catch (Exception e) {
			throw new RuntimeException("Ocorreu um erro na liberação do cursor");
		}
	}

	// adicionar novo animal
	public void inserirAnimal(Animal animal) {
		PreparedStatement psInsert = null;
		try {
			psInsert = conexao.prepareStatement("INSERT INTO animal "
					+ "(especie, Classe_idClasse, Dieta_idDieta) VALUES (?,(select idClasse from classe where nomeClasse = ?),(select idDieta from dieta where nomeDieta = ? ))");
			psInsert.setString(1, animal.getEspecie());
			psInsert.setString(2, animal.getClasse());
			psInsert.setString(3, animal.getDieta());
			psInsert.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
			throw new RuntimeException("Ocorreu um erro na inserção do Animal");
		} finally {
			this.liberar(psInsert);
		}
	}

	// listar animais
	public String listarAnimais(int codigoAnimal) {
		String retorno = "";
		PreparedStatement psSelect = null;
		try {
			if (codigoAnimal > 0) { // lista 1 unico animal
				psSelect = conexao
						.prepareStatement("SELECT cdAnimal, nmAnimal, tpRaca " + "FROM Animais where cdAnimal = ? ");
				psSelect.setInt(1, codigoAnimal);
			} else { // Lista todos os animais
				psSelect = conexao.prepareStatement(
						"select animal.especie, Classe.nomeClasse, dieta.nomeDieta from animal, classe, dieta where \r\n"
								+ "(animal.Classe_idClasse = classe.idClasse and animal.Dieta_idDieta = dieta.idDieta) order by animal.idAnimal;");
			}
			ResultSet rs = psSelect.executeQuery();
			while (rs.next()) {
				retorno += rs.getString("animal.especie") + " - Classe: " + rs.getString("classe.nomeClasse")
						+ " - Dieta: " + rs.getString("dieta.nomeDieta") + "\n";
			}
		} catch (Exception e) {
			throw new RuntimeException("Ocorreu um erro na listagem de animais!");
		} finally {
			this.liberar(psSelect);
		}
		return retorno;
	}

	// tamanho da lista de animais
	public int tamanhoListaAnimais() {
		int retorno = 0;
		PreparedStatement psSelect = null;
		try {
			psSelect = conexao.prepareStatement("Select count(*) as contagem from animal;");
			ResultSet rs = psSelect.executeQuery();
			while (rs.next()) {
				retorno = rs.getInt("contagem");
			}
		} catch (Exception e) {
			throw new RuntimeException("Ocorreu um erro na contagem de animais!");
		} finally {
			this.liberar(psSelect);
		}
		return retorno;
	}

	// tabela de animais
	public String[] tabelaAnimais() {
		String[] retorno = new String[tamanhoListaAnimais()];
		PreparedStatement ps = null;
		try {
			ps = conexao.prepareStatement("select especie from animal order by especie;");
			ResultSet rs = ps.executeQuery();
			int j = 0;
			while (rs.next()) {
				retorno[j] = rs.getString("especie");
				j++;
			}
		} catch (Exception e) {
			throw new RuntimeException("Ocorreu um erro na tabela de animais");
		} finally {
			this.liberar(ps);
		}
		return retorno;
	}

	// excluir animal
	public void excluirAnimal(String string) {
		int codg = 0;
		PreparedStatement ps = null;
		try {
			ps = conexao.prepareStatement("select idAnimal from animal where especie = ?");
			ps.setString(1, string);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				codg = rs.getInt("idAnimal");
			}
			this.liberar(ps);
			ps = conexao.prepareStatement("delete from animal where idAnimal = ?");
			ps.setInt(1, codg);
			ps.executeUpdate();
		} catch (Exception e) {
			throw new RuntimeException("Ocorreu um erro em excluir o animal");
		} finally {
			this.liberar(ps);
		}

	}

	// pesquisar animal
	public String pesquisarAnimal(String string) {
		String retorno = "";
		int codg = 0;
		PreparedStatement ps = null;
		try {
			ps = conexao.prepareStatement("select idAnimal from animal where especie = ?");
			ps.setString(1, string);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				codg = rs.getInt("idAnimal");
			}
			this.liberar(ps);
			ps = conexao.prepareStatement(
					"select animal.especie, classe.nomeClasse, dieta.nomeDieta from animal,dieta,classe where (animal.idAnimal = ? and animal.Classe_idClasse = classe.idClasse"
							+ " and animal.Dieta_idDieta = dieta.idDieta)");
			ps.setInt(1, codg);
			rs = ps.executeQuery();
			while (rs.next()) {
				retorno += rs.getString("animal.especie") + " - Classe: " + rs.getString("classe.nomeClasse")
						+ " - Dieta: " + rs.getString("dieta.nomeDieta") + ".";
			}
			return retorno;
		} catch (Exception e) {
			throw new RuntimeException("Ocorreu um erro na busca pelo animal");
		} finally {
			this.liberar(ps);
		}

	}

}
