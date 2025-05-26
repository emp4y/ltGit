import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AppController {

    final private List<item> items;  // Зүйлийн жагсаалт
    private int nextId;  // Дараагийн өвөрмөц дугаар үүсгэхэд ашиглана

    public AppController() {
        items = new ArrayList<>();
        nextId = 1;
    }

    // Шинэ зүйл нэмэх
    public item addItem(String name, String description, String floor, String dateFound) {
        item newItem = new item(nextId++, name, description, floor, dateFound);
        items.add(newItem);
        return newItem;
    }

    // ID-аар зүйл устгах
    public boolean deleteItem(int id) {
        Optional<item> itemToDelete = items.stream()
                                          .filter(item -> item.getId() == id)
                                          .findFirst();
        if (itemToDelete.isPresent()) {
            items.remove(itemToDelete.get());
            return true;
        }
        return false;
    }

    // Бүх зүйлийг авах
    public List<item> getItems() {
        return items;
    }
}
