package tp.pr2.logica;

public class ReglasComplica implements ReglasJuego {
	
	private Tablero tablero;
	private Ficha ficha;

	@Override
	public Ficha hayGanador(Movimiento ultimoMovimiento, Tablero t) {
		
		return null;
	}

	@Override
	public Tablero iniciaTablero() {
		this.tablero = new Tablero(4, 7);
		return tablero;
	}

	@Override
	public Ficha jugadorInicial() {
		this.ficha = Ficha.BLANCA;
		return this.ficha;
	}

	@Override
	public Ficha siguienteTurno(Ficha ultimoEnPoner, Tablero t) {
		if(ultimoEnPoner == Ficha.BLANCA){
			ficha = Ficha.NEGRA; 
			return Ficha.NEGRA;
		}
		else{
			ficha = Ficha.BLANCA;
			return Ficha.BLANCA;
		}
	}

	@Override
	public boolean tablas(Ficha ultimoEnPoner, Tablero t) {
		if(t.tableroLleno()){
			return true;
		}else
			return false;
	}

}
