package org.cliente;

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
        String nombre;
        String mensaje;
        boolean ganado;

        try (Socket s = new Socket(InetAddress.getLocalHost(),55555); PrintStream ps = new PrintStream(s.getOutputStream()); BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream())); Scanner scan = new Scanner(System.in)){
            System.out.println("Introduzca su nombre");
            nombre = scan.nextLine();


            mensaje = nombre + " " + InetAddress.getLocalHost().getHostAddress();

            do {
                ps.println(mensaje);

                mensaje = br.readLine();

                while (!mensaje.equals("FIN")) {
                    System.out.println(mensaje);
                }


                System.out.println("Introduzca contra quien desea jugar");
                System.out.println("Si desea crear una mesa introduzca NUEVAMESA");
                System.out.println("Si desea actuaizar la lista introduzca ACTUALIZAR");
                mensaje = scan.nextLine();

            }while (mensaje.equals("ACTUALIZAR"));

            if (mensaje.equals("NUEVAMESA")){
                mensaje = mensaje + " 55555";
                ps.println(mensaje);
                ServerSocket ss = new ServerSocket(55555);
                Socket s2 = ss.accept();
                ganado = jugar(s2);
            }else {
                ps.println(mensaje);

                mensaje = br.readLine();


            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static boolean jugar(Socket s){

    }

}
