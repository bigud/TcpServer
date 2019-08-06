import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.TimerTask;

public class TwoSecondsTask extends TimerTask {
    private DataOutputStream out;
    private HashMap<String, Float> lastPrices;

    TwoSecondsTask(DataOutputStream out, HashMap<String, Float> lastPrices) {
        this.out = out;
        this.lastPrices = lastPrices;
    }

    public void run() {
        try {
            PriceStorage myPS = PriceStorage.getInstance();
            for (HashMap.Entry<String, Float> item : myPS.storage.entrySet()) {
                String op = item.getKey();
                Float price = item.getValue();
                Float oldPrice = lastPrices.get(op);
                if (oldPrice == null) {
                    oldPrice = 0.0f;
                }
                if (!oldPrice.equals(price)) {
                    out.writeUTF(op + "=" + price.toString() + ";\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
