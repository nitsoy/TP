package tp.pr2.logica.test;

import tp.pr2.logica.Ficha;
import tp.pr2.logica.Tablero;

public class Utils {

	/**
	 * Comprueba si el tablero está vacío.
	 */
	public static boolean tableroVacio(Tablero t) {
		for (int x = 1; x <= t.getAncho(); ++x)
			if (!columnaVacia(t, x))
				return false;
		return true;
	}
	
	/**
	 * Comprueba si la columna x del tablero está vacía. 
	 */
	public static boolean columnaVacia(Tablero t, int x) {
		for (int y = 1; y <= t.getAlto(); ++y)
			if (t.getCasilla(x,  y) != Ficha.VACIA)
				return false;
		return true;
	}
	
	/**
	 * Devuelve una copia del tablero.
	 */
	public static Tablero copiaTablero(Tablero t) {
		Tablero copia = new Tablero(t.getAncho(), t.getAlto());
		
		for (int x=1; x<=t.getAncho(); x++) {
			for (int y=1; y<=t.getAlto(); y++) {
				copia.setCasilla(x, y, t.getCasilla(x, y));
			}
		}
		
		return copia;
	}
	
	/**
	 * Comprueba si dos tablero son iguales.
	 */
	public static boolean TablerosIguales(Tablero t1, Tablero t2) {
		if (t1 == t2)
			return true;
		
		if ((t1 == null && t2 != null) || (t1 != null && t2 == null))
			return false;
		
		if (t1.getAncho() != t2.getAncho())
			return false;
		if (t1.getAlto() != t2.getAlto())
			return false;
		
		for (int x=1; x<=t1.getAncho(); x++) {
			for (int y=1; y<=t1.getAlto(); y++) {
				if (t1.getCasilla(x, y) != t2.getCasilla(x, y))
					return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Devuelve la ficha contraria
	 */
	public static Ficha contraria(Ficha f) {
		if (f == Ficha.BLANCA)
			return Ficha.NEGRA;
		else if (f == Ficha.NEGRA)
			return Ficha.BLANCA;
		else
			return Ficha.VACIA;
	}
}
