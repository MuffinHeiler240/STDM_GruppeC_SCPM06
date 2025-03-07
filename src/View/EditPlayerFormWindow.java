package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import Model.*;

public class EditPlayerFormWindow extends JFrame {
    private final JLabel playerIdField;
    private final JTextField nachnameField;
    private final JTextField vornameField;
    private final JTextField geburtsdatumField;
    private final JSpinner gespielteSpieleSpinner;
    private final JCheckBox gesperrtCheckBox;
    private final JTextField vereinsbeitrittField;
    private final JSpinner roteKartenSpinner;
    private final JSpinner gelbeKartenSpinner;
    private final JPanel additionalFieldsPanel;
    private final DataModel dataModel;
    private final Spieler player;
    private final SimpleDateFormat dateFormat;

    public EditPlayerFormWindow(DataModel dataModel, Spieler player) {
        this.dataModel = dataModel;
        this.player = player;
        this.dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        setTitle("Edit Player");
        setSize(400, 600);
        setLayout(new GridLayout(0, 2));

        playerIdField = new JLabel(String.valueOf(player.getPlayerId()));
        nachnameField = new JTextField(player.getNachname());
        vornameField = new JTextField(player.getVorname());
        geburtsdatumField = new JTextField(dateFormat.format(player.getGeburtsdatum()));
        gespielteSpieleSpinner = new JSpinner(new SpinnerNumberModel(player.getGespielteSpiele(), 0, Integer.MAX_VALUE, 1));
        gesperrtCheckBox = new JCheckBox();
        gesperrtCheckBox.setSelected(player.isGesperrt());
        vereinsbeitrittField = new JTextField(dateFormat.format(player.getVereinsbeitritt()));
        roteKartenSpinner = new JSpinner(new SpinnerNumberModel(player.getRoteKarten(), 0, Integer.MAX_VALUE, 1));
        gelbeKartenSpinner = new JSpinner(new SpinnerNumberModel(player.getGelbeKarten(), 0, Integer.MAX_VALUE, 1));

        add(new JLabel("Player ID:"));
        add(playerIdField);
        add(new JLabel("Nachname:"));
        add(nachnameField);
        add(new JLabel("Vorname:"));
        add(vornameField);
        add(new JLabel("Geburtsdatum (dd.MM.yyyy):"));
        add(geburtsdatumField);
        add(new JLabel("Gespielte Spiele:"));
        add(gespielteSpieleSpinner);
        add(new JLabel("Gesperrt:"));
        add(gesperrtCheckBox);
        add(new JLabel("Vereinsbeitritt (dd.MM.yyyy):"));
        add(vereinsbeitrittField);
        add(new JLabel("Rote Karten:"));
        add(roteKartenSpinner);
        add(new JLabel("Gelbe Karten:"));
        add(gelbeKartenSpinner);

        additionalFieldsPanel = new JPanel(new GridLayout(0, 2));
        add(additionalFieldsPanel);

        updateAdditionalFields();

        JButton applyButton = new JButton("Apply");
        applyButton.addActionListener(e -> applyChanges());
        add(applyButton);
    }

