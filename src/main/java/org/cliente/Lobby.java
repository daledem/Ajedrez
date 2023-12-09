package org.cliente;

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

    public Lobby(String nombre, BufferedReader br, PrintStream ps){
        this.nombre = nombre;
        this.br = br;
        this.ps = ps;
        frame = new JFrame("Lobby");

        String mesa;

        try {
            mesa = br.readLine();

            while (!mesa.equals("FIN")){
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
                Registrarse reg = new Registrarse();
                reg.showInterface();
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

                    while (!mesa.equals("FIN")){
                        Lobby.this.comboBox1.add(new JLabel(mesa));
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
                    System.out.println(respuesta);

                    if(!respuesta.equals("ERROR")){
                        String [] datosRespuesta = respuesta.split(" ");
                        Socket s = new Socket(datosRespuesta[0],Integer.parseInt(datosRespuesta[1]));
                        /*
                        * Por hacer
                         */
                        ps.println("EXIT");
                        ps.flush();
                        Lobby.this.frame.dispose();
                    }else {
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
                System.out.println(Lobby.this.puertoTextField.getText());
                if(esPuertoValido(Lobby.this.puertoTextField)){
                    int puerto = Integer.parseInt(Lobby.this.puertoTextField.getText());
                    ps.println("TABLE " + puerto);
                    ps.flush();
                    try {
                        ServerSocket ss = new ServerSocket(puerto);
                        Socket s = ss.accept();

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
                }else {
                    Lobby.this.errorTableLabel.setVisible(true);
                    Lobby.this.frame.pack();
                }
            }
        });

        this.frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (ps != null){
                    ps.println("EXIT");
                    ps.flush();
                    ps.close();
                }

                try {
                    if(br != null){
                        br.close();
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
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

}
