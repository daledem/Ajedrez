package org.cliente;

import org.TresEnRaya.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Lobby {
    private String nombre;
    private String contrasena;
    private BufferedReader br;
    private PrintStream ps;
    private JFrame frame;
    private JPanel panel1;
    private JComboBox comboBox1;
    private JButton conectarseButton;
    private JButton crearMesaButton;
    private JTextField puertoTextField;
    private JButton actualizarMesasButton;
    private JButton cancelarButton;
    private JLabel errorConectionLabel;
    private JLabel errorTableLabel;

    public Lobby(String nombre, String contrasena, BufferedReader br, PrintStream ps) {
        this.nombre = nombre;
        this.contrasena = contrasena;
        this.br = br;
        this.ps = ps;
        frame = new JFrame("Lobby");

        String mesa;

        try {
            mesa = br.readLine();

            while (!mesa.equals("FIN")) {
                this.comboBox1.addItem(mesa);
                mesa = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            this.errorConectionLabel.setVisible(true);
            Lobby.this.frame.pack();
        }

        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuPrincipal mp = new MenuPrincipal();
                mp.showInterface();
                Lobby.this.frame.dispose();
            }
        });
        actualizarMesasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Lobby.this.ps.println("UPDATE");
                ps.flush();

                try {
                    String mesa = br.readLine();

                    while (!mesa.equals("FIN")) {
                        Lobby.this.comboBox1.addItem(mesa);
                        mesa = br.readLine();
                    }

                    Lobby.this.errorConectionLabel.setVisible(false);
                    Lobby.this.frame.pack();
                } catch (IOException exc) {
                    exc.printStackTrace();
                    Lobby.this.errorConectionLabel.setVisible(true);
                    Lobby.this.frame.pack();
                }
            }
        });
        puertoTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                Lobby.this.errorTableLabel.setVisible(false);
                Lobby.this.frame.pack();
            }
        });
        conectarseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuario = (String) Lobby.this.comboBox1.getSelectedItem();
                ps.println("GETTABLE " + usuario.split(" ")[0]);
                ps.flush();

                try {
                    String respuesta = br.readLine();

                    if (!respuesta.equals("ERROR")) {
                        String[] datosRespuesta = respuesta.split(" ");
                        Socket s = new Socket(datosRespuesta[0], Integer.parseInt(datosRespuesta[1]));
                        TresEnRayaOnline game = new TresEnRayaOnline(s, null, null, nombre, contrasena);
                        game.showInterface();
                        ps.println("EXIT");
                        ps.flush();
                        Lobby.this.frame.dispose();
                    } else {
                        Lobby.this.errorConectionLabel.setVisible(true);
                        Lobby.this.frame.pack();
                    }

                } catch (IOException ex) {
                    ex.printStackTrace();
                    Lobby.this.errorConectionLabel.setVisible(true);
                    Lobby.this.frame.pack();
                }

            }
        });
        crearMesaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (esPuertoValido(Lobby.this.puertoTextField)) {
                    int puerto = Integer.parseInt(Lobby.this.puertoTextField.getText());
                    ps.println("TABLE " + puerto);
                    ps.flush();
                    try {
                        ServerSocket ss = new ServerSocket(puerto);
                        Socket s = ss.accept();
                        TresEnRayaOnline game = new TresEnRayaOnline(s, ss, new Game(), nombre, contrasena);
                        game.showInterface();
                        ps.println("NTABLE");
                        ps.println("EXIT");
                        ps.flush();
                        Lobby.this.frame.dispose();

                    } catch (IOException ex) {
                        ex.printStackTrace();
                        Lobby.this.errorTableLabel.setVisible(true);
                        Lobby.this.frame.pack();
                        ps.println("NTABLE");
                        ps.flush();
                    }
                } else {
                    Lobby.this.errorTableLabel.setVisible(true);
                    Lobby.this.frame.pack();
                }
            }
        });

        this.frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                if (ps != null) {
                    ps.println("EXIT");
                    ps.flush();
                    ps.close();
                }

                try {
                    if (br != null) {
                        br.close();
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            }

        });
    }

    public void showInterface() {
        frame.setContentPane(this.panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public static boolean esPuertoValido(JTextField puertoTextField) {
        if (puertoTextField == null) {
            return false;
        }
        try {
            int d = Integer.parseInt(puertoTextField.getText());
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panel1 = new JPanel();
        panel1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(3, 1, new Insets(10, 10, 10, 10), -1, -1));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel2, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        comboBox1 = new JComboBox();
        panel2.add(comboBox1, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        conectarseButton = new JButton();
        conectarseButton.setHorizontalAlignment(0);
        conectarseButton.setText("Conectarse");
        panel2.add(conectarseButton, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        errorConectionLabel = new JLabel();
        errorConectionLabel.setForeground(new Color(-1769216));
        errorConectionLabel.setText("ERROR");
        errorConectionLabel.setVisible(false);
        panel2.add(errorConectionLabel, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel3, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        crearMesaButton = new JButton();
        crearMesaButton.setText("Crear mesa");
        panel3.add(crearMesaButton, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        puertoTextField = new JTextField();
        puertoTextField.setText("5555");
        panel3.add(puertoTextField, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        errorTableLabel = new JLabel();
        errorTableLabel.setForeground(new Color(-1769216));
        errorTableLabel.setText("ERROR");
        errorTableLabel.setVisible(false);
        panel3.add(errorTableLabel, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel4, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        actualizarMesasButton = new JButton();
        actualizarMesasButton.setText("Actualizar mesas");
        panel4.add(actualizarMesasButton, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cancelarButton = new JButton();
        cancelarButton.setLabel("       Cancelar       ");
        cancelarButton.setText("       Cancelar       ");
        panel4.add(cancelarButton, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel1;
    }
}
