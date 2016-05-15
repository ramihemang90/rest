package in.hemangrami.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import in.hemangrami.model.Message;
import in.hemangrami.model.User;

public class DBUtils {

	public static Connection getConnection() throws Exception {

		Connection sqlConnection = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			sqlConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/webserviceapp", "root",
					"sysadmin");
		} catch (Exception ex) {
			throw ex;
		}
		return sqlConnection;
	}

	public static boolean authenticateUser(User user) {
		boolean isValid = false;
		Connection connection = null;

		try {
			connection = getConnection();
			String query = "select id,username,password from users where username=? and password =?";
			if (connection != null) {
				PreparedStatement statement = connection.prepareStatement(query);
				statement.setString(1, user.getUsername());
				statement.setString(2, user.getPassword());

				ResultSet resultSet = statement.executeQuery();
				while (resultSet.next()) {
					int userId = resultSet.getInt(1);
					String username = resultSet.getString(2);
					String password = resultSet.getString(3);
					if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
						user.setId(userId);
						isValid = true;
						break; // only one record search will be done.
					}
				}
			}
		} catch (Exception ex) {
			System.out.println("something went wrong while performing authenticateUser operation. Possible reason : "
					+ ex.getMessage());
		} finally {
			try {
				if (connection != null && !connection.isClosed()) {
					connection.close();
				}
			} catch (SQLException ex) {

			}
		}
		return isValid;
	}

	public static List<String> getAssociatedCustomerEmails(int userId) {
		List<String> emailAddresses = new ArrayList<String>();
		Connection connection = null;

		try {
			connection = getConnection();
			String query = "select c.email_address from customers c,user_customer_mappings m where c.id=m.customer_id and m.user_id=? ";
			if (connection != null) {
				PreparedStatement statement = connection.prepareStatement(query);
				statement.setInt(1, userId);
				ResultSet rs = statement.executeQuery();
				while (rs.next()) {
					String emailAddress = rs.getString(1);
					emailAddresses.add(emailAddress);
				}

			}
		} catch (Exception ex) {
			System.out.println(
					"something went wrong while performing AssociatedCustomersEmail operation. Possible reason : "
							+ ex.getMessage());

		} finally {
			try {
				if (connection != null && !connection.isClosed()) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return emailAddresses;
	}

	public static List<String> getAssociatedCustomerMobiles(int userId) {
		List<String> mobileNumbers = new ArrayList<String>();
		Connection connection = null;

		try {
			connection = getConnection();
			String query = "select c.mobile from customers c,user_customer_mappings m where c.id=m.customer_id and m.user_id=? ";
			if (connection != null) {
				PreparedStatement statement = connection.prepareStatement(query);
				statement.setInt(1, userId);
				ResultSet rs = statement.executeQuery();
				while (rs.next()) {
					String mobile = rs.getString(1);
					mobileNumbers.add(mobile);
				}

			}
		} catch (Exception ex) {
			System.out.println(
					"something went wrong while performing AssociatedCustomersEmail operation. Possible reason : "
							+ ex.getMessage());

		} finally {
			try {
				if (connection != null && !connection.isClosed()) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return mobileNumbers;
	}

	public static Message prepareOrgEmailMessage(boolean isCancel, int userId) {

		Connection connection = null;
		Message message = new Message();
		try {
			String query = "";

			if (isCancel) {
				query = "select cancel_email_subject,cancel_email_template from organizations o, users u where o.id=u.org_id and u.id=?";
			} else {
				query = "select send_email_subject,send_email_template from organizations o, users u where o.id=u.org_id and u.id=?";
			}

			connection = getConnection();
			if (connection != null) {
				PreparedStatement statement = connection.prepareStatement(query);
				statement.setInt(1, userId);
				ResultSet rs = statement.executeQuery();
				while (rs.next()) {
					String subject = rs.getString(1);
					String template = rs.getString(2);
					message.setSubject(subject);
					message.setMessage(template);
					break;
				}

			}

		} catch (Exception ex) {
			System.out.println(
					"something went wrong while performing prepareOrgEmailMessage operation. Possible reason : "
							+ ex.getMessage());
		} finally {
			try {
				if (connection != null && !connection.isClosed()) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return message;

	}

	public static Message prepareOrgSMSMessage(boolean isCancel, int userId) {

		Connection connection = null;
		Message message = new Message();
		try {
			String query = "";

			if (isCancel) {
				query = "select cancel_sms_template from organizations o, users u where o.id=u.org_id and u.id=?";
			} else {
				query = "select send_sms_template from organizations o, users u where o.id=u.org_id and u.id=?";
			}

			connection = getConnection();
			if (connection != null) {
				PreparedStatement statement = connection.prepareStatement(query);
				statement.setInt(1, userId);
				ResultSet rs = statement.executeQuery();
				while (rs.next()) {
					String template = rs.getString(1);
					message.setMessage(template);
					break;
				}

			}

		} catch (Exception ex) {
			System.out.println(
					"something went wrong while performing prepareOrgEmailMessage operation. Possible reason : "
							+ ex.getMessage());
		} finally {
			try {
				if (connection != null && !connection.isClosed()) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return message;

	}

	
}
