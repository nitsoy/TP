package tp.pr2.logica;

/**
 * Representar la información de una partida.
 */
public class Partida {
	private Tablero tablero;
	private Ficha ficha;
	private boolean terminada;
	private Ficha ganador;
	private Pila pila;
	private ReglasJuego reglas;
	
	public Partida(){
		terminada = false;
		ganador = Ficha.VACIA;
		ficha = Ficha.BLANCA;
	}
	
	public Partida(ReglasJuego reglas){
		terminada = false;
		tablero = reglas.iniciaTablero();
		ficha = reglas.jugadorInicial();
		pila = new Pila();
		ganador = Ficha.VACIA;
		this.reglas = reglas;
	}
	
	public boolean isTerminada() {
		return terminada;
	}
	
	public boolean ejecutaMovimiento(Movimiento mov){
		if(mov.ejecutaMovimiento(this.tablero) && ficha == mov.color && !isTerminada()){
			pila.apilar(mov);
			ficha = reglas.siguienteTurno(mov.color, tablero);
			if(reglas.hayGanador(mov, tablero) != Ficha.VACIA){
				ganador = mov.color;
				terminada = true;
			}
			if(reglas.tablas(mov.color, tablero)){
				ganador = Ficha.VACIA;
				terminada = true;
			}
			return true;
		}else
			return false;
	}
	public void reset(ReglasJuego reglas){
		terminada = false;
		tablero = reglas.iniciaTablero();
		ficha = reglas.jugadorInicial();
		pila = new Pila();
	}
	
	public void setTerminada(boolean terminada) {
		this.terminada = terminada;
	}

	public boolean undo(){
		if(!this.pila.desApilar(tablero)){
			return false;
		}else{
			ficha = reglas.siguienteTurno(ficha, tablero);
			return true;
		}
	}
	public void setTablero(Tablero tablero) {
		this.tablero = tablero;
	}

	public void setGanador(Ficha ganador) {
		ganador = ganador;
	}

	public Ficha getGanador(){
		return ganador;
	}
	
	public Tablero getTablero(){
		return this.tablero;
	}
	
	public Ficha getTurno(){
		return ficha;
	}
	
	private boolean esTurno(Ficha color){
		if(color == ficha){
			return true;
		}
		else
			return false;
	}
	

	
	
}
