package Server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class MultThreadServer extends Thread {

    private static ArrayList<BufferedWriter>users;
    private static ServerSocket server;
    private String nome;
    private Socket con;
    private InputStream in;
    private InputStreamReader inr;
    private BufferedReader bfr;

    // construtor
    public MultThreadServer(Socket con){
        this.con = con;
        try {
            in  = con.getInputStream();
            inr = new InputStreamReader(in);
            bfr = new BufferedReader(inr);
        } catch (IOException e) {
            e.getMessage();
        }
    }

    // metodo recebe a mensagem de um client e envia para todos os outros
    public void sendToAll(BufferedWriter bwSaida, String msg) throws  IOException
    {
        BufferedWriter bwS;

        for(BufferedWriter bw : users){
            bwS = (BufferedWriter)bw;
            if(!(bwSaida == bwS)){
                bw.write(nome + ": " + msg+"\r\n");
                bw.flush();
            }
        }
    }


    // o metodo roda em toda nova conexão para aloca-la em uma thread, verificar novas mensagens e enviar aos outros clients
    public void run(){

        try{

            String msg;
            OutputStream ou =  this.con.getOutputStream();
            Writer ouw = new OutputStreamWriter(ou);
            BufferedWriter bfw = new BufferedWriter(ouw);
            users.add(bfw);
            nome = msg = bfr.readLine();

            while(!"Sair".equalsIgnoreCase(msg) && msg != null)
            {
                msg = bfr.readLine();
                sendToAll(bfw, msg);
                System.out.println(msg);
            }

        }catch (Exception e) {
            e.getMessage();

        }
    }


    public static void main(String []args) {

        try{
            //Porta do servidor
            int port = 36654;

            server = new ServerSocket(port);
            users = new ArrayList<BufferedWriter>();
            System.out.println("Servidor aberto na porta: " + port);

            /*
            server.accept nao roda nada até receber um conexao
            apos a conexao uma nova thread é criada e o server voltar a aguardar conexoes
             */

            while(true){
                Socket con = server.accept();
                System.out.println(con.getInetAddress().getHostAddress() + " conectado");
                Thread t = new MultThreadServer(con);
                t.start();
            }

        }catch (Exception e) {
            e.getMessage();
        }

    }
}


