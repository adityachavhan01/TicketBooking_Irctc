package ticket.booking.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming (PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
	private String name;
	private String password;
	private String hashPassword;
	private List<Ticket> ticketsBooked;
	private String userId;
	
	
	public User( String name, String password, String hashPassword, List<Ticket> ticketsBooked,String userId) {
		super();
		this.name = name;
		this.password = password;
		this.hashPassword = hashPassword;
		this.ticketsBooked = ticketsBooked;
		this.userId = userId;
	}
	public User() {}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getHashPassword() {
		return hashPassword;
	}
	public void setHashPassword(String hashPassword) {
		this.hashPassword = hashPassword;
	}
	public List<Ticket> getTicketsBooked() {
		return ticketsBooked;
	}
	public void setTicketsBooked(List<Ticket> ticketsBooked) {
		this.ticketsBooked = ticketsBooked;
	}
	public void printTickets() {
		for(int i=0;i<ticketsBooked.size();i++) {
			System.out.println(ticketsBooked.get(i).getTicketInfo());
		}
	}
}
