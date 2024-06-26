package com.magistrados.graph.screens.match.models;

import com.magistrados.graph.components.buttons.DefaultButton;
import com.magistrados.graph.notification.Notifications;
import com.magistrados.graph.screens.match.models.enums.TeamID;
import com.magistrados.models.GameSet;
import com.magistrados.models.MatchPlayerStats;
import com.magistrados.models.Partida;
import com.magistrados.services.GameSetService;
import com.magistrados.services.MatchPlayerStatsService;
import com.magistrados.services.PartidaService;
import com.magistrados.services.TimeService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.function.Consumer;

public abstract class MatchManager extends JFrame {

    private static final int POINTS_MAX = 25;
    private final PartidaService partidaService;
    private final TimeService timeService;
    private final MatchPlayerStatsService statsService;
    private final GameSetService gameSetService;
    private Partida partida;
    private int setNum = 1;
    private GameSet currentSet;
    private MatchJob matchJob;

    public MatchManager(PartidaService partidaService, TimeService timeService, MatchPlayerStatsService statsService, GameSetService gameSetService) {
        this.partidaService = partidaService;
        this.timeService = timeService;
        this.statsService = statsService;
        this.gameSetService = gameSetService;
    }

    public void iniciarPartida(Long timeAId, Long timeBId, String local, LocalDateTime dataHora) {
        this.partida = new Partida();
        partida.setLocal(local);
        partida.setDateTime(dataHora);
        partida.setTimeA(this.timeService.buscarTimeLazy(timeAId));
        partida.setTimeB(this.timeService.buscarTimeLazy(timeBId));
        this.partidaService.salvarPartida(partida);

        partida.setQuantidadeSets(3);
        partida.getGameSets().forEach(this.gameSetService::salvarSet);
        partida.getSetByOrder(1).setIniciado(true);

        partida.getJogadores().forEach(jogador -> {
            final MatchPlayerStats playerStats = new MatchPlayerStats();
            playerStats.setPlayerId(jogador.getId());
            playerStats.setPartidaId(partida.getId());

            jogador.addMatchPlayerStats(playerStats);
            this.statsService.saveMatchPlayerStats(playerStats);
        });

        this.currentSet = partida.getSetByOrder(this.setNum);
        matchJob = new MatchJob(partidaService, partida);
        matchJob.watch();

        // init components of frame

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                matchJob.stopWatch();
                if (!partida.isFinalizada())
                    cancelarPartida();
            }
        });
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(new BorderLayout());
        initComponents();
        //empilha tudo
        this.pack();
        this.setLocationRelativeTo(null);
    }

    public abstract void initComponents();

    public abstract void travarTodosBotoes();

    public abstract void destravarTodosBotoes();

    public abstract DefaultButton getBtnFinalizarSet();

    public abstract void resetBtnFinalizarSet();

    public Partida getPartida() {
        return partida;
    }

    public GameSet getCurrentSet() {
        return currentSet;
    }

    public void cancelarPartida() {
        this.partidaService.deletarPartida(partida);
    }

    public boolean nextSetAndCheckGameWon(TeamID teamID) {
        this.currentSet.finalizarSet(teamID.getId());
        switch (teamID) {
            case TIME_A -> this.partida.addSetsA();
            case TIME_B -> this.partida.addSetsB();
        }
        this.currentSet = partida.getSetByOrder(++this.setNum);
        if (this.currentSet != null && Math.abs(this.partida.getSetsA() - this.partida.getSetsB()) < 2) {
            this.currentSet.setIniciado(true);
            return true;
        }
        return false;
    }

    public void gameSetWon(TeamID teamID) {
        if (!this.nextSetAndCheckGameWon(teamID)) {
            this.travarTodosBotoes();
            this.partida.finalizarPartida(teamID);
            matchJob.stopWatch();

            Notifications.info("Partida sendo finalizada...");

            new Thread(() -> {
                this.partidaService.salvarPartida(partida);
                Notifications.info("Partida finalizada com sucesso");
            }).start();
        }
    }

    public Optional<Runnable> adicionarPontoTimeA() {
        currentSet.addPontosTimeA();
        if (this.checkWonSet(TeamID.TIME_A))
            return Optional.of(() -> this.gameSetWon(TeamID.TIME_A));
        return Optional.empty();
    }

    public Optional<Runnable> adicionarPontoTimeB() {
        currentSet.addPontosTimeB();
        if (this.checkWonSet(TeamID.TIME_B))
            return Optional.of(() -> this.gameSetWon(TeamID.TIME_B));
        return Optional.empty();
    }

    public void removerPontoTimeA() {
        this.currentSet.remPontosTimeA();
        if (this.getBtnFinalizarSet() != null && !checkWonSet(TeamID.TIME_B)) {
            this.resetBtnFinalizarSet();
        } else if (checkWonSet(TeamID.TIME_B)) {
            this.getConfirmarAvancarSet().accept(() -> this.gameSetWon(TeamID.TIME_B));
        }
    }

    public void removerPontoTimeB() {
        this.currentSet.remPontosTimeB();
        if (this.getBtnFinalizarSet() != null && !checkWonSet(TeamID.TIME_A)) {
            this.resetBtnFinalizarSet();
        } else if (checkWonSet(TeamID.TIME_A)) {
            this.getConfirmarAvancarSet().accept(() -> this.gameSetWon(TeamID.TIME_A));
        }
    }

    public abstract Consumer<Runnable> getConfirmarAvancarSet();

    public int pointsDifference() {
        return Math.abs(this.currentSet.getPontosTimeA() - this.currentSet.getPontosTimeB());
    }

    private boolean checkWonSet(TeamID teamID) {
        switch (teamID) {
            case TIME_A -> {
                return !checkPostpone() && currentSet.getPontosTimeA() >= POINTS_MAX;
            }
            case TIME_B -> {
                return !checkPostpone() && currentSet.getPontosTimeB() >= POINTS_MAX;
            }
            default -> {
                return false;
            }
        }
    }

    private boolean checkPostpone() {
        return currentSet.getPontosTimeB() >= POINTS_MAX - 1 &&
                currentSet.getPontosTimeA() >= POINTS_MAX - 1 &&
                pointsDifference() != 2;
    }
}
