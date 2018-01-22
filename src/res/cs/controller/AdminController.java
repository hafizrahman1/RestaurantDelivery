package res.cs.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import res.cs.bo.ItemBO;
import res.cs.bo.UserBO;
import res.cs.exception.RegistrationException;
import res.cs.model.Item;
import res.cs.model.User;

@Controller
public class AdminController {
	// Admin home page
	@RequestMapping(value="/", method=RequestMethod.GET)
	public ModelAndView adminHome() {
		ModelAndView model = new ModelAndView("AdminHome");
		return model;
	}
	
	// Admin account information
	@RequestMapping(value="/AdminAccountInfo", method=RequestMethod.GET)
	public ModelAndView adminAccount(HttpServletRequest request) {
		ModelAndView model = new ModelAndView("AdminAccountInfo");
		return model;
	}
	
	// Update Admin account information
	@RequestMapping(value="/AdminAccountInfo", method=RequestMethod.POST)
	public ModelAndView adminAccountUpdate(HttpServletRequest request) throws IOException {
		// Create a userBO variable
		UserBO userBO = new UserBO();
		ModelAndView model = new ModelAndView("AdminAccountInfo");
		// Get the session object from the request object
		HttpSession session = request.getSession();
		// Get the current user from the session
		User currentUser = (User) session.getAttribute("currentUser");
		// Get the user id from the session object
		int userId = (int) session.getAttribute("userId");
		
		if(currentUser != null) {
			// Read user info from the form data
			String firstName = request.getParameter("firstName");
			String lastName = request.getParameter("lastName");
			String userName = request.getParameter("userName");
			String password = request.getParameter("password");
			String gender = request.getParameter("gender");
			String address = request.getParameter("address");
			Long phoneNumber = Long.parseLong(request.getParameter("phoneNumber"));
			String email = request.getParameter("email");
			int result = 0;
			
			// Populate the current user with updated data
			currentUser.setUserId(userId);
			currentUser.setFirstName(firstName);
			currentUser.setLastName(lastName);
			currentUser.setUserName(userName);
			currentUser.setPassword(password);
			currentUser.setGender(gender);
			currentUser.setAddress(address);
			currentUser.setPhoneNumber(phoneNumber);
			currentUser.setEmail(email);
			
			// Need to check the user information validity
			// Update the user to the database
			try {
				userBO = new UserBO();
				result = userBO.updateUser(currentUser);
				if (result != 0) {
					model.addObject("message", "User has been updated successfully");
					session.setAttribute("currentUser", currentUser);
					System.out.println("User is updated");
				}

			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (RegistrationException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return model;
	}
	
	// Admin navigation page
	@RequestMapping(value="/AdminNavigate", method=RequestMethod.GET)
	public ModelAndView adminNavigate() {
		ModelAndView model = new ModelAndView("AdminNavigate");
		return model;
	}
	
	// Here "command" is a reserved request attribute which is used to display object data into form
	// Get the list of items
	@RequestMapping(value="/AdminItemsList", method=RequestMethod.GET)
	public ModelAndView adminItemsList() throws ClassNotFoundException, RegistrationException, SQLException, IOException {
		// Declare an ItemBO variable
		ItemBO itemBO = new ItemBO();
		ModelAndView model = new ModelAndView("AdminItemsList");
		Item theItem = new Item();
		model.addObject("item", theItem);
		
		// Get all the items from the database
		List<Item> itemsList = itemBO.getAllItems();
		// Add all the items list to the model
		model.addObject("itemsList", itemsList);
		
		// Return the view
		return model;
	}
	
	// Update an item based on the request parameter
	@RequestMapping(value="/UpdateItem", method=RequestMethod.POST)
	public ModelAndView adminUpdateItem(
			@RequestParam(value="name", required=true) String itemName,
			@RequestParam(value="price", required=true) Double itemPrice,
			@RequestParam(value="description", required=true) String itemDescription,
			@RequestParam(value="image", required=true) String itemImage,
			@RequestParam(value="active", required=true) Integer itemActive,
			@RequestParam(value="category", required=true) String itemCategory,
			@RequestParam(value="update", required=false) Integer itemUpdateId,
			@RequestParam(value="delete", required=false) Integer itemDeleteId) throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		
		// Declare a model and view variable
		ModelAndView model = null;
		// Declare an ItemBO variable
		ItemBO itemBO = new ItemBO();
		// For item update/delete operation
		if (itemUpdateId != null) {
			// Declare an Item variable
			Item theItem = new Item();
			theItem.setItemId(itemUpdateId);
			theItem.setItemName(itemName);
			theItem.setItemPrice(itemPrice);
			theItem.setItemDescription(itemDescription);
			theItem.setImage(itemImage);
			theItem.setActive(itemActive);
			theItem.setCategory(itemCategory);
			
			// Now update the item in the database
			int result = itemBO.updateItem(theItem);
			
			// Call the adminItemsList() to get the updated model
			model = adminItemsList();
			if (result != 0) {
				// Add the updated message to the model
				model.addObject("message", "Item Updated Successfully");
			}
			
		} else if (itemDeleteId != null) {
			// Now delete the item
			int result = itemBO.deleteItem(itemDeleteId);
			// Call the adminItemsList() to get the updated model
			model = adminItemsList();
			if(result != 0) {
				// Add the deleted message to the model
				model.addObject("message", "Item Deleted Successfully");
			}
		} else {
			// Display the error
			model = new ModelAndView("/AdminError");
		}
		
		// Return the view
		return model;
	}
	
    //The @ModelAttribute puts request data into model object.  
    @RequestMapping(value="/CreateItem",method = RequestMethod.POST)  
    public ModelAndView adminCreateItem(@ModelAttribute("item") Item item) throws ClassNotFoundException, RegistrationException, IOException, SQLException{  
        // Declare an ItemBO variable
    	ItemBO itemBO = new ItemBO();
    	ModelAndView model = null;
    	// Create the new item
    	int result = itemBO.createItem(item);
    	if (result != 0) {
			// Call the adminItemsList() to get the updated model
    		model = adminItemsList();
    		// Add the created message to the model
    		model.addObject("message", "Item Craeted Successfully");
    	} else{
    		// Display the error
    		model =  new ModelAndView("AdminError");	
    	}
    	// Return the view
    	return model;
    } 
}