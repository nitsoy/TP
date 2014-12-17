package tp.pr2.logica.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import tp.pr2.logica.Ficha;
import tp.pr2.logica.Movimiento;
import tp.pr2.logica.MovimientoComplica;
import tp.pr2.logica.ReglasComplica;
import tp.pr2.logica.ReglasJuego;
import tp.pr2.logica.Tablero;

public class MovimientoComplicaTest extends MovimientoConecta4Test {
	
	@Override
	protected Movimiento getMovimiento(int donde, Ficha color) {
		return new MovimientoComplica(donde, color);
	}
	
	@Override
	protected ReglasJuego getReglas() {
		return new ReglasComplica();
	}
	
	// Movimientos cuando la columna está llena
	@Test
	@Override
	public void testEjecutaMovimientoColumnaLlena() {
		Ficha fichas[] = {
				Ficha.NEGRA, Ficha.NEGRA, Ficha.NEGRA, Ficha.BLANCA, Ficha.BLANCA, Ficha.BLANCA, 
				Ficha.NEGRA, Ficha.NEGRA, Ficha.NEGRA, Ficha.BLANCA, Ficha.BLANCA, Ficha.BLANCA,
				Ficha.NEGRA, Ficha.NEGRA, Ficha.NEGRA, Ficha.BLANCA, Ficha.BLANCA, Ficha.BLANCA,
				Ficha.NEGRA, Ficha.NEGRA, Ficha.NEGRA, Ficha.BLANCA, Ficha.BLANCA, Ficha.BLANCA
				};
		
		Tablero t = r.iniciaTablero();
		
		for (int i=0; i<fichas.length; i++) {
			Movimiento mov = getMovimiento(1, fichas[i]);
			mov.ejecutaMovimiento(t);
			
			// Comprobar desplazamiento
			if (i >= t.getAlto()) {
				for (int j=0; j<t.getAlto(); j++) {
					Ficha esperada = fichas[i - t.getAlto() + j + 1];
					Ficha real = t.getCasilla(1, t.getAlto() - j);
					assertEquals("ejecutaMovimiento() no desplaza bien las fichas cuando la columna está llena", real, esperada);
				}
			}
			
		}
	}

	// Undo cuando la columna estaba llena y se ha desplazado
	@Test
	public void testUndoColumnaLlena() {
		Ficha fichas[] = {
				Ficha.NEGRA, Ficha.NEGRA, Ficha.NEGRA, Ficha.BLANCA, Ficha.BLANCA, Ficha.BLANCA, 
				Ficha.NEGRA, Ficha.NEGRA, Ficha.NEGRA, Ficha.BLANCA, Ficha.BLANCA, Ficha.BLANCA,
				Ficha.NEGRA, Ficha.NEGRA, Ficha.NEGRA, Ficha.BLANCA, Ficha.BLANCA, Ficha.BLANCA,
				Ficha.NEGRA, Ficha.NEGRA, Ficha.NEGRA, Ficha.BLANCA, Ficha.BLANCA, Ficha.BLANCA
				};
		
		Tablero t = r.iniciaTablero();
		
		// Hacer los movimientos
		Movimiento movimientos[] = new Movimiento[fichas.length];
		for (int i=0; i<fichas.length; i++) {
			movimientos[i] = getMovimiento(1, fichas[i]);
			movimientos[i].ejecutaMovimiento(t);
		}

		// Deshacer los movimientos
		for (int i=1; i <= fichas.length - t.getAlto(); i++) {
			movimientos[movimientos.length-i].undo(t);
			
			// Comprobar
			for (int j=1; j<=t.getAlto(); j++) {
				Ficha esperada = fichas[fichas.length - j - i];
				Ficha real = t.getCasilla(1, j);
				assertEquals("undo() no deshace bien los movimientos cuando la columna estaba llena", real, esperada);
			}
		}
	}
}
