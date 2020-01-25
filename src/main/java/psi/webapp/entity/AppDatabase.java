package psi.webapp.entity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import psi.webapp.user.UserNotFoundException;
import psi.webapp.user.UserPasswordsHistoryEntry;

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
	private static final String SELECT_USER_BY_ID_AND_PASSWORD = "SELECT u.id, u.login FROM USERS u WHERE u.id = ? AND u.password = ?";
	private static final String SELECT_USER_PASSWORD_USAGE = "SELECT MAX(up.pass_no) as pass_no, NEXT_USER_PASSWORD_ID(?) - 1 as current_pass_no FROM USER_PASSWORDS up WHERE up.user_id = ? AND up.password = ?";

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

	public boolean hasUserSpecifiedPassword(Long userId, String password) throws ClassNotFoundException, SQLException {
		Class.forName(DRIVER);
		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
				PreparedStatement statement = connection.prepareStatement(SELECT_USER_BY_ID_AND_PASSWORD);) {
			statement.setLong(1, userId);
			statement.setString(2, password);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				return true;
			}
		}
		return false;
	}

	public UserPasswordsHistoryEntry getComparisonWithEarlierUserPasswords(Long userId, String password)
			throws SQLException, ClassNotFoundException {
		UserPasswordsHistoryEntry findedUser = null;

		Class.forName(DRIVER);
		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
				PreparedStatement statement = connection.prepareStatement(SELECT_USER_PASSWORD_USAGE);) {
			statement.setLong(1, userId);
			statement.setLong(2, userId);
			statement.setString(3, password);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				if (findedUser != null) {
					throw new IllegalStateException("Too much result for user password.");
				}
				findedUser = new UserPasswordsHistoryEntry(password, rs.getLong("pass_no"), rs.getLong("current_pass_no"));
			}
		}
		if (findedUser == null) {
			throw new IllegalStateException();
		}
		return findedUser;
	}

}
