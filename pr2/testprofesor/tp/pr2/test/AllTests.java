package tp.pr2.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import tp.pr2.control.test.ControladorTest;

@RunWith(Suite.class) 
@Suite.SuiteClasses( { 
	Conecta4Tests.class,
	ComplicaTests.class,
	ControladorTest.class	
	})
public class AllTests {

}
