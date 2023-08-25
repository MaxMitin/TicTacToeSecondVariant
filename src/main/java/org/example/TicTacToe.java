package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Random;

import static java.awt.Polygon.*;
import static java.lang.Thread.sleep;

public class TicTacToe extends JComponent {

    public static final int FIELD_EMPTY = 0; // пустое поле
    public static final int FIELD_X = 10; // поле с крестиком
    public static final int FIELD_O = 200; // поле с ноликом
    int[][] field; // объявление массива игрового поля

    boolean isXturn;


    boolean isCellValid(int x, int y) {
        if (x < 0 || y < 0 || x >= 5|| y >= 5)
            return false;
        return field[y][x] == FIELD_EMPTY;
    }


    public TicTacToe() {
        //Random random = new Random();
        enableEvents(AWTEvent.MOUSE_EVENT_MASK);
        field = new int[5][5]; // выделение памяти под массив при создании компонента
        initGame();
    }

    public void initGame() {
        for (int i = 0; i < 5; ++i)
            for (int j = 0; j < 5; ++j) {
                field[i][j] = FIELD_EMPTY;
            } // очищение массива путём заполнения его значением "0"
    }

    @Override
    protected void processMouseEvent(MouseEvent mouseEvent)
    {
        super.processMouseEvent(mouseEvent);
        if (mouseEvent.getButton() == MouseEvent.BUTTON1)
        {
            int x = mouseEvent.getX();
            int y = mouseEvent.getY();
            int i = (int) ((float) x / getWidth() * 5);
            int j = (int) ((float) y / getHeight() * 5);
            if (field[i][j] == FIELD_EMPTY)
            {field[i][j] = isXturn?FIELD_O:FIELD_X;

                    //isXturn = !isXturn;
                    //repaint();

                //repaint();
                //turnAI();
                //repaint();
                int res = checkState();
                if(res != 0)
                {
                    if(res == FIELD_O * 5)
                    {
                        JOptionPane.showMessageDialog(this, "Нолики выиграли!", "Победа!", JOptionPane.INFORMATION_MESSAGE);
                    }
                    else if (res == FIELD_X * 5)
                    {
                        JOptionPane.showMessageDialog(this, "Крестики выиграли!", "Победа!", JOptionPane.INFORMATION_MESSAGE);
                    }
                    //turnAI();
                    initGame();
                    repaint();
                }
                turnAI();
                repaint();

            }
        }
    }




    void turnAI() {
        Random random=new Random();
        int x, y;
        do {
            x = random.nextInt(5);
            y = random.nextInt(5);
        } while (!isCellValid(x, y));
        field[y][x] = FIELD_O;
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        graphics.clearRect(0, 0, getWidth(), getHeight()); // очищение холста
        drawGrid(graphics); //рисование сетки из линий
        drawXO(graphics); //рисование текущих крестиков и ноликов
        //graphics.setColor(Color.RED);
        //graphics.drawOval(10, 10, 100, 100);
    }

    void drawGrid(Graphics graphics) // размеры игрового поля
    {
        int w = getWidth();
        int h = getHeight();
        int dw = w / 5;
        int dh = h / 5;
        graphics.setColor(Color.BLACK); // цвет разделительных линий
        for (int i = 1; i < 5; i++)
        {
            graphics.drawLine(0, dh * i, w, dh * i); // горизонтальные линии
            graphics.drawLine(dw * i, 0, dw * i, h); // вертикальные линии
        }
    }

    void drawX(int i, int j, Graphics graphics)
    {
        graphics.setColor(Color.GREEN);

        int dw = getWidth() / 5;
        int dh = getHeight() / 5;
        int x = i * dw;
        int y = j * dh;

        graphics.drawLine(x, y, x + dw, y + dh);
        graphics.drawLine(x, y + dh, x + dw, y);
    }

    void drawO(int i, int j, Graphics graphics)
    {
        graphics.setColor(Color.BLUE);
        int dw = getWidth() / 5;
        int dh = getHeight() / 5;
        int x = i * dw;
        int y = j * dh;
        graphics.drawOval(x + 3 * dw / 100, y, dw * 9 / 10, dh);
    }

    void drawXO(Graphics graphics)
    {
        for (int i = 0; i < 5; ++i)
        {
            for (int j = 0; j < 5; ++j)
            {
                if (field[i][j] == FIELD_X){drawX(i, j, graphics);} //если в данной ячейке - крестик, то он рисуется
                else if (field[i][j] == FIELD_O){drawO(i, j, graphics);} // то же самое - для нолика
            }
        }
    }

    int checkState()
    { // проверка диагоналей
        int diag = 0;
        int diag2 = 0;
        for (int i = 0; i < 5; i++)
        {
            diag += field[i][i]; // сумма значений по диагонали от левого угла
            diag2 += field[i][4 - i]; // сумма значений по диагонали от правого угла
        }
        if (diag == FIELD_O * 5 || diag == FIELD_X * 5){return diag;} // если по диагонали стоят одни крестики или одни нолики, то выходим из метода
        if (diag2 == FIELD_O * 5 || diag2 == FIELD_X * 5){return diag2;} // то же самое для второй диагонали
        int check_i, check_j;
        boolean hasEmpty = false;
        for (int i = 0; i < 5; i++) // цикл по всем рядам
        {
            check_i = 0;
            check_j = 0;
            for (int j = 0; j < 5; j++)
            {// суммирование знаков в текущем ряду
                if (field[i][j] == 0)
                {
                    hasEmpty = true;
                }
                check_i += field[i][j];
                check_j += field[j][i];
                }
            // если выигрыш крестика или нолика, то - выход из игры
            if(check_i == FIELD_O * 5 || check_i == FIELD_X * 5){return check_i;}
            if (check_j == FIELD_O * 5 || check_j == FIELD_X * 5){return  check_j;}
            } if(hasEmpty) return 0; else return -1;
        }
    }

//https://vc.ru/dev/141885-poznaem-osnovy-java-i-sozdaem-krestiki-noliki
