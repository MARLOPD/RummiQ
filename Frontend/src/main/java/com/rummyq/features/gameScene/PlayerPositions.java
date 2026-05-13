package com.rummyq.features.gameScene;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import com.rummyq.model.ScreenConfig;

public class PlayerPositions {
    private static final double screenWidth = ScreenConfig.getScreenWidth();
    private static final double screenHeight = ScreenConfig.getScreenHeight();

    public static void positionUserPanel(Pane panel, int position) {
        switch (position) {
            case 1:
                StackPane.setAlignment(panel, Pos.CENTER_LEFT);
                StackPane.setMargin(panel, new Insets(0, 0, 0, screenWidth * 0.065));
                break;
            case 2:
                StackPane.setAlignment(panel, Pos.CENTER_RIGHT);
                StackPane.setMargin(panel, new Insets(0, screenWidth * 0.052, 0, 0));
                break;
            case 3:
                StackPane.setAlignment(panel, Pos.TOP_CENTER);
                StackPane.setMargin(panel, new Insets(screenHeight * 0.065, 0, 0, 0));
                break;
            default:
                break;
        }
    }
}
