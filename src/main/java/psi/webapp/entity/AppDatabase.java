package psi.webapp.entity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AppDatabase {

	private static final String URL = "jdbc:oracle:thin:@localhost:1521:orcl";
	private static final String USERNAME = "\"oracle_app\"";
	private static final String PASSWORD = "praktyka";
	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";

	private static final String INSERT_USER_QUERY = "INSERT INTO USERS(login, password) VALUES (?, ?)";

	private static AppDatabase instance;

	public static AppDatabase getInstance() {
		if (instance != null) {
			return instance;
		}
		return new AppDatabase();
	}

	private AppDatabase() {

	}

	public void save(User user) throws SQLException, ClassNotFoundException {
		Class.forName(DRIVER);
		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
				PreparedStatement statement = connection.prepareStatement(INSERT_USER_QUERY);) {
			statement.setString(1, user.getLogin());
			statement.setString(2, user.getPassword());
			statement.executeUpdate();
		}
	}

}
