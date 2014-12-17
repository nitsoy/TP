package tp.pr2.logica.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import tp.pr2.logica.Ficha;
import tp.pr2.logica.Movimiento;
import tp.pr2.logica.MovimientoConecta4;
import tp.pr2.logica.Partida;
import tp.pr2.logica.ReglasConecta4;
import tp.pr2.logica.ReglasJuego;
import tp.pr2.logica.Tablero;

public class CuatroEnRayaConecta4Test {
	
	protected ReglasJuego r;
	
	@Before
	public void init() {
		r = getReglas();
	}
	
	protected ReglasJuego getReglas() {
		return new ReglasConecta4();
	}
	
	protected Movimiento getMovimiento(int donde, Ficha color) {
		return new MovimientoConecta4(donde, color);
	}
	
	// Tableros sin 4 en raya
	@Test
	public void testNoCuatroEnRaya() {
		Partida p = new Partida(r);
		Tablero t = p.getTablero();

		for (int x=t.getAncho(); x>=1; x--) {
			for (int y=3; y>=1; y--) {
				Movimiento mov = getMovimiento(x, p.getTurno());
				assertTrue(p.ejecutaMovimiento(mov));
				
				assertEquals("Detectado incorrectamente 4 en línea tras ejecutar movimiento", Ficha.VACIA, r.hayGanador(mov, t));
				assertEquals("Detectado incorrectamente ganador tras ejecutar movimiento", p.getGanador(), Ficha.VACIA);
				assertFalse("Detectado incorrectamente tablas tras ejecutar movimiento", r.tablas(mov.getJugador(), t));		
				assertFalse("Detectado incorrectamente partida terminada tras ejecutar movimiento", p.isTerminada());				
			}
		}
	}
	
	// Prepara la partida para que se pueda colocar, en el siguiente movimiento
	// la ficha del color dado en la posición indicada. Para eso utiliza
	// las reglas de la partida de C4. Para que pueda hacerlo, debe haber una columna
	// vacía en el tablero y que la columna donde se quiere colocar
	// cumpla las restricciones del conecta 4...
	private boolean preparaColocacionFicha(Partida p, Ficha color, int x, int y) {

		if (p.isTerminada()) return false;
		
		Tablero t = p.getTablero();

		// Sanity-check: encima de y no hay nada
		for (int i = y; i >= 1; --i)
			if (t.getCasilla(x, i) != Ficha.VACIA)
				return false;
		
		// Sacamos la fila sobre la que nos apoyaríamos
		int ultimaConFicha = y + 1;
		while ((ultimaConFicha <= t.getAlto()) && (t.getCasilla(x, ultimaConFicha) == Ficha.VACIA))
			ultimaConFicha++;

		int aPoner = ultimaConFicha - y; // Con la ficha final que no pondremos

		if ((aPoner % 2 == 1) != (p.getTurno() == color)) {
			// Hay que poner una en una columna dummy para ajustar
			// turno
			
			/*
			int aux = columnaAdecuada(t, p.getTurno(), x);
			if (aux == 0) return false;
			p.ejecutaMovimiento(getMovimiento(aux, p.getTurno()));
			*/
			
			// HACK: poner y quitar la ficha para cambiar el turno
			p.ejecutaMovimiento(getMovimiento(1, p.getTurno()));
			t.setCasilla(1, t.getAlto(), Ficha.VACIA);
		}
		
		// Antes de poner, garantizamos que no hay huecos por
		// debajo...
		for (int i = ultimaConFicha + 1; i <= t.getAlto(); ++i) {
			if (t.getCasilla(x,i) == Ficha.VACIA)
				t.setCasilla(x, i, (color == Ficha.BLANCA) ? Ficha.NEGRA : Ficha.BLANCA);
		}
		
		while (aPoner > 1) {
			Movimiento mov = getMovimiento(x, p.getTurno());
			assertTrue(p.ejecutaMovimiento(mov));
			
			assertTrue("Detectado ganador incorrectamente tras ejecutar movimiento", r.hayGanador(mov, t) == Ficha.VACIA);
			assertFalse("Detectado tablas incorrectamente tras ejecutar movimiento", r.tablas(mov.getJugador(), t));
			
			aPoner--;
		}
		
		return true;
	}	
	
