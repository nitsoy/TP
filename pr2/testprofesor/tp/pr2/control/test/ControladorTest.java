package tp.pr2.control.test;

import java.util.Scanner;

import org.junit.Test;

import static org.junit.Assert.fail;
import tp.pr2.control.Controlador;
import tp.pr2.logica.Partida;
import tp.pr2.logica.ReglasConecta4;

public class ControladorTest {
	
	// Comprobar que existe la clase Controlador
	@Test
	public void testCtor() {
		try {
			Partida p = new Partida(new ReglasConecta4());
			Scanner sc = new Scanner(System.in);
			new Controlador(p, sc);
		} catch (Exception e) {
			fail("El constructor de Controlador falla.");
		}
	}
}
