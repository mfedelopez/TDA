package app;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Vector;

public class Grafo {
	private int[][] nodos;
	private int vectorVertices[];
	final int INF = 9999;
	final int INICIO = 1;
	final int MAX = 9999;

	public int[][] getMatriz() {
		return this.nodos;
	}

	public Grafo generarGrafoAleatorioProbabilidad(final int cantNodos, final double probabilidad) {

		this.nodos = new int[cantNodos][cantNodos];

		for (int i = 0; i < cantNodos; i++) {
			for (int j = i + 1; j < cantNodos; j++) {
				double nroAleatorio = this.randomWithRange(1, 100);

				if (probabilidad > nroAleatorio) {
					this.nodos[i][j] = 1;
					this.nodos[j][i] = 1;
				}
			}
		}

		return this;
	}

	public Grafo generarGrafoAleatorioPorcentajeAdyacencia(final int cantNodos, final double adyacencia) {
		this.nodos = new int[cantNodos][cantNodos];
		int cantMaximaAristas = calcularMaxNroAristas(cantNodos);
		int aristasGeneradas = 0;

		cantMaximaAristas *= adyacencia;

		for (int i = 0; i < cantNodos && aristasGeneradas < cantMaximaAristas; i++) {
			for (int j = i + 1; j < cantNodos; j++) {

				this.nodos[i][j] = 1;
				this.nodos[j][i] = 1;
				aristasGeneradas++;
			}
		}

		return this;
	}

	public Grafo generarGrafoRegularPorcentajeAdyacencia(final int cantNodos, final double adyacencia) {
		this.nodos = new int[cantNodos][cantNodos];

		for (int i = 0; i < cantNodos; i++) {
			for (int j = 0; j < cantNodos; j++) {
				// Condicion: que no haya pasado por la arista,
				// No sea el valor de la diagonal
				// y el porcentaje de adyacencia no este dentro del rango
				if (this.nodos[i][j] == 0 && i != j && adyacencia / 2 > this.randomWithRange(1, 100)) {
					this.nodos[i][j] = 1;
					this.nodos[j][i] = 1;
					this.nodos[cantNodos - 1 - i][cantNodos - 1 - j] = 1;
					this.nodos[cantNodos - 1 - j][cantNodos - 1 - i] = 1;
				}
			}
		}
		return this;
	}

	public Grafo generarGrafoRegular(final int cantNodos, final int grado) {
		this.nodos = new int[cantNodos][cantNodos];

		if (grado > (cantNodos - 1)) {
			return null;
		}

		for (int i = 0; i < cantNodos; i++) {
			for (int j = 0; j < cantNodos; j++) {
				// Condicion: que no haya pasado por la arista,
				// No sea el valor de la diagonal
				// y la cantidad de aristas del vertice no supere al grado dado
				if (this.nodos[i][j] == 0 && i != j && getCantidadAristas(i) < grado) {
					this.nodos[i][j] = 1;
					this.nodos[j][i] = 1;
					this.nodos[cantNodos - 1 - i][cantNodos - 1 - j] = 1;
					this.nodos[cantNodos - 1 - j][cantNodos - 1 - i] = 1;
				}
			}
		}
		return this;
	}

	public Grafo generarGrafoNPartito(final int cantNodos, final int particiones) {
		this.nodos = new int[cantNodos][cantNodos];
		int particion = 0;

		int[] vectorParticiones = new int[cantNodos];
		for (int i = 0; i < cantNodos; i++) {
			vectorParticiones[i] = particion;
			particion++;
			if (particion == particiones) {
				particion = 0;
			}
		}

		if (particiones > (cantNodos - 1)) {
			return null;
		}

		for (int i = 0; i < cantNodos; i++) {
			for (int j = 0; j < cantNodos; j++) {
				// Condicion: que no haya pasado por la arista,
				// No sea el valor de la diagonal
				// y la cantidad de aristas del vertice no supere al grado dado
				if (this.nodos[i][j] == 0 && i != j && vectorParticiones[j] != vectorParticiones[i]) {
					this.nodos[i][j] = 1;
					this.nodos[j][i] = 1;
					this.nodos[cantNodos - 1 - i][cantNodos - 1 - j] = 1;
					this.nodos[cantNodos - 1 - j][cantNodos - 1 - i] = 1;
				}
			}
		}

		return this;
	}

	public int calcularMaxNroAristas(int cantNodos) {
		return (cantNodos * (cantNodos - 1)) / 2;
	}

