package org.cliente;

import org.TresEnRaya.Game;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPrincipal {
    private JButton jugarOfflineButton;
    private JButton jugarOnlineButton;
    private JPanel panel1;
    private JFrame frame;

    public MenuPrincipal() {
        this.frame = new JFrame("Menu Principal");

        jugarOfflineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TresEnRayaOffline tresOff = new TresEnRayaOffline(new Game());
                tresOff.showInterface();
                MenuPrincipal.this.frame.dispose();
            }
        });
        jugarOnlineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Registrarse reg = new Registrarse();
                reg.showInterface();
                MenuPrincipal.this.frame.dispose();
            }
        });
    }

    public void showInterface(){
        frame.setContentPane(this.panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
