package ticket.booking.services;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import ticket.booking.entities.Train;
import ticket.booking.entities.User;
import ticket.booking.userServiceUtil.UserServiceUtil;

public class UserBookingService {
	
	private User user;
	private static final String USERS_PATH = "app/src/main/java/ticket/booking/localDb/userDb.json";
	private List<User> userList;
	private ObjectMapper  objectMapper = new ObjectMapper();
	
	//	************************************constructors*************************************************
	//	constructor if no user is there in userList
		public UserBookingService() throws IOException {
			loadUserFromList();		
		}
	//	constructor if user is sign-up already
		public UserBookingService(User user) throws IOException {
			this.user	= user;
			loadUserFromList();
		}
		
	private void loadUserFromList() throws IOException {
	    File users = new File(USERS_PATH);
	    if (!users.exists()) {
	        System.out.println("User database file not found. Creating a new one.");
	        userList = new ArrayList<>();
	        saveUserListToFile(); // Create an empty file if it doesn't exist
	    } else if (users.length() == 0) {
	        System.out.println("User database file is empty. Initializing with an empty list.");
	        userList = new ArrayList<>();
	    } else {
	        try {
	            userList = objectMapper.readValue(users, new TypeReference<List<User>>() {});
	        } catch (IOException ex) {
	            System.out.println("Error reading user database file. Initializing with an empty list.");
	            userList = new ArrayList<>();
	        }
	    }
	}
//	*************************************login function******************************************************
//	login user function
//	filter takes a integer/ lamb-ada function as a input and give boolean output
//  user1 is from the userList and user is the one which is trying to login
	public Boolean loginUser(User user1) {
		Optional<User> foundUser = userList.stream().filter(user11 -> {
			return user1.getName().equals(user.getName()) &&
					UserServiceUtil.checkPassword(user.getPassword(),user1.getPassword());
		}).findFirst();
		return foundUser.isPresent();
	}
//	***************************************sign-up function**************************************
//	sign-up function
	public Boolean signUp(User user1) {
		try {
			userList.add(user1);
			saveUserListToFile();
			return Boolean.TRUE;
		}catch(IOException ex) {
			return Boolean.FALSE;
			
		}
	}
//	Serializing the user-data using objectMapper
	public void saveUserListToFile() throws IOException {
		File usersFile = new File(USERS_PATH);
		objectMapper.writeValue( usersFile,userList);
	}
	
//	**************************************fetch booking function*********************************
	public void fetchBooking() {
		
	}
//	************************************cancel booking service***********************************
	public Boolean cancelBooking() {
		return false;
	}
//	************************************getTrains function *************************************
	public List<Train> getTrains(String source,String destination){
		try {
			TrainBookingService trainService = new TrainBookingService();
			return trainService.searchTrain(source,destination);
		}
		catch(IOException ex) {
			return new ArrayList<>();
		}
	}
// ****************fetching seats booked*************
	public List<List<Integer>> fetchedSeats(Train train){
		return train.getSeatsBooked();
	}
//	***********************************book train seat *****************************************
	public Boolean bookTrainSeat(Train train,int row,int seat) {
		try {
			TrainBookingService trainService = new TrainBookingService();
			List<List<Integer>> seats = train.getSeatsBooked();
			if(row >=0 && row < seats.size() && seat >=0  && seat < seats.get(row).size()) {
				if(seats.get(row).get(seat) == 0) {
					seats.get(row).set(seat,1);
					train.setSeatsBooked(seats);
					trainService.addTrain(train);
					return true;
				}
				else {
					return false; // seat is already booked
				}
			}
			else {
				return false; // invalid row or seat index
			}
			
		}catch(IOException ex) {
			return false;
		}
	}
}
