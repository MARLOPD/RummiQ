package com.rummyq.features.gameScene;

import com.rummyq.core.UIColors;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class GameTiles {

    private static final double TILE_WIDTH = 45;
    private static final double TILE_HEIGHT = 60;

    private static final double SCALE_FACTOR = 2;

    public static StackPane createTile(String number, Color color) {
        StackPane tile = new StackPane();
        tile.setPrefSize(TILE_WIDTH, TILE_HEIGHT);

        Rectangle body = new Rectangle(TILE_WIDTH, TILE_HEIGHT);
        body.setFill(UIColors.COLOR_CREMA);
        body.setArcWidth(8);
        body.setArcHeight(8);
        body.setStroke(Color.web("#d1c4a8"));

        DropShadow tileShadow = new DropShadow(3, Color.rgb(0, 0, 0, 0.5));
        body.setEffect(tileShadow);

        Label lblNumber = new Label(number);
        lblNumber.setFont(Font.font("Georgia", FontWeight.BOLD, 20));
        lblNumber.setTextFill(color);

        tile.getChildren().addAll(body, lblNumber);

        tile.setOnMouseEntered(e -> {
            tile.setTranslateY(-5);
            body.setStroke(UIColors.COLOR_ORO);
            body.setStrokeWidth(2);
        });
        tile.setOnMouseExited(e -> {
            tile.setTranslateY(0);
            body.setStroke(Color.web("#d1c4a8"));
            body.setStrokeWidth(1);
        });

        tile.setCursor(javafx.scene.Cursor.HAND);
        return tile;
    }

    private static final Image SECRET_TILE_TEXTURE = new Image(
            GameSceneIndividuals.class.getResourceAsStream("/assets/secreteTile.png"));

    /**
     * Crea una ficha oculta (el reverso) con la textura verde.
     */
    public static StackPane createSecretTile() {
        StackPane tile = new StackPane();
        tile.setMaxWidth(TILE_WIDTH * SCALE_FACTOR);
        tile.setMaxHeight(TILE_HEIGHT * SCALE_FACTOR);

        tile.setPrefWidth(TILE_WIDTH * SCALE_FACTOR);
        tile.setPrefHeight(TILE_HEIGHT * SCALE_FACTOR);

        Rectangle body = new Rectangle(TILE_WIDTH * SCALE_FACTOR, TILE_HEIGHT * SCALE_FACTOR);
        body.setArcWidth(8 * SCALE_FACTOR);
        body.setArcHeight(8 * SCALE_FACTOR);

        if (SECRET_TILE_TEXTURE != null && !SECRET_TILE_TEXTURE.isError()) {
            body.setFill(new ImagePattern(SECRET_TILE_TEXTURE));
        } else {
            body.setFill(Color.DARKGREEN);
        }

        body.setStroke(Color.WHITE);
        body.setStrokeWidth(2);

        DropShadow tileShadow = new DropShadow(3, Color.rgb(0, 0, 0, 0.6));
        tile.setEffect(tileShadow);
        tile.getChildren().add(body);

        tile.setOnMouseEntered(e -> {
            tile.setTranslateY(-5);
            body.setStroke(UIColors.COLOR_ORO);
            body.setStrokeWidth(2);
        });
        tile.setOnMouseExited(e -> {
            tile.setTranslateY(0);
            body.setStroke(Color.WHITE);
            body.setStrokeWidth(1);
        });

        tile.setCursor(javafx.scene.Cursor.HAND);
        return tile;
    }

    public static HBox secretTilesOnTheTable(int count) {
        HBox table = new HBox(-TILE_WIDTH * 1.8);
        table.setAlignment(Pos.CENTER);
        for (int i = count - 1; i >= 0; i--) {
            StackPane tile = createSecretTile();

            DropShadow shadow = new DropShadow();
            shadow.setRadius(4.0);
            shadow.setOffsetX(-2.0);
            shadow.setOffsetY(2.0);
            shadow.setColor(Color.rgb(0, 0, 0, 0.5));
            tile.setEffect(shadow);

            table.getChildren().add(tile);
        }
        table.setPrefSize(TILE_WIDTH * -1.85 * count, TILE_HEIGHT * -1.85 * count);
        table.setMaxSize(TILE_WIDTH * -1.85 * count, TILE_HEIGHT * -1.85 * count);
        return table;
    }
}
