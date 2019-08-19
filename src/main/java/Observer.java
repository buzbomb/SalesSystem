import java.time.LocalDate;
import java.util.*;

public class Observer {
    private Map<Order, LocalDate> orderList = new TreeMap<>();
    private Map<UUID, Integer> productsOnOrder = new TreeMap<>();

    public void addToOrder(LocalDate date, Order newOrder){
        orderList.put(newOrder, date);
    }

    public void printProductFrequency(Map<UUID, Product> theProducts){
        for(Map.Entry<Order, LocalDate> tempOrder : orderList.entrySet()) {
            for(Map.Entry<UUID, Integer> entry : tempOrder.getKey().getItems().entrySet()){
                if(productsOnOrder.containsKey(entry.getKey())){
                    productsOnOrder.put(entry.getKey(), entry.getValue() + productsOnOrder.get(entry.getKey()));
                } else {
                    productsOnOrder.put(entry.getKey(), entry.getValue());
                }
            }
        }
        for(Map.Entry<UUID, Integer> onOrderItem : productsOnOrder.entrySet()){
            System.out.println("Product: " + theProducts.get(onOrderItem.getKey()).getName() + " Quantity: " + onOrderItem.getValue());
        }
    }

    public Map<Order, LocalDate> getOrderList() {
        return orderList;
    }

    public void setOrderList(Map<Order, LocalDate> orderList) {
        this.orderList = orderList;
    }
}
