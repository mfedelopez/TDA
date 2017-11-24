package app;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

class test {

	@Test
	void testKruskal() throws FileNotFoundException {
		Grafo grafo=new Grafo();
		grafo.leerGrafo();
		System.out.print("Kruskal: \n");
		int [][] resultado = grafo.kruskal();
		for(int i=0; i<resultado.length;i++) {
			for(int j=0; j<resultado.length;j++) {
				System.out.print("["+resultado[i][j]+"]");
			}
			System.out.print("\n");
		}
		System.out.print("\n");
	}

	@Test
	void testDijsktra() throws FileNotFoundException {
		Grafo grafo=new Grafo();
		System.out.print("Dijkstra: \n");
		grafo.leerGrafo();
		grafo.dijkstra(7);
		System.out.print("\n");
	}
	
	@Test
	void testPrim() throws FileNotFoundException {
		Grafo grafo=new Grafo();
		grafo.leerGrafo();
		int [][] resultado = grafo.prim();
		System.out.print("Prim: \n");
		for(int i=0; i<resultado.length;i++) {
			for(int j=0; j<resultado.length;j++) {
				System.out.print("["+resultado[i][j]+"]");
			}
			System.out.print("\n");
		}
		System.out.print("\n");
	}
}
