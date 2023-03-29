import java.awt.Font;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import java.io.*;
import java.net.*;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import java.awt.BorderLayout;



public class client  extends JFrame {


    Socket socket ;
    BufferedReader br;
    PrintWriter out;


    //declare components
    private JLabel heading = new JLabel("client area");
    private JTextArea messageArea= new JTextArea();
    private JTextField messageInput= new JTextField();
    private Font font = new Font ("Roboto",Font.PLAIN,20);

    //constructor

    public client (){

        try{
            System.out.println("sending request to server");
            socket = new Socket ("127.0.0.1",7778);

            System.out.println("connection done");

            br= new BufferedReader(new InputStreamReader(socket.getInputStream()));
           out= new PrintWriter(socket.getOutputStream());


          createGUI();
          handleEvents();


           startReading();
           //startWriting();

        }
        catch(Exception e){

            
            e.printStackTrace();

        }
    }
              
               public void handleEvents() {

                messageInput.addKeyListener(new KeyListener(){

                @Override
                public void keyTyped(KeyEvent e){


                }

                @Override
                public void keyPressed(KeyEvent e){

                }

                @Override
                public void keyReleased(KeyEvent e){
                  if (e.getKeyCode() == 10){

                  String contentToSend = messageInput.getText();
                  messageArea.append ("Me : "+ contentToSend+ "\n");
                  out.println(contentToSend);
                  out.flush();
                  messageInput.setText(" ");
                  messageInput.requestFocus();
                  }


                  


                }
              });


               }
               
            private void createGUI(){

              this.setTitle("client messager[END]");
              this.setSize(500,500);
              this.setLocationRelativeTo(null);
              this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

             //coding for component
              heading.setFont(font);
              messageArea.setFont(font);
              messageInput.setFont(font);
              heading.setIcon(new ImageIcon("chat logo.jpg"));
             heading.setHorizontalTextPosition(SwingConstants.CENTER);
             heading.setVerticalTextPosition(SwingConstants.BOTTOM);
              heading.setHorizontalAlignment(SwingConstants.CENTER);
              heading.setBorder(BorderFactory.createEmptyBorder(1,1,1,1));
              messageArea.setEditable(false);
              messageInput.setHorizontalAlignment(SwingConstants.CENTER);


              //frame ka layout set karenge

              this.setLayout(new BorderLayout());
              // adding the component to frame

              this.add (heading,BorderLayout.NORTH);
              JScrollPane jScrollPane = new JScrollPane(messageArea);
              this.add(jScrollPane,BorderLayout.CENTER);
              this.add (messageInput,BorderLayout.SOUTH);




              this.setVisible (true);
        
            }
          
            //startreading method

         public void startReading() {
            //thread read karke deta rahega
            
            Runnable r1 =()->{
              System.out.println("reader started...");
              
              try {
              while(true){
      
                  
      
                  String msg= br.readLine();
      
                  if(msg.equals("exit")){
      
                      System.out.println("server  terminated the chat");
                      JOptionPane.showMessageDialog(this,"server terminated the chat");
                      socket.close();

                      messageInput.setEnabled(false);
                      socket.close();
                      break;
                  }
      
                  //System.out.println("server :"+msg);

                  messageArea.append("Server : " + msg+ "\n");
                }
              }
              catch(Exception e){
                  //e.printStackTrace();
                  System.out.println("connection closed");


              }
              
          
      
      
      
            };
            new Thread(r1).start();
          
      
        }

            //start writing method
          
          public void startWriting(){
           // thread- data user lega and the send karega client tak
             
           Runnable r2 =()->{
      
              System.out.println("writer started...");
             
              try{
             
              while (!socket.isClosed()){
      
                  
      
                      BufferedReader br1 =new BufferedReader(new InputStreamReader(System.in));
      
                      String content = br1.readLine();
      
                      out.println(content);
                      out.flush();
                      if(content.equals("exit")){
                        socket.close();
                        break;

                      }
                       System.out.println("connection is closed");

                    }

                    


              }
            
    
                  
                  catch(Exception e){
                    e.printStackTrace();
                      //System.out.println("connection is closed");

                  }
                

           };
               
               new Thread(r2).start();
      
          }
    
    public static void main (String args[]){

        System.out. println("this is client...");

        new client();

    }

}

