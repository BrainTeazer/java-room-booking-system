import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class FileMethod {
    private static String FILENAME = "rooms.txt";

    // read and store from FILENAME 
    public static Set<Room> readFile() {
        Set<Room> roomList = new HashSet<>();

        try {
            File file = new File(FileMethod.FILENAME);
            Scanner allRooms = new Scanner(file);

            while(allRooms.hasNextLine()) {
                String[] values = allRooms.nextLine().split(" ");
                
                Room newRoom = new Room(Integer.parseInt(values[0]),
                                        values[1], 
                                        Double.parseDouble(values[2]), 
                                        Boolean.parseBoolean(values[3]), 
                                        Boolean.parseBoolean(values[4]), 
                                        values[5]
                                    );

                roomList.add(newRoom);
            }

            allRooms.close();

        }
        catch (FileNotFoundException e) {
            System.out.println("Error occurred.");
            e.printStackTrace();
        }
        return roomList;
    }

    public void setFileName(String FILENAME) {
        FileMethod.FILENAME = FILENAME;
    }

    // save set of rooms to FILENAME
    public static void saveData(Set<Room> rooms) {
        File roomFile = new File(FileMethod.FILENAME);
        
        try {
            FileWriter fW = new FileWriter(roomFile, false);

            for (Room room : rooms) {
                fW.write(room.toString());
            }

            fW.close();
        }
        catch (IOException e) {
            System.out.println("Error occurred.");
            e.printStackTrace();
        }
        
    }

}
