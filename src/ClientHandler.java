import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


// ClientHandler class
class ClientHandler extends Thread
{

    DateFormat fordate = new SimpleDateFormat("yyyy/MM/dd");
    DateFormat fortime = new SimpleDateFormat("hh:mm:ss");
    final DataInputStream dis;
    final DataOutputStream dos;
    final Socket s;
    private Usuario user = new Usuario();
    private List<Usuario> listUser;
    private String mensagem;
    private Usuario remetente;
    private Usuario destinatario;
    private Mensagem msg = new Mensagem(mensagem, remetente, destinatario);
    private List<Mensagem> listMsg;
    String userName;

    Server servCli = new Server();

    // Constructor
    public ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos, List<Usuario> listUser,List<Mensagem> listMsg)
    {
        this.s = s;
        this.dis = dis;
        this.dos = dos;
        this.listUser = listUser;
        this.listMsg = listMsg;
    }

    @Override
    public void run()
    {

        try{
            //salvando caracteristicas do usuario
            userName = dis.readUTF();
            user.setNome(userName);
            user.setClientHandler(this);
            Date date = new Date();
            user.setId(String.valueOf(date.getTime()));

            System.out.println(userName);
            System.out.println(getId());
            System.out.println(user.getClientHandler());
            listUser.add(this.user);


        } catch (IOException e) {
            e.printStackTrace();
        }


        while (true)
        {
            try {
                String received = "";

                dos.writeUTF( "I________________________________________I\n"+
                                  "I        ESCOLHA A OPCAO QUE DESEJA.     I\n"+
                                  "I --> 1 - ENVIAR MENSAGEM PARA TODOS.    I\n"+
                                  "I --> 2 - ENVIAR MENSAGEM DIRECIONADA.   I\n"+
                                  "I --> 3 - VER SUAS MENSAGENS             I\n"+
                                  "I________________________________________I\n"+
                                  "DIGITE A OPCAO A SEGUIR:                 ");
                received = dis.readUTF(); //recebendo op

                if(received.equals("SAIR"))
                {
                    System.out.println("Client " + this.s + " sends exit...");
                    System.out.println("Closing this connection.");
                    this.s.close();
                    System.out.println("Connection closed");
                    break;
                }

                switch (received) {
                    case "1":
                        dos.writeUTF(received);//enviando op de volta
                        String readMsg = dis.readUTF();//recebendo mensagem
                        for (int i = 0; i< listUser.size(); i++ ){
                            if( this.user.getNome()!= listUser.get(i).getNome()){
                                listMsg.add(new Mensagem(mensagem = "T =<"+readMsg, remetente = this.user, destinatario = listUser.get(i)));
                            }
                        }
                        dos.writeUTF("Mensagem enviada");
                    break;

                    case "2":      //returna mesngem para cliente direcionado
                        dos.writeUTF(received);//enviando op de volta
                        String readQuem  = dis.readUTF();//recebendo para destinatario

                        dos.writeUTF("Digite sua mensagem:");
                        String readMsgdes  = dis.readUTF();//recebendo para destinatario

                        for (int i = 0; i< listUser.size(); i++ ){

                            System.out.println( readQuem + " + " + listUser.get(i).getNome());
                            if( readQuem.equals(listUser.get(i).getNome())){
                                listMsg.add(new Mensagem(mensagem = "D =>"+readMsgdes, remetente = this.user, destinatario = listUser.get(i)));
                                System.out.println("entrei no IF");
                            }
                        }
                        dos.writeUTF("Mensagem enviada");
                    break;

                    case "3":       //retorna todas as mensagens
                        dos.writeUTF(received);//enviando op de volta
                        System.out.println(dis.readUTF()); //solicitação de carga

                        String concatMsg = "";
                        for (int i=0;  i<listMsg.size(); i++) {
                            if(listMsg.get(i).getDestinatario() == this.user){
                                concatMsg +=  listMsg.get(i).getRemetente().getNome() + " - "+ listMsg.get(i).getMensagem() + "\n";
                            }
                        }

                        if (concatMsg.isEmpty()) {
                            dos.writeUTF("Não há mensagens!");
                        } else {
                            dos.writeUTF( concatMsg);
                        }
                    break;

                    default:

                        dos.writeUTF(received);// mandando operador de volta
                        String erro = dis.readUTF();
                        dos.writeUTF(">>>" + erro + "<<<" + "Essa operação é invalida" );

                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try
        {
            // closing resources
            this.dis.close();
            this.dos.close();

        }catch(IOException e){
            e.printStackTrace();
        }


    }
}

