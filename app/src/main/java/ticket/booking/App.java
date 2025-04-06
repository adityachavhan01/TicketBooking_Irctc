package ticket.booking;

import java.io.IOException;
import java.util.*;

import ticket.booking.entities.User;
import ticket.booking.entities.Train;
import ticket.booking.services.UserBookingService;
import ticket.booking.userServiceUtil.UserServiceUtil;

public class App {

    public static void main(String[] args) {
        String trainId = null;
        List<Train> trains = null;
        System.out.println("Running Train Booking System");
        Scanner scan = new Scanner(System.in);
        int option = 0;
        UserBookingService userBookingService = null;
        try {
            userBookingService = new UserBookingService();
        } catch (IOException ex) {
            System.out.println("Error in loading user data");
        }

        // Optional: check if initialization not succeeded
        if (userBookingService == null) {
            System.out.println("user data is not null");
        }

        while (option != 7) {
            System.out.println("Choose Option");
            System.out.println("1. Sign Up");
            System.out.println("2. Login");
            System.out.println("3.Fetch Booking");
            System.out.println("4. Search Trains");
            System.out.println("5. Book a Seat");
            System.out.println("6. Cancel my Booking currently not working");
            System.out.println("7. Exit the App");
            option = scan.nextInt();
            // Train trainSelectedForBooking = new Train();
            switch (option) {
                case 1:
                    System.out.println("Enter the username to signup");
                    String nameToSignUp = scan.next();
                    System.out.println("Enter the password to signup");
                    String passwordToSignUp = scan.next();
                    User userToSignUp = new User(nameToSignUp, passwordToSignUp,
                            UserServiceUtil.hashPassword(passwordToSignUp), new ArrayList<>(),
                            UUID.randomUUID().toString());
                    if (userBookingService.signUp(userToSignUp)) {
                        System.out.println("***********Sign Up Successful********************");
                    }
                    ;
                    break;
                case 2:
                    System.out.println("Enter the username to Login");
                    String nameToLogin = scan.next();
                    System.out.println("Enter the password to signup");
                    String passwordToLogin = scan.next();
                    User userToLogin = new User(nameToLogin, passwordToLogin,
                            UserServiceUtil.hashPassword(passwordToLogin), new ArrayList<>(),
                            UUID.randomUUID().toString());
                    try {
                        userBookingService = new UserBookingService(userToLogin);
                        System.out.println("*****************Login Successful************************");
                    } catch (IOException ex) {
                        System.out.println(
                                "***********Login Failed check your credintials and try again********************");
                        return;
                    }
                    break;
                case 3:
                    System.out.println("***********service under work**********");
                    break;
                case 4:
                    System.out.println("Search for trains");
                    System.out.println("Type you source station");
                    String src = scan.next();
                    System.out.println("Type you destination station");
                    String dest = scan.next();
                    trains = userBookingService.getTrains(src, dest);
                    for (Train train : trains) {
                        System.out.println("Train ID: " + train.getTrainId());
                        System.out.println("Stations " + train.getStationTimes());
                    }
                    System.out.println("Enter the train id to book a seat");
                    trainId = scan.next();
                    break;
                case 5:
                    // Initialize the selected train
                    Train selectedTrain = null;
                    // Search for the train with matching trainId
                    for (Train train : trains) {
                        if (train.getTrainId().equals(trainId)) {
                            selectedTrain = train;
                            break;
                        }
                    }
                    // Show seats only if a matching train was found
                    if (selectedTrain != null) {
                        List<List<Integer>> seatsBooked = userBookingService.fetchedSeats(selectedTrain);
                        System.out.println("Seats Booked:");
                        for (List<Integer> row : seatsBooked) {
                            System.out.println(row);
                        }
                    } else {
                        System.out.println("Invalid Train ID selected.");
                    }
                    // *****************************************************
                    System.out.println("Enter the row to book a seat");
                    int row = scan.nextInt();
                    System.out.println("Choose the seat to book");
                    int seat = scan.nextInt();
                    boolean booked = userBookingService.bookTrainSeat(selectedTrain, row, seat);
                    if(booked == true){
                        System.out.println("*********seat booked******");
                    }
                    else{
                        System.out.println("seat is not booked");
                    }
                    break;
                case 6:
                    System.out.println("service under work");
                    break;
                case 7:
                    System.out.println("Exiting the app");
                    break;

            }
        }
        scan.close();
    }
}
