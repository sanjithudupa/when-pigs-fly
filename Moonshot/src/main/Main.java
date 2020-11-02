package main;

import engine.GameEngine;
import engine.GameLogic;

public class Main {
    public static void main(String[] args) {
        try {
            boolean vSync = true;
            GameLogic gameLogic = new Game();
            GameEngine gameEng = new GameEngine("GAME",
                810, 1440, vSync, gameLogic);
            gameEng.start();
        } catch (Exception excp) {
            excp.printStackTrace();
            System.exit(-1);
        }
    }
}