package com.magistrados.graph.components.buttons;

import com.magistrados.graph.Colors;
import com.magistrados.graph.listeners.SmoothColorTransitionMouseListener;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionListener;

public class DefaultButton extends JButton {

    public final Color BTN_COLOR = new Color(0, 28, 61);
    public final Color BTN_HOVER_COLOR = new Color(17, 42, 93);
    private static final Font FONT = new Font("Inter", Font.BOLD, 20);

    public DefaultButton(String text, ActionListener listener) {
        this.setText(text);
        this.setFont(FONT);
        this.setMinimumSize(new Dimension(300, 100));
        this.setMaximumSize(new Dimension(300, 100));
        this.setForeground(Colors.TEXT_COLOR);
        this.setBackground(BTN_COLOR);
        this.setFocusPainted(false);
        this.setOpaque(true);
        this.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.addMouseListener(new SmoothColorTransitionMouseListener(this, BTN_HOVER_COLOR, BTN_COLOR));
        this.addActionListener(listener);
    }

    public DefaultButton(String text, ActionListener listener, Font font) {
        this.setText(text);
        this.setFont(font);
        this.setMinimumSize(new Dimension(300, 100));
        this.setMaximumSize(new Dimension(300, 100));
        this.setForeground(Colors.TEXT_COLOR);
        this.setBackground(BTN_COLOR);
        this.setFocusPainted(false);
        this.setOpaque(true);
        this.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.addMouseListener(new SmoothColorTransitionMouseListener(this, BTN_HOVER_COLOR, BTN_COLOR));
        this.addActionListener(listener);
    }

    public DefaultButton(String text, ActionListener listener, Font font, Dimension dimension) {
        this.setText(text);
        this.setFont(font);
        this.setMinimumSize(dimension);
        this.setMaximumSize(dimension);
        this.setForeground(Colors.TEXT_COLOR);
        this.setBackground(BTN_COLOR);
        this.setFocusPainted(false);
        this.setOpaque(true);
        this.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.addMouseListener(new SmoothColorTransitionMouseListener(this, BTN_HOVER_COLOR, BTN_COLOR));
        this.addActionListener(listener);
    }

    public DefaultButton(String text, ActionListener listener, Dimension dimension) {
        this.setText(text);
        this.setFont(FONT);
        this.setMinimumSize(dimension);
        this.setMaximumSize(dimension);
        this.setForeground(Colors.TEXT_COLOR);
        this.setBackground(BTN_COLOR);
        this.setFocusPainted(false);
        this.setOpaque(true);
        this.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.addMouseListener(new SmoothColorTransitionMouseListener(this, BTN_HOVER_COLOR, BTN_COLOR));
        this.addActionListener(listener);
    }

    public DefaultButton(String text, ActionListener listener, Font font, Dimension dimension, Border border) {
        this.setText(text);
        this.setFont(font);
        this.setMinimumSize(dimension);
        this.setMaximumSize(dimension);
        this.setForeground(Colors.TEXT_COLOR);
        this.setBackground(BTN_COLOR);
        this.setFocusPainted(false);
        this.setOpaque(true);
        this.setBorder(border);
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.addMouseListener(new SmoothColorTransitionMouseListener(this, BTN_HOVER_COLOR, BTN_COLOR));
        this.addActionListener(listener);
    }
}
