package api;

import Main.DBConnection;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Path("/users")
public class UserResource {

    public static class AuthRequest {
        public String username;
        public String password;
    }

    public static class AuthResponse {
        public boolean authenticated;
        public AuthResponse(boolean authenticated) {
            this.authenticated = authenticated;
        }
    }

    @POST
    @Path("/authenticate")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response authenticateUser(AuthRequest request) {
        String sql = "SELECT password_hash FROM users WHERE username = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, request.username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String storedPassword = rs.getString("password_hash");

                    // For demo: plain text compare; replace with hashed password check in production
                    boolean isAuthenticated = storedPassword.equals(request.password);
                    return Response.ok(new AuthResponse(isAuthenticated)).build();
                } else {
                    // Username not found
                    return Response.ok(new AuthResponse(false)).build();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Database error: " + e.getMessage())
                    .build();
        }
    }
}
