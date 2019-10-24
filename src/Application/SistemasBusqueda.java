package Application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.gnostice.pdfone.PdfDocument;
import com.gnostice.pdfone.PdfException;
import com.gnostice.pdfone.PdfSearchElement;
import com.gnostice.pdfone.PdfSearchMode;
import com.gnostice.pdfone.PdfSearchOptions;

public class SistemasBusqueda {
	
	/**
	 * Este metodo obtiene las siguientes características de cualquier archivo:
	 * Nombre,
	 * Tamaño en bytes,
	 * Ruta y 
	 * Fecha de creación.
	 * @param archivo
	 */
	public static void Caracteristicas(ArrayList<Object> Archivos) {
		// Tamaño de archivo
		for(int i = 0; i < Archivos.size(); i++) {
			File archivo = new File(Archivos.get(i).toString());
			Long longitud = archivo.length();
			ArrayList<Object> CRTRS = new ArrayList<Object>();
	
			CRTRS.add(archivo.getName());
			CRTRS.add(longitud);
	                
	        //Fecha de creacion del archivo
	        BasicFileAttributes attrs;
			try {
			    attrs = Files.readAttributes(archivo.toPath(), BasicFileAttributes.class);
			    FileTime time = attrs.creationTime();
			    
			    String pattern = "yyyyMMdd";
			    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
				
			    String formatted = simpleDateFormat.format(new Date( time.toMillis()));
			    CRTRS.add(formatted);
			} catch (IOException e) {
			    e.printStackTrace();
			}
			System.out.println(CRTRS);
		}
	}
	
	/**
	 * Este metodo permite buscar un String dentro de un .txt
	 */
	public static void BusquedaTXT(String palabra) {
		ArrayList<Object> Archivos = new ArrayList<Object>();
		try {
			File ArchivoTXT = new File("src/temp.txt");
			Archivos.add(ArchivoTXT);
			Caracteristicas(Archivos);
			BufferedReader b = new BufferedReader(new FileReader(ArchivoTXT));
			String readLine = "";
			while ((readLine = b.readLine()) != null) {
				System.out.println(readLine);
			}
			b.close();
		} catch (FileNotFoundException e) {
			System.out.println("El archivo no existe");	
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Este metodo permite buscar un String dentro de un PDF
	 */
	public static void BusquedaPDF(String palabra) {
		ArrayList<Object> Archivos = new ArrayList<Object>();
		try {
			//Instanciacion de cada variable necesaria
	        PdfSearchElement ElementoBuscado;
	        PdfDocument doc = new PdfDocument();
	        doc.load("src/tarea.pdf");
	        File PDFfile = new File(doc.getInputFilePath() + '/' + doc.getInputFileName());
	        Archivos.add(PDFfile);
			Caracteristicas(Archivos);
	        int Cant_Pages = doc.getPageCount();
	        
	        //Iteracion en las paginas
	        for(int i = 1; i< Cant_Pages; i++) {
		        ArrayList<?> ResultadosBusqueda = 
		 	           (ArrayList<?>) doc.search(palabra, i, PdfSearchMode.LITERAL, PdfSearchOptions.NONE);
		 	        doc.close();
		 	        
		 	        int n = ResultadosBusqueda.size();
		 	        if(n == 0) {
		 	        	System.out.println("La palabra '" + palabra + "' no se ecuentra en el archivo");		 	        
		 	        }
		 	        
		 	        //Iteracion para la buqueda de la palabra en cada línea
		 	        for (i = 0; i < n; i++) {
		 	        	ElementoBuscado = (PdfSearchElement) ResultadosBusqueda.get(i);
		 	            // Print search results to console output
		 	            System.out.println("Found \"" + 
		 	            		ElementoBuscado.getMatchString()  + 
		 	                               "\" in page #" + 
		 	                              ElementoBuscado.getPageNum() + 
		 	                               " text \"" + 
		 	                              ElementoBuscado.getLineContainingMatchString()  + 
		 	                               "\"" );
		 	        }
	        }
	        
		} catch (FileNotFoundException e) {
			System.out.println("El archivo no existe");
		} catch (IOException e) {
			e.printStackTrace();
		} catch(PdfException e) {
			e.getStackTrace();
		}
	}
}
