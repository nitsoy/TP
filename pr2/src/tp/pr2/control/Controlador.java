package tp.pr2.control;
import tp.pr2.logica.*;

import java.util.Scanner;

public class Controlador {
	private Partida partida;
	private static Scanner in;
	private Ficha jugadr;
	
	public Controlador(Partida p, java.util.Scanner in){
		partida = p;
		Controlador.in = in;
	}
	/**
	 * Ejecuta la partida hasta que ésta termina.
	 */
	public void run(){
		ReglasJuego reglas;
		Partida p;
		while(!partida.isTerminada()){
			System.out.print("Qué quieres hacer? ");
			String comando  = in.nextLine().toLowerCase();
			
			switch (comando) {
			case "jugar co":
				reglas = new ReglasComplica();
				p = new Partida(reglas);
				jugar(reglas, p, 2);
				break;
			case "jugar c4":
				reglas = new ReglasConecta4();
				p = new Partida(reglas);
				jugar(reglas, p, 1);
				break;
			default:
				System.out.println("");
				System.err.println("No te entiendo.");
				break;
			}
		}
	}
	
	private String getTextoFicha(){
		if(jugadr == Ficha.BLANCA){
			return "blancas";
		}
		else
			return "negras";
	}
	
	/**
	 * Método auxiliar para finalizar la partida 
	 */
	public void salir(){
		System.exit(0);
	}
	
	private void jugar(ReglasJuego reglas, Partida par, int tipoMov){
		jugadr = reglas.jugadorInicial();
		while(!par.isTerminada()){
			par.getTablero().toString();
			System.out.println("Juegan las "+ getTextoFicha());
			System.out.print("Qué quieres hacer? ");
			String comando  = in.nextLine().toLowerCase();
			switch(comando) {
			case "poner":
				System.out.print("Introduce la columna: ");
				int num = 0;
				num = in.nextInt();
				Movimiento mov;
				if(tipoMov == 1){
					mov = new MovimientoConecta4(num, jugadr);
				}else{
					mov = new MovimientoComplica(num, jugadr);
				}
				if(par.ejecutaMovimiento(mov)){
					if(reglas.hayGanador(mov, par.getTablero()) != Ficha.VACIA){
						par.setGanador(par.getGanador());
						par.setTerminada(true);
						par.getTablero().toString();
						//System.out.println("Ganan las "+ f);
					}
					
				}else{
					System.err.println("Movimiento incorrecto");
				}
				in.nextLine();
				break;
			case "salir":
				salir();
				break;
			case "reiniciar":
				par.reset(reglas);
				System.out.println("Partida reiniciada.");
				break;
			case "deshacer": 
				if(!par.undo()){
					System.err.println("Imposible deshacer.");
				}else
					jugadr = reglas.siguienteTurno(jugadr, par.getTablero());
				break;
			case "jugar co":
				reglas = new ReglasComplica();
				par.reset(reglas);
				System.out.println("Partida reiniciada.");
				jugar(reglas , par, 2);
				break;
			case "jugar c4":
				reglas = new ReglasConecta4();
				par.reset(reglas);
				System.out.println("Partida reiniciada.");
				jugar(reglas , par, 2);
				break;
			default:
				System.out.println("");
				System.err.println("No te entiendo.");
				break;	
			}
		}
		
	}
}
