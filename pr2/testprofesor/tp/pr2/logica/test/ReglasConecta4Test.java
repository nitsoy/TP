package tp.pr2.logica.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import tp.pr2.logica.Ficha;
import tp.pr2.logica.ReglasConecta4;
import tp.pr2.logica.ReglasJuego;
import tp.pr2.logica.Tablero;

public class ReglasConecta4Test {
	
	protected ReglasJuego r;
	
	@Before
	public void init() {
		r = getReglas();
	}
	
	protected ReglasJuego getReglas() {
		return new ReglasConecta4();
	}

	@Test
	public void testIniciaTablero() {
		Tablero t = r.iniciaTablero();
		assertEquals("El tablero no tiene el ancho adecuado", 7, t.getAncho());
		assertEquals("El tablero no tiene el alto adecuado", 6, t.getAlto());
		assertTrue("El tablero debe empezar vacío", Utils.tableroVacio(t));
	}
	
	@Test
	public void testJugadorInicial() {
		assertEquals("Deberían empezar juango blancas", Ficha.BLANCA, r.jugadorInicial());
	}
	
	@Test
	public void testSiguienteTurno() {
		Tablero t = r.iniciaTablero();
		
		// Independiente del estado
		assertEquals("Después de blancas deberían jugar negras", Ficha.NEGRA, r.siguienteTurno(Ficha.BLANCA, t));
		assertEquals("Después de blancas deberían jugar negras", Ficha.NEGRA, r.siguienteTurno(Ficha.BLANCA, t));
		assertEquals("Después de negras deberían jugar blancas", Ficha.BLANCA, r.siguienteTurno(Ficha.NEGRA, t));
		assertEquals("Después de negras deberían jugar blancas", Ficha.BLANCA, r.siguienteTurno(Ficha.NEGRA, t));
		
		// Independiente del tablero
		for (int x=t.getAncho(); x>=1; x--) {
			for (int y=t.getAlto(); y>=1; y--) {
				Ficha ficha = (x + y + y/3) % 2 == 0 ? Ficha.NEGRA : Ficha.BLANCA;
				t.setCasilla(x, y, ficha);
				assertEquals("Después de negras deberían jugar blancas", Ficha.NEGRA, r.siguienteTurno(Ficha.BLANCA, t));
				assertEquals("Después de negras deberían jugar blancas", Ficha.BLANCA, r.siguienteTurno(Ficha.NEGRA, t));
			}
		}
	}
}
