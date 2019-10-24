package Application;

import java.util.ArrayList;
import java.util.List;

public class SistemasOrdenamiento {
	/**
	 * Este metodo realiza el sistema de ordenamiento bubblesort con un ArrayList
	 * @param input (Lista a ordenar)
	 */
	public static void BubbleSort(ArrayList<ArrayList<Object>> input) {
		ArrayList<Object> temp;
		for (int Primero = 0; Primero < input.size(); Primero++) {
			String Palabra1 = input.get(Primero).get(0).toString();
			for (int Segundo = Primero + 1; Segundo < input.size(); Segundo++) {
				String Palabra2 = input.get(Segundo).get(0).toString();
				// comparing strings
				if (Palabra2.compareTo(Palabra1) < 0) {
					temp = input.get(Primero);
					input.set(Primero, input.get(Segundo));
					input.set(Segundo, temp);
				}
			}
		}
		System.out.println(input);
	}
	/**
	 * Este metodo realiza el sistema de ordenamiento RadixSort con un ArrayList
	 * @param input (Lista a ordenar)
	 */
	
	public static void OrdenRaiz(ArrayList<ArrayList<Object>> input) {
		ArrayList<Integer> ArchivoTamaño = new ArrayList<Integer>();
		ArrayList<Integer> IndiceTamaño = new ArrayList<Integer>();
		for(int i = 0; i<input.size(); i++) {
			String TamañoString = input.get(i).get(1).toString();
			int TamañoInt = Integer.parseInt(TamañoString);
			ArchivoTamaño.add(TamañoInt);
			IndiceTamaño.add(i);
		}
		radixSort(ArchivoTamaño);
		ArrayList<Object> temp;
		for (int Primero = 0; Primero < input.size(); Primero++) {
			String Numero1 = input.get(Primero).get(1).toString();
			for (int Segundo = Primero + 1; Segundo < input.size(); Segundo++) {
				String Numero2 = input.get(Segundo).get(1).toString();
				if (Numero2.compareTo(Numero1) < 0) {
					temp = input.get(Primero);
					input.set(Primero, input.get(Segundo));
					input.set(Segundo, temp);
				}
			}
		}
		System.out.println(input);
	}
	public static void radixSort(ArrayList<Integer> input) {
		final int RADIX = 10;
    
		// declare and initialize bucket[]
		List<Integer>[] bucket = new ArrayList[RADIX];

		for (int i = 0; i < bucket.length; i++) {
			bucket[i] = new ArrayList<Integer>();
		}

		// sort
		boolean maxLength = false;
		int tmp = -1, placement = 1;
		while (!maxLength) {
			maxLength = true;
	
			// split input between lists
			for (Integer i : input) {
				tmp = i / placement;
				bucket[tmp % RADIX].add(i);
				if (maxLength && tmp > 0) {
					maxLength = false;
				}
			}
  
			// empty lists into input array
			int a = 0;
			for (int b = 0; b < RADIX; b++) {
				for (Integer i : bucket[b]) {
					input.set(a++, i);
				}
				bucket[b].clear();
			}
  
			// move to next digit
			placement *= RADIX;
		}
	}

	
	/**
	 * Este metodo declara los parámetros para que el sistema
	 * de ordenamiento Quicksort se realice de manera correcta
	 * @param input (Lista a ordenar)
	 */
	
	public static void OrdenRapido(ArrayList<ArrayList<Object>> input) {
		ArrayList<Integer> ArchivoFecha = new ArrayList<Integer>();
		ArrayList<Integer> IndiceFecha = new ArrayList<Integer>();
		for(int i = 0; i<input.size(); i++) {
			String TamañoString = input.get(i).get(2).toString();
			int TamañoInt = Integer.parseInt(TamañoString);
			ArchivoFecha.add(TamañoInt);
			IndiceFecha.add(i);
		}
		ordenacionRapida(ArchivoFecha);
		ArrayList<Object> temp;
		for (int Primero = 0; Primero < input.size(); Primero++) {
			String Numero1 = input.get(Primero).get(2).toString();
			for (int Segundo = Primero + 1; Segundo < input.size(); Segundo++) {
				String Numero2 = input.get(Segundo).get(2).toString();
				if (Numero2.compareTo(Numero1) < 0) {
					temp = input.get(Primero);
					input.set(Primero, input.get(Segundo));
					input.set(Segundo, temp);
				}
			}
		}
		System.out.println(input);
	}
	public static void ordenacionRapida(ArrayList<Integer> input){
        final int N=input.size();
        quickSort(input, 0, N-1);
        }
	
	/**
	 * Este método realiza la lógica del Quicksort
	 * @param input (Lista a ordenar)
	 * @param inicio (Indice de inicio del arraylist)
	 * @param fin (Indice final del arraylist)
	 */
	
	public static void quickSort(ArrayList<Integer> input, int inicio, int fin){
        if(inicio>=fin) return;
        int pivote = input.get(inicio);
        int elemIzq = inicio + 1;
        int elemDer = fin;
        while(elemIzq <= elemDer){
        	while(elemIzq <= fin && input.get(elemIzq)<pivote){
        		elemIzq++;
        		}
        	while(elemDer > inicio && input.get(elemDer) >= pivote){
        		elemDer--;
        		}
        	if(elemIzq < elemDer){
        		int temp = input.get(elemIzq);
        		input.set(elemIzq, input.get(elemDer));
        		input.set(elemDer, temp);
        		}
        	}
        if(elemDer > inicio){
        	int temp = input.get(inicio);
        	input.set(inicio, input.get(elemDer));
        	input.set(elemDer, temp);
        	}
        quickSort(input, inicio, elemDer-1);
        quickSort(input, elemDer+1, fin);
	}
}		
