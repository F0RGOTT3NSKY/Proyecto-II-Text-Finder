package Application;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SistemasOrdenamiento {
	public static void BubbleSort(ArrayList<String> input) throws IOException {
		String temp;
		for (int Primero = 0; Primero < input.size(); Primero++) {
			String Fecha1 = ObtenerFecha(input.get(Primero));
			int Fecha1int = Integer.parseInt(Fecha1);
			for (int Segundo = Primero + 1; Segundo < input.size(); Segundo++) {
				String Fecha2 = ObtenerFecha(input.get(Segundo));
				int Fecha2int = Integer.parseInt(Fecha2);
				if(Fecha2int < Fecha1int) {
					temp = input.get(Primero);
					input.set(Primero, input.get(Segundo));
					input.set(Segundo, temp);
				}
			}
		}
		System.out.println(input);
	}
	
	public static String ObtenerFecha (String ArchivoPrincipal) throws IOException {
		BasicFileAttributes attrs;
		File archivo = new File(ArchivoPrincipal);
	    attrs = Files.readAttributes(archivo.toPath(), BasicFileAttributes.class);
	    FileTime time = attrs.creationTime();
	    
	    String pattern = "yyyyMMdd";
	    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		
	    String formatted = simpleDateFormat.format(new Date( time.toMillis()));
	    return formatted;
	}
	
	public static void OrdenRapido(ArrayList<String> input) {
		int start = 0;
		int end = input.size() - 1;
		quickSort(input, start, end);
		System.out.println(input);
	}
	public static void quickSort(ArrayList<String> input, int start, int end) {
		int i = start;
		int j = end;
		File Pivote = new File(input.get(i));
		System.out.println(i+(j-i)/2);
		String pivot = Pivote.getName();
		while(i<=j) {
			while(input.get(i).compareTo(pivot) < 0) {
				i++;
			}
			while (input.get(j).compareTo(pivot) > 0) {
				j--;
			}
			if (i <= j) {
                swap(input, i, j);
                i++;
                j--;
			}	
		}
		if (start < j) {
            quickSort(input, start, j);
        }
        if (i < end) {
            quickSort(input, i, end);
        }
		
	}

	private static void swap(ArrayList<String> input, int i, int j) {
		String temp = input.get(i);
        input.set(i, input.get(j));
        input.set(j, temp);
	}
	
	public static void OrdenFinal(ArrayList<String> input, int start, int end) {
		while (start < end) {
			String temp = input.get(start);
			input.set(start, input.get(end));
			input.set(end, temp);
			start++;
			end--;
		}
	}
	
	public static void RadixSort(ArrayList<String> input) throws IOException {
		String temp;
		for (int Primero = 0; Primero < input.size(); Primero++) {
			long Tamaño1 = ObtenerTamaño(input.get(Primero));
			for (int Segundo = Primero + 1; Segundo < input.size(); Segundo++) {
				long Tamaño2 = ObtenerTamaño(input.get(Segundo));
				if(Tamaño2 < Tamaño1) {
					temp = input.get(Primero);
					input.set(Primero, input.get(Segundo));
					input.set(Segundo, temp);
				}
			}
		}
		System.out.println(input);
	}
	
	public static long ObtenerTamaño(String ArchivoPrincipal) {
		File archivo = new File(ArchivoPrincipal);
		long TamañoArchivo = archivo.length();
		return TamañoArchivo;
	}
}		
