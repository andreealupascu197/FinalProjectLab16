package ro.fasttrackit.finalproject.service;

public class InvalidName extends RuntimeException {
    private String name;
    public InvalidName(String name) {
        this.name =name;
    }
    public String getName() {
        return name;
    }
}
