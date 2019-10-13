package Application;

import java.io.File;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToolBar;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {
	public static final Pane DisplayPane = new Pane();
	public static final TreeView<File> ExplorerPane = new TreeView<File>(new SimpleFileTreeItem(new File("C:\\")));
	public static final TitledPane PDFPane = new TitledPane("PDF",new Label("Show all PDF files available"));
	public static final TitledPane TXTPane = new TitledPane("TXT",new Label("Show all PDF files available"));
	public static final TitledPane DOCXPane = new TitledPane("DOCX",new Label("Show all PDF files available"));
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		Button button = new Button();
		button.setText("Example");
		Button button2 = new Button();
		button2.setText("WTF");
		SplitPane SplitPane = new SplitPane();
		BorderPane FileBorderPane = new BorderPane();
		BorderPane ExplorerBorderPane = new BorderPane();
		Accordion FileAccordion = new Accordion();
		FileBorderPane.setMinWidth(200);
		FileBorderPane.setMaxWidth(400);
		
		SplitPane.getItems().addAll(FileBorderPane,DisplayPane);
			DisplayPane.getChildren().add(button);
			DisplayPane.setMinHeight(600);
			DisplayPane.setMinWidth(800);
			DisplayPane.setMaxHeight(600);
			DisplayPane.setMaxWidth(1000);
			FileBorderPane.setTop(ExplorerBorderPane);
				ExplorerBorderPane.setMinHeight(300);
				ExplorerBorderPane.setMinWidth(200);
				ToolBar ToolBar = new ToolBar();
				ToolBar.setStyle("-fx-background-color: #047ff8");
				Label Label = new Label("Current Directory");
				Label.setTextFill(Color.WHITE);
				Label Label2 = new Label();
				ToolBar.getItems().addAll(Label,Label2);
				ExplorerBorderPane.setTop(ToolBar);
				ExplorerBorderPane.setCenter(ExplorerPane);
			FileBorderPane.setBottom(FileAccordion);
				FileAccordion.getPanes().add(PDFPane);
				FileAccordion.getPanes().add(TXTPane);
				FileAccordion.getPanes().add(DOCXPane);
				PDFPane.setContent(button);
		Scene primaryScene = new Scene(SplitPane, 1200, 600);
		
		primaryStage.setScene(primaryScene);
        primaryStage.setTitle("Text Finder v0.1");
        primaryStage.show();
	}

}
