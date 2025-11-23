import java.util.ArrayList;

public class restaurant {
    public static void main(String[] args) {
        Employee e1 = new Employee("Mohammad", "09024159575", "Chef", 40000000);
        Employee e2 = new Employee("Peyman", "09111111111", "Dishwasher", 7000000);
        Employee e3 = new Employee("Hamid", "09222222222", "Waiter", 12000000);
        e1.addHoursWorked(180);
        e2.addHoursWorked(170);
        e3.addHoursWorked(150);
        Customer c1 = new Customer("Ali", "09333333333");
        Customer c2 = new Customer("Mina", "09444444444");
        Customer c3 = new Customer("Salar", "09555555555");
        Customer c4 = new Customer("Samyar", "09666666666");
        Customer c5 = new Customer("Hooman", "09777777777");

        ArrayList<MenuItem> menu = new ArrayList<>();
        Food f1 = new Food("Pizza", 500000, "food", "Medium", 30);
        Food f2 = new Food("Cheeseburger", 350000, "food", "Mild", 25);
        Food f3 = new Food("Hotdog", 200000, "food", "Spicy", 20);
        Beverage b1 = new Beverage("Cola", 60000, "Beverage", "large", "cold");
        Beverage b2 = new Beverage("Sprite", 60000, "Beverage", "medium", "cold");
        Beverage b3 = new Beverage("Doogh", 40000, "Beverage", "small", "cold");
        menu.add(f1);
        menu.add(f2);
        menu.add(f3);
        menu.add(b1);
        menu.add(b2);
        menu.add(b3);

        ArrayList<Order> orders = new ArrayList<>();
        orders.add(createOrder(c1, new MenuItem[]{f1, b1, b2}));
        orders.add(createOrder(c1, new MenuItem[]{f1, f2, b3}));
        orders.add(createOrder(c1, new MenuItem[]{f1, f3, b1}));
        orders.add(createOrder(c2, new MenuItem[]{f2, b2, b3}));
        orders.add(createOrder(c2, new MenuItem[]{f3, b3, b2}));
        orders.add(createOrder(c2, new MenuItem[]{f1, f2, f3}));
        orders.add(createOrder(c3, new MenuItem[]{f1, f2, b1}));
        orders.add(createOrder(c3, new MenuItem[]{f2, f3, b2}));
        orders.add(createOrder(c3, new MenuItem[]{f3, b2, b3}));
        orders.add(createOrder(c4, new MenuItem[]{f1, b1, b3}));
        orders.add(createOrder(c4, new MenuItem[]{f2, b2, b3}));
        orders.add(createOrder(c4, new MenuItem[]{f3, f1, b2}));
        orders.add(createOrder(c5, new MenuItem[]{f2, b1, b2}));
        orders.add(createOrder(c5, new MenuItem[]{f3, b3, b2}));
        orders.add(createOrder(c5, new MenuItem[]{f1, f2, f3}));

        Customer[] customers = new Customer[]{c1, c2, c3, c4};
        Customer mostLoyal = findLoyalest(customers);
        System.out.println("Customers:");       
        System.out.println(c1.getInfo());
        System.out.println(c2.getInfo());
        System.out.println(c3.getInfo());
        System.out.println(c4.getInfo());
        System.out.println(c5.getInfo());
        System.out.println("");
        System.out.println("Employees:");
        System.out.println(e1.getInfo() + ", " + "Calculated Salary: " + e1.calculateSalary());
        System.out.println(e2.getInfo() + ", " + "Calculated Salary: " + e2.calculateSalary());
        System.out.println(e3.getInfo() + ", " + "Calculated Salary: " + e3.calculateSalary());
        System.out.println("");
        System.out.println("Menu Items:");
        for (int i=0; i<menu.size(); i++){ 
            System.out.println(menu.get(i).getDetails());
        }
        System.out.println("");
        System.out.println("Orders:");
        for (int i=0; i<orders.size(); i++){
            System.out.println(orders.get(i).getOrderSummary());
        }
        System.out.println("");
        System.out.println("Most Loyal Customer:");
        System.out.println(mostLoyal.getInfo());
        
    }

