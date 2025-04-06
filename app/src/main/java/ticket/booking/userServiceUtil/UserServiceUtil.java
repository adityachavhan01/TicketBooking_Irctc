package ticket.booking.userServiceUtil;

import org.mindrot.jbcrypt.BCrypt;

public class UserServiceUtil
{
    public static String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }

        // String password = "monkey123";

        // // Generate salt and hash password
        // String hashed = BCrypt.hashpw(password, BCrypt.gensalt(12));
        // System.out.println("Hashed password: " + hashed);

        // // Check password
        // boolean match = BCrypt.checkpw("monkey123", hashed);
        // System.out.println("Password match: " + match);
}