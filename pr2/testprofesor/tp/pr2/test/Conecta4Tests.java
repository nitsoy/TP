package tp.pr2.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import tp.pr2.logica.test.TableroTest;
import tp.pr2.logica.test.CuatroEnRayaConecta4Test;
import tp.pr2.logica.test.MovimientoConecta4Test;
import tp.pr2.logica.test.PartidaConecta4Test;
import tp.pr2.logica.test.ReglasConecta4Test;
import tp.pr2.logica.test.UndoConecta4Test;


@RunWith(Suite.class) 
@Suite.SuiteClasses( { 
	TableroTest.class,
	MovimientoConecta4Test.class,
	ReglasConecta4Test.class,
	PartidaConecta4Test.class,
	UndoConecta4Test.class,
	CuatroEnRayaConecta4Test.class,
	})
public class Conecta4Tests {

}
