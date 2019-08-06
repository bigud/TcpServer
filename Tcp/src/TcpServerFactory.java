import java.io.BufferedReader;
        import java.io.IOException;
        import java.io.InputStreamReader;
        import java.net.ServerSocket;
        import java.net.Socket;
        import java.util.concurrent.ExecutorService;
        import java.util.concurrent.Executors;

public class TcpServerFactory {
    static ExecutorService executeIt = Executors.newFixedThreadPool(4);

    public static void main(String[] args) {
        PriceStorage myPriceStorage = PriceStorage.getInstance();
        Thread priceGenerator = new Thread(new PriceGenerator());
        priceGenerator.start();
        try (ServerSocket server = new ServerSocket(3333);
             BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Server socket created, command console reader for listen to server commands");

            while (!server.isClosed()) {

                if (br.ready()) {
                    System.out.println("Main Server found any messages in channel, let's look at them.");

                    String serverCommand = br.readLine();
                    if (serverCommand.equalsIgnoreCase("quit")) {
                        System.out.println("Main Server initiate exiting...");
                        server.close();
                        break;
                    }
                }

                Socket client = server.accept();
                executeIt.execute(new TcpServer(client));
                System.out.print("Connection accepted.");
            }
            executeIt.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}