package Server;

import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class teste {
    public static void main(String[] args) throws IOException {

        int porta = 3322;
        InetAddress ip = InetAddress.getByName("192.168.0.17");

        ServerSocket server = new ServerSocket(porta, 0, ip);
        System.out.println("Server iniciado na porta " + porta);

        //ao criar o server socket ele já é bindado ao ip do dispositivo, por isso n da pra usar
        //server.bind(new InetSocketAddress("192.168.0.1", 0));

        //recuperando info do IP e port
        InetAddress inet = server.getInetAddress();
        System.out.println("Host Address = " + inet.getHostAddress());
        System.out.println("Host Name = " + inet.getHostName());
        System.out.println("Port = " + server.getLocalPort());

        //verificando se ServerSocket está aberto e bindado a algum IP
        System.out.println(server.isClosed());
        System.out.println(server.isBound());

        System.out.println(server.toString());

        Socket client = server.accept();
        //a partir daqui só é executado após alguma conexão

        //captura
        Scanner entrada = new Scanner(client.getInputStream());
        //envio
        PrintStream saida = new PrintStream(client.getOutputStream());
        Scanner message = new Scanner(System.in);
        while (message.hasNextLine()) {
            saida.println(message.nextLine());
        }


    }
}
