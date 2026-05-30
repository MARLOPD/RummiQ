package com.rummyq.core;

import com.rummyq.view.GameScene;
import com.rummyq.websocket.dto.RoomDTO;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class EnterRoom {

    private final Stage owner;

    public EnterRoom(Stage owner) {
        this.owner = owner;
    }

    public void mostrar() {
        Stage dialog = new Stage();
        dialog.initOwner(owner);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initStyle(StageStyle.UNDECORATED);
        dialog.setTitle("Entrar a una sala");

        VBox contenido = new VBox(16);
        contenido.setPadding(new Insets(36, 44, 36, 44));
        contenido.setAlignment(Pos.TOP_CENTER);
        contenido.setStyle(
                "-fx-background-color: linear-gradient(to bottom right, #1e3d28, #12261a);" +
                        "-fx-border-color: rgba(201,168,76,0.45);" +
                        "-fx-border-width: 1.5;" +
                        "-fx-border-radius: 14;" +
                        "-fx-background-radius: 14;");

        // Título
        Label titulo = new Label("Entrar a una sala");
        titulo.setFont(Font.font("Georgia", FontWeight.BOLD, 26));
        titulo.setTextFill(Color.web("#f0d080"));
        VBox.setMargin(titulo, new Insets(0, 0, 8, 0));

        // Input Field
        VBox idGroup = com.rummyq.features.signUp.SignUpSceneIndividuals.createField("ID de la sala", "Ej: 123456",
                false);
        TextField inputId = (TextField) idGroup.lookup("#campo");

        // Botón
        Button btnEntrar = new Button("Entrar");
        btnEntrar.setPrefWidth(280);
        btnEntrar.setPrefHeight(44);
        btnEntrar.setFont(Font.font("Georgia", FontWeight.BOLD, 13));
        btnEntrar.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #c9a84c, #8a6a20);" +
                        "-fx-text-fill: #1a0e00;" +
                        "-fx-background-radius: 6;" +
                        "-fx-cursor: hand;");

        btnEntrar.setOnAction(e -> {
            RoomDTO.setRoomId(inputId.getText());
            com.rummyq.websocket.GameWebSocketClient.getInstance().joinGame();
            dialog.close();
            GameScene gameScene = new GameScene(owner);
            gameScene.showScreen();
        });

        // Botón Cancelar
        Button btnCancelar = new Button("Cancelar");
        btnCancelar
                .setStyle("-fx-background-color: transparent; -fx-text-fill: rgba(201,168,76,0.7); -fx-cursor: hand;");
        btnCancelar.setOnAction(e -> dialog.close());

        VBox.setMargin(btnEntrar, new Insets(10, 0, 0, 0));

        contenido.getChildren().addAll(titulo, idGroup, btnEntrar, btnCancelar);

        Scene escena = new Scene(contenido, 480, 420);
        escena.setFill(Color.TRANSPARENT);
        dialog.setScene(escena);
        dialog.showAndWait();
    }
}
