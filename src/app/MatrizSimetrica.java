package app;

import java.util.LinkedList;

public class MatrizSimetrica {

	private int cantNodos;
	//private int [][]ms;
	//private LinkedList <Integer>[]v;
	private int []vector;
	
	/**
	 * Constructor parametrizado.
	 * @param ms
	 */
	public MatrizSimetrica(int cantNodos) {
		this.cantNodos = cantNodos;
		int cantMaxAristas = (int)((cantNodos*(cantNodos-1))/2);
		this.vector = new int[cantMaxAristas];
	}
	
	public int getPos(int f, int c) {
		return vector[convertir(f, c)];
	}

	public void setPos(int f, int c) {
		vector[convertir(f, c)] = 1;
	}

	public void setPos(int f, int c, int num) {
		vector[convertir(f, c)] = num;
	}

	public int getMaxAristas() {
		return this.vector.length;
	}

	public int getCantNodos() {
		return this.cantNodos;
	}

	/**
	 * Convertir el índice de un elemento de la matriz, a el índice del vector. 
	 * @param f fila de la matriz.
	 * @param c columna de la matriz.
	 * @return índice del vector.
	 */
	private int convertir(int f, int c) {
		//El if está para elegir siempre el triángulo superior de la matriz.
		if (f > c) {
			int aux = f;
			f = c;
			c = aux;
		}
		return (int) (f * cantNodos + c - (Math.pow(f, 2) + 3 * f + 2) / 2);
	}
	
}