    private static Order createOrder(Customer customer, MenuItem[] items){
        Order order = new Order(customer);
        for(int i=0; i<items.length; i++){
            order.addItem(items[i]);
        }
        order.calculateTotal();
        return order;
    }

    private static Customer findLoyalest(Customer[] customers){
        Customer loyalest = customers[0];
        for (int i=1; i<customers.length; i++){
            if (customers[i].getLoyaltyPoints()>loyalest.getLoyaltyPoints()){
                loyalest = customers[i];
            }
        }
        return loyalest;
    }
}

abstract class Person {
    private String name;
    private String phoneNumber;
    public Person(String name, String phoneNumber){
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public abstract String getInfo();

    public String getName(){
        return name;
    }
    public String getPhoneNumber(){
        return phoneNumber;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }
}

class Customer extends Person {
    private static int customerCount = 0;
    private String customerId;
    private int loyaltyPoints;

    public Customer(String name, String phoneNumber){
        super(name, phoneNumber);
        customerCount += 1;
        if (customerCount<10){
            this.customerId = "C00" + customerCount;
        }
        else if (customerCount<100){
            this.customerId = "C0" + customerCount;
        }
        else if (customerCount<1000){
            this.customerId = "C" + customerId;
        }
        
        this.loyaltyPoints = 0;
    }

    public void addLoyaltyPoints(double total){
        if (total>=500000 && total<1000000){
            loyaltyPoints += 1;
        } 
        else if (total>=1000000){
            loyaltyPoints += 2;
        }
    }

    public double getDiscount(){
        if (loyaltyPoints>=3 && loyaltyPoints<5){
            return 0.95;
        } 
        else if (loyaltyPoints>=5){
            return 0.9;
        }
        return 1;
    }
    public String getCustomerId(){
        return customerId;
    }
    public int getLoyaltyPoints(){
        return loyaltyPoints;
    }
    public void setCustomerId(String id){
        this.customerId = id;
    }
    public void setLoyaltyPoints(int points){
        this.loyaltyPoints = points;
    }


    @Override
    public String getInfo(){
        return "ID: " + customerId + ", " + "Name: " + getName() + ", " + "Phone: " + getPhoneNumber() + ", " + "Loyalty Points: " + loyaltyPoints;
    }
}

class Employee extends Person {
    private static int employeeCount = 0;
    private String employeeId;
    private String position;
    private double salary;
    private double hoursWorked;

    public Employee(String name, String phoneNumber, String position, double salary){
        super(name, phoneNumber);
        employeeCount += 1;
        this.position = position;
        this.salary = salary;
        this.hoursWorked = 0;
        if (employeeCount<10){
            this.employeeId = "E00" + employeeCount;
        }
        else if (employeeCount<100){
            this.employeeId = "E0" + employeeCount;
        }
        else if (employeeCount<1000){
            this.employeeId = "E" + employeeCount;
        }
    }

    public void addHoursWorked(double hours){
        this.hoursWorked += hours;   
    }
    public double calculateSalary(){
        double baseSalary = salary;
        if (hoursWorked<160){
            return 0;
        }
        else if (hoursWorked==160){
            return baseSalary;
        }
        else{
            return baseSalary + (1.5*baseSalary*((hoursWorked-160)/160));
        } 
    }

    public String getEmployeeId(){
        return employeeId;
    }
    public String getPosition(){
        return position;
    }
    public double getBaseSalary(){
        return salary;
    }
    public double getHoursWorked(){
        return hoursWorked;
    }
    public void setEmployeeId(String id){
        this.employeeId = id;
    }
    public void setPosition(String position){
        this.position = position;
    }
    public void setSalary(double salary){
        this.salary = salary;
    }

