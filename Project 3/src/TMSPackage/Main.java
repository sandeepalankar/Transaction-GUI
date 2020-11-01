package TMSPackage;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


/**
 * This class sets the stage, so to speak, for the Transaction Manager System. It creates the GUI from the 
 * TransactionManager.fxml file and adds it to the user's screen.
 * @author Sandeep Alankar, Graham Deubner
 *
 */
public class Main extends Application {
    /**
     * This method sets the stage, so to speak, for the Transaction Manager System.
     * It creates the GUI from the TransactionManager.fxml file and adds it to the
     * user's screen.
     */
    @Override
    public void start(Stage primaryStage) {
        try {
            BorderPane root = (BorderPane) FXMLLoader.load(getClass().getResource("TransactionManager.fxml"));
            Scene scene = new Scene(root,775,700);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Transaction Manager System");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Starts the application.
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
