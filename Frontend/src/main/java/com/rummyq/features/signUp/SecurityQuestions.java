package com.rummyq.features.signUp;

public class SecurityQuestions {
    private static final String[] QUESTIONS = {
            "¿Cuál es el nombre del tu primera mascota?",
            "¿En qué ciudad naciste?",
            "¿Cuál es el apellido de soltera de tu madre?",
            "¿Cuál fue tu primer trabajo?",
            "¿Cuál es el nombre de tu mejor amigo de infancia?"
    };

    public static String[] getQuestions() {
        return QUESTIONS;
    }
}