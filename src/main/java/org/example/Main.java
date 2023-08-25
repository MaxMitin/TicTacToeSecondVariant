package org.example;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {

        //System.out.println("Запускаем игру.");
        //System.out.println("Завершаем игру.");
        JFrame window = new JFrame("Игра Крестик-Нолик"); //главное окно
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // кнопка Х, закрывающая окно
        window.setSize(400, 400); //размер окна
        window.setLayout(new BorderLayout()); // менеджер компоновки
        window.setLocationRelativeTo(null); // чтобы окно было по центру экрана
        window.setVisible(true); // видимость окна
        TicTacToe game = new TicTacToe(); // создание объекта класса Игры
        window.add(game); // добавление объекта в окно
        //System.out.println("Конец");
    }
}