package Application;

import javafx.application.Application;
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
import javafx.stage.Stage;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.function.Function;
import javafx.scene.control.TextField;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;


public class Main extends Application {
	public static final Pane DisplayPane = new Pane();
	//public static final TreeView<File> ExplorerPane = new TreeView<File>(new SimpleFileTreeItem(new File("C:\\")));
	private static final String ROOT_FOLDER = "C:/Users"; // TODO: change or make selectable
    TreeItem<FilePath> rootTreeItem;
    TreeView<FilePath> treeView;
	public static final TitledPane PDFPane = new TitledPane("PDF",new Label("Show all PDF files available"));
	public static final TitledPane TXTPane = new TitledPane("TXT",new Label("Show all TXT files available"));
	public static final TitledPane DOCXPane = new TitledPane("DOCX",new Label("Show all DOCX files available"));
	public static final int AccodionSize = 70;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		treeView = new TreeView<FilePath>();
        TextField filter = new TextField();
        filter.textProperty().addListener((observable, oldValue, newValue) -> filterChanged(newValue));
        
		Button button = new Button();
		button.setText("Example");
		Button button2 = new Button();
		button2.setText("WTF");
		SplitPane SplitPane = new SplitPane();
		BorderPane ExplorerBorderPane = new BorderPane();
		SplitPane FileSplitPane = new SplitPane();
		FileSplitPane.setOrientation(Orientation.VERTICAL);
		Accordion FileAccordion = new Accordion();
		FileAccordion.setMaxHeight(AccodionSize);		
		SplitPane.getItems().addAll(FileSplitPane,DisplayPane);
			DisplayPane.getChildren().add(button);
			DisplayPane.setMinWidth(600);
			FileSplitPane.getItems().addAll(ExplorerBorderPane,FileAccordion);
				ExplorerBorderPane.setMinHeight(300);
				ExplorerBorderPane.setMinWidth(200);
				ToolBar ToolBar = new ToolBar();
				ToolBar.setStyle("-fx-background-color: #047ff8");
				Label Label = new Label("Filter");
				Label.setTextFill(Color.WHITE);
				ToolBar.getItems().addAll(Label,filter);
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
