package Application;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToolBar;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.function.Function;
import java.awt.Desktop;

import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.TextField;

public class Main extends Application {
	public static final BorderPane DisplayPane = new BorderPane();
	private static String ROOT_FOLDER = "E:/Trabajos/TEC/Intro Bostezo"; // TODO: change or make selectable
	//private static String ROOT_FOLDER = "C:\\Users\\Mauricio\\Desktop\\Universidad\\-TEC-\\Trabajos\\IV Semestre\\Datos I\\Proyecto II\\Proyecto II"; // TODO: change or make selectable
    TreeItem<FilePath> rootTreeItem;
    TreeView<FilePath> treeView;
    
	public static final TitledPane PDFPane = new TitledPane("PDF",new Label("Show all PDF files available"));
	public static final TitledPane PDFWordSearch = new TitledPane("PDF",new Label("Show all PDF files available"));
	public static final VBox PDFVbox = new VBox();
	public static final VBox PDFSearchVbox = new VBox();
	public static final TableView PDFTableView = new TableView();
	public static final SplitPane PDFSplitPane = new SplitPane();
	
	public static final TitledPane TXTPane = new TitledPane("TXT",new Label("Show all TXT files available"));
	public static final TitledPane TXTWordSearch = new TitledPane("TXT",new Label("Show all TXT files available"));
	public static final VBox TXTVbox = new VBox();
	public static final VBox TXTSearchVbox = new VBox();
	public static final TableView TXTTableView = new TableView();
	public static final SplitPane TXTSplitPane = new SplitPane();
	
	public static final TitledPane DOCXPane = new TitledPane("DOCX",new Label("Show all DOCX files available"));
	public static final TitledPane DOCXWordSearch = new TitledPane("DOCX",new Label("Show all DOCX files available"));
	public static final VBox DOCXVbox = new VBox();
	public static final VBox DOCXSearchVbox = new VBox();
	public static final TableView DOCXTableView = new TableView();
	public static final SplitPane DOCXSplitPane = new SplitPane();

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		ArrayList<String> ArchivosTXT = new ArrayList<String>();
		ArrayList<String> ArchivosPDF = new ArrayList<String>();
		ArrayList<String> ArchivosDOCX = new ArrayList<String>();
		treeView = new TreeView<FilePath>();
        TextField filter = new TextField();
        filter.textProperty().addListener((observable, oldValue, newValue) -> filterChanged(newValue));
        filter.setMinWidth(200);
        //CREACION DE TABLECOLUMNS
        TableColumn PDFNameColumn = new TableColumn("Name");
        PDFNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        PDFNameColumn.setMinWidth(172);
        PDFNameColumn.setSortable(false);
        TableColumn PDFSizeColumn = new TableColumn("Size");
        PDFSizeColumn.setCellValueFactory(new PropertyValueFactory<>("size"));
        PDFSizeColumn.setMinWidth(50);
        PDFSizeColumn.setSortable(false);
        TableColumn PDFDateColumn = new TableColumn("Created");
        PDFDateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        PDFDateColumn.setMinWidth(50);
        PDFDateColumn.setSortable(false);
        PDFTableView.getColumns().addAll(PDFNameColumn,PDFSizeColumn,PDFDateColumn);
        TableColumn TXTNameColumn = new TableColumn("Name");
        TXTNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        TXTNameColumn.setMinWidth(172);
        TXTNameColumn.setSortable(false);
        TableColumn TXTSizeColumn = new TableColumn("Size");
        TXTSizeColumn.setCellValueFactory(new PropertyValueFactory<>("size"));
        TXTSizeColumn.setMinWidth(50);
        TXTSizeColumn.setSortable(false);
        TableColumn TXTDateColumn = new TableColumn("Created");
        TXTDateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        TXTDateColumn.setMinWidth(50);
        TXTDateColumn.setSortable(false);
        TXTTableView.getColumns().addAll(TXTNameColumn,TXTSizeColumn,TXTDateColumn);
        TableColumn DOCXNameColumn = new TableColumn("Name");
        DOCXNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        DOCXNameColumn.setMinWidth(172);
        DOCXNameColumn.setSortable(false);
        TableColumn DOCXSizeColumn = new TableColumn("Size");
        DOCXSizeColumn.setCellValueFactory(new PropertyValueFactory<>("size"));
        DOCXSizeColumn.setMinWidth(50);
        DOCXSizeColumn.setSortable(false);
        TableColumn DOCXDateColumn = new TableColumn("Created");
        DOCXDateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        DOCXDateColumn.setMinWidth(50);
        DOCXDateColumn.setSortable(false);
        DOCXTableView.getColumns().addAll(DOCXNameColumn,DOCXSizeColumn,DOCXDateColumn);
        //EVENT TO ADD FILES TO TABLEVIEW
        
