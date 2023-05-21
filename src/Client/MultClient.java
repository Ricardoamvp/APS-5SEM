package Client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;
import javax.swing.*;

public class MultClient extends javax.swing.JFrame implements ActionListener, KeyListener{

    private static final long serialVersionUID = 1L;
    private JTextArea texto;
    private JTextField txtMsg;
    private JButton btnSend;
    private JButton btnSair;
    private JLabel lblChat;
    private JLabel lblMsg;
    private JPanel pnlContent;
    private Socket socket;
    private OutputStream ou ;
    private Writer ouw;
    private BufferedWriter bfw;
    private JTextField txtIP;
    private JTextField txtPort;
    private JTextField txtName;

    // a
    public MultClient() throws IOException{
        JLabel lblMessage = new JLabel("Servidor");
        txtIP = new JTextField("127.0.0.1");
        txtPort = new JTextField("36654");
        txtName = new JTextField();
        Object[] texts = {lblMessage, txtIP, txtPort, txtName};
        JOptionPane.showMessageDialog(null, texts);
        pnlContent = new JPanel();
        texto = new JTextArea(20,40);
        texto.setEditable(false);
        texto.setBackground(Color.white);
        txtMsg = new JTextField(40);
        lblChat = new JLabel("Chat");
        lblMsg = new JLabel("Mensagem");
        btnSend = new JButton("Enviar");
        btnSair = new JButton("Sair");
        btnSend.addActionListener(this);
        btnSair.addActionListener(this);
        btnSend.addKeyListener(this);
        txtMsg.addKeyListener(this);
        JScrollPane scroll = new JScrollPane(texto);
        texto.setLineWrap(true);
        pnlContent.add(lblChat);
        pnlContent.add(scroll);
        pnlContent.add(lblMsg);
        pnlContent.add(txtMsg);
        pnlContent.add(btnSair);
        pnlContent.add(btnSend);
        pnlContent.setBackground(Color.cyan);
        texto.setBorder(BorderFactory.createEtchedBorder(Color.GREEN,Color.GREEN));
        txtMsg.setBorder(BorderFactory.createEtchedBorder(Color.GREEN, Color.GREEN));
        setTitle("Chat");
        setContentPane(pnlContent);
        setLocationRelativeTo(null);
        setResizable(false);
        setSize(470,470);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    //a
    public void conectar() throws IOException{

        socket = new Socket(txtIP.getText(),Integer.parseInt(txtPort.getText()));
        ou = socket.getOutputStream();
        ouw = new OutputStreamWriter(ou);
        bfw = new BufferedWriter(ouw);
        bfw.write(txtName.getText()+"\r\n");
        bfw.flush();
    }

    //a
    public void enviarMensagem(String msg) throws IOException{

        if(msg.equals("Sair")){
            bfw.write("Desconectado \r\n");
            texto.append("Desconectado \r\n");
        }else{
            bfw.write(msg+"\r\n");
            texto.append( txtName.getText() + ": " + txtMsg.getText()+"\r\n");
        }
        bfw.flush();
        txtMsg.setText("");
    }

    //a
    public void escutar() throws IOException{

        InputStream in = socket.getInputStream();
        InputStreamReader inr = new InputStreamReader(in);
        BufferedReader bfr = new BufferedReader(inr);
        String msg = "";

        while(!"Sair".equalsIgnoreCase(msg))

            if(bfr.ready()){
                msg = bfr.readLine();
                if(msg.equals("Sair"))
                    texto.append("Servidor caiu! \r\n");
                else
                    texto.append(msg+"\r\n");
            }
    }

    //a
    public void sair() throws IOException{

        enviarMensagem("Sair");
        bfw.close();
        ouw.close();
        ou.close();
        socket.close();
    }

    //a
    @Override
    public void actionPerformed(ActionEvent e) {

        try {
            if(e.getActionCommand().equals(btnSend.getActionCommand()))
                enviarMensagem(txtMsg.getText());
            else
            if(e.getActionCommand().equals(btnSair.getActionCommand()))
                sair();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }

    //a
    @Override
    public void keyPressed(KeyEvent e) {

        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            try {
                enviarMensagem(txtMsg.getText());
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public void keyTyped(KeyEvent arg0) {
        // TODO Auto-generated method stub
    }

    //a
    public static void main(String []args) throws IOException{

        MultClient app = new MultClient();
        app.conectar();
        app.escutar();
    }



}
