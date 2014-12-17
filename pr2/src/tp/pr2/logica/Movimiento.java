package tp.pr2.logica;

public abstract class Movimiento {
	
	protected int donde;
	protected Ficha color;
	protected int fila;
	
	public void setFila(int fila) {
		this.fila = fila;
	}

	protected Movimiento(int donde, Ficha color) {
		this.donde = donde;
		this.color = color;
	}
	
	public abstract boolean ejecutaMovimiento(Tablero tab);
	
	public Ficha getJugador(){
		return color;
	}
	
	public abstract void undo(Tablero tab);
	
	
}
