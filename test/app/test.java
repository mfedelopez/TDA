package app;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

class test {

	@Test
	void test() throws FileNotFoundException {
		Grafo grafo=new Grafo();
		grafo.leerGrafo();
		int [][] resultado = grafo.kruskal();
		for(int i=0; i<resultado.length;i++) {
			for(int j=0; j<resultado.length;j++) {
				System.out.print("["+resultado[i][j]+"]");
			}
			System.out.print("\n");
		}
	}

}
