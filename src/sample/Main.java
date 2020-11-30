package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;



public class Main extends Application {
    Stage window;
    private double xOffset=0;
    private double yOffset=0;
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        window=primaryStage;
        window.setTitle("Register");//titeln på rutan
        window.initStyle(StageStyle.UNDECORATED); //odekorerat window!
        root.setOnMousePressed(new EventHandler<MouseEvent>() {  // sätter x+y coordinater på skärmen när man klickar på rutan.
            @Override
            public void handle(MouseEvent mouseEvent) {
                xOffset = window.getX() - mouseEvent.getScreenX();
                yOffset= window.getY() - mouseEvent.getScreenY();
            }
        });
        root.setOnMouseDragged(new EventHandler<MouseEvent>() { //gör så att rutan flyttas dit man drar den.
            @Override
            public void handle(MouseEvent mouseEvent) {
                window.setX(mouseEvent.getScreenX() + xOffset);
                window.setY(mouseEvent.getScreenY() + yOffset);
            }
        });
        window.setScene(new Scene(root, 727, 378));

        window.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
