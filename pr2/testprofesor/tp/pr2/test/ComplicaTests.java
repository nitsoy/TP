package tp.pr2.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import tp.pr2.logica.test.TableroTest;
import tp.pr2.logica.test.CuatroEnRayaComplicaTest;
import tp.pr2.logica.test.MovimientoComplicaTest;
import tp.pr2.logica.test.PartidaComplicaTest;
import tp.pr2.logica.test.ReglasComplicaTest;
import tp.pr2.logica.test.UndoComplicaTest;

@RunWith(Suite.class) 
@Suite.SuiteClasses( { 
	TableroTest.class,
	MovimientoComplicaTest.class,
	ReglasComplicaTest.class,
	PartidaComplicaTest.class,
	UndoComplicaTest.class,
	CuatroEnRayaComplicaTest.class,
	})
public class ComplicaTests {

}
