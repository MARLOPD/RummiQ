package com.rummyq.features.passwordRecoveryScene;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class RecoverySceneFormInformation {
    public TextField emailField;
    public TextField answerField;
    public PasswordField newPasswordField;
    public PasswordField confirmPasswordField;
    public Label questionLabel;
    public VBox answerSection;
    public VBox newPasswordSection;

    public Label messageLabel;
    public Button btnAccion;
}