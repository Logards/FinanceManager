package com.example.financemanager;

import com.example.financemanager.repository.Database;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;

public class Application extends javafx.application.Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("expense-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Finance Manager");
            stage.setScene(scene);
            InputStream iconStream = Application.class.getResourceAsStream("/icons/icon.png");
            if (iconStream != null) {
                stage.getIcons().add(new Image(iconStream));
            } else {
                System.err.println("Impossible de charger l'icône : /icons/icon.png");
            }
            if (Database.isOK()) {
                stage.show();
            } else {
                System.err.println("Erreur de connexion à la base de données. Veuillez vérifier les logs.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Erreur lors du démarrage de l'application : " + e.getMessage());
        }
    }
}