	private void testCuatroEnRaya(int posX[], int posY[], int ultima, Ficha color) {
		Partida p = new Partida(getReglas());
		Tablero t = p.getTablero();

		if (!preparaColocacionFicha(p, color, posX[ultima], posY[ultima]))
			fail("Error interno en los test :-?");
		
		for (int i = 0; i < posX.length; ++i) {
			if (i != ultima)
				t.setCasilla(posX[i], posY[i], color);
		}
		
		assertFalse("Partida terminada de forma anticipada con un tablero con tres fichas de color " + color, p.isTerminada());
		Movimiento mov = getMovimiento(posX[ultima], color);
		assertTrue(p.ejecutaMovimiento(mov));
		
		assertTrue("Partida no terminada tras cuatro en raya de " + color, p.isTerminada());
		assertTrue("HayGanador incorrecto tras victoria de" + color, r.hayGanador(mov, t) == color);
		assertFalse("tablas incorrecto tras victoria de" + color, r.tablas(color, t));
		assertEquals("Ganador incorrecto tras victoria de " + color, color, p.getGanador());
		
		for (int x = 1; x <= 7; ++x) {
			assertFalse("No se debe poder poner tras terminar la partida.", p.ejecutaMovimiento(getMovimiento(x, Ficha.BLANCA)));
			assertFalse("No se debe poder poner tras terminar la partida.", p.ejecutaMovimiento(getMovimiento(x, Ficha.NEGRA)));
		}
	}
	
	private void pruebaCuatroEnRaya(int posX[], int posY[]) {
		for (int i = 0; i < posX.length; ++i) {
			testCuatroEnRaya(posX, posY, i, Ficha.BLANCA);
			testCuatroEnRaya(posX, posY, i, Ficha.NEGRA);
		}
	}
	
	// Partidas que terminan con todas las posibles 4 en raya
	// horizontal
	@Test
	public void testCuatroEnRayaHorizontal() {
		Tablero t = r.iniciaTablero();
		
		int []posX = new int[4];
		int []posY = new int[4];
		for (int x = 1; x <= t.getAncho() - 3; ++x) {
			for (int y = 1; y <= t.getAlto(); ++y) {
				for (int l = 0; l < 4; ++l) {
					posX[l] = x + l;
					posY[l] = y;
				}
				pruebaCuatroEnRaya(posX, posY);
			}
		}
	}
	
	// Partidas que terminan con todas las posibles 4 en raya
	// vertical
	@Test
	public void testCuatroEnRayaVertical() {
		Tablero t = r.iniciaTablero();
		
		int []posX = new int[4];
		int []posY = new int[4];
		for (int x = 1; x <= t.getAncho(); ++x) {
			for (int y = 1; y <= t.getAlto() - 3; ++y) {
				for (int l = 0; l < 4; ++l) {
					posX[l] = x;
					posY[l] = y + l;
				}
				testCuatroEnRaya(posX, posY, 0, Ficha.BLANCA);
				testCuatroEnRaya(posX, posY, 0, Ficha.NEGRA);
			}
		}
	}
	
	// Partidas que terminan con todas las posibles 4 en raya
	// diagonal /
	@Test
	public void testCuatroEnRayaDiag1() {
		Tablero t = r.iniciaTablero();
		
		int []posX = new int[4];
		int []posY = new int[4];
		for (int i = 1; i <= t.getAlto() + t.getAncho() - 1; ++i) {
			int sx = Math.max(1, i-t.getAlto()-1);
			int sy = Math.min(i, t.getAlto());
			while ((sy - 4 >= 0) && (sx + 3 <= t.getAncho())) {
				for (int l = 0; l < 4; ++l) {
					posX[l] = sx + l;
					posY[l] = sy - l;
				}
				pruebaCuatroEnRaya(posX, posY);
				sy--; sx++;
			}
		}
	}
	
	// Partidas que terminan con todas las posibles 4 en raya
	// diagonal \
	@Test
	public void testCuatroEnRayaDiag2() {
		Tablero t = r.iniciaTablero();
		
		int []posX = new int[4];
		int []posY = new int[4];
		for (int i = 1; i <= t.getAlto() + t.getAncho() - 1; ++i) {
			int sx = Math.min(i,  t.getAncho());
			int sy = Math.min(t.getAncho() + t.getAlto() - i, t.getAlto());
			while ((sy - 4 >= 0) && (sx - 4 >= 0)) {
				for (int l = 0; l < 4; ++l) {
					posX[l] = sx - l;
					posY[l] = sy - l;
				}
				pruebaCuatroEnRaya(posX, posY);
				sy--; sx--;
			}
		}
	}
}
