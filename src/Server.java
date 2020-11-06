// Java implementation of  Server side
// It contains two classes : Server and ClientHandler
// Save file as Server.java

import java.io.*;
import java.util.*;
import java.net.*;

// Server class
public class Server
{



    public static void main(String[] args) throws IOException
    {
        final  List<Usuario> listUser = new ArrayList<Usuario>();
        final  List<Mensagem>listMsg = new ArrayList<Mensagem>();
        // server is listening on port 5056
        ServerSocket ss = new ServerSocket(10001);

        Scanner scn = new Scanner(System.in);

        // running infinite loop for getting
        // client request
        while (true)
        {
            Socket s = null;

            try {

                // socket object to receive incoming client requests

                s = ss.accept();

                System.out.println("inicializei o servidor");
                System.out.println("Um novo cliente está conectado: " + s);

                // obtaining input and out streams
                DataInputStream dis = new DataInputStream(s.getInputStream());
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());

                System.out.println("Atribuindo novo thread para este cliente");

                // create a new thread object
                Thread t = new ClientHandler(s, dis, dos, listUser, listMsg);

                //startando thread
                t.start();



            }
            catch (Exception e){
                s.close();
                e.printStackTrace();
            }
        }



    }
}






 /*   public void setClientes(Map<String, String> clientes) {
        this.clientes = clientes;
    }

    public void setClientesName(String name, String th ) {
        for (String key : clientes.values()) {//key mostra o chave do map
            if(key.trim().equals(th)){
                clientes.put(name, th);
                break;// break foi inserido para que o for pudess
                //rodar até encontrar o nome para substituição
            }
        }
        System.out.println( clientes.keySet()+ " - " + clientes.values());
    }


       clientes.put("", t.getName() );

       clientes.put( s.getInetAddress().toString() , t );
       System.out.println(clientes.entrySet());

      public Map<String, String> getClientes() {
        return ;
    }

        public Map<String, String> getCliconec() {
        String r = "";
        for (String key : clientes.values()) {//key mostra o chave do map
            r = r + clientes.values().toString();
        }
        return r ;
        //return (Map<String, String>) clientes.entrySet();
    }



  */