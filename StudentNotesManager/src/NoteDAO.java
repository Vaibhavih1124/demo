import java.sql.*;
import java.util.ArrayList;

public class NoteDAO {

    public static void createTable() {
        String sql = """
                    CREATE TABLE IF NOT EXISTS notes (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        title TEXT,
                        content TEXT,
                        important INTEGER
                    )
                """;

        try (Connection con = DBConnection.connect();
                Statement st = con.createStatement()) {
            st.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addNote(String title, String content, boolean important) {
        String sql = "INSERT INTO notes(title, content, important) VALUES (?, ?, ?)";

        try (Connection con = DBConnection.connect();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, title);
            ps.setString(2, content);
            ps.setInt(3, important ? 1 : 0);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<String[]> getAllNotes() {
        ArrayList<String[]> list = new ArrayList<>();
        String sql = "SELECT * FROM notes";

        try (Connection con = DBConnection.connect();
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new String[] {
                        rs.getString("id"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getString("important")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static ArrayList<String[]> searchNotes(String keyword) {
        ArrayList<String[]> list = new ArrayList<>();
        String sql = "SELECT * FROM notes WHERE title LIKE ?";

        try (Connection con = DBConnection.connect();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new String[] {
                        rs.getString("id"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getString("important")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void updateNote(int id, String title, String content, boolean important) {
        String sql = "UPDATE notes SET title=?, content=?, important=? WHERE id=?";

        try (Connection con = DBConnection.connect();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, title);
            ps.setString(2, content);
            ps.setInt(3, important ? 1 : 0);
            ps.setInt(4, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteNote(int id) {
        String sql = "DELETE FROM notes WHERE id=?";

        try (Connection con = DBConnection.connect();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}