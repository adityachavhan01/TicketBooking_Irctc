package ticket.booking.services;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import ticket.booking.entities.Train;

public class TrainBookingService {
	private List<Train> trainList;
	private ObjectMapper objectMapper = new ObjectMapper();
	private static final String TRAIN_DB_PATH = "app/src/main/java/ticket/booking/localDb/TrainDb.json";
	// constructors
	public TrainBookingService() throws IOException{
		File train = new File(TRAIN_DB_PATH);
		trainList = objectMapper.readValue(train,new TypeReference<List<Train>>() {});
	}
//	**************************************searchTrain***************************************************
	public List<Train> searchTrain(String src,String dest) {
		return trainList.stream().filter(train -> validTrain(train,src,dest)).collect(Collectors.toList());
	}
	public Boolean validTrain(Train train,String src,String dest) {
		List<String> stations = train.getStations();
		// if(stations == null || stations.isEmpty()) {
		// 	return false; // No stations available
		// }
		int indexOfSrc = stations.indexOf(src.toLowerCase());
		int indexOfDest = stations.indexOf(dest.toLowerCase());
		return indexOfSrc != -1 && indexOfDest != -1 && indexOfSrc < indexOfDest;
	}
//	***************************************addTrain*********************************************************
	public void addTrain(Train newTrain) {
		Optional<Train> existingTrain = trainList.stream()
                .filter(train -> train.getTrainId().equalsIgnoreCase(newTrain.getTrainId()))
                .findFirst();
		if (existingTrain.isPresent()) {
            // If a train with the same trainId exists, update it instead of adding a new one
            updateTrain(newTrain);
        } else {
            // Otherwise, add the new train to the list
            trainList.add(newTrain);
            saveTrainListToFile();
        }
	}
//	updateTrain
	public void updateTrain(Train updatedTrain) {
		 // Find the index of the train with the same trainId
		OptionalInt index = IntStream.range(0, trainList.size())
                .filter(i -> trainList.get(i).getTrainId().equalsIgnoreCase(updatedTrain.getTrainId()))
                .findFirst();
		if (index.isPresent()) {
            // If found, replace the existing train with the updated one
            trainList.set(index.getAsInt(), updatedTrain);
            saveTrainListToFile();
		}else {
            // If not found, treat it as adding a new train
            addTrain(updatedTrain);
        }
	}
//	saving train list to file
	private void saveTrainListToFile() {
        try {
            objectMapper.writeValue(new File(TRAIN_DB_PATH), trainList);
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception based on your application's requirements
        }
    }
}
