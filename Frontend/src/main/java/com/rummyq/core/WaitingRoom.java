package com.rummyq.core;

import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rummyq.model.User;
import com.rummyq.view.MainScene;
import com.rummyq.websocket.dto.RoomDTO;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class WaitingRoom {
    private final Stage owner;
    private static VBox contenido;
    private static VBox playersContainer;
    private static final Logger log = LoggerFactory.getLogger(WaitingRoom.class);

    public WaitingRoom(Stage owner, String adminName) {
        this.owner = owner;
        new RoomDTO(createRoomId());
        com.rummyq.websocket.GameWebSocketClient.getInstance().joinGame();
        RoomDTO.addPlayer(adminName);
    }

    public void mostrar(StackPane pane) {
        Stage dialog = new Stage();
        dialog.initOwner(owner);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initStyle(StageStyle.UNDECORATED);
        dialog.setTitle("Waiting");

        contenido = new VBox(16);
        contenido.setPadding(new Insets(36, 44, 36, 44));
        contenido.setAlignment(Pos.TOP_CENTER);
        contenido.setStyle(
                "-fx-background-color: linear-gradient(to bottom right, #1e3d28, #12261a);" +
                        "-fx-border-color: rgba(201,168,76,0.45);" +
                        "-fx-border-width: 1.5;" +
                        "-fx-border-radius: 14;" +
                        "-fx-background-radius: 14;");

        // Título
        Label titulo = new Label("Esperando Iniciar Partida");
        titulo.setFont(Font.font("Georgia", FontWeight.BOLD, 26));
        titulo.setTextFill(Color.web("#f0d080"));
        VBox.setMargin(titulo, new Insets(0, 0, 8, 0));

        Button btnCancelar = new Button("Cancelar");
        btnCancelar
                .setStyle("-fx-background-color: transparent; -fx-text-fill: rgba(201,168,76,0.7); -fx-cursor: hand;");
        btnCancelar.setOnAction(e -> {
            dialog.close();
            MainScene mainScene = new MainScene(owner);
            mainScene.showScreen();
        });

        Button btnCerrar = new Button("Iniciar Partida");
        btnCerrar.setPrefWidth(280);
        btnCerrar.setPrefHeight(44);
        btnCerrar.setFont(Font.font("Georgia", FontWeight.BOLD, 13));
        btnCerrar.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #c9a84c, #8a6a20);" +
                        "-fx-text-fill: #1a0e00;" +
                        "-fx-background-radius: 6;" +
                        "-fx-cursor: hand;");

        btnCerrar.setOnAction(e -> {
            if (RoomDTO.getPlayers().stream().distinct().count() < 2) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error");
                alert.setHeaderText("No hay suficientes jugadores");
                alert.setContentText("Deben haber al menos 2 jugadores para iniciar la partida.");
                alert.showAndWait();
                return;
            }
            com.rummyq.websocket.GameWebSocketClient.getInstance().startGame();
            dialog.close();
            Parent parent = pane.getParent();

            if (parent instanceof Pane _pane) {
                _pane.getChildren().remove(pane);
            }
        });
        VBox.setMargin(btnCerrar, new Insets(10, 0, 0, 0));
        Label roomId = new Label("ID: " + RoomDTO.getRoomId().substring(0, 6));
        roomId.setFont(Font.font("Georgia", 12));
        roomId.setTextFill(Color.web("#f5ead6", 0.85));
        roomId.setWrapText(true);
        roomId.setMaxWidth(360);

        Label copyIcon = new Label("⧉");
        copyIcon.setTextFill(Color.web("#a0b8a0"));
        copyIcon.setFont(Font.font(14));
        copyIcon.setStyle("-fx-cursor: hand;");
        copyIcon.setOnMouseClicked(e -> {
            javafx.scene.input.Clipboard clipboard = javafx.scene.input.Clipboard.getSystemClipboard();
            javafx.scene.input.ClipboardContent content = new javafx.scene.input.ClipboardContent();
            content.putString(RoomDTO.getRoomId());
            clipboard.setContent(content);
        });

        HBox idContainer = new HBox(8, roomId, copyIcon);
        idContainer.setAlignment(Pos.CENTER);

        playersContainer = new VBox(12);
        actualizarVBoxJugadores();

        HBox info = new HBox(14, idContainer, playersContainer);
        info.setAlignment(Pos.CENTER);
        contenido.getChildren().addAll(titulo, info, btnCerrar, btnCancelar);

        Scene escena = new Scene(contenido, 480, 420);
        escena.setFill(Color.TRANSPARENT);
        dialog.setScene(escena);
        dialog.show();
    }

    public static void updatePlayers() {
        log.debug("[WaitingRoom] Updating players. User: " + User.getEmail());
        log.info("Encolando actualización...");
        Platform.runLater(() -> {
            log.info("Ejecutando en JavaFX thread...");
            WaitingRoom.actualizarVBoxJugadores();
            log.info("Actualización completada");
        });
    }

    private static void actualizarVBoxJugadores() {
        if (playersContainer == null)
            return;
        playersContainer.getChildren().clear();
        int index = 1;
        for (String name : RoomDTO.getPlayers().stream().distinct().collect(Collectors.toList())) {
            playersContainer.getChildren().add(crearFilaRegla(String.valueOf(index), name));
            log.debug("[WaitingRoom] Player added: " + name);
            index++;
        }
    }

    private static HBox crearFilaRegla(String numero, String texto) {
        // Número en círculo dorado
        Label numLabel = new Label(numero);
        numLabel.setMinSize(26, 26);
        numLabel.setPrefSize(26, 26);
        numLabel.setAlignment(Pos.CENTER);
        numLabel.setFont(Font.font("Georgia", FontWeight.BOLD, 12));
        numLabel.setTextFill(Color.web("#1a0e00"));
        numLabel.setStyle(
                "-fx-background-color: #c9a84c;" +
                        "-fx-background-radius: 13;");

        // Texto de la regla
        Label textoLabel = new Label(texto);
        textoLabel.setFont(Font.font("Georgia", 13));
        textoLabel.setTextFill(Color.web("#f5ead6", 0.85));
        textoLabel.setWrapText(true);
        textoLabel.setMaxWidth(360);

        HBox fila = new HBox(14, numLabel, textoLabel);
        fila.setAlignment(Pos.TOP_LEFT);
        return fila;
    }

    private String createRoomId() {
        if (RoomDTO.getRoomId() != null) {
            return RoomDTO.getRoomId();
        }
        return UUID.randomUUID().toString();
    }
}
