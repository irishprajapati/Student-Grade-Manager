package db;

import io.github.cdimascio.dotenv.Dotenv;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static Dotenv dotenv;

    private static void loadEnv() {
        if (dotenv == null) {
            dotenv = Dotenv.configure()
                    .ignoreIfMissing()
                    .load();
        }
    }

    public static Connection getConnection() throws SQLException {
        loadEnv();

        String url = dotenv.get("DB_URL");
        String user = dotenv.get("DB_USERNAME");
        String pass = dotenv.get("DB_PASSWORD");

        if (url == null || user == null || pass == null) {
            throw new RuntimeException("DB environment variables are missing!");
        }

        return DriverManager.getConnection(url, user, pass);
    }
}