package com.rummyq.core;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Diálogo modal que muestra las reglas básicas del RummyQ.
 * Se abre desde el botón "¿Cómo jugar?" de PantallaInicio.
 */
public class DialogReglas {

    private static final String[][] REGLAS = {
            { "1", "Cada jugador recibe 14 fichas al inicio de la partida." },
            { "2", "En tu turno, toma una ficha del montón o la descartada por el jugador anterior." },
            { "3", "Forma grupos (3+ fichas del mismo número, colores distintos) o escaleras (3+ consecutivas del mismo color)." },
            { "4", "El comodín ☆ puede sustituir cualquier ficha en un grupo o escalera." },
            { "5", "Gana el primero en colocar todas sus fichas en la mesa. ¡Di RummyQ!" }
    };

    private final Stage owner;

    public DialogReglas(Stage owner) {
        this.owner = owner;
    }

    public void mostrar() {
        Stage dialog = new Stage();
        dialog.initOwner(owner);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initStyle(StageStyle.UNDECORATED);
        dialog.setTitle("¿Cómo jugar?");

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
        Label titulo = new Label("¿Cómo jugar?");
        titulo.setFont(Font.font("Georgia", FontWeight.BOLD, 26));
        titulo.setTextFill(Color.web("#f0d080"));
        VBox.setMargin(titulo, new Insets(0, 0, 8, 0));

        // Reglas
        VBox listaReglas = new VBox(12);
        for (String[] regla : REGLAS) {
            listaReglas.getChildren().add(crearFilaRegla(regla[0], regla[1]));
        }

        // Botón cerrar
        Button btnCerrar = new Button("Entendido — ¡A jugar!");
        btnCerrar.setPrefWidth(280);
        btnCerrar.setPrefHeight(44);
        btnCerrar.setFont(Font.font("Georgia", FontWeight.BOLD, 13));
        btnCerrar.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #c9a84c, #8a6a20);" +
                        "-fx-text-fill: #1a0e00;" +
                        "-fx-background-radius: 6;" +
                        "-fx-cursor: hand;");
        btnCerrar.setOnAction(e -> dialog.close());
        VBox.setMargin(btnCerrar, new Insets(10, 0, 0, 0));

        contenido.getChildren().addAll(titulo, listaReglas, btnCerrar);

        Scene escena = new Scene(contenido, 480, 420);
        escena.setFill(Color.TRANSPARENT);
        dialog.setScene(escena);
        dialog.showAndWait();
    }

    private HBox crearFilaRegla(String numero, String texto) {
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
}