	@Override
	public String toString() {
		String str = "";
		for (int i = 0; i < this.nodos.length; i++) {
			for (int j = 0; j < this.nodos.length; j++) {
				str += "[" + this.nodos[i][j] + "]";
			}
			str += "\n";
		}
		return str;
	}

	int randomWithRange(int min, int max) {
		int range = (max - min) + 1;
		return (int) (Math.random() * range) + min;
	}

	int getCantidadAristas(int arista) {
		int ret = 0;
		for (int i = 0; i < this.nodos.length; i++) {
			if (this.nodos[arista][i] == 1)
				ret++;
		}
		return ret;
	}

	public void resolverGrafoPorSecuencialAleatorio(int cantNodos, int cantParticiones) {
		this.generarGrafoNPartito(cantNodos, cantParticiones);

		vectorVertices = new int[cantNodos];
		boolean coloreado = false;

		for (int i = 0; i < cantNodos; i++) {
			vectorVertices[i] = 0;
		}

		vectorVertices[0] = 1;

		for (int i = 1; i < cantNodos; i++) {
			for (int j = 0; j < cantNodos; j++) {
				if (i != j && nodos[i][j] == 1 && !coloreado) {
					if (vectorVertices[i] <= vectorVertices[j]) {
						vectorVertices[i] = this.getMinColor() + 1;
					} else {
						vectorVertices[i] = vectorVertices[j];
					}

					coloreado = true;
				}
			}
			coloreado = false;
		}
	}

	public String getVectorColoreo() {
		String str = "";
		for (int i = 0; i < vectorVertices.length; i++) {
			str += "Nodo: " + (i + 1) + " Color: " + vectorVertices[i] + " \n";
		}

		return str;
	}

	public int getMinColor() {
		int min = 999;
		for (int i = 0; i < vectorVertices.length; i++) {
			if (min > vectorVertices[i])
				min = vectorVertices[i];
		}
		return min;

	}

	public void leerGrafo() throws FileNotFoundException {
		String miPath = "C:\\Users\\fedee\\eclipse-workspace\\TDA\\";
		Scanner sc = new Scanner(new File(miPath + "datos.in"));
		int cantNodos = sc.nextInt();
		int cantConexiones = sc.nextInt();
		this.nodos = new int[cantNodos][cantNodos]; // vector para almacenar la lectura

		for (int i = 0; i < cantConexiones; i++) {
			int nodoa = sc.nextInt();
			int nodob = sc.nextInt();
			int peso = sc.nextInt();
			this.nodos[nodoa - 1][nodob - 1] = peso;
			this.nodos[nodob - 1][nodoa - 1] = peso;
		}

		sc.close();
	}

	int[][] kruskal() {
		final int INF = 9999;
		int cantNodos = this.nodos.length;
		int[][] adyacencia = this.nodos;
		int[][] arbol = new int[cantNodos][cantNodos];
		int[] pertenece = new int[cantNodos]; // indica a que árbol pertenece el nodo

		for (int i = 0; i < cantNodos; i++) {
			pertenece[i] = i;
		}

		int nodoA = 0;
		int nodoB = 0;
		int arcos = 1;
		while (arcos < cantNodos) {
			// Encontrar el arco mínimo que no forma ciclo y guardar los nodos y la
			// distancia.
			int min = INF;
			for (int i = 0; i < cantNodos; i++)
				for (int j = 0; j < cantNodos; j++)
					if (min > adyacencia[i][j] && adyacencia[i][j] != 0 && pertenece[i] != pertenece[j]) {
						min = adyacencia[i][j];
						nodoA = i;
						nodoB = j;
					}

			// Si los nodos no pertenecen al mismo árbol agrego el arco al árbol mínimo.
			if (pertenece[nodoA] != pertenece[nodoB]) {
				arbol[nodoA][nodoB] = min;
				arbol[nodoB][nodoA] = min;

				// Todos los nodos del árbol del nodoB ahora pertenecen al árbol del nodoA.
				int temp = pertenece[nodoB];
				pertenece[nodoB] = pertenece[nodoA];
				for (int k = 0; k < cantNodos; k++)
					if (pertenece[k] == temp)
						pertenece[k] = pertenece[nodoA];

				arcos++;
			}
		}
		return arbol;
	}

