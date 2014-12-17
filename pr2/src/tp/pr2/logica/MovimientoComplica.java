package tp.pr2.logica;

public class MovimientoComplica extends Movimiento {

	
	//Estaclase tiene atributos adicionales
	//guardar la ficha que se desplaza
	
	public MovimientoComplica(int donde, Ficha color) {
		super(donde, color);
	}

	@Override
	public boolean ejecutaMovimiento(Tablero tab) {
		
		return false;
	}

	@Override
	public void undo(Tablero tab) {
		
	}




}
