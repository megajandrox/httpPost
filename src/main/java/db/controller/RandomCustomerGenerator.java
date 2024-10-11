package db.controller;

public class RandomCustomerGenerator {
    public String getRandomUsername() {
        return "user" + Math.random();
    }

    public String getRandomEmail() {
        return "user" + Math.random() + "@gmail.com";
    }
}
