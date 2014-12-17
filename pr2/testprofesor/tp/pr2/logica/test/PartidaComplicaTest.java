package tp.pr2.logica.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import tp.pr2.logica.Ficha;
import tp.pr2.logica.Movimiento;
import tp.pr2.logica.MovimientoComplica;
import tp.pr2.logica.Partida;
import tp.pr2.logica.ReglasComplica;
import tp.pr2.logica.ReglasJuego;
import tp.pr2.logica.Tablero;

public class PartidaComplicaTest {
	
	private Partida p;
	
	@Before
	public void init() {
		p = new Partida(getReglas());
	}
	
	private ReglasJuego getReglas() {
		return new ReglasComplica();
	}
	
	private Movimiento getMovimiento(int donde, Ficha color) {
		return new MovimientoComplica(donde, color);
	}
	
	@Test
	public void testCtor() {
		assertFalse("Una partida recien empezada no debería estar terminada", p.isTerminada());
		assertEquals("Las partidas las empiezan siempre las blancas.", Ficha.BLANCA, p.getTurno());
		assertEquals("El tablero del juego es de 4x7", 4, p.getTablero().getAncho());
		assertEquals("El tablero del juego es de 4x7", 7, p.getTablero().getAlto());
		assertFalse("Al principio de la partida no hay nada que deshacer.", p.undo());
	}
	
	@Test
	public void testEjecutaMovimientoSimple() {
		assertTrue(p.ejecutaMovimiento(getMovimiento(1, Ficha.BLANCA)));
		assertEquals("Tras colocar en la columna 1, la casilla (1, 7) del tablero deberia estar ocupada por las blancas",
				Ficha.BLANCA,
				p.getTablero().getCasilla(1,  7));
		assertFalse("Tras un movimiento, la partida no debería haber terminado.", p.isTerminada());
		assertEquals("Después de las blancas, juegan las negras.", Ficha.NEGRA, p.getTurno());
	}
	
	@Test
	public void testEjecutaMovimientoInvalido1() {
		assertFalse("ejecutaMovimiento() no debe admitir movimiento de ficha que no tiene el turno.",
				p.ejecutaMovimiento(getMovimiento(1, Ficha.NEGRA)));
	}
	
	@Test
	public void testEjecutaMovimientoInvalido3() {
		for (int x = -10; x <= 10; ++x) {
			if ((1 <= x) && (x <= 4)) continue;
			assertFalse("ejecutaMovimiento() debe fallar con columna invalida.", p.ejecutaMovimiento(getMovimiento(x, Ficha.BLANCA)));
		}
	}
	
	@Test
	public void persistenciaTablero() {
		// Comprobación que no está en la documentación pero de implementación
		// de sentido común (y, dicho sea de paso, que nos permite tomar atajos
		// en los test del cuatro en raya).
		Tablero t = p.getTablero();
		assertTrue(p.ejecutaMovimiento(getMovimiento(3, Ficha.BLANCA)));
		assertTrue("No se debe cambiar el objeto tablero en medio de una partida (solo admitido si se llama a reset()).",
				t == p.getTablero());
		assertEquals("Tras colocar en la columna 3, la casilla (3, 7) del tablero deberia estar ocupada por las blancas",
				Ficha.BLANCA,
				t.getCasilla(3,  7));
	}
	
	@Test
	public void testReset1() {
		
		assertTrue(p.ejecutaMovimiento(getMovimiento(3, Ficha.BLANCA)));
		p.reset(getReglas());
		assertTrue("Tras reset, el tablero debe quedar vacio", Utils.tableroVacio(p.getTablero()));
		assertEquals("Tras reset, el turno debe ser de las blancas", Ficha.BLANCA, p.getTurno());

	}
	
	private void completaColumna(int c) {
		for (int y=1; y<=7; y++) {
			assertTrue(p.ejecutaMovimiento(getMovimiento(c, p.getTurno())));
			assertFalse("La partida no debería terminar si no hay ganador", p.isTerminada());
			assertEquals("La partida detecta un ganador sin que haya 4 en raya", p.getGanador(), Ficha.VACIA);
		}
	}
	
	@Test
	public void partidaEnTablas() {
		
		p = new Partida(getReglas());
		
		// Tablero sin conecta 4
		completaColumna(1);
		completaColumna(3);
		completaColumna(2);
		completaColumna(4);
		
		// Tablero con varios conecta cuatro
		assertTrue(p.ejecutaMovimiento(getMovimiento(3, p.getTurno())));
		assertFalse("La partida no debería terminar si no hay 4 en raya", p.isTerminada());
		assertEquals("No debería haber ganador si no hay 4 en raya", p.getGanador(), Ficha.VACIA);
		
		assertTrue(p.ejecutaMovimiento(getMovimiento(4, p.getTurno())));
		assertFalse("La partida no debería terminar si hay 4 en raya de ambos colores en el tablero", p.isTerminada());
		assertEquals("No debería haber ganador si hay 4 en raya de ambos colores en el tablero", p.getGanador(), Ficha.VACIA);
	}
	
	@Test
	public void varios4EnRaya() {
		p = new Partida(getReglas());
		Tablero t = p.getTablero();
		
		// Tablero con un grupo de 4 en raya blanco y otro negro, con
		// columnas completas y otras no completas. Al meter una nueva
		// ficha blanca en una columna no completa se crea otro grupo 
		// de blancas. No debería haber ganador.
		
		// Rellenar dos columnas
		Ficha ficha;
		for (int x = 3; x <= 4; x++) {
			ficha = Ficha.BLANCA;
			for (int y = 7; y >= 1; y--) {
				t.setCasilla(x, y, ficha);
				ficha = Utils.contraria(ficha);
			}
		}
		
		// Poner fichas en otra dos columnas
		t.setCasilla(2, 7, Ficha.BLANCA);
		t.setCasilla(2, 6, Ficha.NEGRA);
		t.setCasilla(2, 5, Ficha.BLANCA);
		
		t.setCasilla(1, 7, Ficha.BLANCA);
		t.setCasilla(1, 6, Ficha.NEGRA);
		
		// Ejecutar movimiento
		Movimiento mov = getMovimiento(1, Ficha.BLANCA);
		assertTrue("Se debería poder poner una ficha en una columna incompleta", p.ejecutaMovimiento(mov));
		assertEquals("No debería haber ganador si un movimiento crea 4 en raya blancas pero en el tablero ya había (y sigue habiendo) 4 en raya negras", p.getGanador(), Ficha.VACIA);
		assertFalse("La partida no debería terminar si hay varios 4 en raya de ambos colores en el tablero", p.isTerminada());
	}
}
