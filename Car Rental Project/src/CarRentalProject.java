//Car Rental Project

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Define a Car class to represent individual cars
class Car {
    private String carId;
    private String brand;
    private String model;
    private double basePricePerDay; 
    private boolean isAvailable;

    //Made public corresponding method to access and get the information from the Private class.

    // Constructor to initialize car properties
    public Car(String carId, String brand, String model, double basePricePerDay) {
        this.carId = carId;
        this.brand = brand;
        this.model = model;
        this.basePricePerDay = basePricePerDay;
        this.isAvailable = true; //the value is true right now because the car is available, when the car is gone it will become "false"
    }

    // Getter methods to access car information
    public String getCarId() {
        return carId;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    // Calculate rental price based on the number of days
    public double calculatePrice(int rentalDays) {
        return basePricePerDay * rentalDays;
    }

    // Check if the car is available for rent
    public boolean isAvailable() {
        return isAvailable;
    }

    // Mark the car as rented and when the customer will return the car it will become true.
    public void rent() {
        isAvailable = false;
    }

    // Mark the car as returned and it will become false when the customer will rent the car.
    public void returnCar() {
        isAvailable = true;
    }
}

// Define a Customer class to represent individual customers
class Customer {
    private String customerId;
    private String name;

    // Constructor to initialize customer properties
    public Customer(String customerId, String name) {
        this.customerId = customerId;
        this.name = name;
    }

    // Getter methods to access customer information
    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }
}

// Define a Rental class to represent the rental transactions
class Rental {
    private Car car;
    private Customer customer;
    private int days;

    // Constructor to initialize rental properties
    public Rental(Car car, Customer customer, int days) {
        this.car = car;
        this.customer = customer;
        this.days = days;
    }

    // Getter methods to access rental information
    public Car getCar() {
        return car;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getDays() {
        return days;
    }
}

// Define the main CarRentalSystem class to manage the rental system
class CarRentalSystem {
    private List<Car> cars;
    private List<Customer> customers;
    private List<Rental> rentals;

    // Constructor to initialize the system with empty lists
    public CarRentalSystem() {
        cars = new ArrayList<>();
        customers = new ArrayList<>();
        rentals = new ArrayList<>();
    }

    // Add a new car to the system
    public void addCar(Car car) {
        cars.add(car);
    }

    // Add a new customer to the system
    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    // Rent a car to a customer for a specified number of days
    public void rentCar(Car car, Customer customer, int days) {
        if (car.isAvailable()) {
            car.rent();
            rentals.add(new Rental(car, customer, days));
        } else {
            System.out.println("Car is not available for rent.");
        }
    }

    // Return a rented car to the system
    public void returnCar(Car car) {
        car.returnCar();
        Rental rentalToRemove = null;
        for (Rental rental : rentals) {
            if (rental.getCar() == car) {
                rentalToRemove = rental;
                break;
            }
        }
        if (rentalToRemove != null) {
            rentals.remove(rentalToRemove);
        } else {
            System.out.println("Car was not rented.");
        }
    }

    // Display a simple text-based menu for the user to interact with the system
    public void menu() {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println("===== Car Rental System =====");
                System.out.println("1. Rent a Car");
                System.out.println("2. Return a Car");
                System.out.println("3. Exit");
                System.out.print("Enter your choice: ");

                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                if (choice == 1) {

                    // Rental process
                    System.out.println("\n== Rent a Car ==\n");

                    // Prompt user for name
                    System.out.print("Enter your name: ");
                    String customerName = scanner.nextLine();

                    // Display available cars
                    System.out.println("\nAvailable Cars:");
                    for (Car car : cars) {
                        if (car.isAvailable()) {
                            System.out.println(car.getCarId() + " - " + car.getBrand() + " " + car.getModel());
                        }
                    }

                    // Prompt user for car selection and rental duration
                    System.out.print("\nEnter the car ID you want to rent: ");
                    String carId = scanner.nextLine();
                    System.out.print("Enter the number of days for rental: ");
                    int rentalDays = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    // Create a new customer and add to the system
                    Customer newCustomer = new Customer("CUS" + (customers.size() + 1), customerName);
                    addCustomer(newCustomer);

                    // Find the selected car and proceed with rental if available
                    Car selectedCar = null;
                    for (Car car : cars) {
                        if (car.getCarId().equals(carId) && car.isAvailable()) {
                            selectedCar = car;
                            break;
                        }
                    }

                    if (selectedCar != null) {
                        // Calculate total price and display rental information
                        double totalPrice = selectedCar.calculatePrice(rentalDays);
                        System.out.println("\n== Rental Information ==\n");
                        System.out.println("Customer ID: " + newCustomer.getCustomerId());
                        System.out.println("Customer Name: " + newCustomer.getName());
                        System.out.println("Car: " + selectedCar.getBrand() + " " + selectedCar.getModel());
                        System.out.println("Rental Days: " + rentalDays);
                        System.out.printf("Total Price: $%.2f%n", totalPrice);

                        // Confirm rental with user input
                        System.out.print("\nConfirm rental (Y/N): ");
                        String confirm = scanner.nextLine();

                        if (confirm.equalsIgnoreCase("Y")) {
                            rentCar(selectedCar, newCustomer, rentalDays);
                            System.out.println("\nCar rented successfully.");
                        } else {
                            System.out.println("\nRental canceled.");
                        }
                    } else {
                        System.out.println("\nInvalid car selection or car not available for rent.");
                    }
                } else if (choice == 2) {

                    // Return process
                    System.out.println("\n== Return a Car ==\n");
                    System.out.print("Enter the car ID you want to return: ");
                    String carId = scanner.nextLine();

                    // Find the car to return and proceed if it was rented
                    Car carToReturn = null;
                    for (Car car : cars) {
                        if (car.getCarId().equals(carId) && !car.isAvailable()) {
                            carToReturn = car;
                            break;
                        }
                    }

                    if (carToReturn != null) {

                        // Find the customer associated with the returned car
                        Customer customer = null;
                        for (Rental rental : rentals) {
                            if (rental.getCar() == carToReturn) {
                                customer = rental.getCustomer();
                                break;
                            }
                        }

                        // Process the return and display success message
                        if (customer != null) {
                            returnCar(carToReturn);
                            System.out.println("Car returned successfully by " + customer.getName());
                        } else {
                            System.out.println("Car was not rented or rental information is missing.");
                        }
                    } else {
                        System.out.println("Invalid car ID or car is not rented.");
                    }
                } else if (choice == 3) {
                    // Exit the program
                    break;
                } else {
                    System.out.println("Invalid choice. Please enter a valid option.");
                }
            }
        }

        // Display a closing message when the program exits
        System.out.println("\nThank you for using our Car Rental System!");
    }
}

// The main class to run the CarRentalSystem
public class CarRentalProject {
    public static void main(String[] args) {

        // Initialize the CarRentalSystem and add sample cars
        CarRentalSystem rentalSystem = new CarRentalSystem();
        Car car1 = new Car("C001", "Toyota", "Camry", 65.0);
        Car car2 = new Car("C002", "Honda", "Accord", 75.0);
        Car car3 = new Car("C003", "Tesla", "Model 3", 90.0);
        Car car4 = new Car("C004", "Tesla", "Model Y", 150.0);
        Car car5 = new Car("C005", "BMW", "X7", 350.0);

        // Add cars to the system and start the menu
        rentalSystem.addCar(car1);
        rentalSystem.addCar(car2);
        rentalSystem.addCar(car3);
        rentalSystem.addCar(car4);
        rentalSystem.addCar(car5);
        rentalSystem.menu();
    }
}
