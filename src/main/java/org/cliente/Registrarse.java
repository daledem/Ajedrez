package org.cliente;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
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

                try (Socket s = new Socket(InetAddress.getLocalHost(),55555); PrintStream ps = new PrintStream(s.getOutputStream()); BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()))){

                    ps.println("GET " + Registrarse.this.textFieldNombre.getText() + " " + Registrarse.this.textFieldContrasena.getText());
                    ps.flush();

                    respuesta = br.readLine();

                    if(respuesta.equals("OK")){

                    }else {
                        Registrarse.this.errorLabel.setVisible(true);
                        Registrarse.this.frame.pack();
                    }

                } catch (IOException exc){
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

                try (Socket s = new Socket(InetAddress.getLocalHost(),55555); PrintStream ps = new PrintStream(s.getOutputStream()); BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()))){

                    ps.println("PUT " + Registrarse.this.textFieldNombre.getText() + " " + Registrarse.this.textFieldContrasena.getText());
                    ps.flush();

                    respuesta = br.readLine();

                    if(respuesta.equals("OK")){

                    }else {
                        Registrarse.this.errorLabel.setVisible(true);
                        Registrarse.this.frame.pack();
                    }

                } catch (IOException exc){
                    exc.printStackTrace();
                    Registrarse.this.errorLabel.setVisible(true);
                    Registrarse.this.frame.pack();
                }
            }
        });
    }

    public void showInterface(){
        frame.setContentPane(this.panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public void quitarMensajeError(){
        this.errorLabel.setVisible(false);
        this.frame.pack();
    }
}
