package tp.pr2.logica.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import tp.pr2.logica.Ficha;
import tp.pr2.logica.Movimiento;
import tp.pr2.logica.MovimientoComplica;
import tp.pr2.logica.Partida;
import tp.pr2.logica.ReglasComplica;
import tp.pr2.logica.ReglasJuego;
import tp.pr2.logica.Tablero;

public class CuatroEnRayaComplicaTest extends CuatroEnRayaConecta4Test {

	@Override
	protected ReglasJuego getReglas() {
		return new ReglasComplica();
	}
	
	@Override
	protected Movimiento getMovimiento(int donde, Ficha color) {
		return new MovimientoComplica(donde, color);
	}
	
	private boolean preparaColocacionFichaDesplaza(Partida p, Ficha color, int x, int y) {

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
		
		// Cambiar turno?
		if ((aPoner % 2 == 1) != (p.getTurno() == color)) {
			
			// HACK: poner en una columna vacía y quitar la ficha
			p.ejecutaMovimiento(getMovimiento(1, p.getTurno()));
			t.setCasilla(1, t.getAlto(), Ficha.VACIA);
		}
		
		// Rellenar la columna
		aPoner = ultimaConFicha;
		while (aPoner > 1) {
			Movimiento mov = getMovimiento(x, p.getTurno());
			assertTrue(p.ejecutaMovimiento(mov));
			
			assertTrue("Detectado ganador incorrectamente tras ejecutar movimiento", r.hayGanador(mov, t) == Ficha.VACIA);
			assertFalse("Detectado tablas incorrectamente tras ejecutar movimiento", r.tablas(mov.getJugador(), t));
			
			aPoner--;
		}
		
		// Cambiar turno?
		if (color != p.getTurno()) {
			// HACK: poner en una columna vacía y quitar la ficha
			int columnaVacia = x % t.getAncho() + 1;
			p.ejecutaMovimiento(getMovimiento(columnaVacia, p.getTurno()));
			t.setCasilla(columnaVacia, t.getAlto(), Ficha.VACIA);
		}
		
		return true;
	}	
	
	private void testCuatroEnRayaDesplaza(int posX[], int posY[], int ultima, Ficha color) {
		Partida p = new Partida(getReglas());
		Tablero t = p.getTablero();

		if (!preparaColocacionFichaDesplaza(p, color, posX[ultima], posY[ultima]))
			fail("Error interno en los test :-?");
		
		for (int i = 0; i < posX.length; ++i) {
			if (i != ultima)
				t.setCasilla(posX[i], posY[i], color);
		}
		
		assertFalse("Partida terminada de forma anticipada con un tablero con tres fichas de color " + color, p.isTerminada());
		Movimiento mov = getMovimiento(posX[ultima], p.getTurno());
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

	
	private void pruebaCuatroEnRayaDesplaza(int posX[], int posY[]) {
		for (int i = 0; i < posX.length; ++i) {
			posY[i]--;
			testCuatroEnRayaDesplaza(posX, posY, i, Ficha.BLANCA);
			testCuatroEnRayaDesplaza(posX, posY, i, Ficha.NEGRA);
			posY[i]++;
		}
	}
	
	
	// Partidas que terminan con todas las posibles 4 en raya
	// horizontal
	@Test
	public void testCuatroEnRayaHorizontalDesplaza() {
		Tablero t = r.iniciaTablero();
		
		int []posX = new int[4];
		int []posY = new int[4];
		for (int x = 1; x <= t.getAncho() - 3; ++x) {
			for (int y = 1; y <= t.getAlto(); ++y) {
				for (int l = 0; l < 4; ++l) {
					posX[l] = x + l;
					posY[l] = y;
				}
				pruebaCuatroEnRayaDesplaza(posX, posY);
			}
		}
	}
	
	// Partidas que terminan con todas las posibles 4 en raya
	// vertical
	// Nota: en vertical no tiene sentido

	
	// Partidas que terminan con todas las posibles 4 en raya
	// diagonal /
	@Test
	public void testCuatroEnRayaDiag1Desplaza() {
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
				
				pruebaCuatroEnRayaDesplaza(posX, posY);
				sy--; sx++;
			}
		}
	}
	
	// Partidas que terminan con todas las posibles 4 en raya
	// diagonal \
	@Test
	public void testCuatroEnRayaDiag2Desplaza() {
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
				pruebaCuatroEnRayaDesplaza(posX, posY);
				sy--; sx--;
			}
		}
	}
}
