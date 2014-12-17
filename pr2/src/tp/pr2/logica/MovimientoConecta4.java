package tp.pr2.logica;

public class MovimientoConecta4 extends Movimiento {

	public MovimientoConecta4(int donde, Ficha color){
		super(donde, color);
	}
	
	@Override
	public boolean ejecutaMovimiento(Tablero tab) {
		if(tab.hayEspacio(donde) > 0){
			int x = tab.hayEspacio(donde);
			tab.setCasilla(this.donde, x, this.color);
			setFila(x);
			return true;
		}
		else
			return false;
	}
	@Override
	public void undo(Tablero tab) {
		int x = tab.buscarCasilla(this.donde);
		int y = this.donde;
		tab.setCasilla(y, x, Ficha.VACIA);
	}
}
