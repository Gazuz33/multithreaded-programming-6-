package com.company;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.Timer;
@SuppressWarnings("serial")
public class Field extends JPanel {
    // Флаг приостановленности движения
    private boolean paused;
    // Динамический список скачущих мячей
    private ArrayList<BouncingBall> balls = new ArrayList<>(10);
    // Класс таймер отвечает за регулярную генерацию событий ActionEvent
// При создании его экземпляра используется анонимный класс,
// реализующий интерфейс ActionListener
    private Timer repaintTimer = new Timer(10, new ActionListener() {
        public void actionPerformed(ActionEvent ev) {
// Задача обработчика события ActionEvent - перерисовка окна
            repaint();
        }
    });
    // Конструктор класса BouncingBall
    public Field() {
// Установить цвет заднего фона белым
        setBackground(Color.WHITE);
// Запустить таймер
        repaintTimer.start();
    }
    // Унаследованный от JPanel метод перерисовки компонента
    public void paintComponent(Graphics g) {
// Вызвать версию метода, унаследованную от предка
        super.paintComponent(g);
        Graphics2D canvas = (Graphics2D) g;
        canvas.drawString("Constructor",71,100);
        canvas.drawString("Teleport",1245,100);
        canvas.drawString("Destructor",1242,580);
// Последовательно запросить прорисовку от всех мячей из списка
        for (BouncingBall ball: balls) {
            ball.paint(canvas);
        }
        //Визуализация
        //Конструктор
        g.drawLine(60, 60, 140, 60);
        g.drawLine(60, 60, 60, 140);
        g.drawLine(60, 140, 140, 140);
        g.drawLine(140, 60, 140, 140);
        //Деструктор
        g.drawLine(getWidth() - 140, getHeight() - 140, getWidth() - 140, getHeight() - 60);
        g.drawLine(getWidth() - 140, getHeight() - 140, getWidth() - 60, getHeight() - 140);
        g.drawLine(getWidth() - 140, getHeight() - 60, getWidth() - 60, getHeight() - 60);
        g.drawLine(getWidth() - 60, getHeight() - 140, getWidth() - 60, getHeight() - 60);
        //Телепорт
        g.drawLine(getWidth() - 140, 60, getWidth() - 60, 60);
        g.drawLine(getWidth() - 140, 60, getWidth() - 140, 140);
        g.drawLine(getWidth() - 60, 60, getWidth() - 60, 140);
        g.drawLine(getWidth() - 140, 140, getWidth() - 60, 140);

    }

    // Метод добавления нового мяча в список
    public void addBall() {
//Заключается в добавлении в список нового экземпляра BouncingBall
// Всю инициализацию положения, скорости, размера, цвета
// BouncingBall выполняет сам в конструкторе
        balls.add(new BouncingBall(this));
    }
    public void addBall(int radius, int speed, Color color, double x, double y){
        balls.add(new BouncingBall(this, radius, speed, color, x, y));
    }
    public void addBall(int radius, int speed, Color color) {
        balls.add(new BouncingBall(this, radius, speed, color));
    }

    // Методы pause() и resume() синхронизированные, т.е. только один поток может
// одновременно быть внутри
    public synchronized void pause() {
// Включить режим паузы
        paused = true;
    }
    public synchronized void resume() {
// Выключить режим паузы
        paused = false;
// Будим все ожидающие продолжения потоки
        notifyAll();
    }
    // Синхронизированный метод проверки, может ли мяч двигаться
// (не включен ли режим паузы?)
    public synchronized void canMove(BouncingBall ball) throws InterruptedException {
        if (paused) {
// Если режим паузы включен, то поток, зашедший
// внутрь данного метода, засыпает
            wait();
        }
    }
}