    private void updateAdditionalFields() {
        additionalFieldsPanel.removeAll();
        if (player instanceof Verteidiger verteidiger) {
            addAdditionalField("Geblockte Angriffe:", new JSpinner(new SpinnerNumberModel(verteidiger.getGeblockteAngriffe(), 0, Integer.MAX_VALUE, 1)));
            addAdditionalField("Gewonnene Zweikämpfe:", new JSpinner(new SpinnerNumberModel(verteidiger.getGewonneneZweikaempfe(), 0, Integer.MAX_VALUE, 1)));
            addAdditionalField("Passquote:", new JTextField(String.valueOf(verteidiger.getPassqoute())));
        } else if (player instanceof Stuermer stuermer) {
            addAdditionalField("Geschossene Tore:", new JSpinner(new SpinnerNumberModel(stuermer.getGeschosseneTore(), 0, Integer.MAX_VALUE, 1)));
            addAdditionalField("Schussgenauigkeit:", new JTextField(String.valueOf(stuermer.getSchussgenauigkeit())));
            addAdditionalField("Chancenverwertung:", new JTextField(String.valueOf(stuermer.getChancenverwertung())));
        } else if (player instanceof Mittelfeldspieler mittelfeldspieler) {
            addAdditionalField("Anzahl Vorlagen:", new JSpinner(new SpinnerNumberModel(mittelfeldspieler.getAnzahlVorlagen(), 0, Integer.MAX_VALUE, 1)));
            addAdditionalField("Tore:", new JSpinner(new SpinnerNumberModel(mittelfeldspieler.getTore(), 0, Integer.MAX_VALUE, 1)));
            addAdditionalField("Passquote:", new JTextField(String.valueOf(mittelfeldspieler.getPassquote())));
        } else if (player instanceof Torwart torwart) {
            addAdditionalField("Spiele ohne Gegentor:", new JSpinner(new SpinnerNumberModel(torwart.getSpieleOhneGegentor(), 0, Integer.MAX_VALUE, 1)));
            addAdditionalField("Gegentore:", new JSpinner(new SpinnerNumberModel(torwart.getGegentore(), 0, Integer.MAX_VALUE, 1)));
            addAdditionalField("Haltequote:", new JTextField(String.valueOf(torwart.getHaltequote())));
        }
        additionalFieldsPanel.revalidate();
        additionalFieldsPanel.repaint();
    }

    private void addAdditionalField(String label, JComponent component) {
        additionalFieldsPanel.add(new JLabel(label));
        additionalFieldsPanel.add(component);
    }

    private void applyChanges() {
        try {
            player.setNachname(nachnameField.getText());
            player.setVorname(vornameField.getText());
            player.setGeburtsdatum(dateFormat.parse(geburtsdatumField.getText()));
            player.setGespielteSpiele((int) gespielteSpieleSpinner.getValue());
            player.setGesperrt(gesperrtCheckBox.isSelected());
            player.setVereinsbeitritt(dateFormat.parse(vereinsbeitrittField.getText()));
            player.setRoteKarten((int) roteKartenSpinner.getValue());
            player.setGelbeKarten((int) gelbeKartenSpinner.getValue());

            switch (player) {
                case Verteidiger verteidiger -> {
                    verteidiger.setGeblockteAngriffe((int) ((JSpinner) additionalFieldsPanel.getComponent(1)).getValue());
                    verteidiger.setGewonneneZweikaempfe((int) ((JSpinner) additionalFieldsPanel.getComponent(3)).getValue());
                    verteidiger.setPassqoute(Double.parseDouble(((JTextField) additionalFieldsPanel.getComponent(5)).getText()));
                }
                case Stuermer stuermer -> {
                    stuermer.setGeschosseneTore((int) ((JSpinner) additionalFieldsPanel.getComponent(1)).getValue());
                    stuermer.setSchussgenauigkeit(Double.parseDouble(((JTextField) additionalFieldsPanel.getComponent(3)).getText()));
                    stuermer.setChancenverwertung(Double.parseDouble(((JTextField) additionalFieldsPanel.getComponent(5)).getText()));
                }
                case Mittelfeldspieler mittelfeldspieler -> {
                    mittelfeldspieler.setAnzahlVorlagen((int) ((JSpinner) additionalFieldsPanel.getComponent(1)).getValue());
                    mittelfeldspieler.setTore((int) ((JSpinner) additionalFieldsPanel.getComponent(3)).getValue());
                    mittelfeldspieler.setPassquote(Double.parseDouble(((JTextField) additionalFieldsPanel.getComponent(5)).getText()));
                }
                case Torwart torwart -> {
                    torwart.setSpieleOhneGegentor((int) ((JSpinner) additionalFieldsPanel.getComponent(1)).getValue());
                    torwart.setGegentore((int) ((JSpinner) additionalFieldsPanel.getComponent(3)).getValue());
                    torwart.setHaltequote(Double.parseDouble(((JTextField) additionalFieldsPanel.getComponent(5)).getText()));
                }
                default -> {
                }
            }

            dataModel.notifyListeners();
            JOptionPane.showMessageDialog(this, "Player updated successfully!");
            dispose();
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(this, "Please enter dates in the format dd.MM.yyyy.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Please fill out all fields correctly.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
