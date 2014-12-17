package tp.pr2.logica;

import java.util.Arrays;

/**
 * Clase que almacena la información de un tablero.
 */
public class Tablero {
	private Ficha[][] tablero;
	private int alto; // numColumna
	private int ancho; // numFila

	/**
	 * Clase que almacena el estado de un tablero de Fichas
	 * 
	 * @param tx
	 *            representa el ancho del tablero
	 * @param ty
	 *            representa el alto del tablero
	 */
	public Tablero(int tx, int ty) {
		if (tx <= 0 || ty <= 0) {
			ancho = 1;
			alto = 1;
			tablero = new Ficha[alto][ancho];
			reset();
		} else {
			ancho = tx;
			alto = ty;
			tablero = new Ficha[alto][ancho];
			reset();
		}
	}

	/**
	 * Método para obtener el alto del tablero.
	 * 
	 * @return Devuelve el alto del tablero
	 */
	public int getAlto() {
		return alto;
	}

	/**
	 * Método para obtener el ancho del tablero.
	 * 
	 * @return Devuelve el ancho del tablero
	 */
	public int getAncho() {
		return ancho;
	}

	/**
	 * Método para reiniciar la partida
	 */
	public void reset() {
		for (int f = 0; f < this.alto; f++) {
			for (int c = 0; c < this.ancho; c++) {
				tablero[f][c] = Ficha.VACIA;
			}
		}
	}

	/**
	 * Método para acceder a la información de una casilla o celda concreta
	 * 
	 * @param x
	 *            Número de Columna
	 * @param y
	 *            Número de fila
	 * @return Información de la casilla. Si la casilla no es válida, se
	 *         devuelve Ficha.VACIA
	 */
	public Ficha getCasilla(int x, int y) {
		if (hayEspacio(y - 1, x - 1)) {
			return tablero[y - 1][x - 1];
		} else {
			return Ficha.VACIA;
		}
	}

	/**
	 * Método que permite especificar el nuevo contenido de una casilla. También
	 * puede utilizarse para quitar una ficha
	 * 
	 * @param x
	 *            Número de Columna
	 * @param y
	 *            Número de fila
	 * @param color
	 *            Color de la casilla. Si se indica Ficha.VACIA, la celda
	 *            quedará sin ficha.
	 */
	public void setCasilla(int x, int y, Ficha color) {
		if (hayEspacio(y - 1, x - 1)) {
			tablero[y - 1][x - 1] = color;
		}
	}

