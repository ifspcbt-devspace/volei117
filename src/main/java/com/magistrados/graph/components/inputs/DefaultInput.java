package com.magistrados.graph.components.inputs;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;


public class DefaultInput extends JTextField {
    private final Border defaultBorder = BorderFactory.createLineBorder(new Color(74, 76, 80));

    public DefaultInput(int sizeX, int sizeY, String... text) {
        Font font = new Font("Roboto", Font.PLAIN, 20);
        this.setCaretColor(new Color(0, 178,255));
        this.setText(String.join(" ", text));
        this.setFont(font);
        this.setMinimumSize(new Dimension(sizeX, sizeY));
        this.setMaximumSize(new Dimension(sizeX, sizeY));
        this.setForeground(Color.WHITE);
        this.setBackground(new Color(74, 76, 80));
        this.setOpaque(true);
        this.setBorder(defaultBorder);

        this.setCursor(new Cursor(Cursor.TEXT_CURSOR));
        // Salva a borda padrão no construtor
        addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                setBorder(BorderFactory.createLineBorder(new Color(91, 155, 213))); // Define a cor da borda ao ganhar o foco
            }

            @Override
            public void focusLost(FocusEvent e) {
                setBorder(defaultBorder); // Restaura a borda padrão ao perder o foco
            }
        });


    }

}
