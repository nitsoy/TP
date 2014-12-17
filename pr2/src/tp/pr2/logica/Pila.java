package tp.pr2.logica;

import java.util.ArrayList;
import java.util.Collections;


public class Pila {
	private int num;
	private ArrayList<Movimiento> lista;
	
	public Pila(){
		this.num = 0;
		this.lista = new ArrayList<Movimiento>();
	}
	
	public void apilar(Movimiento mov){
		if(this.num == 10){
			Collections.rotate(lista, num-1);
			lista.set(this.num-1, mov);
		}else{
			lista.add(this.num, mov);
			this.num = this.num +1;
		}
	}
	
	public boolean desApilar(Tablero tab){
		if(this.num <= 0){
			return false;
		}else{
			Movimiento x  = this.lista.get(this.num-1);
			x.undo(tab);
			this.num = this.num-1;
			return true;
		}
	}
}
