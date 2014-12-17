package tp.pr2.logica.test;

import java.util.Stack;

import org.junit.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import tp.pr2.logica.Ficha;
import tp.pr2.logica.Movimiento;
import tp.pr2.logica.MovimientoConecta4;
import tp.pr2.logica.Partida;
import tp.pr2.logica.ReglasConecta4;
import tp.pr2.logica.ReglasJuego;
import tp.pr2.logica.Tablero;

public class UndoConecta4Test {
	
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
	public void testUndoTrasMovimiento() {
		Partida p = new Partida(r);
		
		p.ejecutaMovimiento(getMovimiento(1, Ficha.BLANCA));
		assertTrue("Tras un movimiento, undo() debería funcionar", p.undo());
		assertTrue("Al hacer undo() tras un movimiento, el tablero debe quedar vacío.", Utils.tableroVacio(p.getTablero()));
		assertEquals("Al hacer undo() tras un movimiento, debe ser turno de las blancas.", Ficha.BLANCA, p.getTurno());
		assertFalse("Al hacer undo() tras un movimiento, la partida no ha debido terminar.", p.isTerminada());
	}
	
	@Test
	public void testUndo() {
		Partida p = new Partida(r);
		Tablero t = p.getTablero();
		
		Stack<Tablero> pila = new Stack<Tablero>();
		for (int x=1; x<=t.getAncho(); x++) {
			pila.push(Utils.copiaTablero(t));

			p.ejecutaMovimiento(getMovimiento(x, p.getTurno()));
		}
		
		for (int i=0; i<Math.min(t.getAncho(), 10); i++) {
			assertTrue("undo() debería poder ejecutarse pero devuelve false", p.undo());
			assertTrue("undo() no deja el tablero como estaba", Utils.TablerosIguales(t, pila.pop()));
		}
	}
	

	@Test
	public void testUndoMuchasVeces() {
		Partida p = new Partida(r);
		Tablero t = p.getTablero();
		
		Stack<Tablero> pila = new Stack<Tablero>();
		for (int x=1; x<=t.getAncho(); x++) {
			for (int y=1; y<=3; y++) {
				pila.push(Utils.copiaTablero(t));
				
				p.ejecutaMovimiento(getMovimiento(x, p.getTurno()));
			}
		}
		
		for (int i=0; i<10; i++) {
			assertTrue("undo() debería poder ejecutarse pero devuelve false", p.undo());
			assertTrue("undo() no deja el tablero como estaba cuando se ha hecho más de 10 movimientos", Utils.TablerosIguales(t, pila.pop()));
		}
	}
	
	@Test
	public void testNoUndoTrasReset() {
		Partida p = new Partida(r);
		
		assertTrue(p.ejecutaMovimiento(getMovimiento(3, p.getTurno())));
		p.reset(getReglas());
		assertFalse("Tras reset, undo() no debe funcionar.", p.undo());
	}
}