    @Override
    public String getInfo(){
        return "ID: " + employeeId + ", " + "Name: " + getName() + ", " + "Phone: " + getPhoneNumber() + ", " + "Position: " + position + ", " + "HoursWorked: " + hoursWorked;
    }
}

abstract class MenuItem {
    private static int itemCount = 0;
    private int itemId;
    private String name;
    private double price;
    private String category;

    public MenuItem(String name, double price, String category){
        itemCount += 1;
        this.itemId = itemCount;
        this.name = name;
        this.price = price;
        this.category = category;
    }
    public abstract String getDetails();

    public int getItemId(){
        return itemId;
    }
    public String getName(){
        return name;
    }
    public double getPrice(){
        return price;
    }
    public String getCategory(){
        return category;
    }
    public void setId(int id){
        this.itemId = id;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setPrice(double price){
        this.price = price;
    }
    public void setCategory(String category){
        this.category = category;
    }
}

class Food extends MenuItem {
    private String spiceLevel;
    private double preparationTime;

    public Food(String name, double price, String category, String spiceLevel, double preparationtime) {
        super(name, price, category);
        this.spiceLevel = spiceLevel;
        this.preparationTime = preparationtime;
    }

    public String getSpiceLevel(){
        return spiceLevel;
    }
    public double getPreparationTime(){
        return preparationTime;
    }
    public void setSpiceLevel(String spicelevel){
        this.spiceLevel = spicelevel;
    }
    public void setPreparationTime(double preparationtime){
        this.preparationTime = preparationtime;
    }

    @Override
    public String getDetails() {
        return "ID: " + getItemId() + ", " + "Name: " + getName() + ", " + "Price: " + getPrice() + ", " + "Category: " + getCategory() + ", " + "Spice: " + spiceLevel + ", " + "Preparation Time: " + preparationTime + "min";
    }
}

class Beverage extends MenuItem {
    private String size;
    private String temperature;

    public Beverage(String name, double price, String category, String size, String temperature) {
        super(name, price, category);
        this.size = size;
        this.temperature = temperature;
    }

    public String getSize(){
        return size;
    }
    public String getTemperature(){
        return temperature;
    }
    public void setSize(String size){
        this.size = size;
    }
    public void setTemperature(String temprature){
        this.temperature = temprature;
    }

    @Override
    public String getDetails() {
        return "ID: " + getItemId() + ", " + "Name: " + getName() + ", " + "Price: " + getPrice() + ", " + "Category: " + getCategory() + ", " + "Size: " + size + ", " + "Temperature: " + temperature;
    }
}

class Order {
    private static int orderCount = 0;
    private int orderId;
    private Customer customer;
    private ArrayList<MenuItem> items;
    private double totalAmount;
    public Order(Customer customer){
        orderCount += 1;
        this.orderId = orderCount;
        this.customer = customer;
        this.items = new ArrayList<>();
        this.totalAmount = 0;
    }

    public void addItem(MenuItem item){
        items.add(item); 
    }

    public void calculateTotal(){
        double sum = 0;
        for (int i=0; i<items.size(); i++){
            sum += items.get(i).getPrice();
        }
        customer.addLoyaltyPoints(sum);
        double discountApplied = customer.getDiscount();
        totalAmount = sum*discountApplied;
    }

    public int getOrderId(){
        return orderId;
    }
    public Customer getCustomer(){
        return customer;
    }
    public double getTotalAmount(){
        return totalAmount;
    }
    public void setOrderId(int id){
        this.orderId = id;
    }
    public void setCustomer(Customer customer){
        this.customer = customer;
    }
    public void setTotalAmount(double total){
        this.totalAmount = total;
    }
    public String getOrderSummary(){
        String result = "Order ID: " + orderId + ", " + "Customer: " + customer.getName() + ", " + "Total Amount: " + totalAmount + "\nItems: ";
        for (int i=0; i<items.size(); i++){
            if (i<items.size()-1){
                result += items.get(i).getName() + " - ";
            }

            else{
                result += items.get(i).getName();
            }
        }

        return result;
    }

}
