// Java implementation for a client
// Save file as Client.java

import java.io.*;
import java.net.*;
import java.util.Scanner;

// Client class
public class Client
{
    public static void main(String[] args) throws IOException
    {
        try
        {

            Scanner scn = new Scanner(System.in);

            // getting localhost ip
            InetAddress ip = InetAddress.getByName("localhost");

            // establish the connection with server port 5056
            Socket s = new Socket(ip, 9998);

            // obtaining input and out streams
            DataInputStream dis = new DataInputStream(s.getInputStream());
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());

            // the following loop performs the exchange of
            // information between client and client handler

            System.out.println("Qual seu nome:"); //nome
            String receivd = scn.nextLine();
            dos.writeUTF(receivd);

            while (true)
            {
                System.out.println(dis.readUTF()); // tabela de opcoes
                String op = scn.nextLine();
                dos.writeUTF(op);// mandando op
                String opServ = dis.readUTF();
                switch (opServ){//recebendo op
                    case "1":
                        System.out.println("Digite sua mensagem:");
                        String msg = scn.nextLine();
                        dos.writeUTF(msg);//enviando mensagem para serve
                    break;
                    case "2":
                        System.out.println("Para quem deseja enviar a mensagem:");
                        String destinatario = scn.nextLine();
                        dos.writeUTF(destinatario);//enviando mensagem para serve

                        System.out.println(dis.readUTF());
                        String msgdes = scn.nextLine();
                        dos.writeUTF(msgdes);//enviando mensagem para serve
                       break;
                    case "3":
                        dos.writeUTF("Carregando Mensagens \n"); // Solicitando carga de mensagens


                    break;

                    default:
                        dos.writeUTF(opServ);//dis.readUTF()
                    break;
                }

                String received = dis.readUTF();// readUTF LE O RESULTADO DAS OPERAÇÕES SERVIDOR
                System.out.println("Resultado:  \n"+received);
                // If client sends exit,close this connection
                // and then break from the while loop
                if(op.equals("SAIR"))
                {
                    System.out.println("Closing this connection : " + s);
                    s.close();
                    System.out.println("Connection closed");
                    break;
                }

                // printing date or time as requested by client

            }

            // closing resources
            scn.close();
            dis.close();
            dos.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}