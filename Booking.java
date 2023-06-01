import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.Map.Entry;
import java.util.stream.Collectors;


public class Booking {
    final static private int roomTypeWeight = 4;
    final static private int roomPriceWeight = 3;
    final static private int hasBalconyWeight = 1;
    final static private int hasLoungeWeight = 1;
    final static private int maxMatch = roomPriceWeight + roomPriceWeight + hasBalconyWeight + hasLoungeWeight;
    
    // Initial message
    public static void startMsg() {
        System.out.println("- - Room Booking System - -\n" +
                            "NOTE: Use C after choosing a number to cancel during the process.\n\n" +
                            "- - MAIN MENU - -\n" +
                            "1 - Reserve Room\n" +
                            "2 - Cancel Room\n" +
                            "3 - View Room Reservations\n" +
                            "Q - Quit\n" +
                            "Pick : "
                        );
    }

    
    // get all rooms under the provided email
    public static Set<Room> getUserRooms(Set<Room> allRooms, String eMail) {
        Set<Room> matchedRooms = new HashSet<>();
        for (Room room : allRooms) {
            if(room.getEMail().equals(eMail)){
                matchedRooms.add(room);
            }
        }
        return matchedRooms;
    }

    

    public static Set<Room> reserveRoom(Set<Room> allRooms, String eMail) {
        Map<Room, Integer> roomWeight = new HashMap<>();

        System.out.println("- - Room Type - -\n\n");
        int i = 1;

        // print room types
        for (roomTypes roomtype: roomTypes.values()) {
            System.out.printf("%d - %s\n", i, roomtype);
            i++;
        }

        System.out.println("C - Cancel\n");

        System.out.println("Pick: ");
        
        Scanner sc = new Scanner(System.in);
        String roomType = new String();

        // get roomtype
        while(true) {
            String option = sc.nextLine();

            switch(option) {
                case "C":
                    return allRooms;
                case "1":
                case "2":
                case "3":
                    int roomTypeIndex = Integer.parseInt(option);
                    roomType = roomTypes.values()[roomTypeIndex-1].toString(); // index into roomTypes enum and get value
                    break;
                default:
                    System.out.println("Pick again: ");
                    continue;
            }
            break;
        }

        // get roomprice
        double price;
        while(true) {
            System.out.println("What price? ");
            try {
                String option = sc.nextLine();
                if (option.equals("C")) {
                    return allRooms;
                }
                price = Double.parseDouble(option);
                break;
            }
            catch(NumberFormatException ignore) {
                System.err.println("Invalid input");
            }
        }

        // ask if user wants balcony
        boolean hasBalcony;
        while(true) {
            System.out.println("Do you need a balcony (Y/N/C)? ");
            String val = sc.nextLine().toUpperCase();
            if (val.equals("Y")) {
                hasBalcony = true;
                break;
            } else if (val.equals("N")) {
                hasBalcony = false;
                break;
            } else if (val.equals("C")) {
                return allRooms;
            }
            System.out.println("Invalid input.");
        }

        // ask if user wants lounge
        boolean hasLounge;
        while(true) {
            System.out.println("Do you need a lounge (Y/N/C)? ");
            String val = sc.nextLine().toUpperCase();
            if (val.equals("Y")) {
                hasLounge = true;
                break;
            } else if (val.equals("N")) {
                hasLounge = false;
                break;
            } else if (val.equals("C")) {
                return allRooms;
            }
            System.out.println("Invalid input.");
        }

        /* 
            Each criteria has its own weight. 
            For each matched criteria the room is given a higher weightage, then sorted (higher weightage at the top).
            Weights of criteria (initialized above):
                - roomType: 4
                - roomPrice = 3
                - hasBalcony = 1
                - hasLounge = 1
        */ 
        int largestMatch = 0;
        System.out.println("Rooms that match your requirements. Pick one: ");
        for (Room room : allRooms) {
            int match = 0;
            if (!room.getEMail().equals("free")) {
                continue;
            }
            if(room.getRoomType().equals(roomType)) {
                match+=roomTypeWeight;
            }
            if (room.getRoomPrice() == price) {
                match+=roomPriceWeight;
            }
            if (room.getHasBalcony() == hasBalcony) {
                match+=hasBalconyWeight;
            }
            if(room.getHasLounge() == hasLounge){
                match+=hasLoungeWeight;
            }
            
            roomWeight.put(room, match);  

            // Check the largest match to let user know if any free room's have all criteria matched
            largestMatch = (match > largestMatch) ? match : largestMatch;     

        }

        if (largestMatch < maxMatch) {
            System.out.println("There are no rooms that match all your criteria.\nListed are rooms with the next most criteria met.");
        }

        // sorting the map based on the key (i.e. the weight) in descending order
        roomWeight = roomWeight
                        .entrySet()
                        .stream()
                        .sorted(Entry.comparingByValue(Comparator.reverseOrder()))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2)->e1, LinkedHashMap::new));

        
        // print sorted free rooms
        roomWeight.forEach(
            (k,v) -> {
                System.out.printf(
                                    "Room number: %d\tRoom Type: %s\tPrice: %.2f\tBalcony: %b\tLounge: %b\n", 
                                    k.getRoomNum(), 
                                    k.getRoomType(), 
                                    k.getRoomPrice(), 
                                    k.getHasBalcony(), 
                                    k.getHasLounge()
                                );
            });   
        

        // get room number from User for reservation
        int rN;
        while(true) {
            System.out.println("Choose (room number): ");  
            try {          
                String option = sc.nextLine();
                if (option.equals("C")) {
                    return allRooms;
                }
                rN = Integer.parseInt(option);
                break;
            } catch(NumberFormatException ignore) {
                System.out.println("Invalid input");
            }
        }
        
        // change email address of chosenRoom
        for (Room room : roomWeight.keySet()) {
            if (room.getRoomNum() == rN) {
                Room chosenRoom = room;
                allRooms.remove(chosenRoom);
                chosenRoom.setEMail(eMail);
                allRooms.add(chosenRoom);
                break;
            }
        }

        return allRooms;
       
    }

    public static Room getChosenRoom(Set<Room> allRooms, String eMail) {

        while(true){

            // get all rooms reserved by user and print
            Set<Room> matchedRooms = getUserRooms(allRooms, eMail);
            System.out.println("Choose a room: ");

            matchedRooms.forEach((room) -> {
                System.out.printf("Room number - %d\n\n", room.getRoomNum());
            });
            
            System.out.println("C: Close");
            System.out.println("Choice (Room Number): ");

            Scanner sc = new Scanner(System.in);
            String option = new String();
            Room chosenRoom = new Room();

            while(true) {
                option = sc.nextLine();

                // cancel
                if (option.equals("C")) {
                    return chosenRoom;
                }

                try {
                    boolean isRoomNumber = false;
                    int roomIndex = Integer.parseInt(option);

                    // get room chosen by user based on the roomNumber
                    for (Room room: matchedRooms) {
                        if (room.getRoomNum() == roomIndex) {
                            chosenRoom = room;
                            isRoomNumber = true;
                            break;
                        }
                    }  

                    // check for valid roomnumber
                    if (!isRoomNumber) {
                        System.out.println("Invalid room number.");
                        continue;
                    }
                    return chosenRoom;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input");
                    continue;
                }
                
            }}
    }


    public static Set<Room> cancelRoom(Set<Room> allRooms, String eMail) {
        Room chosenRoom = getChosenRoom(allRooms, eMail);
        if (!allRooms.remove(chosenRoom)){
            return allRooms;
        }
        chosenRoom.setEMail("free");
        allRooms.add(chosenRoom);
        return allRooms;
    }


    public static void viewReservations(Set<Room> allRooms, String eMail) {
        Scanner sc = new Scanner(System.in);
        while(true) {
            Room chosenRoom = getChosenRoom(allRooms, eMail);
            if (!allRooms.contains(chosenRoom)) {
                return;
            }

            // get detailed information about chosen room
            System.out.printf(chosenRoom.toString());
            System.out.println("C - Close");
            System.out.println("B - Back\n");
            System.out.println("Choice: ");

            while(true) {
                String option = sc.nextLine().toUpperCase();
                switch(option) {
                    case "C":
                        return;
                    case "B":
                        break;
                    default:
                        System.out.println("Invalid input.");
                        continue;
                }
                break;
            }
            }
            
    }

    public static void main(String[] args) {
        Set<Room> allRooms = FileMethod.readFile();
        String eMail = Email.getEmail();
        Scanner sc = new Scanner(System.in);
       
        while(true) {
            startMsg();
            char option = sc.next().charAt(0);

            switch (option) {
                case '1':
                    allRooms = reserveRoom(allRooms, eMail);
                    continue;
                
                case '2':
                    allRooms = cancelRoom(allRooms, eMail);
                    break;

                case '3':
                    viewReservations(allRooms, eMail);
                    break;

                case 'Q':
                    FileMethod.saveData(allRooms);
                    sc.close();
                    return;

                default:
                    continue;
            }
            

        }

    }
}
