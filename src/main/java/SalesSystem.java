import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SalesSystem {
    public static void main(String[] args) throws IOException {
        Observer theObserver = new Observer();
        Scanner sc = new Scanner(System.in);
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String orderString;
        ObjectMapper om = new ObjectMapper();
        Map<UUID, Product> products = om.<List<Product>>readValue(SalesSystem.class.getResourceAsStream("/productList.json"), new TypeReference<List<Product>>(){})
                .stream()
                .collect(Collectors.toMap(Product::getId, Function.identity()));

        while(true){
            LocalDate date;
            while(true){
                try{
                    System.out.println("Order Date dd/mm/yyyy: ");
                    String dateAsString = sc.nextLine();
                    date = LocalDate.parse(dateAsString, format);
                    break;
                } catch(DateTimeParseException e){
                    System.out.println("Incorrect Date Format");
                    e.printStackTrace();
                } catch(DateTimeException e){
                    e.printStackTrace();
                }
            }
            Order theOrder = new Order();
            theOrder.setId(UUID.randomUUID());
            theOrder.setCustomer(UUID.randomUUID());
            theOrder.newOrder(products);
            theObserver.addToOrder(date, theOrder);
            theObserver.printProductFrequency(products);
            orderString = om.writeValueAsString(theOrder);
            theOrder.sendOrder(orderString);
        }
    }
}
