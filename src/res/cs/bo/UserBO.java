package res.cs.bo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import res.cs.dao.UserDAO;
import res.cs.exception.RegistrationException;
import res.cs.model.User;

public class UserBO {

	//Create user by calling the UserDAO
	public int createUser(User user) throws IOException, RegistrationException, SQLException, ClassNotFoundException {
		final UserDAO userDAO = new UserDAO();
		Integer userId = null;
		try {
			userId = userDAO.createUser(user);
			
		}catch(RegistrationException e) {
			throw new RegistrationException(e.getMessage());
		}
		return userId;
	}
	
	//Get the user object by the user name using the UserDAO
	public User getUser(String userName) throws RegistrationException, SQLException, ClassNotFoundException, IOException{
		final UserDAO userDAO = new UserDAO();
		User user = null;
		try {
			user = userDAO.getUser(userName);
		}catch(RegistrationException e) {
			throw new RegistrationException(e.getMessage());
		}
		return user;
	}
	
	//Get all the users list using UserDAO
	public List<User> getAllUsers() throws RegistrationException, SQLException, ClassNotFoundException, IOException{
		final UserDAO userDAO = new UserDAO();
		List<User> usersList = null;
		try {
			usersList = userDAO.getAllUsers();
		}catch(RegistrationException e) {
			throw new RegistrationException(e.getMessage());
		}
		return usersList;	
	}
	
	//Update the user using the UserDAO
	public void updateUser(User user) throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		final UserDAO userDAO = new UserDAO();
		try {
			userDAO.updateUser(user);
		}catch(RegistrationException e) {
			throw new RegistrationException(e.getMessage());
		}
	}
	
}
