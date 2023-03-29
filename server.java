import java.net.*;
import java.io.*;


public class server {

    ServerSocket server ;
    Socket socket;
    BufferedReader br;
    PrintWriter out;


     //constructer
    public server () {
         try{
        server= new ServerSocket(7778);
         System.out.println("server is ready to accept connection");
         System.out.println("waiting.....");

           socket=server.accept();

           br= new BufferedReader(new InputStreamReader(socket.getInputStream()));
           out= new PrintWriter(socket.getOutputStream());

           startReading();
           startWriting();

         }

         catch(Exception e){

            e.printStackTrace();
         }



    }

    public void startReading() {
      //thread read karke deta rahega
      
      Runnable r1 =()->{
        System.out.println("reader started...");
               
        try{

        while(true){

            

            String msg= br.readLine();

            if(msg.equals("exit")){

                System.out.println("client terminated the chat");
                socket.close();
                break;
            }
        

            System.out.println("client :"+  msg);
        }
    }

        
        catch(Exception e){
           // e.printStackTrace();
           System.out.println("connection is closed");

        

        
    }
    



      };
      new Thread(r1).start();
    

    }
    
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
                if (content.equals("exist")){
                    socket.close();
                    break;
                

                }
            }
        }
        


            
            catch(Exception e){
            
                System.out.println("connection closed");

            }
        
        
     };
         
         new Thread(r2).start();

    }
    public static void main (String args[]){

        System.out.println("this is server ...going to start server");

        new server();


    }
}
