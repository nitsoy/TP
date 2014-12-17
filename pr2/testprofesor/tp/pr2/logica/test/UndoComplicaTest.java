package tp.pr2.logica.test;

import tp.pr2.logica.Ficha;
import tp.pr2.logica.Movimiento;
import tp.pr2.logica.MovimientoComplica;
import tp.pr2.logica.ReglasComplica;
import tp.pr2.logica.ReglasJuego;

public class UndoComplicaTest extends UndoConecta4Test {
	
	@Override
	protected Movimiento getMovimiento(int donde, Ficha color) {
		return new MovimientoComplica(donde, color);
	}
	
	@Override
	protected ReglasJuego getReglas() {
		return new ReglasComplica();
	}
}
