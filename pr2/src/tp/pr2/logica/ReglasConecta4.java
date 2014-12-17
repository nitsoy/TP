package tp.pr2.logica;


public class ReglasConecta4 implements ReglasJuego {
	
	private Tablero tablero;
	private Ficha ficha;
	public ReglasConecta4(){
		
	}

	@Override
	public Ficha hayGanador(Movimiento ultimoMovimiento, Tablero t){
		if(t.ganador(ultimoMovimiento.fila-1, ultimoMovimiento.donde-1) > 0){
			return ultimoMovimiento.color;
		}else{
			return Ficha.VACIA;
		}
	}

	@Override
	public Tablero iniciaTablero() {
		tablero = new Tablero(7, 6);
		return tablero;
	}

	@Override
	public Ficha jugadorInicial() {
		ficha = Ficha.BLANCA;
		return ficha;
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