	void dijkstra(int n) {
		int inicio = n - 1;
		int cantNodos = nodos.length;
		int distancia[] = new int[cantNodos]; //Vector de salida, tiene los caminos minimos de N a cualquier otro de los nodos
		Boolean nodosVisitados[] = new Boolean[cantNodos]; 
		
		//Inicializacion
		for (int i = 0; i < cantNodos; i++) {
			distancia[i] = MAX;
			nodosVisitados[i] = false;
		}
		
		//Distancia a si mismo es siempre 0
		distancia[inicio] = 0;

		//Aplico algoritmo de dijkstra
		for (int i = 0; i < cantNodos - 1; i++) {
			//Agarro la distancia minima de los que todavia no visite. 
			int vertice = distanciaMinima(distancia, nodosVisitados);

			//Lo marco como visitado para no volver a tocarlo
			nodosVisitados[vertice] = true;

			//Para el nodo que tome, sumo la distancia en el vector
			for (int j = 0; j < cantNodos; j++) {
				//Si no es un nodo que ya visite
				//Y tengo una arista
				//Y la distancia no es el maximo
				//Y la distancia es menor
				if (!nodosVisitados[j] && nodos[vertice][j] != 0 && distancia[vertice] != MAX && distancia[vertice] + nodos[vertice][j] < distancia[j]) {
					//Sumo la distancia
					distancia[j] = distancia[vertice] + nodos[vertice][j];
				}
			}
		}

		// saco por pantalla el resultado
		printSolution(distancia, cantNodos);
	}

	// A utility function to print the constructed distance array
	void printSolution(int dist[], int n) {
		System.out.println("Distance from starting vertex");
		for (int i = 0; i < nodos.length; i++)
			System.out.println((i + 1) + " \t\t " + dist[i]);
	}

	int distanciaMinima(int distancia[], Boolean nodosVisitados[]) {
		int min = MAX; 
		int vertice = -1;

		for (int i = 0; i < nodos.length; i++)
			if (nodosVisitados[i] == false && distancia[i] <= min) {
				min = distancia[i];
				vertice = i;
			}

		return vertice;
	}

	int[][] prim() {
    	int cantNodos = this.nodos.length;
    	int cantAristasMarcadas = 0;
    	final int INF = 9999;
        int[][] adyacencia = this.nodos;
        int[][] arbol = new int[cantNodos][cantNodos];
        boolean[] aristasMarcadas = new boolean[cantNodos];
        
        // Inicializo las distancias del arbol en INF.
        for(int i = 0; i < cantNodos; i++) {
        	for(int j=0;j<cantNodos;j++) {
        		arbol[i][j] = INF;	
        	}
        	aristasMarcadas[i]=false;
        }
            

        int padre = 0;
        int hijo = 0;
        while(cantAristasMarcadas + 1 < cantNodos){
            padre = hijo;
            // Marco la fila y elimino la columna del nodo padre.
            aristasMarcadas[padre]=true;
            cantAristasMarcadas++;
            for(int i = 0; i < cantNodos; i++)
                adyacencia[i][padre] = INF;

            // Encuentro la menor distancia entre las filas marcadas.
            // El nodo padre es la linea marcada y el nodo hijo es la columna del minimo.
            int min = INF;
            for(int i=0; i<cantNodos; i++)
                for(int j = 0; j < cantNodos; j++)
                    if(min > adyacencia[i][j] && adyacencia[i][j]!=0){
                        min = adyacencia[i][j];
                        padre = i;
                        hijo = j;
                    }

            arbol[padre][hijo] = min;
            arbol[hijo][padre] = min;
        }
        
        for(int i=0; i<cantNodos; i++) {
        	for(int j=0; j<cantNodos; j++) {
        		if(arbol[i][j]==INF) {
        			arbol[i][j] = 0;
        		}
        	}
        }
        return arbol;
    }
	
	
	public int[][] floyd() {
		int cantNodos = this.nodos.length+1;
		int[][] distancias = this.nodos;// Arrays.copyOf(this.matAdyacencia, FINAL_ARRAY);

		for (int k = 1; k < cantNodos; k++) {
			for (int i = 1; i < cantNodos; i++) {
				for (int j = 1; j < cantNodos; j++) {
					distancias[i][j] = Math.min(distancias[i][j], distancias[i][k] + distancias[k][j]);
				}
			}
		}

		for (int i = 1; i < cantNodos; i++) {
			System.out.println("Nodo: " + i);
			for (int j = 1; j < cantNodos; j++) {
				System.out.println("distancia a " + j + " es " + distancias[i][j]);
			}
			System.out.println("------------------------------");
		}

		return distancias;
	}
	
}