        TextField Buscador = new TextField();
        Button Boton_Buscar = new Button();
		treeView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {       		
				public void handle (MouseEvent e) {
					if(e.getButton().equals(MouseButton.PRIMARY)){
			            if(e.getClickCount() == 2){
			                System.out.println("Double clicked");
			                String Item = new String(treeView.getSelectionModel().getSelectedItem().getValue().getPath().toString());
							String ItemName = new String(treeView.getSelectionModel().getSelectedItem().getValue().getPath().getFileName().toString());
							File archivo = new File(Item);
							Long ItemSize = archivo.length();
							String ItemDate = new String();
							BasicFileAttributes attrs;
							try {
							    attrs = Files.readAttributes(archivo.toPath(), BasicFileAttributes.class);
							    FileTime time = attrs.creationTime();
							    String pattern = "yyyy-MM-dd";
							    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
							    String formatted = simpleDateFormat.format(new Date( time.toMillis()));
							    ItemDate = formatted;
							} catch (IOException e1) {
							    e1.printStackTrace();
							}
							System.out.println("Added Name: "+ItemName+" Size: "+ItemSize+"B"+" Date: "+ItemDate);
							if(Item.contains(".pdf")) {
								ArchivosRepetidos(Item, ArchivosPDF, ItemName, ItemSize, ItemDate);
							}else if(Item.contains(".txt")) {
								ArchivosRepetidos(Item, ArchivosTXT, ItemName, ItemSize, ItemDate);
							}else if(Item.contains(".docx")) {
								ArchivosRepetidos(Item, ArchivosDOCX, ItemName, ItemSize, ItemDate);
							}else {
								System.out.println("No se acepta este archivo");
							}
							
			            }
			        }
					      						
				}				
			});
        
        TextField ShowDirectory = new TextField();
        ShowDirectory.setEditable(false);
        ShowDirectory.setMinWidth(200);
        ShowDirectory.setText(ROOT_FOLDER);
		Button DirectoryButton = new Button();
		DirectoryButton.setText("Change Directory");
		DirectoryButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				DirectoryChooser directoryChooser = new DirectoryChooser();
				File selectedDirectory = directoryChooser.showDialog(primaryStage);
				if(selectedDirectory == null){
				     //No Directory selected
				}else{
				     System.out.println(selectedDirectory.getAbsolutePath());
				     ShowDirectory.setText(selectedDirectory.getAbsolutePath());
				     ROOT_FOLDER = selectedDirectory.getAbsolutePath();
			     	 // create tree
			         try {
						 createTree();
					 } catch (IOException e) {
				    	 e.printStackTrace();
					 }
			         // show tree structure in tree view
			         treeView.setRoot(rootTreeItem);
				}
			}
		});
		//CREACION DE PANELES
		ToolBar BarraBuscador = new ToolBar();
		BarraBuscador.setStyle("-fx-background-color: #FF4500");
		BarraBuscador.getItems().addAll(Buscador, Boton_Buscar);
		SplitPane SplitPane = new SplitPane();
		BorderPane ExplorerBorderPane = new BorderPane();
		SplitPane FileSplitPane = new SplitPane();
		DisplayPane.setTop(BarraBuscador);
		Boton_Buscar.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent e) {
				String PalabraBuscar = String.valueOf(Buscador.getText());
				if(PalabraBuscar.length() == 0) {
					System.out.println("Ingrese una palabra");
				}
				SistemasBusqueda.BusquedaTXT(ArchivosTXT, PalabraBuscar);
				SistemasBusqueda.BusquedaPDF(ArchivosPDF, PalabraBuscar);
			}
			
		});
		
		FileSplitPane.setOrientation(Orientation.VERTICAL);
		Accordion FileAccordion = new Accordion();
		Accordion WordSearch = new Accordion();
		WordSearch.getPanes().addAll(PDFWordSearch,TXTWordSearch,DOCXWordSearch);
		ScrollPane PDFScroll = new ScrollPane();
		PDFScroll.setContent(PDFSearchVbox);
		PDFWordSearch.setContent(PDFScroll);
		ScrollPane TXTScroll = new ScrollPane();
		TXTScroll.setContent(TXTSearchVbox);
		TXTWordSearch.setContent(TXTScroll);
		ScrollPane DOCXScroll = new ScrollPane();
		DOCXScroll.setContent(DOCXSearchVbox);
		DOCXWordSearch.setContent(DOCXScroll);
		WordSearch.setMinHeight(557);
		SplitPane SplitPane2 = new SplitPane();
		SplitPane2.setOrientation(Orientation.VERTICAL);
		SplitPane2.getItems().addAll(DisplayPane,WordSearch);
		SplitPane.getItems().addAll(FileSplitPane,SplitPane2);
			FileSplitPane.getItems().addAll(ExplorerBorderPane,FileAccordion);
				ExplorerBorderPane.setMinHeight(300);
				ExplorerBorderPane.setMinWidth(200);
				ToolBar ToolBar = new ToolBar();
				ToolBar.setStyle("-fx-background-color: #FF4500");
				Label Label = new Label("Filter");
				Label.setTextFill(Color.WHITE);
				Label Space = new Label("        ");
				ToolBar.getItems().addAll(Label,filter,Space,DirectoryButton,ShowDirectory);
				ExplorerBorderPane.setTop(ToolBar);
				ExplorerBorderPane.setCenter(treeView);
				Label Label1= new Label("Archivos Seleccionados");
				Label1.setTextFill(Color.WHITE);
				ToolBar Ordenamientos = new ToolBar();
				Ordenamientos.setStyle("-fx-background-color: #FF4500");
				Button NameSort = new Button("   Name   ");
				NameSort.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {       		
					public void handle (MouseEvent e) {
						if(e.getButton().equals(MouseButton.PRIMARY)){
							QuickSort ob = new QuickSort();
							ob.sort(ArchivosPDF, 0, ArchivosPDF.size()-1);
							System.out.println(ArchivosPDF);
							PDFTableView.getItems().clear();
							for(int x=0;x<ArchivosPDF.size();x++) {
								File File = new File(ArchivosPDF.get(x));
								String ItemName = File.getName();
								Long ItemSize = File.length();
								String ItemDate = new String();
								BasicFileAttributes attrs;
								try {
								    attrs = Files.readAttributes(File.toPath(), BasicFileAttributes.class);
								    FileTime time = attrs.creationTime();
								    String pattern = "yyyy-MM-dd";
								    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
								    String formatted = simpleDateFormat.format(new Date( time.toMillis()));
								    ItemDate = formatted;
								} catch (IOException e1) {
								    e1.printStackTrace();
								}
								PDFTableView.getItems().add(new TableViewCreator(ItemName, ItemSize.toString()+"B", ItemDate));
							}
							QuickSort ob1 = new QuickSort();
							ob1.sort(ArchivosTXT, 0, ArchivosTXT.size()-1);
							System.out.println(ArchivosTXT);
							TXTTableView.getItems().clear();
							for(int x=0;x<ArchivosTXT.size();x++) {
								File File = new File(ArchivosTXT.get(x));
								String ItemName = File.getName();
								Long ItemSize = File.length();
								String ItemDate = new String();
								BasicFileAttributes attrs;
								try {
								    attrs = Files.readAttributes(File.toPath(), BasicFileAttributes.class);
								    FileTime time = attrs.creationTime();
								    String pattern = "yyyy-MM-dd";
								    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
								    String formatted = simpleDateFormat.format(new Date( time.toMillis()));
								    ItemDate = formatted;
								} catch (IOException e1) {
								    e1.printStackTrace();
								}
								TXTTableView.getItems().add(new TableViewCreator(ItemName, ItemSize.toString()+"B", ItemDate));
							}
							QuickSort ob11 = new QuickSort();
							ob11.sort(ArchivosDOCX, 0, ArchivosDOCX.size()-1);
							System.out.println(ArchivosDOCX);
							DOCXTableView.getItems().clear();
							for(int x=0;x<ArchivosDOCX.size();x++) {
								File File = new File(ArchivosDOCX.get(x));
								String ItemName = File.getName();
								Long ItemSize = File.length();
								String ItemDate = new String();
								BasicFileAttributes attrs;
								try {
								    attrs = Files.readAttributes(File.toPath(), BasicFileAttributes.class);
								    FileTime time = attrs.creationTime();
								    String pattern = "yyyy-MM-dd";
								    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
								    String formatted = simpleDateFormat.format(new Date( time.toMillis()));
								    ItemDate = formatted;
								} catch (IOException e1) {
								    e1.printStackTrace();
								}
								DOCXTableView.getItems().add(new TableViewCreator(ItemName, ItemSize.toString()+"B", ItemDate));
							}
							
						}
					}
				});
				Button SizeSort = new Button("   Size   ");
				SizeSort.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {       		
					public void handle (MouseEvent e) {
						if(e.getButton().equals(MouseButton.PRIMARY)){
							try {
								SistemasOrdenamiento.RadixSort(ArchivosPDF);
								System.out.println(ArchivosPDF);
								PDFTableView.getItems().clear();
								for(int x=0;x<ArchivosPDF.size();x++) {
									File File = new File(ArchivosPDF.get(x));
									String ItemName = File.getName();
									Long ItemSize = File.length();
									String ItemDate = new String();
									BasicFileAttributes attrs;
									try {
									    attrs = Files.readAttributes(File.toPath(), BasicFileAttributes.class);
									    FileTime time = attrs.creationTime();
									    String pattern = "yyyy-MM-dd";
									    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
									    String formatted = simpleDateFormat.format(new Date( time.toMillis()));
									    ItemDate = formatted;
									} catch (IOException e1) {
									    e1.printStackTrace();
									}
									PDFTableView.getItems().add(new TableViewCreator(ItemName, ItemSize.toString()+"B", ItemDate));
								}
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							try {
								SistemasOrdenamiento.RadixSort(ArchivosTXT);
								System.out.println(ArchivosTXT);
								TXTTableView.getItems().clear();
								for(int x=0;x<ArchivosTXT.size();x++) {
									File File = new File(ArchivosTXT.get(x));
									String ItemName = File.getName();
									Long ItemSize = File.length();
									String ItemDate = new String();
									BasicFileAttributes attrs;
									try {
									    attrs = Files.readAttributes(File.toPath(), BasicFileAttributes.class);
									    FileTime time = attrs.creationTime();
									    String pattern = "yyyy-MM-dd";
									    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
									    String formatted = simpleDateFormat.format(new Date( time.toMillis()));
									    ItemDate = formatted;
									} catch (IOException e1) {
									    e1.printStackTrace();
									}
									TXTTableView.getItems().add(new TableViewCreator(ItemName, ItemSize.toString()+"B", ItemDate));
								}
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							try {
								SistemasOrdenamiento.RadixSort(ArchivosDOCX);
								System.out.println(ArchivosDOCX);
								DOCXTableView.getItems().clear();
								for(int x=0;x<ArchivosDOCX.size();x++) {
									File File = new File(ArchivosDOCX.get(x));
									String ItemName = File.getName();
									Long ItemSize = File.length();
									String ItemDate = new String();
									BasicFileAttributes attrs;
									try {
									    attrs = Files.readAttributes(File.toPath(), BasicFileAttributes.class);
									    FileTime time = attrs.creationTime();
									    String pattern = "yyyy-MM-dd";
									    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
									    String formatted = simpleDateFormat.format(new Date( time.toMillis()));
									    ItemDate = formatted;
									} catch (IOException e1) {
									    e1.printStackTrace();
									}
									DOCXTableView.getItems().add(new TableViewCreator(ItemName, ItemSize.toString()+"B", ItemDate));
								}
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					}
				});
				Button DateSort = new Button("   Date   ");
				DateSort.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {       		
					public void handle (MouseEvent e) {
						if(e.getButton().equals(MouseButton.PRIMARY)){
							try {
								SistemasOrdenamiento.BubbleSort(ArchivosPDF);
								System.out.println(ArchivosPDF);
								PDFTableView.getItems().clear();
								for(int x=0;x<ArchivosPDF.size();x++) {
									File File = new File(ArchivosPDF.get(x));
									String ItemName = File.getName();
									Long ItemSize = File.length();
									String ItemDate = new String();
									BasicFileAttributes attrs;
									try {
									    attrs = Files.readAttributes(File.toPath(), BasicFileAttributes.class);
									    FileTime time = attrs.creationTime();
									    String pattern = "yyyy-MM-dd";
									    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
									    String formatted = simpleDateFormat.format(new Date( time.toMillis()));
									    ItemDate = formatted;
									} catch (IOException e1) {
									    e1.printStackTrace();
									}
									PDFTableView.getItems().add(new TableViewCreator(ItemName, ItemSize.toString()+"B", ItemDate));
								}
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							try {
								SistemasOrdenamiento.BubbleSort(ArchivosTXT);
								System.out.println(ArchivosTXT);
								TXTTableView.getItems().clear();
								for(int x=0;x<ArchivosTXT.size();x++) {
									File File = new File(ArchivosTXT.get(x));
									String ItemName = File.getName();
									Long ItemSize = File.length();
									String ItemDate = new String();
									BasicFileAttributes attrs;
									try {
									    attrs = Files.readAttributes(File.toPath(), BasicFileAttributes.class);
									    FileTime time = attrs.creationTime();
									    String pattern = "yyyy-MM-dd";
									    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
									    String formatted = simpleDateFormat.format(new Date( time.toMillis()));
									    ItemDate = formatted;
									} catch (IOException e1) {
									    e1.printStackTrace();
									}
									TXTTableView.getItems().add(new TableViewCreator(ItemName, ItemSize.toString()+"B", ItemDate));
								}
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							try {
								SistemasOrdenamiento.BubbleSort(ArchivosDOCX);
								System.out.println(ArchivosDOCX);
								DOCXTableView.getItems().clear();
								for(int x=0;x<ArchivosDOCX.size();x++) {
									File File = new File(ArchivosDOCX.get(x));
									String ItemName = File.getName();
									Long ItemSize = File.length();
									String ItemDate = new String();
									BasicFileAttributes attrs;
									try {
									    attrs = Files.readAttributes(File.toPath(), BasicFileAttributes.class);
									    FileTime time = attrs.creationTime();
									    String pattern = "yyyy-MM-dd";
									    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
									    String formatted = simpleDateFormat.format(new Date( time.toMillis()));
									    ItemDate = formatted;
									} catch (IOException e1) {
									    e1.printStackTrace();
									}
									DOCXTableView.getItems().add(new TableViewCreator(ItemName, ItemSize.toString()+"B", ItemDate));
								}
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					}
				});
				Label Space2 = new Label("                                                                      ");
				Ordenamientos.getItems().addAll(Label1,Space2,NameSort,new Separator(),SizeSort,new Separator(),DateSort);
				ExplorerBorderPane.setBottom(Ordenamientos);
				FileAccordion.getPanes().add(PDFPane);
				PDFPane.setContent(PDFSplitPane); //TODO: Splitpanes
				PDFSplitPane.getItems().addAll(PDFVbox,PDFTableView);
				PDFVbox.setMaxWidth(258);
				FileAccordion.getPanes().add(TXTPane);
				TXTPane.setContent(TXTSplitPane); //TODO: Splitpanes
				TXTSplitPane.getItems().addAll(TXTVbox,TXTTableView);
				TXTVbox.setMaxWidth(258);
				FileAccordion.getPanes().add(DOCXPane);
				DOCXPane.setContent(DOCXSplitPane); //TODO: Splitpanes
				DOCXSplitPane.getItems().addAll(DOCXVbox,DOCXTableView);
				DOCXVbox.setMaxWidth(258);
		Scene primaryScene = new Scene(SplitPane, 1200, 600);
		primaryStage.setScene(primaryScene);
        primaryStage.setTitle("Text Finder v0.3");
        primaryStage.show();
        // create tree
        createTree();

        // show tree structure in tree view
        treeView.setRoot(rootTreeItem);
	}
	public static ArrayList<String> ArchivosRepetidos(String name, ArrayList<String> input, String ItemName, Long ItemSize, String ItemDate) {
		if(input.size() == 0) {
			input.add(name);
			if(name.contains(".pdf")) {
				PDFTableView.getItems().add(new TableViewCreator(ItemName, ItemSize.toString()+"B", ItemDate));
				Button button = new Button();
				button.setText(ItemName);
				BImageView Image1 = new ImageViewBuilder().setImageDirectory(ImageType.pdf.toString()).build();
				button.setGraphic(Image1.getImageView());
				button.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {       		
					public void handle (MouseEvent e) {
						if(e.getButton().equals(MouseButton.SECONDARY)){
							PDFVbox.getChildren().remove(button);
							PDFTableView.getItems().remove(input.indexOf(name));
							input.remove(name);
						}
					}
				});
				PDFVbox.getChildren().add(button);
				button.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {       		
					public void handle (MouseEvent e) {
						if(e.getButton().equals(MouseButton.PRIMARY)) {
							File file = new File(name);
							if(!Desktop.isDesktopSupported()){
					            System.out.println("Desktop is not supported");
					            return;
					        }
					        
					        Desktop desktop = Desktop.getDesktop();
					        if(file.exists())
								try {
									desktop.open(file);
								} catch (IOException e1) {
									e1.printStackTrace();
								}
						}
					}
				});
			}else if(name.contains(".txt")) {
				TXTTableView.getItems().add(new TableViewCreator(ItemName, ItemSize.toString()+"B", ItemDate));
				Button button = new Button();
				button.setText(ItemName);
				BImageView Image1 = new ImageViewBuilder().setImageDirectory(ImageType.txt.toString()).build();
				button.setGraphic(Image1.getImageView());
				button.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {       		
					public void handle (MouseEvent e) {
						if(e.getButton().equals(MouseButton.SECONDARY)){
							TXTVbox.getChildren().remove(button);
							TXTTableView.getItems().remove(input.indexOf(name));
							input.remove(name);
						}
					}
				});
				TXTVbox.getChildren().add(button);
				button.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {       		
					public void handle (MouseEvent e) {
						if(e.getButton().equals(MouseButton.PRIMARY)) {
							File file = new File(name);
							if(!Desktop.isDesktopSupported()){
					            System.out.println("Desktop is not supported");
					            return;
					        }
					        
					        Desktop desktop = Desktop.getDesktop();
					        if(file.exists()) {
								try {
									desktop.open(file);
								} catch (IOException e1) {
									e1.printStackTrace();
								}
					        }
						}
					}
				});
			}else if(name.contains(".docx")) {
				DOCXTableView.getItems().add(new TableViewCreator(ItemName, ItemSize.toString()+"B", ItemDate));
				Button button = new Button();
				button.setText(ItemName);
				BImageView Image1 = new ImageViewBuilder().setImageDirectory(ImageType.docx.toString()).build();
				button.setGraphic(Image1.getImageView());
				button.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {       		
					public void handle (MouseEvent e) {
						if(e.getButton().equals(MouseButton.SECONDARY)){
							DOCXVbox.getChildren().remove(button);
							DOCXTableView.getItems().remove(input.indexOf(name));
							input.remove(name);
						}
					}
				});
				DOCXVbox.getChildren().add(button);
				button.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {       		
					public void handle (MouseEvent e) {
						if(e.getButton().equals(MouseButton.PRIMARY)) {
							File file = new File(name);
							if(!Desktop.isDesktopSupported()){
					            System.out.println("Desktop is not supported");
					            return;
					        }
					        
					        Desktop desktop = Desktop.getDesktop();
					        if(file.exists())
								try {
									desktop.open(file);
								} catch (IOException e1) {
									e1.printStackTrace();
								}
						}
					}
				});
			}
			return input;
		}
		for(int i = 0; i<input.size(); i++) {
			String Comparacion = input.get(i);
			if(name.equals(Comparacion)) {
				return input;
			}
		}
		input.add(name);
		if(name.contains(".pdf")) {
			PDFTableView.getItems().add(new TableViewCreator(ItemName, ItemSize.toString()+"B", ItemDate));
			Button button = new Button();
			button.setText(ItemName);
			BImageView Image1 = new ImageViewBuilder().setImageDirectory(ImageType.pdf.toString()).build();
			button.setGraphic(Image1.getImageView());
			button.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {       		
				public void handle (MouseEvent e) {
					if(e.getButton().equals(MouseButton.SECONDARY)){
						PDFVbox.getChildren().remove(button);
						PDFTableView.getItems().remove(input.indexOf(name));
						input.remove(name);
						
					}
				}
			});
			PDFVbox.getChildren().add(button);
		}else if(name.contains(".txt")) {
			TXTTableView.getItems().add(new TableViewCreator(ItemName, ItemSize.toString()+"B", ItemDate));
			Button button = new Button();
			button.setText(ItemName);
			BImageView Image1 = new ImageViewBuilder().setImageDirectory(ImageType.txt.toString()).build();
			button.setGraphic(Image1.getImageView());
			button.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {       		
				public void handle (MouseEvent e) {
					if(e.getButton().equals(MouseButton.SECONDARY)){
						TXTVbox.getChildren().remove(button);
						TXTTableView.getItems().remove(input.indexOf(name));
						input.remove(name);
					}
				}
			});
			TXTVbox.getChildren().add(button);
		}else if(name.contains(".docx")) {
			DOCXTableView.getItems().add(new TableViewCreator(ItemName, ItemSize.toString()+"B", ItemDate));
			Button button = new Button();
			button.setText(ItemName);
			BImageView Image1 = new ImageViewBuilder().setImageDirectory(ImageType.docx.toString()).build();
			button.setGraphic(Image1.getImageView());
			button.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {       		
				public void handle (MouseEvent e) {
					if(e.getButton().equals(MouseButton.SECONDARY)){
						DOCXVbox.getChildren().remove(button);
						DOCXTableView.getItems().remove(input.indexOf(name));
						input.remove(name);
					}
				}
			});
			DOCXVbox.getChildren().add(button);
		}
		return input;
	}
	private void createTree() throws IOException {

        // create root
        rootTreeItem = createTreeRoot();

        // create tree structure recursively
        createTree( rootTreeItem);

        // sort tree structure by name
        rootTreeItem.getChildren().sort( Comparator.comparing( new Function<TreeItem<FilePath>, String>() {
            @Override
            public String apply(TreeItem<FilePath> t) {
                return t.getValue().toString().toLowerCase();
            }
        }));

    }
	public static void createTree(TreeItem<FilePath> rootItem) throws IOException {

        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(rootItem.getValue().getPath())) {

            for (Path path : directoryStream) {

                TreeItem<FilePath> newItem = new TreeItem<FilePath>( new FilePath( path));
                newItem.setExpanded(true);
                rootItem.getChildren().add(newItem);                
                if (Files.isDirectory(path)) {
                    createTree(newItem);
                }
            }
        }
        // catch exceptions, e. g. java.nio.file.AccessDeniedException: c:\System Volume Information, c:\$RECYCLE.BIN
        catch( Exception ex) {
            //ex.printStackTrace();
            System.out.println(ex);
        }
    }
	public void AgregarALista (LinkedList<String> input, String Item) {
	}		
	private void filter(TreeItem<FilePath> root, String filter, TreeItem<FilePath> filteredRoot) {

        for (TreeItem<FilePath> child : root.getChildren()) {

            TreeItem<FilePath> filteredChild = new TreeItem<>( child.getValue());
            filteredChild.setExpanded(true);
            filter(child, filter, filteredChild );
            
            if (!filteredChild.getChildren().isEmpty() || isMatch(filteredChild.getValue(), filter)) {
                filteredRoot.getChildren().add(filteredChild);
                
            }

        }
    }
	private boolean isMatch(FilePath value, String filter) {
        return value.toString().toLowerCase().contains( filter.toLowerCase()); // TODO: optimize or change (check file extension, etc)
    }
	private void filterChanged(String filter) {
        if (filter.isEmpty()) {
            treeView.setRoot(rootTreeItem);
        }
        else {
            TreeItem<FilePath> filteredRoot = createTreeRoot();
            filter(rootTreeItem, filter, filteredRoot);
            treeView.setRoot(filteredRoot);
        }
    }
	private TreeItem<FilePath> createTreeRoot() {
        TreeItem<FilePath> root = new TreeItem<FilePath>( new FilePath( Paths.get( ROOT_FOLDER)));
        root.setExpanded(true);
        return root;
    }
    private static class FilePath {

        Path path;
        String text;

        public FilePath( Path path) {

            this.path = path;

            // display text: the last path part
            // consider root, e. g. c:\
            if( path.getNameCount() == 0) {
                this.text = path.toString();
            }
            // consider folder structure
            else {
                this.text = path.getName( path.getNameCount() - 1).toString();
            }

        }

        public Path getPath() {
            return path;
        }

        public String toString() {

            // hint: if you'd like to see the entire path, use this:
            // return path.toString();

            // show only last path part
            return text;

        }
    }
    public static void main(String[] args) {
		launch(args);
	}
}
