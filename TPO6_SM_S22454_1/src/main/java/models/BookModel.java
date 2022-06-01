package models;

public class BookModel {

    private String name;
    private String description;

    public BookModel(String name, String description){
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription(){
        return description;
    }
}
