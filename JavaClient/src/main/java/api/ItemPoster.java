package api;
import org.json.JSONObject;

public class ItemPoster {

    public static String insertItem(String name, int floor, String location, int foundBy) {
        try {
            // Create JSON payload
            JSONObject json = new JSONObject();
            json.put("name", name);
            json.put("floor", floor);
            json.put("location", location);
            json.put("found_by", foundBy);
            json.put("commission", 0);

            // POST request to /items endpoint
            return ApiClient.post("/items", json.toString());

        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }
}