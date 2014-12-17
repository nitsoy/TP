package tp.pr2.logica.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import tp.pr2.logica.Ficha;
import tp.pr2.logica.Movimiento;
import tp.pr2.logica.MovimientoConecta4;
import tp.pr2.logica.ReglasConecta4;
import tp.pr2.logica.ReglasJuego;
import tp.pr2.logica.Tablero;

public class MovimientoConecta4Test {
	
	protected ReglasJuego r;
	
	@Before
	public void init() {
		r = getReglas();
	}
	
	protected Movimiento getMovimiento(int donde, Ficha color) {
		return new MovimientoConecta4(donde, color);
	}
	
	protected ReglasJuego getReglas() {
		return new ReglasConecta4();
	}
	
	@Test
	public void testGetJugador() {
		Movimiento mov = getMovimiento(1, Ficha.BLANCA);
		assertEquals("getJugador() no devuelve el jugador con el que fue creado el movimiento", mov.getJugador(), Ficha.BLANCA);
		
		mov = getMovimiento(1, Ficha.NEGRA);
		assertEquals("getJugador() no devuelve el jugador con el que fue creado el movimiento", mov.getJugador(), Ficha.NEGRA);
	}
	
	// Movimientos con columna dentro del tablero
	@Test
	public void testEjecutaMovimientoDentro() {
		Tablero t = r.iniciaTablero(); 

		for (int x=1; x<=t.getAncho(); x++) {
			Movimiento mov = getMovimiento(x, Ficha.BLANCA);
			assertTrue("ejecutaMovimiento() devuelve false al introducir la primera ficha en una columna válida", mov.ejecutaMovimiento(t));
		}
	}
	
	// Movimientos con columna fuera del tablero
	@Test
	public void testEjecutaMovimientoFuera() {
		Tablero t = r.iniciaTablero(); 

		for (int x=-20; x<=20; x++) {
			if ((x < 1) || (x > t.getAncho())) {
				Movimiento mov = getMovimiento(x, Ficha.BLANCA);
				assertFalse("ejecutaMovimiento() devuelve true cuando se invoca con un valor de columna incorrecto", mov.ejecutaMovimiento(t));
			}
		}
	}
	
	// Movimientos cuando la columna no está llena
	@Test
	public void testEjecutaMovimientoColumnaNoLlena() {
		Tablero t = r.iniciaTablero();
		
		for (int x = t.getAncho(); x >= 1; x--) {
			for (int y = t.getAlto(); y >= 1; y--) {
				Ficha ficha = (x + y * t.getAncho()/2)  % 2 == 0 ? Ficha.NEGRA : Ficha.BLANCA;
				Movimiento mov = getMovimiento(x, ficha);
				mov.ejecutaMovimiento(t);
				assertEquals("ejecutaMovimiento() no coloca la ficha correctamente en el tablero", t.getCasilla(x, y), ficha);
			}
		}
	}
	
	// Movimientos cuando la columna está llena
	@Test
	public void testEjecutaMovimientoColumnaLlena() {

		Tablero t = r.iniciaTablero();
		for (int i=0; i<t.getAlto(); i++) {
			Movimiento mov = getMovimiento(1, Ficha.BLANCA);
			mov.ejecutaMovimiento(t);
		}
		
		Movimiento mov = getMovimiento(1, Ficha.BLANCA);
		assertFalse("ejecutaMovimiento() devuelve true al poner ficha en una columna llena", mov.ejecutaMovimiento(t));
	}

	// Undo cuando la columna no se ha llenado
	@Test
	public void testUndo() {
		Tablero t = r.iniciaTablero();
		
		// Ejecutar movimientos
		Movimiento mov[] = new Movimiento[t.getAlto()];
		for (int i=0; i<t.getAlto(); i++) {
			mov[i] = getMovimiento(2, Ficha.BLANCA);
			mov[i].ejecutaMovimiento(t);
		}
		
		// Deshacer
		for (int i=1; i<=t.getAlto(); i++) {
			assertTrue("undo() no deja el tableto como estaba antes de ejecutaMovimiento()", t.getCasilla(2, i) == Ficha.BLANCA);
			mov[t.getAlto()-i].undo(t);
			assertTrue("undo() no elimina la ficha puesta como con ejecutaMovimiento()", t.getCasilla(2, i) == Ficha.VACIA);
		}
	}
}
