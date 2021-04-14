package com.company;
import java.awt.GridLayout;
import javax.swing.*;
public class Text {
    Text() {
        JFrame frame = new JFrame("Demo");
        JTextArea textArea = new JTextArea("The text added here is just for demo. "+ "\nThis demonstrates the usage of JTextArea in Java. ");
        int begn = 0;
        int end = 10;
        textArea.replaceRange(null, begn, end);
        textArea.append("In this example we have deleted some text from the beginning."+ "\nWe have also appended some text.");
        frame.add(textArea);
        frame.setSize(550,300);
        frame.setLayout(new GridLayout(2, 2));
        frame.setVisible(true);
    }
    public static void main(String args[]) {
        new Text();
    }
}