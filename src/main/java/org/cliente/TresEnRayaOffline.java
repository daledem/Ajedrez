package org.cliente;

import org.TresEnRaya.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TresEnRayaOffline {
    private Game game;
    private String turno;
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

    public TresEnRayaOffline(Game game) {
        this.game = game;
        this.frame = new JFrame("Tres en raya");


        this.frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                MenuPrincipal mp = new MenuPrincipal();
                mp.showInterface();
                frame.dispose();
            }

        });

        button11.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TresEnRayaOffline.this.gestionarBoton(1, 1);
            }
        });

        button12.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TresEnRayaOffline.this.gestionarBoton(1, 2);
            }
        });

        button13.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TresEnRayaOffline.this.gestionarBoton(1, 3);
            }
        });

        button21.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TresEnRayaOffline.this.gestionarBoton(2, 1);
            }
        });

        button22.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TresEnRayaOffline.this.gestionarBoton(2, 2);
            }
        });

        button23.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TresEnRayaOffline.this.gestionarBoton(2, 3);
            }
        });

        button31.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TresEnRayaOffline.this.gestionarBoton(3, 1);
            }
        });

        button32.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TresEnRayaOffline.this.gestionarBoton(3, 2);
            }
        });

        button33.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                TresEnRayaOffline.this.gestionarBoton(3, 3);

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
        if (game.markSquare(colum - 1, row - 1)) {
            actualizarBotones();
            if (game.finish()) {
                JFrame ganador = new JFrame("Ganador");
                ganador.add(new JLabel("El ganador es " + game.getWinner()));
                ganador.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                ganador.pack();
                ganador.setVisible(true);
                this.frame.dispose();
            } else {
                game.changeTurn();
            }
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

}
