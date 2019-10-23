package Application;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToolBar;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import Application.LinkedList.Node;

import java.util.List;
import java.util.function.Function;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.TextField;



public class Main extends Application {
	public static final Pane DisplayPane = new Pane();
	//public static final TreeView<File> ExplorerPane = new TreeView<File>(new SimpleFileTreeItem(new File("C:\\")));
	private static String ROOT_FOLDER = "E:/Musica"; // TODO: change or make selectable
    TreeItem<FilePath> rootTreeItem;
    TreeView<FilePath> treeView;
	public static final TitledPane PDFPane = new TitledPane("PDF",new Label("Show all PDF files available"));
	public static final TitledPane TXTPane = new TitledPane("TXT",new Label("Show all TXT files available"));
	public static final TitledPane DOCXPane = new TitledPane("DOCX",new Label("Show all DOCX files available"));
	public static final int AccodionSize = 70;
	Node head;

	@Override
	public void start(Stage primaryStage) throws Exception {
		LinkedList<String> TODOSArchivos = new LinkedList<String>();
		LinkedList<String> ArchivosTXT = new LinkedList<String>();
		LinkedList<String> ArchivosDOCX = new LinkedList<String>(); 
		LinkedList<String> ArchivosPDF = new LinkedList<String>(); 
		treeView = new TreeView<FilePath>();
        TextField filter = new TextField();
        filter.textProperty().addListener((observable, oldValue, newValue) -> filterChanged(newValue));
        filter.setMinWidth(200);
			treeView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {       		
				
				public void handle (MouseEvent e) {
					String Item = new String(treeView.getSelectionModel().getSelectedItem().getValue().toString());
					System.out.println(Item);
					if(Item.length()<4) {
						System.out.println();
					} else {
						String subString = Item.substring(Item.length()-4);
						if(subString.equals(".pdf")) {
							ArchivosPDF.add(Item);
						} if(subString.equals(".txt")) {
							ArchivosTXT.add(Item);

						} if(subString.equals("docx")) {
							ArchivosDOCX.add(Item);
						} else {
							System.out.println("No se acepta este archivo");
						}
						System.out.println(TODOSArchivos);      	
					}
				}				
			});
        
        TextField ShowDirectory = new TextField();
        ShowDirectory.setEditable(false);
        ShowDirectory.setMinWidth(200);
        ShowDirectory.setText(ROOT_FOLDER);
		Button button = new Button();
		button.setText("Example");
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
		
		SplitPane SplitPane = new SplitPane();
		BorderPane ExplorerBorderPane = new BorderPane();
		SplitPane FileSplitPane = new SplitPane();
		FileSplitPane.setOrientation(Orientation.VERTICAL);
		Accordion FileAccordion = new Accordion();
		FileAccordion.setMaxHeight(AccodionSize);		
		SplitPane.getItems().addAll(FileSplitPane,DisplayPane);
			DisplayPane.getChildren().add(button);
			FileSplitPane.getItems().addAll(ExplorerBorderPane,FileAccordion);
				ExplorerBorderPane.setMinHeight(300);
				ExplorerBorderPane.setMinWidth(200);
				ToolBar ToolBar = new ToolBar();
				ToolBar.setStyle("-fx-background-color: #047ff8");
				Label Label = new Label("Filter");
				Label.setTextFill(Color.WHITE);
				Label Space = new Label("        ");
				ToolBar.getItems().addAll(Label,filter,Space,DirectoryButton,ShowDirectory);
				ExplorerBorderPane.setTop(ToolBar);
				ExplorerBorderPane.setCenter(treeView);
				FileAccordion.getPanes().add(PDFPane);
				FileAccordion.getPanes().add(TXTPane);
				FileAccordion.getPanes().add(DOCXPane);
				PDFPane.setContent(button);
		Scene primaryScene = new Scene(SplitPane, 1200, 600);
		
		primaryStage.setScene(primaryScene);
        primaryStage.setTitle("Text Finder v0.2");
        primaryStage.show();
        // create tree
        createTree();

        // show tree structure in tree view
        treeView.setRoot(rootTreeItem);
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
