import java.util.HashMap;
public class PriceStorage {
    private static PriceStorage ourInstance = new PriceStorage();
    public HashMap<String, Float> storage = new HashMap<String, Float>();
    public static PriceStorage getInstance() {
        return ourInstance;
    }

    private PriceStorage() {

    }
    public void setOperatorPrice(String operator, float price){
        this.storage.put(operator, price);
    }
    public float getPrice(String operator){
        return this.storage.get(operator);
    }

}

