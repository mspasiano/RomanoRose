package it.spasia.model;

public class Group {
    private static final long serialVersionUID = 1L;
    private long id;
    private long id_card;
    private String name;
    private String window;

    public Group(long id, long id_card, String name, String window) {
        this.id = id;
        this.id_card = id_card;
        this.name = name;
        this.window = window;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId_card() {
        return id_card;
    }

    public void setId_card(long id_card) {
        this.id_card = id_card;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWindow() {
        return window;
    }

    public void setWindow(String window) {
        this.window = window;
    }
}
