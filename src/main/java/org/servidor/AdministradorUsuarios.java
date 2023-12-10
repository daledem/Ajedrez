package org.servidor;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.net.Socket;

public class AdministradorUsuarios extends Thread{

    private Socket s;

    public AdministradorUsuarios(Socket s){
        this.s = s;
    }


    public void run(){
        String mensaje;
        String [] resultado;
        String ip;
        String nombre;
        String contrasena;
        int elo;

        try (BufferedReader br = new BufferedReader(new InputStreamReader(this.s.getInputStream())); PrintStream ps = new PrintStream(this.s.getOutputStream())){

            mensaje = br.readLine();

            if (mensaje.startsWith("CHANGE ")){ // Si el mensaje debera ser de la forma 'CHANGE <nombre> <puntos>
                                                // donde puntos es la cantidad de puntos ganados o perdidos
                                                // Despues de esto el servidor cierra la conexion con el cliente
                resultado = mensaje.split(" ");

                if (resultado.length == 3){
                    elo = Integer.parseInt(resultado[2]);
                    nombre = resultado[1];
                    if (sumarElo(nombre,elo)){
                        this.mensajeOk(ps);
                    }else {
                        this.mensajeError(ps);
                    }

                }else {
                    this.mensajeError(ps);
                }
            }else if(mensaje.startsWith("GET ")){
                resultado = mensaje.split(" "); // El mensaje debe ser de la forma GET <nombre> <contraseña>
                                                      // El servidor comprobara si el usuario esta registrado y si la contraseña es correcta

                if (resultado.length == 3){
                    nombre = resultado[1];
                    contrasena = resultado[2];
                    ip = this.s.getInetAddress().toString();

                    if(this.buscarUsuario(nombre,contrasena,ip)) {
                        this.mensajeOk(ps);
                        this.gestionarConsulta(nombre,ps,br);
                    }else {
                        this.mensajeError(ps);
                    }

                }else {
                    mensajeError(ps);
                }

            } else if (mensaje.startsWith("PUT ")) {
                resultado = mensaje.split(" "); // El mensaje debe ser de la forma PUT <nombre> <contraseña>
                                                      // Registra a un nuevo usuario si <nombre> no esta ya registrado

                if (resultado.length == 3){
                    nombre = resultado[1];
                    contrasena = resultado[2];
                    ip = this.s.getInetAddress().toString();

                    if (!buscarUsuario(nombre,ip)){
                        if(aniadirUsuario(nombre,contrasena,ip)){
                            this.mensajeOk(ps);
                            gestionarConsulta(nombre,ps,br);
                        }
                    }else {
                        mensajeError(ps);
                    }

                }else {
                    mensajeError(ps);
                }
            }else{
                mensajeError(ps);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (this.s != null){
                    this.s.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }

        }


    }

    // Se encarga de gestionar las consultas de los usuarios registrados
    public void gestionarConsulta(String nombre,PrintStream ps,BufferedReader br) throws IOException{
        String mensaje;
        String [] partesMensaje;
        String nombreRival;
        int puerto;

        enviarMesas(ps);

        mensaje = br.readLine();

        while(!mensaje.equals("EXIT")) { // El servidor se comunica con el cliente mientras no reciba en mensaje EXIT

            if (mensaje.startsWith("TABLE ")) {
                // Si desea crear una nueva, el usuario enviara
                // TABLE <puerto>, siendo <puerto> el puerto en el
                // que el usuario hosteara la mesa

                partesMensaje = mensaje.split(" ");
                if (partesMensaje.length == 2) {
                    puerto = Integer.parseInt(partesMensaje[1]);
                    aniadirMesa(nombre, puerto);
                    mensajeOk(ps);
                } else {
                    mensajeError(ps);
                }

            } else if (mensaje.equals("NTABLE")) {
                // Si desea indicar que ya no se encuentra esperando un ruval, el usuario enviara
                // NTABLE y el servidor se encargara de borrar la mesa
                if(quitarMesa(nombre)){
                    mensajeOk(ps);
                }else {
                    mensajeError(ps);
                }

            } else if(mensaje.equals("UPDATE")){
                // Si el usuario quiere que se actualizaen las mesas disponibles
                // enviara UPDATE y el servidor le contestara con la lista de mesas actuales

                enviarMesas(ps);
            } else if (mensaje.startsWith("GETTABLE ")) {
                partesMensaje = mensaje.split(" ");

                if(partesMensaje.length == 2){
                    nombreRival = partesMensaje[1];
                    if(!enviarUsuarioEnMesa(nombreRival,ps)){
                        mensajeError(ps);
                    }
                }

            }else{
                this.mensajeError(ps);
            }

            mensaje = br.readLine();
        }

    }


    // Se encarga de actualizar el elo del cliente
    private boolean sumarElo(String nombre,int variacionElo){
        DocumentBuilderFactory dbf = null;
        DocumentBuilder db = null;
        Document doc = null;
        int nuevoElo;
        boolean sumado = false;

        try {
            dbf = DocumentBuilderFactory.newInstance();
            db = dbf.newDocumentBuilder();
            doc = db.parse(new File("src/main/xml/Usuarios.xml"));

            NodeList usuarios = doc.getElementsByTagName("usuario");

            int i = 0;
            int lenght = usuarios.getLength();
            while (i < lenght && !sumado ){
                Element usuario = (Element) usuarios.item(i);

                if (usuario.getAttributeNode("nombre").getValue().equals(nombre)){
                    NodeList elementosUsuario = usuario.getElementsByTagName("elo");
                    Element elo = (Element) elementosUsuario.item(0);

                    nuevoElo = Integer.parseInt(elo.getNodeValue()) + variacionElo;
                    elo.setNodeValue(String.valueOf(nuevoElo));
                    sumado = true;
                }
                i++;
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("src/main/xml/Usuarios.xml"));
            transformer.transform(source, result);

        } catch (ParserConfigurationException | IOException | SAXException | TransformerException e) {
            e.printStackTrace();
        }

        return sumado;
    }

    private void mensajeError(PrintStream ps){
        ps.println("ERROR");
    }

    private void mensajeOk(PrintStream ps){
        ps.println("OK");
    }

    // Busca el usuario por el nombre y devuelve true si existe y la contraseña es correcta
    // Devuelve false en caso contrario
    private boolean buscarUsuario(String nombre, String contrasena,String ip){
        DocumentBuilderFactory dbf = null;
        DocumentBuilder db = null;
        Document doc = null;
        boolean encontrado = false;
        boolean contrasenaCorrecta = false;

        try {
            dbf = DocumentBuilderFactory.newInstance();
            db = dbf.newDocumentBuilder();
            doc = db.parse(new File("src/main/xml/Usuarios.xml"));

            NodeList usuarios = doc.getElementsByTagName("usuario");

            int i = 0;
            int lenght = usuarios.getLength();
            while (i<lenght && !encontrado ){
                Element usuario = (Element) usuarios.item(i);

                if (usuario.getAttributeNode("nombre").getValue().equals(nombre)){

                    if(usuario.getAttributeNode("contrasena").getValue().equals(contrasena)){
                        if (!usuario.getAttributeNode("ip").getValue().equals(ip)){
                            usuario.getAttributeNode("ip").setValue(ip);
                        }
                        contrasenaCorrecta = true;
                    }

                    encontrado = true;
                }
                i++;
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("src/main/xml/Usuarios.xml"));
            transformer.transform(source, result);



        } catch (ParserConfigurationException | IOException | SAXException | TransformerException e) {
            e.printStackTrace();
        }

        return contrasenaCorrecta;
    }

    // Busca usuario por el nombre y devulve true si esta registrado
    // Devuelve false en caso contrario
    public boolean buscarUsuario(String nombre,String ip){
        DocumentBuilderFactory dbf = null;
        DocumentBuilder db = null;
        Document doc = null;
        boolean encontrado = false;

        try {
            dbf = DocumentBuilderFactory.newInstance();
            db = dbf.newDocumentBuilder();
            doc = db.parse(new File("src/main/xml/Usuarios.xml"));

            NodeList usuarios = doc.getElementsByTagName("usuario");

            int i = 0;
            int lenght = usuarios.getLength();
            while (i<lenght && !encontrado ){
                Element usuario = (Element) usuarios.item(i);

                if (usuario.getAttributeNode("nombre").getValue().equals(nombre)){
                    if (!usuario.getAttributeNode("ip").getValue().equals(ip)){
                        usuario.getAttributeNode("ip").setValue(ip);
                    }

                    encontrado = true;
                }
                i++;
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("src/main/xml/Usuarios.xml"));
            transformer.transform(source, result);



        } catch (ParserConfigurationException | IOException | SAXException | TransformerException e) {
            e.printStackTrace();
        }

        return encontrado;

    }

    // Envia el nombre de todos los usuarios que esten esperando en una mesa junto a su elo
    private void enviarMesas(PrintStream ps){
        DocumentBuilderFactory dbf = null;
        DocumentBuilder db = null;
        Document doc = null;
        String elo;
        String nombre;
        boolean esperandoEnMesa;

        try {
            dbf = DocumentBuilderFactory.newInstance();
            db = dbf.newDocumentBuilder();
            doc = db.parse(new File("src/main/xml/Usuarios.xml"));

            NodeList usuarios = doc.getElementsByTagName("usuario");

            int i = 0;
            int lenght = usuarios.getLength();
            while (i<lenght){
                Element usuario = (Element) usuarios.item(i);
                NodeList mesa = usuario.getElementsByTagName("*");

                esperandoEnMesa = (mesa.getLength() == 2);

                if (esperandoEnMesa){
                    elo = mesa.item(0).getFirstChild().getNodeValue();
                    nombre = usuario.getAttributeNode("nombre").getValue();

                    ps.println(nombre + " " + elo);
                }
                i++;
            }

            ps.println("FIN");
            ps.flush();
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
    }

    // Registra a un usuario y devuelve true si el nombre no se encuentra ya registrado
    // Devuelve false en caso contrario
    private boolean aniadirUsuario(String nombre,String contrasena, String ip){
        DocumentBuilderFactory dbf = null;
        DocumentBuilder db = null;
        Document doc = null;
        boolean creado = false;
        Element nuevoUsuario = null;
        Element elo = null;
        Text txtElo = null;

        try {
            dbf = DocumentBuilderFactory.newInstance();
            db = dbf.newDocumentBuilder();
            doc = db.parse(new File("src/main/xml/Usuarios.xml"));

            nuevoUsuario = doc.createElement("usuario");
            nuevoUsuario.setAttribute("nombre",nombre);
            nuevoUsuario.setAttribute("contrasena",contrasena);
            nuevoUsuario.setAttribute("ip",ip);

            elo = doc.createElement("elo");
            txtElo = doc.createTextNode("850");
            elo.appendChild(txtElo);

            nuevoUsuario.appendChild(elo);

            doc.getDocumentElement().appendChild(nuevoUsuario);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("src/main/xml/Usuarios.xml"));
            transformer.transform(source, result);

            creado = true;

        } catch (ParserConfigurationException | IOException | SAXException | TransformerException e) {
            e.printStackTrace();
        }

        return creado;
    }


    // Envia la ip y el puerto en el que se encuentra el usuario nobre y devuelve true
    // Si el nombre no esta registrado o el usuario no se encuentra en una mesa disponible devuelve false
    private boolean enviarUsuarioEnMesa(String nombre, PrintStream ps){
        DocumentBuilderFactory dbf = null;
        DocumentBuilder db = null;
        Document doc = null;
        String puerto;
        String nombreXml;
        String ip;
        boolean enviado = false;
        boolean esperandoEnMesa;

        try {
            dbf = DocumentBuilderFactory.newInstance();
            db = dbf.newDocumentBuilder();
            doc = db.parse(new File("src/main/xml/Usuarios.xml"));

            NodeList usuarios = doc.getElementsByTagName("usuario");

            int i = 0;
            int lenght = usuarios.getLength();
            while (i<lenght && !enviado){
                Element usuario = (Element) usuarios.item(i);
                NodeList mesa = usuario.getElementsByTagName("*");

                nombreXml = usuario.getAttributeNode("nombre").getValue();
                esperandoEnMesa = mesa.getLength() == 2;

                if (esperandoEnMesa && nombreXml.equals(nombre)){
                    puerto = usuario.getAttributeNode("puerto").getValue();
                    ip = usuario.getAttributeNode("ip").getValue().substring(1);

                    ps.println(ip + " " + puerto);
                    ps.flush();
                    enviado = true;
                }
                i++;
            }

        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
        return enviado;
    }

    // Si nombre esta registrado, devuelve true y establece puerto como el puerto
    // Devuelve false en caso contrario
    private boolean aniadirMesa(String nombre,int puerto){
        DocumentBuilderFactory dbf = null;
        DocumentBuilder db = null;
        Document doc = null;
        Element esperandoEnMesa;
        boolean aniadida = false;

        try {
            dbf = DocumentBuilderFactory.newInstance();
            db = dbf.newDocumentBuilder();
            doc = db.parse(new File("src/main/xml/Usuarios.xml"));

            NodeList usuarios = doc.getElementsByTagName("usuario");

            int i = 0;
            int lenght = usuarios.getLength();
            while (i<lenght && !aniadida){
                Element usuario = (Element) usuarios.item(i);
                NodeList elementosUsuario = usuario.getElementsByTagName("*");

                if (usuario.getAttributeNode("nombre").getValue().equals(nombre) && elementosUsuario.getLength() == 1){
                    usuario.setAttribute("puerto",String.valueOf(puerto));

                    esperandoEnMesa = doc.createElement("esperandoEnMesa");
                    usuario.appendChild(esperandoEnMesa);

                    aniadida = true;
                }
                i++;
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("src/main/xml/Usuarios.xml"));
            transformer.transform(source, result);

        } catch (ParserConfigurationException | IOException | SAXException | TransformerException e) {
            e.printStackTrace();
        }
        return aniadida;
    }

    // Devuelve true si el nombre esta registrado, se encuentra en una mesa disponible y esta se ha eliminado correctamente
    // Devuelve false en caso contrario
    private boolean quitarMesa(String nombre){
        DocumentBuilderFactory dbf = null;
        DocumentBuilder db = null;
        Document doc = null;
        boolean eliminada = false;

        try {
            dbf = DocumentBuilderFactory.newInstance();
            db = dbf.newDocumentBuilder();
            doc = db.parse(new File("src/main/xml/Usuarios.xml"));

            NodeList usuarios = doc.getElementsByTagName("usuario");

            int i = 0;
            int lenght = usuarios.getLength();
            while (i<lenght && !eliminada){
                Element usuario = (Element) usuarios.item(i);
                NodeList elementosUsuario = usuario.getElementsByTagName("*");

                if (usuario.getAttributeNode("nombre").getValue().equals(nombre) && elementosUsuario.getLength() == 2){
                    usuario.removeAttribute("puerto");

                    usuario.removeChild(usuario.getLastChild());

                    eliminada = true;
                }
                i++;
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("src/main/xml/Usuarios.xml"));
            transformer.transform(source, result);

        } catch (ParserConfigurationException | IOException | SAXException | TransformerException e) {
            e.printStackTrace();
        }
        return eliminada;
    }

}
