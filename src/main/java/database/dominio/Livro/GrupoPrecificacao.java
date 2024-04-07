package database.dominio.Livro;

public enum GrupoPrecificacao {
	Grupo1, Grupo2, Grupo3;

	public static GrupoPrecificacao getValue(int i) {
		if(i == 1) {
			return Grupo1;
		}else if(i ==2) {
			return Grupo2;
		}else if(i ==3) {
			return Grupo3;
		}
		
		return null;
	}
	
	public static int getNumber(GrupoPrecificacao g) {
		if(g == Grupo1) {
			return 1;
		}else if(g == Grupo2) {
			return 2;
		}else if(g == Grupo3) {
			return 3;
		}
		
		return -1;
	}
}
