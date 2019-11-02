package Application;

import java.awt.Desktop;
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

import java.text.ParseException;

import com.gnostice.pdfone.PdfDocument;
import com.gnostice.pdfone.PdfException;
import com.gnostice.pdfone.PdfSearchElement;
import com.gnostice.pdfone.PdfSearchMode;
import com.gnostice.pdfone.PdfSearchOptions;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

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
		ArrayList<Object> CaracteristicasArchivos = new ArrayList<Object>();
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
			    CaracteristicasArchivos.add(CRTRS);
			} catch (IOException e) {
			    e.printStackTrace();
			}
		}
		System.out.println(CaracteristicasArchivos);
	}
	
	/**
	 * Este metodo permite buscar un String dentro de un .txt
	 */
	public static void BusquedaTXT(ArrayList<String> input, String palabra) {
		for(int i = 0; i < input.size(); i++) {
			try {
				File ArchivoTXT = new File(input.get(i));
				BufferedReader b = new BufferedReader(new FileReader(ArchivoTXT));
				String readLine = "";
				while ((readLine = b.readLine()) != null) {
					if(readLine.contains(palabra)) {
						TextField TextField = new TextField();
						TextField.setEditable(false);
						TextField.setText(ArchivoTXT.getName() + " | " + readLine);
						TextField.setMinWidth(500);
						 Button button = new Button("Open");
			 	            button.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {       		
								public void handle (MouseEvent e) {
									if(e.getButton().equals(MouseButton.PRIMARY)) {
										
									}
								}
							});
			 	        HBox Hbox = new HBox();
			 	        Hbox.getChildren().addAll(TextField,button);
			 	        Main.TXTSearchVbox.getChildren().add(Hbox);
					}
				}
				b.close();
			} catch (FileNotFoundException e) {
				System.out.println("El archivo no existe");	
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Este metodo permite buscar un String dentro de un PDF
	 */
	public static void BusquedaPDF(ArrayList<String> input,String palabra) {
		for(int indice = 0; indice < input.size(); indice++) {
			try {
				//Instanciacion de cada variable necesaria
		        PdfSearchElement ElementoBuscado;
		        PdfDocument doc = new PdfDocument();
		        File File = new File(input.get(indice));
		        doc.load(input.get(indice));
		        int Cant_Pages = doc.getPageCount();
		        
		        //Iteracion en las paginas
		        for(int i = 1; i< Cant_Pages; i++) {
			        ArrayList<?> ResultadosBusqueda = 
			 	           (ArrayList<?>) doc.search(palabra, i, PdfSearchMode.LITERAL, PdfSearchOptions.NONE);
			 	        doc.close();
			 	        
			 	        int n = ResultadosBusqueda.size();
			 	        
			 	        //Iteracion para la buqueda de la palabra en cada línea
			 	        for (i = 0; i < n; i++) {
			 	        	ElementoBuscado = (PdfSearchElement) ResultadosBusqueda.get(i);
			 	            // Print search results to console output
			 	            TextField TextField = new TextField();
			 	            TextField.setEditable(false);
			 	            TextField.setText(File.getName() + " | " + ElementoBuscado.getLineContainingMatchString());
			 	            TextField.setMinWidth(500);
			 	            Button button = new Button("Open");
			 	            button.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {       		
								public void handle (MouseEvent e) {
									if(e.getButton().equals(MouseButton.PRIMARY)) {
										if(!Desktop.isDesktopSupported()){
								            System.out.println("Desktop is not supported");
								            return;
								        }
								        Desktop desktop = Desktop.getDesktop();
								        if(File.exists())
											try {
												desktop.open(File);
											} catch (IOException e1) {
												e1.printStackTrace();
											}
									}
								}
							});
			 	            HBox Hbox = new HBox();
			 	            Hbox.getChildren().addAll(TextField,button);
			 	            Main.PDFSearchVbox.getChildren().add(Hbox);
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
}
