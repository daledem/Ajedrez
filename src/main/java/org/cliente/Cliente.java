package org.cliente;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Cliente {

    public static void main(String[] args) throws UnknownHostException {
        System.out.println(InetAddress.getLocalHost().toString());
        Registrarse reg = new Registrarse();
        reg.showInterface();

        /*
        String nombre;
        String mensaje;
        boolean ganado;

        try (Socket s = new Socket(InetAddress.getLocalHost(),55555); PrintStream ps = new PrintStream(s.getOutputStream()); BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream())); Scanner scan = new Scanner(System.in)){
            System.out.println("Introduzca su nombre");
            nombre = scan.nextLine();


            mensaje = nombre + " " + InetAddress.getLocalHost().getHostAddress();
            ps.println(mensaje);
            ps.flush();

            mensaje = br.readLine();

            while (!mensaje.equals("FIN")) {
                System.out.println(mensaje);
                mensaje = br.readLine();
            }

            do {
                System.out.println("Introduzca contra quien desea jugar");
                System.out.println("Si desea crear una mesa introduzca NUEVAMESA");
                System.out.println("Si desea actuaizar la lista introduzca ACTUALIZAR");
                mensaje = scan.nextLine();

            }while (mensaje.equals("ACTUALIZAR"));

            if (mensaje.equals("NUEVAMESA")){
                mensaje = mensaje + " 66666";
                ps.println(mensaje);
                ps.flush();
                ServerSocket ss = new ServerSocket(6666);
                Socket s2 = ss.accept();
                ganado = jugar(s2);
            }else {
                ps.println(mensaje);
                ps.flush();

                System.out.println(br.readLine());
                //Por implementar

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

         */

    }

    private static boolean jugar(Socket s){
        return false;
    }

}
