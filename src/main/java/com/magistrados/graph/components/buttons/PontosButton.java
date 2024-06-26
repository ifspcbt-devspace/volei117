package com.magistrados.graph.components.buttons;

import com.magistrados.graph.screens.match.MatchManagerFrame;
import com.magistrados.graph.screens.match.models.enums.TeamID;
import com.magistrados.models.MatchPlayerStats;

import javax.swing.*;
import java.awt.*;

public class PontosButton extends DefaultButton {

    private static final Font btnFont = new Font("Roboto", Font.BOLD, 14);

    public PontosButton(String text, MatchPlayerStats stats, OperatorButton operatorButton,
                        MatchManagerFrame matchManagerFrame, TeamID teamID) {
        super(text, null);
        this.setFont(btnFont);
        this.setMinimumSize(new Dimension(100, 45));
        this.setMaximumSize(new Dimension(100, 45));
        this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        this.addActionListener(e -> {
            if (operatorButton.isSomando()) {
                stats.addPonto();
                switch (teamID) {
                    case TIME_A ->
                            matchManagerFrame.adicionarPontoTimeA().ifPresent(matchManagerFrame.getConfirmarAvancarSet());
                    case TIME_B ->
                            matchManagerFrame.adicionarPontoTimeB().ifPresent(matchManagerFrame.getConfirmarAvancarSet());
                }
                matchManagerFrame.updateSetsComponent();
            } else stats.removePonto();
            this.setText("(" + stats.getQuantidadePontos() + ")");
        });
    }

    public PontosButton(String text, Font font, MatchPlayerStats stats, OperatorButton operatorButton) {
        super(text, null, font);
        this.setMinimumSize(new Dimension(100, 45));
        this.setMaximumSize(new Dimension(100, 45));
        this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        this.addActionListener(e -> {
            if (operatorButton.isSomando())
                stats.addPonto();
            else stats.removePonto();
            this.setText("(" + stats.getQuantidadePontos() + ")");
        });
    }
}
