import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Timer;

public class TcpServer implements Runnable {
    private static Socket clientDialog;
    private HashMap<String, Float> lastPrices = new HashMap<String, Float>();
    public TcpServer(Socket client) {
        TcpServer.clientDialog = client;
    }
    @Override
    public void run() {
        try {
            DataOutputStream out = new DataOutputStream(clientDialog.getOutputStream());
            DataInputStream in = new DataInputStream(clientDialog.getInputStream());
            System.out.println("DataInputStream created");
            System.out.println("DataOutputStream  created");
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String ln = null;
            Timer mTimer = new Timer();
            TwoSecondsTask twoSecondsTask = new TwoSecondsTask(out, lastPrices);
            mTimer.scheduleAtFixedRate(twoSecondsTask,1000,2000);
            while ((ln = reader.readLine()) != null) {
                if ((ln.charAt(0) == 'r' & (ln.length() == 1))) {
                    System.out.println(pricesToRow());
                    out.writeUTF(pricesToRow());
                } else if ((ln.charAt(0) == 'c') & (ln.length() == 1)) {
                    System.out.println(pricesToColumn());
                    out.writeUTF(pricesToColumn());
                } else if ((ln.charAt(0) == 'q') & (ln.length() == 1)) {
                    System.out.println("Quit connection");
                    out.writeUTF("Quit connection");
                    in.close();
                    out.close();
                    clientDialog.close();
                    break;
                } else
                    System.out.println("_" + ln + "_");
                out.flush();
                System.out.flush();
            }
            mTimer.cancel();
            System.out.println("Closing connections & channels.");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private String pricesToRow(){
        PriceStorage myPS = PriceStorage.getInstance();
        String S="";
        for (HashMap.Entry<String, Float> item : myPS.storage.entrySet()) {
            S = S + item.getKey()+"="+ item.getValue()+";";
        }
        return S;
    }
    private String pricesToColumn(){
        PriceStorage myPS = PriceStorage.getInstance();
        String S="";
        for (HashMap.Entry<String, Float> item : myPS.storage.entrySet()) {
            S = S + item.getKey()+"="+ item.getValue()+"\n";
        }
        return S;
    }
}