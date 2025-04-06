package ticket.booking.entities;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Builder
public class Train {


	private String trainId;
	private String trainNo;
	@JsonProperty("seats_Booked")
	private List<List<Integer>> seatsBooked;
	private Map<String,String> stationTimes;
	private List<String> stations;

	
	public Train(String trainId, String trainNo,List<List<Integer>> seatsBooked, 
			Map<String, String> stationTimes, List<String> station) {
		
		this.trainId = trainId;
		this.trainNo = trainNo;
		this.seatsBooked = seatsBooked;
		this.stationTimes = stationTimes;
		this.stations = station;
	}
	
	public Train() {}
	
	public  String getTrainInfo() {
		return String.format("Train Id: %s Train No:%s",trainId,trainNo);
	}
	
	public String getTrainId() {
		return trainId;
	}
	public void setTrainId(String trainId) {
		this.trainId = trainId;
	}
	public String getTrainNo() {
		return trainNo;
	}
	public void setTrainNo(String trainNo) {
		this.trainNo = trainNo;
	}
	public List<List<Integer>> getSeatsBooked() {
		return seatsBooked;
	}
	public void setSeatsBooked(List<List<Integer>> seatsBooked) {
		this.seatsBooked = seatsBooked;
	}
	public Map<String, String> getStationTimes() {
		return stationTimes;
	}
	public void setStationTimes(Map<String, String> stationTimes) {
		this.stationTimes = stationTimes;
	}
	public List<String> getStations() {
		return stations;
	}
	public void setStations(List<String> station) {
		this.stations = station;
	}
	
	
	
}
