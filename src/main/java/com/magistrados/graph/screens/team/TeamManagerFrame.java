package com.magistrados.graph.screens.team;

import com.magistrados.graph.Colors;
import com.magistrados.internal.validations.exceptions.ValidationException;
import com.magistrados.exceptions.EntityNotFoundException;
import com.magistrados.graph.components.buttons.DefaultButton;
import com.magistrados.graph.components.inputs.DefaultInput;
import com.magistrados.graph.components.labels.DefaultLabel;
import com.magistrados.graph.notification.Notifications;
import com.magistrados.internal.validations.validators.create.CreateTeamValidator;
import com.magistrados.internal.validations.validators.edit.EditTeamValidator;
import com.magistrados.internal.validations.validators.find.FindTeamValidator;
import com.magistrados.internal.validations.validators.remove.RemoveTeamValidator;
import com.magistrados.models.Time;
import com.magistrados.models.create.CreateTeam;
import com.magistrados.models.edit.EditTeam;
import com.magistrados.models.find.FindTeam;
import com.magistrados.models.remove.RemoveTeam;
import com.magistrados.services.TimeService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class TeamManagerFrame extends JFrame {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(TeamManagerFrame.class);
    private final TimeService timeService;
    Font font = new Font("Roboto", Font.BOLD, 20);
    private boolean realizandoOperacao = false;
    private JPanel inputPanel;
    private JPanel buttonsPanel;
    private JPanel mainPanel;
    private JPanel paddingPanel;
    private JPanel formPanel;
    private JPanel operationPanel;
    private JTextField campoIdTime;
    private JTextField campoNomeTime;
    private JTextField campoVitorias;
    private JTextField campoDerrotas;
    private JLabel labelIdTime;
    private JLabel labelVitoriasTime;
    private JLabel labelVitorias;
    private JLabel labelDerrotas;

    public TeamManagerFrame(TimeService timeService) throws HeadlessException {
        super("Gerenciador de Times");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(new BorderLayout());
        this.timeService = timeService;
        this.initComponents();
        this.pack();
        this.setLocationRelativeTo(null);

    }

    public void initComponents() {
        //declarando Jlabels
        labelIdTime = createLabel(font, "ID do Time:");
        labelVitoriasTime = createLabel(font, "Nome do Time: ");
        labelVitorias = createLabel(font, "Número de Vitórias: ");
        labelDerrotas = createLabel(font, "Número de Derrotas:");

        //declarando JTextField
        campoIdTime = createInput(300, 40);
        campoNomeTime = createInput(300, 40);
        campoVitorias = createInput(300, 40, "0");
        campoDerrotas = createInput(300, 40, "0");

        // painel principal
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Criando um painel de preenchimento com EmptyBorder
        paddingPanel = new JPanel(new BorderLayout());
        paddingPanel.setBorder(BorderFactory.createEmptyBorder(50, 25, 50, 25));
        paddingPanel.setBackground(Colors.BACKGROUND_COLOR);

        // Form panel
        formPanel = new JPanel(new BorderLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 70));
        formPanel.setBackground(Colors.BACKGROUND_COLOR);

        // Operations panel
        operationPanel = new JPanel(new BorderLayout());
        operationPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        operationPanel.setBackground(Colors.BACKGROUND_COLOR);

        // Criando painel com box layout para ficar um botão debaixo do outro
        buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
        buttonsPanel.setBackground(Colors.BACKGROUND_COLOR);

        // Criando painel de inputs com group layout
        inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());
        inputPanel.setBackground(Colors.BACKGROUND_COLOR);

        // Criando botões CRUD
        this.createButton(buttonsPanel, "Adicionar Time", adicionarTimeListener(), false);
        this.createButton(buttonsPanel, "Visualizar Time", buscarTimeListener(), true);
        this.createButton(buttonsPanel, "Editar Time", editarTimeListener(), true);
        this.createButton(buttonsPanel, "Remover Time", removerTimeListener(), true);
        this.createButton(buttonsPanel, "Limpar tudo", this.cleanFieldsListener(), true);

        // Configuração do GroupLayout
        GroupLayout layout = new GroupLayout(inputPanel);
        inputPanel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        // Configuração das horizontais
        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
        hGroup.addGroup(layout.createParallelGroup()
                .addComponent(labelIdTime)
                .addComponent(labelVitoriasTime)
                .addComponent(labelVitorias)
                .addComponent(labelDerrotas));
        hGroup.addGroup(layout.createParallelGroup()
                .addComponent(campoIdTime)
                .addComponent(campoNomeTime)
                .addComponent(campoVitorias)
                .addComponent(campoDerrotas));
        layout.setHorizontalGroup(hGroup);

        // Configuração das verticais
        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
        vGroup.addGap(110);
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(labelIdTime)
                .addComponent(campoIdTime));
        vGroup.addGap(50);
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(labelVitoriasTime)
                .addComponent(campoNomeTime));
        vGroup.addGap(50);
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(labelVitorias)
                .addComponent(campoVitorias));
        vGroup.addGap(50);
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(labelDerrotas)
                .addComponent(campoDerrotas));
        layout.setVerticalGroup(vGroup);

        // os inputs ficam dentro do form panel
        formPanel.add(inputPanel, BorderLayout.CENTER);
        // os botões ficam dentro do operations panel
        operationPanel.add(buttonsPanel, BorderLayout.CENTER);

        // no padding panel ficam o formPanel e o buttonsPanel
        paddingPanel.add(operationPanel, BorderLayout.EAST);
        paddingPanel.add(formPanel, BorderLayout.WEST);

        // no painel principal fica o paddingPanel
        mainPanel.add(paddingPanel, BorderLayout.CENTER);

        this.add(mainPanel, BorderLayout.CENTER);

    }

    private void createButton(JPanel panel, String text, ActionListener listener, boolean space) {
        if (space) panel.add(Box.createRigidArea(new Dimension(0, 50)));
        final DefaultButton button = new DefaultButton(text, listener);
        panel.add(button);
    }

    private DefaultInput createInput(int sizeX, int sizeY, String... text) {
        return new DefaultInput(sizeX, sizeY, text);
    }

    private DefaultLabel createLabel(Font font, String text) {
        return new DefaultLabel(font, text);
    }

    private ActionListener removerTimeListener() {
        return e -> {
            if (realizandoOperacao) return;
            final RemoveTeam removeTeam = new RemoveTeam(
                    this.campoIdTime.getText(),
                    this.campoNomeTime.getText()
            );

            try {
                new RemoveTeamValidator().validate(removeTeam);
            } catch (ValidationException ex) {
                ex.printOnFile();
                return;
            }
            realizandoOperacao = true;

            new Thread(() -> {
                try {
                    this.timeService.deletarTime(removeTeam);
                    cleanFields();
                    realizandoOperacao = false;
                    Notifications.info("Time deletado com sucesso!");
                } catch (Exception ex) {
                    this.cleanFields();
                    realizandoOperacao = false;
                    Notifications.error("Não foi possível deletar o time.");
                }
            }).start();
        };
    }

    private ActionListener editarTimeListener() {
        return e -> {
            if (realizandoOperacao) return;
            final EditTeam editTeam = new EditTeam(
                    this.campoIdTime.getText(),
                    this.campoNomeTime.getText(),
                    this.campoVitorias.getText(),
                    this.campoDerrotas.getText()
            );

            try {
                new EditTeamValidator().validate(editTeam);
            } catch (ValidationException ex) {
                ex.printOnFile();
                return;
            }
            realizandoOperacao = true;

            Notifications.info("Uma requisição para edição do time foi enviada, aguarde.");

            new Thread(() -> {
                try {
                    final Time time = this.timeService.editarTime(editTeam);
                    setFields(time);
                    realizandoOperacao = false;
                    Notifications.info("Time editado com sucesso!");
                } catch (Exception ex) {
                    cleanFields();
                    realizandoOperacao = false;
                    Notifications.error("Não foi possível editar o time.");
                }
            }).start();
        };
    }

    private ActionListener adicionarTimeListener() {
        return e -> {
            if (realizandoOperacao) return;
            final CreateTeam createTeam = new CreateTeam(
                    this.campoNomeTime.getText(),
                    this.campoVitorias.getText(),
                    this.campoDerrotas.getText()
            );
            try {
                new CreateTeamValidator().validate(createTeam);
            } catch (ValidationException ex) {
                ex.printOnFile();
                return;
            }
            realizandoOperacao = true;

            Notifications.info("Uma requisição para registrar o time foi enviada, aguarde o ID.");

            new Thread(() -> {
                try {
                    final Time time = this.timeService.criarTime(createTeam);
                    this.campoIdTime.setText(time.getId().toString());
                    realizandoOperacao = false;
                } catch (Exception ex) {
                    cleanFields();
                    realizandoOperacao = false;
                    Notifications.error("Não foi possível criar o time.");
                }
            }).start();
        };
    }

    private ActionListener buscarTimeListener() {
        return e -> {
            if (realizandoOperacao) return;
            final FindTeam findTeam = new FindTeam(
                    this.campoIdTime.getText(),
                    this.campoNomeTime.getText()
            );
            try {
                new FindTeamValidator().validate(findTeam);
            } catch (ValidationException ex) {
                ex.printOnFile();
                return;
            }
            realizandoOperacao = true;

            Notifications.info("Uma requisição para buscar time foi enviada.");

            new Thread(() -> {
                try {
                    final Time time = this.timeService.buscarTime(findTeam);
                    this.setFields(time);
                    realizandoOperacao = false;
                } catch (Exception ex) {
                    cleanFields();
                    realizandoOperacao = false;
                    if (ex instanceof EntityNotFoundException) {
                        Notifications.error(ex.getMessage());
                        return;
                    }
                    Notifications.error("Não foi possível buscar o time.");
                    log.error("Não foi possível buscar o time", ex);
                }
            }).start();
        };
    }

    private ActionListener cleanFieldsListener() {
        return e -> cleanFields();
    }

    private void cleanFields() {
        this.campoIdTime.setText("");
        this.campoNomeTime.setText("");
        this.campoVitorias.setText("0");
        this.campoDerrotas.setText("0");
    }

    private void setFields(Time time) {
        this.campoIdTime.setText(time.getId().toString());
        this.campoNomeTime.setText(time.getNomeTime());
        this.campoVitorias.setText(time.getVitorias().toString());
        this.campoDerrotas.setText(time.getDerrotas().toString());
    }
}
