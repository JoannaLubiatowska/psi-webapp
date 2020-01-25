package psi.webapp.entity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import psi.webapp.user.UserNotFoundException;

public class AppDatabase {

	private static final String URL = "jdbc:oracle:thin:@localhost:1521:orcl";
	private static final String USERNAME = "\"oracle_app\"";
	private static final String PASSWORD = "praktyka";
	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";

	private static final String INSERT_USER_QUERY = "INSERT INTO USERS(login, password) VALUES (?, ?)";
	private static final String SELECT_USER_BY_CREDENTIALS = "SELECT u.id, u.login FROM USERS u WHERE u.login = ? AND u.password = ?";
	private static final String SELECT_USER_BY_ID = "SELECT u.id, u.login FROM USERS u WHERE u.id = ?";
	private static final String UPDATE_USER_QUERY = "UPDATE USERS SET login = ?, password = ? WHERE id = ?";
	private static final String SELECT_USER_BY_LOGIN = "SELECT u.id, u.login FROM USERS u WHERE u.login = ?";

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

	public User getUserByCredentials(String login, String password)
			throws UserNotFoundException, SQLException, ClassNotFoundException {
		User loggedUser = null;

		Class.forName(DRIVER);
		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
				PreparedStatement statement = connection.prepareStatement(SELECT_USER_BY_CREDENTIALS);) {
			statement.setString(1, login);
			statement.setString(2, password);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				if (loggedUser != null) {
					throw new IllegalStateException("Credentials passed for more than one user.");
				}
				loggedUser = User.builder().id(rs.getLong("id")).login(rs.getString("login")).build();
			}
		}
		if (loggedUser == null) {
			throw new UserNotFoundException();
		}
		return loggedUser;
	}

	public User getUserById(Long userId)
			throws UserNotFoundException, SQLException, ClassNotFoundException {
		User findedUser = null;

		Class.forName(DRIVER);
		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
				PreparedStatement statement = connection.prepareStatement(SELECT_USER_BY_ID);) {
			statement.setLong(1, userId);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				if (findedUser != null) {
					throw new IllegalStateException("Credentials passed for more than one user.");
				}
				findedUser = User.builder().id(rs.getLong("id")).login(rs.getString("login")).build();
			}
		}
		if (findedUser == null) {
			throw new UserNotFoundException();
		}
		return findedUser;
	}

	public void update(User user) throws SQLException, ClassNotFoundException {
		Class.forName(DRIVER);
		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
				PreparedStatement statement = connection.prepareStatement(UPDATE_USER_QUERY);) {
			statement.setString(1, user.getLogin());
			statement.setString(2, user.getPassword());
			statement.setLong(3, user.getId());
			statement.executeUpdate();
		}
	}

	public boolean isUserWithLogin(String login) throws ClassNotFoundException, SQLException {
		Class.forName(DRIVER);
		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
				PreparedStatement statement = connection.prepareStatement(SELECT_USER_BY_LOGIN);) {
			statement.setString(1, login);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				return true;
			}
		}
		return false;
	}

}
