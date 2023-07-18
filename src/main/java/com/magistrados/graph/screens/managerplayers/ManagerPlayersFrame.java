package com.magistrados.graph.screens.managerplayers;


import com.magistrados.api.validations.exceptions.ValidationException;
import com.magistrados.graph.buttons.DefaultButton;
import com.magistrados.graph.inputs.DefaultInput;
import com.magistrados.graph.labels.DefaultLabel;
import com.magistrados.internal.validators.create.CreatePlayerValidator;
import com.magistrados.internal.validators.edit.EditPlayerValidator;
import com.magistrados.internal.validators.find.FindPlayerValidator;
import com.magistrados.internal.validators.remove.RemovePlayerValidator;
import com.magistrados.models.Jogador;
import com.magistrados.models.create.CreatePlayer;
import com.magistrados.models.edit.EditPlayer;
import com.magistrados.models.find.FindPlayer;
import com.magistrados.models.remove.RemovePlayer;
import com.magistrados.services.JogadorService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ManagerPlayersFrame extends JFrame {

    private final Font font = new Font("Roboto", Font.BOLD, 20);
    private final JogadorService jogadorService;
    private JPanel inputPanel;
    private JPanel mainPanel;
    private JPanel buttonsPanel;
    private JPanel paddingPanel;
    private JPanel formPanel;
    private JPanel operationPanel;
    private JLabel labelIdJogador;
    private JLabel labelIdTime;
    private JLabel labelNome;
    private JLabel labelNumeroJogador;
    private JLabel labelBloqueiosFeitos;
    private JLabel labelDefesasFeitas;
    private JLabel labelSaquesFeitos;
    private JLabel labelPontosFeitos;
    private JTextField campoIdJogador;
    private JTextField campoIdTime;
    private JTextField campoNome;
    private JTextField campoNumeroJogador;
    private JTextField campoBloqueiosFeitos;
    private JTextField campoDefesasFeitas;
    private JTextField campoSaquesFeitos;
    private JTextField campoPontosFeitos;
    private GroupLayout layout;
    private GroupLayout.SequentialGroup hGroup;
    private GroupLayout.SequentialGroup vGroup;

    public ManagerPlayersFrame(JogadorService jogadorService) throws HeadlessException {
        super("Gerenciador de Jogadores");
        this.jogadorService = jogadorService;
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(new BorderLayout());
        this.initComponents();
        this.pack();
        this.setLocationRelativeTo(null);
    }

    public void initComponents() {
        //declarando Jlabels
        labelIdJogador = createLabel(font, "ID do Jogador(a):");
        labelIdTime = createLabel(font, "ID do Time:");
        labelNome = createLabel(font, "Nome:");
        labelNumeroJogador = createLabel(font, "Número do Jogador:");
        labelBloqueiosFeitos = createLabel(font, "Número de Bloqueios Feitos:");
        labelDefesasFeitas = createLabel(font, "Número de Defesas Feitas:");
        labelSaquesFeitos = createLabel(font, "Número de Saques Feitos:");
        labelPontosFeitos = createLabel(font, "Número de Pontos Feitos:");

        //declarando JTextField
        campoIdJogador = createInput(300, 40);
        campoIdTime = createInput(300, 40);
        campoNome = createInput(300, 40);
        campoNumeroJogador = createInput(300, 40);
        campoBloqueiosFeitos = createInput(300, 40, "0");
        campoDefesasFeitas = createInput(300, 40, "0");
        campoSaquesFeitos = createInput(300, 40, "0");
        campoPontosFeitos = createInput(300, 40, "0");


        // lado leste (botões)
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Criando um painel de preenchimento com EmptyBorder
        paddingPanel = new JPanel(new BorderLayout());
        paddingPanel.setBorder(BorderFactory.createEmptyBorder(50, 25, 50, 25));
        paddingPanel.setBackground(Color.decode("#171717"));

        // Form panel
        formPanel = new JPanel(new BorderLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 70));
        formPanel.setBackground(Color.decode("#171717"));

        // Operations panel
        operationPanel = new JPanel(new BorderLayout());
        operationPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        operationPanel.setBackground(Color.decode("#171717"));

        // Criando painel com box layout para ficar um botão debaixo do outro
        buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
        buttonsPanel.setBackground(Color.decode("#171717"));

        // Criando painel de inputs com group layout
        inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());
        inputPanel.setBackground(Color.decode("#171717"));

        // Configuração do GroupLayout
        layout = new GroupLayout(inputPanel);
        inputPanel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);


        // Configuração das horizontais
        hGroup = layout.createSequentialGroup();
        hGroup.addGroup(layout.createParallelGroup()
                .addComponent(labelIdJogador)
                .addComponent(labelIdTime)
                .addComponent(labelNome)
                .addComponent(labelNumeroJogador)
                .addComponent(labelBloqueiosFeitos)
                .addComponent(labelDefesasFeitas)
                .addComponent(labelSaquesFeitos)
                .addComponent(labelPontosFeitos));
        hGroup.addGroup(layout.createParallelGroup()
                .addComponent(campoIdJogador)
                .addComponent(campoIdTime)
                .addComponent(campoNome)
                .addComponent(campoNumeroJogador)
                .addComponent(campoBloqueiosFeitos)
                .addComponent(campoDefesasFeitas)
                .addComponent(campoSaquesFeitos)
                .addComponent(campoPontosFeitos));
        layout.setHorizontalGroup(hGroup);

        // Configuração das verticais
        vGroup = layout.createSequentialGroup();
        vGroup.addGap(40);
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(labelIdJogador)
                .addComponent(campoIdJogador));
        vGroup.addGap(20);
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(labelIdTime)
                .addComponent(campoIdTime));
        vGroup.addGap(20);
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(labelNome)
                .addComponent(campoNome));
        vGroup.addGap(20);
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(labelNumeroJogador)
                .addComponent(campoNumeroJogador));
        vGroup.addGap(20);
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(labelBloqueiosFeitos)
                .addComponent(campoBloqueiosFeitos));
        vGroup.addGap(20);
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(labelDefesasFeitas)
                .addComponent(campoDefesasFeitas));
        vGroup.addGap(20);
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(labelSaquesFeitos)
                .addComponent(campoSaquesFeitos));
        vGroup.addGap(20);
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(labelPontosFeitos)
                .addComponent(campoPontosFeitos));
        layout.setVerticalGroup(vGroup);

        // Criando botões CRUD
        this.createButton(buttonsPanel, "Adicionar Jogador(a)", this.adicionarJogadorListener(), false);
        this.createButton(buttonsPanel, "Visualizar Jogador(a)", e -> {
        }, true);
        this.createButton(buttonsPanel, "Editar Jogador(a)", this.editarJogadorListener(), true);
        this.createButton(buttonsPanel, "Remover Jogador(a)", this.removerJogadorListener(), true);
        this.createButton(buttonsPanel, "Limpar tudo", this.cleanFieldsListener(), true);

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

    private ActionListener cleanFieldsListener() {
        return e -> this.cleanFields();
    }

    private ActionListener editarJogadorListener() {
        return e -> {
            final EditPlayer editPlayer = new EditPlayer(
                    this.campoNome.getText(),
                    this.campoIdTime.getText(),
                    this.campoNumeroJogador.getText(),
                    this.campoBloqueiosFeitos.getText(),
                    this.campoSaquesFeitos.getText(),
                    this.campoDefesasFeitas.getText(),
                    this.campoPontosFeitos.getText()
            );

            try {
                new EditPlayerValidator().validate(editPlayer);
            } catch (ValidationException ex) {
                ex.printOnFile();
                return;
            }

            this.jogadorService.editarJogador(Long.valueOf(this.campoIdJogador.getText()), editPlayer);
        };
    }

    private ActionListener removerJogadorListener() {
        return e -> {
            final RemovePlayer removePlayer = new RemovePlayer(
                    this.campoIdJogador.getText(),
                    this.campoIdTime.getText(),
                    this.campoNumeroJogador.getText(),
                    this.campoNome.getText()
            );

            try {
                new RemovePlayerValidator().validate(removePlayer);
            } catch (ValidationException ex) {
                ex.printOnFile();
                return;
            }

            this.jogadorService.deletarJogador(removePlayer);
            this.cleanFields();
        };
    }

    private ActionListener adicionarJogadorListener() {
        return e -> {
            final CreatePlayer createPlayer = new CreatePlayer(
                    this.campoNome.getText(),
                    this.campoIdTime.getText(),
                    this.campoNumeroJogador.getText(),
                    this.campoBloqueiosFeitos.getText(),
                    this.campoSaquesFeitos.getText(),
                    this.campoDefesasFeitas.getText(),
                    this.campoPontosFeitos.getText()
            );
            try {
                new CreatePlayerValidator().validate(createPlayer);
            } catch (ValidationException ex) {
                ex.printOnFile();
                return;
            }

            final Jogador jogador = this.jogadorService.criarJogador(createPlayer);
            this.campoIdJogador.setText(jogador.getId().toString());
        };
    }

    private ActionListener buscarJogadorListener() {
        return e -> {
            final FindPlayer findPlayer = new FindPlayer(
                    this.campoIdJogador.getText(),
                    this.campoIdTime.getText(),
                    this.campoNome.getText(),
                    this.campoNumeroJogador.getText()
            );
            try {
                new FindPlayerValidator().validate(findPlayer);
            } catch (ValidationException ex) {
                ex.printOnFile();
                return;
            }

            final Jogador jogador = this.jogadorService.buscarJogador(findPlayer.id());
        };
    }

    public void cleanFields() {
        this.campoIdJogador.setText("");
        this.campoNome.setText("");
        this.campoIdTime.setText("");
        this.campoNumeroJogador.setText("");
        this.campoBloqueiosFeitos.setText("");
        this.campoSaquesFeitos.setText("");
        this.campoDefesasFeitas.setText("");
        this.campoPontosFeitos.setText("");
    }
}
