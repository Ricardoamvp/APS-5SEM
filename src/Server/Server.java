package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {

    public static void main(String[] args) {

        try {
            ServerSocket server = new ServerSocket(3322);
            System.out.println("Servidor abrto na porta 3322");

            //nao executa o restante até ter uma conexao
            Socket client = server.accept();
            System.out.println("Dispositivo conectado - " + client.getInetAddress().getHostAddress());

            Scanner input = new Scanner(client.getInputStream());
            while(input.hasNextLine()) {

                String msg = input.nextLine();

                // pra não vir mensagem vazia
                if(!msg.isBlank()){
                    System.out.println(client.getInetAddress().getHostAddress() + ": " + msg);
                }


            }

            input.close();
            server.close();

        } catch (IOException ex) {
            System.out.println(ex.getMessage());

        }

    }

}
