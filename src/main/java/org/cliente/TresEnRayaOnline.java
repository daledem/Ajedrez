package org.cliente;

import org.TresEnRaya.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class TresEnRayaOnline {
    private Socket s;
    private ServerSocket ss;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private Game game;
    private String turno;
    private String nombre;
    private String contrasena;
    private JFrame frame;
    private JButton button11;
    private JPanel panel1;
    private JButton button21;
    private JButton button31;
    private JButton button22;
    private JButton button32;
    private JButton button12;
    private JButton button13;
    private JButton button23;
    private JButton button33;

    public TresEnRayaOnline(Socket s, ServerSocket ss, Game game, String nombre, String contrasena) {
        this.s = s;
        this.ss = ss;
        this.game = game;
        this.nombre = nombre;
        this.contrasena = contrasena;
        this.frame = new JFrame("Tres en raya");

        try {
            this.oos = new ObjectOutputStream(this.s.getOutputStream());
            this.ois = new ObjectInputStream(this.s.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                try {
                    if (oos != null) {
                        oos.close();
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                try {
                    if (ois != null) {
                        ois.close();
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                try {
                    if (s != null) {
                        s.close();
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                try {
                    if (ss != null) {
                        ss.close();
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            }

        });

        try {
            if (this.game == null) {
                this.turno = "0";
                this.desactivarBotones();
                this.game = (Game) ois.readObject();
                this.activarBotones();
                this.actualizarBotones();
            } else {
                this.turno = "X";
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        button11.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (TresEnRayaOnline.this.button11.isEnabled()) {
                    TresEnRayaOnline.this.gestionarBoton(1, 1);
                }
            }
        });

        button12.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (TresEnRayaOnline.this.button12.isEnabled()) {
                    TresEnRayaOnline.this.gestionarBoton(1, 2);
                }
            }
        });

        button13.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (TresEnRayaOnline.this.button13.isEnabled()) {
                    TresEnRayaOnline.this.gestionarBoton(1, 3);
                }
            }
        });

        button21.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (TresEnRayaOnline.this.button21.isEnabled()) {
                    TresEnRayaOnline.this.gestionarBoton(2, 1);
                }
            }
        });

        button22.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (TresEnRayaOnline.this.button22.isEnabled()) {
                    TresEnRayaOnline.this.gestionarBoton(2, 2);
                }
            }
        });

        button23.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (TresEnRayaOnline.this.button23.isEnabled()) {
                    TresEnRayaOnline.this.gestionarBoton(2, 3);
                }
            }
        });

        button31.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (TresEnRayaOnline.this.button31.isEnabled()) {
                    TresEnRayaOnline.this.gestionarBoton(3, 1);
                }
            }
        });

        button32.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (TresEnRayaOnline.this.button32.isEnabled()) {
                    TresEnRayaOnline.this.gestionarBoton(3, 2);
                }
            }
        });

        button33.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (TresEnRayaOnline.this.button33.isEnabled()) {
                    TresEnRayaOnline.this.gestionarBoton(3, 3);
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

    public void gestionarBoton(int colum, int row) {
        try {
            game.changeTurn();
            if (game.markSquare(colum - 1, row - 1)) {
                oos.writeObject(game);
                oos.flush();
                if (game.finish()) {
                    terminarJuego();
                    return;
                }
                this.desactivarBotones();
                this.game = (Game) ois.readObject();
                this.activarBotones();
                this.actualizarBotones();
                if (game.finish()) {
                    terminarJuego();
                }
            } else {
                game.changeTurn();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void actualizarBotones() {
        this.button11.setText(game.getTable()[0][0].getType());
        this.button12.setText(game.getTable()[0][1].getType());
        this.button13.setText(game.getTable()[0][2].getType());
        this.button21.setText(game.getTable()[1][0].getType());
        this.button22.setText(game.getTable()[1][1].getType());
        this.button23.setText(game.getTable()[1][2].getType());
        this.button31.setText(game.getTable()[2][0].getType());
        this.button32.setText(game.getTable()[2][1].getType());
        this.button33.setText(game.getTable()[2][2].getType());
        this.frame.pack();
    }

    public void activarBotones() {
        this.button11.setEnabled(true);
        this.button12.setEnabled(true);
        this.button13.setEnabled(true);
        this.button21.setEnabled(true);
        this.button22.setEnabled(true);
        this.button23.setEnabled(true);
        this.button31.setEnabled(true);
        this.button32.setEnabled(true);
        this.button33.setEnabled(true);
    }

    public void desactivarBotones() {
        this.button11.setEnabled(false);
        this.button12.setEnabled(false);
        this.button13.setEnabled(false);
        this.button21.setEnabled(false);
        this.button22.setEnabled(false);
        this.button23.setEnabled(false);
        this.button31.setEnabled(false);
        this.button32.setEnabled(false);
        this.button33.setEnabled(false);
    }

    public void terminarJuego() {
        try (Socket s = new Socket("192.168.56.1", 55555); PrintStream ps = new PrintStream(s.getOutputStream())) {
            int puntos;

            if (this.game.getWinner().equals(" ")) {
                puntos = 0;
            } else if (this.game.getWinner().equals(this.turno)) {
                puntos = 15;
            } else {
                puntos = -15;
            }

            ps.println("CHANGE " + nombre + " " + puntos);
            ps.flush();

            Socket s2 = new Socket("192.168.56.1", 55555);
            PrintStream ps2 = new PrintStream(s2.getOutputStream());
            BufferedReader br2 = new BufferedReader(new InputStreamReader(s2.getInputStream()));

            ps2.println("GET " + nombre + " " + contrasena);
            ps2.flush();
            if (br2.readLine().equals("OK")) {
                Lobby lobby = new Lobby(nombre, contrasena, br2, ps2);
                lobby.showInterface();
                this.frame.dispose();
            } else {
                try {
                    if (s2 != null) {
                        s2.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
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
        panel1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(3, 3, new Insets(10, 10, 10, 10), -1, -1));
        button11 = new JButton();
        button11.setText(" ");
        panel1.add(button11, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        button21 = new JButton();
        button21.setText(" ");
        panel1.add(button21, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        button22 = new JButton();
        button22.setText(" ");
        panel1.add(button22, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        button32 = new JButton();
        button32.setText(" ");
        panel1.add(button32, new com.intellij.uiDesigner.core.GridConstraints(1, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        button12 = new JButton();
        button12.setText(" ");
        panel1.add(button12, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        button13 = new JButton();
        button13.setText(" ");
        panel1.add(button13, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        button23 = new JButton();
        button23.setText(" ");
        panel1.add(button23, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        button33 = new JButton();
        button33.setText(" ");
        panel1.add(button33, new com.intellij.uiDesigner.core.GridConstraints(2, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        button31 = new JButton();
        button31.setText(" ");
        panel1.add(button31, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel1;
    }
}
