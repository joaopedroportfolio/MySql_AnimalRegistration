package Objetos;

public class Animal {

	String especie, dieta, classe;

	public Animal() {
	}

	public Animal(String especie, String dieta, String classe) {
		this.especie = especie;
		this.dieta = dieta;
		this.classe = classe;
	}

	public String getEspecie() {
		return especie;
	}

	public void setEspecie(String especie) {
		this.especie = especie;
	}

	public String getDieta() {
		return dieta;
	}

	public void setDieta(String dieta) {
		this.dieta = dieta;
	}

	public String getClasse() {
		return classe;
	}

	public void setClasse(String classe) {
		this.classe = classe;
	}

}
