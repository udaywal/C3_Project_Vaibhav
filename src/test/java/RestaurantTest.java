import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;
import java.time.LocalTime;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.ArrayList;

class RestaurantTest {

    static RestaurantService service = new RestaurantService();
    static Restaurant restaurant;

    // It will trigger before all tests
    @BeforeAll
    public static void beforeAll() {
        
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");

        restaurant = service.addRestaurant("Amelie's cafe", "Chennai", openingTime, closingTime);

        restaurant.addToMenu("Sweet corn soup", 119);
        restaurant.addToMenu("Vegetable lasagne", 269);
    }

    // >>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time() {

        Restaurant searchedRestaurant = service.findRestaurantByName("Ruskin's cafe");

        boolean isRestaurantOpen = searchedRestaurant.isRestaurantOpen();

        assertEquals(true, isRestaurantOpen);
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time() {

        Restaurant searchedRestaurant = Mockito.mock(Restaurant.class);

        LocalTime outsideWorkingHoursTime = LocalTime.parse("23:30:00");

        Mockito.when(searchedRestaurant.getCurrentTime()).thenReturn(outsideWorkingHoursTime);

        boolean isRestaurantOpen = searchedRestaurant.isRestaurantOpen();

        assertEquals(false, isRestaurantOpen);
    }

    // <<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    // >>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1() {

        int initialMenuSize = restaurant.getMenu().size();

        restaurant.addToMenu("Sizzling brownie", 319);

        assertEquals(initialMenuSize + 1, restaurant.getMenu().size());
    }

    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {

        int initialMenuSize = restaurant.getMenu().size();

        restaurant.removeFromMenu("Vegetable lasagne");

        assertEquals(initialMenuSize - 1, restaurant.getMenu().size());
    }

    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        assertThrows(itemNotFoundException.class, () -> restaurant.removeFromMenu("French fries"));
    }

    // <<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    // >>>>>>>>>>>>>>>>>>>>>>>>>>>ORDER CALCULATION<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    // Failing test case for order value calculation
    @Test
    public void throw_item_not_found_exception_while_calculating_the_order_value () {

        // arrange
        Restaurant searchedRestaurant = service.findRestaurantByName("Ruskin's cafe");

        // act
        List<String> items = new ArrayList<String>();
        items.add("Invalid item name");

        // assertion
        assertThrows(itemNotFoundException.class, () -> searchedRestaurant.calculateOrderValue(items));
    }

    // Passed test case for order value calculation
    @Test
    public void calculate_the_order_value_of_items_when_the_item_names_are_provided() {

        // arrange
        Restaurant searchedRestaurant = service.findRestaurantByName("Ruskin's cafe");

        // act
        List<String> items = new ArrayList<String>();
        items.add("Latte");
        items.add("Cold coffee");
        int totalOrderValue = searchedRestaurant.calculateOrderValue(items);
        
        // assertion
        assertEquals(180, totalOrderValue);
    }

    // <<<<<<<<<<<<<<<<<<<<<<<ORDER CALCULATION>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

}