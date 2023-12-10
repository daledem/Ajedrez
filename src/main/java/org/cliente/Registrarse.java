package org.cliente;

import javax.swing.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class Registrarse {
    private JFrame frame;
    private JPanel panel1;
    private JTextField textFieldNombre;
    private JTextField textFieldContrasena;
    private JButton iniciarSesionButton;
    private JButton registrarseButton;
    private JLabel errorLabel;


    public Registrarse() {
        frame = new JFrame("Registrarse");


        iniciarSesionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String respuesta;

                try {
                    String nombre = Registrarse.this.textFieldNombre.getText();
                    String contrasena = Registrarse.this.textFieldContrasena.getText();
                    Socket s = new Socket("192.168.56.1", 55555);
                    PrintStream ps = new PrintStream(s.getOutputStream());
                    BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
                    ps.println("GET " + nombre + " " + contrasena);
                    ps.flush();

                    respuesta = br.readLine();

                    if (respuesta.equals("OK")) {
                        Lobby lobby = new Lobby(nombre, contrasena, br, ps);
                        lobby.showInterface();
                        Registrarse.this.frame.dispose();
                    } else {
                        Registrarse.this.errorLabel.setVisible(true);
                        Registrarse.this.frame.pack();
                    }

                } catch (IOException exc) {
                    exc.printStackTrace();
                    Registrarse.this.errorLabel.setVisible(true);
                    Registrarse.this.frame.pack();
                }
            }
        });

        textFieldNombre.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                Registrarse.this.quitarMensajeError();
            }
        });
        textFieldContrasena.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                Registrarse.this.quitarMensajeError();
            }
        });
        registrarseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String respuesta;

                try {
                    String nombre = Registrarse.this.textFieldNombre.getText();
                    String contrasena = Registrarse.this.textFieldContrasena.getText();
                    Socket s = new Socket("192.168.56.1", 55555);
                    PrintStream ps = new PrintStream(s.getOutputStream());
                    BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
                    ps.println("PUT " + nombre + " " + contrasena);
                    ps.flush();

                    respuesta = br.readLine();

                    if (respuesta.equals("OK")) {
                        Lobby lobby = new Lobby(nombre, contrasena, br, ps);
                        lobby.showInterface();
                        Registrarse.this.frame.dispose();
                    } else {
                        Registrarse.this.errorLabel.setVisible(true);
                        Registrarse.this.frame.pack();
                    }

                } catch (IOException exc) {
                    exc.printStackTrace();
                    Registrarse.this.errorLabel.setVisible(true);
                    Registrarse.this.frame.pack();
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

    public void quitarMensajeError() {
        this.errorLabel.setVisible(false);
        this.frame.pack();
    }

}
