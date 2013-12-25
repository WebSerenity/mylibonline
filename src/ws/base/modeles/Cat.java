package ws.base.modeles;

public class Cat {
	private int id;
	private String texte;
	
	public Cat(){
		
	}
	
	public Cat(String texte){
		this.texte = texte;
	}
	
	public Cat(int id, String texte){
		this.id = id;
		this.texte = texte;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTexte() {
		return texte;
	}

	public void setTexte(String texte) {
		this.texte = texte;
	}

}
