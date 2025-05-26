public class item {
    private int id;  // Тус бүрийн зүйлд оноогдсон өвөрмөц дугаар
    private String name;  // Зүйлийн нэр
    private String description;  // Тайлбар
    private String floor;  // Давхар
    private String dateFound;  // Олсон огноо

    public item(int id, String name, String description, String floor, String dateFound) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.floor = floor;
        this.dateFound = dateFound;
    }

    // Мэдээлэл авах ба тохируулах геттер, сеттерүүд
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getFloor() { return floor; }
    public void setFloor(String floor) { this.floor = floor; }

    public String getDateFound() { return dateFound; }
    public void setDateFound(String dateFound) { this.dateFound = dateFound; }

    @Override
    public String toString() {
        return id + ": " + name + " (" + floor + ")";
    }
}
