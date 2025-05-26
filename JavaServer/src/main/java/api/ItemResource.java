package api;

import Main.DBConnection;
import Main.Item;
import Main.ItemFilter;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Path("/items")
public class ItemResource {

    // Existing GET method (optional to keep)
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Item> getItems() {
        // Return all items, no filtering
        List<Item> items = new ArrayList<>();
        String sql = "SELECT * FROM items_found";

        try (Connection conn = DBConnection.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Item item = new Item();
                item.id = rs.getInt("id");
                item.name = rs.getString("name");
                item.floor = rs.getInt("floor");
                item.location = rs.getString("location");
                item.found_by = rs.getString("found_by");
                item.commission = rs.getDouble("commission");
                items.add(item);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;
    }

    // New POST method with filter JSON in request body
    @POST
    @Path("/search")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<Item> getItemsFiltered(ItemFilter filter) {
        List<Item> items = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM items_found WHERE 1=1");
        sql.append(" AND floor = ?");
        sql.append(" AND location = ?");

        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

            int paramIndex = 1;
            stmt.setInt(paramIndex++, filter.floor);
            stmt.setString(paramIndex++, filter.location);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Item item = new Item();
                item.id = rs.getInt("id");
                item.name = rs.getString("name");
                item.floor = rs.getInt("floor");
                item.location = rs.getString("location");
                item.found_by = rs.getString("found_by");
                item.commission = rs.getDouble("commission");
                items.add(item);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertItem(Item item) {
        String sql = "INSERT INTO items_found (name, floor, location, found_by, commission) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, item.name);
            stmt.setInt(2, item.floor);
            stmt.setString(3, item.location);
            stmt.setString(4, item.found_by);
            stmt.setDouble(5, item.commission);
            stmt.executeUpdate();

        } catch (SQLException e) {
            return Response.status(500).entity("Error: " + e.getMessage()).build();
        }

        return Response.status(201).entity("Item added.").build();
    }
}
