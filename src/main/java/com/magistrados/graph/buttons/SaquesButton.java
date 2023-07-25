package com.magistrados.graph.buttons;

import com.magistrados.models.MatchPlayerStats;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class SaquesButton extends DefaultButton {

    private static final Font btnFont = new Font("Roboto", Font.BOLD, 14);

    public SaquesButton(String text, MatchPlayerStats stats, OperatorButton operatorButton) {
        super(text, null);
        this.setFont(btnFont);
        this.setMinimumSize(new Dimension(100, 45));
        this.setMaximumSize(new Dimension(100, 45));
        this.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        this.addActionListener(e -> {
            if (operatorButton.isSomando())
                stats.addSaque();
            else stats.removeSaque();
            this.setText("(" + stats.getQuantidadeSaques() + ")");
        });
    }

    public SaquesButton(String text, Font font, MatchPlayerStats stats, OperatorButton operatorButton) {
        super(text, null, font);
        this.setMinimumSize(new Dimension(100, 45));
        this.setMaximumSize(new Dimension(100, 45));
        this.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        this.addActionListener(e -> {
            if (operatorButton.isSomando())
                stats.addSaque();
            else stats.removeSaque();
            this.setText("(" + stats.getQuantidadeSaques() + ")");
        });
    }

    public SaquesButton(String text, Font font, Dimension dimension, MatchPlayerStats stats, OperatorButton operatorButton) {
        super(text, null, font, dimension);
        this.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        this.addActionListener(e -> {
            if (operatorButton.isSomando())
                stats.addSaque();
            else stats.removeSaque();
            this.setText("(" + stats.getQuantidadeSaques() + ")");
        });
    }

    public SaquesButton(String text, Font font, Dimension dimension, Border border, MatchPlayerStats stats, OperatorButton operatorButton) {
        super(text, null, font, dimension, border);
        this.addActionListener(e -> {
            if (operatorButton.isSomando())
                stats.addSaque();
            else stats.removeSaque();
            this.setText("(" + stats.getQuantidadeSaques() + ")");
        });
    }
}