	/**
	 * Método para comprobar si hay espacio para colocar una ficha en la casilla
	 * indicada
	 * 
	 * @param c
	 *            Número de Columna
	 * @param f
	 *            Número de fila
	 * @return Booleano indicando si hay o no espacio disponible en el tablero
	 */
	private boolean hayEspacio(int c, int f) {
		if (columnaValida(f) && filaValida(c)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Comprueba que la columna indicada sea correcta
	 * 
	 * @param c
	 *            Número de columna
	 * @return Booleano True si la columna es correcta, false en caso contrario
	 */
	private boolean columnaValida(int c) {
		return (c >= 0) && (c < this.ancho);
	}

	/**
	 * Comprueba que la fila indicada sea correcta
	 * 
	 * @param f
	 *            Número de fila
	 * @return @return Booleano True si la columna es correcta, false en caso
	 *         contrario
	 */
	private boolean filaValida(int f) {
		return (f >= 0) && (f < this.alto);
	}

	/**
	 * Método para visualizar el contenido del tablero
	 */
	public String toString() {
		for (int i = 0; i < this.getAlto(); i++) {
			System.out.print("|");
			for (int j = 0; j < this.getAncho(); j++) {
				if (tablero[i][j] == Ficha.VACIA) {
					System.out.print(" ");
				}
				if (tablero[i][j] == Ficha.NEGRA) {
					System.out.print("X");
				}
				if (tablero[i][j] == Ficha.BLANCA) {
					System.out.print("O");
				}
			}
			System.out.print("|");
			System.out.println("");
		}
		System.out.println(getLineas());
		System.out.println(getNumeros());
		System.out.println("");
		return "";
	}

	/**
	 * Método auxiliar para completar la visializaión del tablero.
	 * 
	 * @return Devuelve las líneas inferiores para visualizarlas en el toString
	 */
	private String getLineas() {
		String s = "+";
		for (int i = 0; i < this.getAncho(); i++) {
			for (int j = 0; j < 1; j++) {
				s = s + "-";
			}
		}
		return s + "+";
	}

	/**
	 * Método auxiliar que para completar la visiualización del tablero.
	 * 
	 * @return Devulave los números de colmnas visibles en el toString
	 */
	private String getNumeros() {
		String s = " ";
		for (int i = 0; i < this.getAncho(); i++) {
			for (int j = 0; j < 1; j++) {
				s = s + (i + 1);
			}
		}
		return s + " ";
	}

	/**
	 * Método para comprobar si hay espacio para colocar una ficha en la casilla
	 * indicada
	 * @param c Número de columna
	 * @return Devuelve la fila disponible en la columna indicada
	 */
	public int hayEspacio(int c) {
		int i = -1;
		for (int x = 0; x < this.alto; x++) {
			if (columnaValida(c - 1) && this.tablero[x][c - 1] == Ficha.VACIA) {
				i = i + 1;
			}
		}
		return i + 1;
	}

	
	public int hayEspacioComplica(int c) {
		int i = -1;
		for (int x = 0; x < alto; x++) {
			if (columnaValida(c - 1) && tablero[x][c - 1] == Ficha.VACIA) {
				i = i + 1;
			}
		}
		if(i==-1){
			return 0;
		}else
			return i + 1;
	}
	/**
	 * 
	 * @param c
	 * @return
	 */
	public int buscarCasilla(int c) {
		int i = 0;
		for (int x = 0; x < this.alto; x++) {
			if (columnaValida(c - 1) && this.tablero[x][c - 1] == Ficha.VACIA) {
				i = i + 1;
			}
		}
		int j = i + 1;
		return j;
	}

	/**
	 * Método que comprueba si el tablero está ocupado por fichas horizontales,
	 * verticales o en tablas
	 * 
	 * @param f
	 *            Número de fila
	 * @param c
	 *            Número de columna
	 * @return Devuelve 1 si la partida ha terminado
	 */
	public int ganador(int f, int c) {
		if (!columnaValida(c))
			return -1;
		if (!filaValida(f)) {
			return -1;
		}
		Ficha aBuscar = this.tablero[f][c];

		int fIni = f + 1;
		int enLinea = 1;
		while ((filaValida(fIni)) && (this.tablero[fIni][c] == aBuscar)) {
			enLinea++;
			fIni++;
		}
		fIni = f - 1;
		while ((filaValida(fIni)) && (this.tablero[fIni][c] == aBuscar)) {
			enLinea++;
			fIni--;
		}

		if (enLinea >= 4) {
			return 1;
		}

		int cIni = c + 1;
		enLinea = 1;

		while ((columnaValida(cIni)) && (this.tablero[f][cIni] == aBuscar)) {
			enLinea++;
			cIni++;
		}
		cIni = c - 1;
		while ((columnaValida(cIni)) && (this.tablero[f][cIni] == aBuscar)) {
			enLinea++;
			cIni--;
		}

		if (enLinea >= 4) {
			return 1;
		}

		cIni = c + 1;
		fIni = f + 1;
		enLinea = 1;
		while ((filaValida(fIni)) && (columnaValida(cIni))
				&& (this.tablero[fIni][cIni] == aBuscar)) {
			enLinea++;
			cIni++;
			fIni++;
		}
		fIni = f - 1;
		cIni = c - 1;
		while ((filaValida(fIni)) && (columnaValida(cIni))
				&& (this.tablero[fIni][cIni] == aBuscar)) {
			enLinea++;
			cIni--;
			fIni--;
		}

		if (enLinea >= 4) {
			return 1;
		}

		cIni = c + 1;
		fIni = f - 1;
		enLinea = 1;
		while ((filaValida(fIni)) && (columnaValida(cIni))
				&& (this.tablero[fIni][cIni] == aBuscar)) {
			enLinea++;
			cIni++;
			fIni--;
		}
		fIni = f + 1;
		cIni = c - 1;
		while ((filaValida(fIni)) && (columnaValida(cIni))
				&& (this.tablero[fIni][cIni] == aBuscar)) {
			enLinea++;
			cIni--;
			fIni++;
		}

		if (enLinea >= 4) {
			return 1;
		}
		return 0;
	}

	/**
	 * Metodo para comprobar si el tablero esta lleno
	 * 
	 * @return true si el tablero tiene todas las casillas ocupadas. Fase en
	 *         otro caso.
	 */
	public boolean tableroLleno() {
		int lleno = 0;
		int totalCasillas = ancho * alto;
		for (int f = 0; f < alto; f++) {
			for (int c = 0; c < ancho; c++) {
				if (tablero[f][c] != Ficha.VACIA) {
					lleno = lleno + 1;
				}
			}
		}
		if (lleno == totalCasillas) {
			return true;
		} else {
			return false;
		}
	}


	public static void main(String[] args) {
		Tablero t = new Tablero(4, 7);
		t.toString();
		t.setCasilla(1, 1, Ficha.NEGRA);
		t.setCasilla(1, 2, Ficha.NEGRA);
		t.setCasilla(1, 3, Ficha.NEGRA);
		t.setCasilla(1, 4, Ficha.NEGRA);
		t.setCasilla(1, 5, Ficha.NEGRA);
		t.setCasilla(1, 6, Ficha.NEGRA);
		t.setCasilla(1, 7, Ficha.NEGRA);
		t.toString();
		System.out.println(t.hayEspacioComplica(1));
		t.setCasilla(1, 1, Ficha.BLANCA);
		t.setCasilla(1, 1, Ficha.NEGRA);
		t.toString();
		
		
	}

}
