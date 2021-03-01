import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class RestaurantService {
    
    Restaurant restaurant;
    private static List<Restaurant> restaurants = new ArrayList<>();

    public RestaurantService () {
        
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");

        restaurant = this.addRestaurant("Ruskin's cafe","Delhi",openingTime,closingTime);
        restaurant.addToMenu("Latte",111);
        
        restaurant.addToMenu("Cold coffee",69);
    }

    public Restaurant findRestaurantByName(String restaurantName){
        for (Restaurant restaurant : restaurants) {
            if (restaurant.getName().equals(restaurantName)) {
                return restaurant;
            }
        }
        throw new restaurantNotFoundException(restaurantName +" does not exists");
    }

    public Restaurant addRestaurant(String name, String location, LocalTime openingTime, LocalTime closingTime) {
        Restaurant newRestaurant = new Restaurant(name, location, openingTime, closingTime);
        restaurants.add(newRestaurant);
        return newRestaurant;
    }

    public Restaurant removeRestaurant(String restaurantName) throws restaurantNotFoundException {
        Restaurant restaurantToBeRemoved = findRestaurantByName(restaurantName);
        restaurants.remove(restaurantToBeRemoved);
        return restaurantToBeRemoved;
    }

    public List<Restaurant> getRestaurants() {
        return restaurants;
    }

}
