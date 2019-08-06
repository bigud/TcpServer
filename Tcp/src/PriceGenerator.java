import java.util.Random;
/*
MTS
BEELINE
MEGAFON
TELE2
YOTA
 */
public class PriceGenerator implements Runnable {
    Random r = new Random();

    @Override
    public void run() {
        while (true) {
            try {
                PriceStorage myPriceStorage = PriceStorage.getInstance();
                int opId = r.nextInt(5);
                String opStr = "MTS";
                switch (opId) {
                    case 1:
                        opStr = "BEELINE";
                        break;
                    case 2:
                        opStr = "MEGAFON";
                        break;
                    case 3:
                        opStr = "TELE2";
                        break;
                    case 4:
                        opStr = "YOTA";
                        break;
                }
                float price = r.nextFloat();
                myPriceStorage.setOperatorPrice(opStr, price);
                Thread.sleep(600);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
