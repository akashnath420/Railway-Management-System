import java.io.*;
import java.util.*;

public class TrainReservationSystem {
    private static final List<Train> trains = new ArrayList<>();
    private static final String reservationsFile = "seats_reserved.txt";

    static {
        trains.add(new Train(1001, "Akash Express", "Dhaka", "Feni", 220, "9am"));
        trains.add(new Train(1002, "Shihab Express", "Dhaka", "Chittagong", 455, "12pm"));
        trains.add(new Train(1003, "Manha Express", "Dhaka", "Brahmanbaria", 180, "8am"));
        trains.add(new Train(1004, "Upoban Express", "Dhaka", "Rajshahi", 850, "11am"));
        trains.add(new Train(1005, "Mohanagar Express", "Dhaka", "Jashore", 612, "7am"));
        trains.add(new Train(1006, "Goduli Express", "Feni", "Chittagong", 150, "9.30am"));
        trains.add(new Train(1007, "Probati Express", "Feni", "Cumilla", 89, "1pm"));
        trains.add(new Train(1008, "Sonar Bangla Express", "Cox's Bazar", "Feni", 701, "4pm"));
        trains.add(new Train(1009, "Upokul Express", "Cox's Bazar", "Brahmanbaria", 750, "3.35pm"));
        trains.add(new Train(1010, "Arnob Express", "Dhaka", "Cox's Bazar", 990, "4.15pm"));
    }

    public static void viewDetails() {
        System.out.println("-----------------------------------------------------------------------------");
        System.out.printf("%-8s%-25s%-30s%-10s%-10s\n", "Tr.No", "Name", "Destinations", "Charges", "Time");
        System.out.println("-----------------------------------------------------------------------------");
        for (Train train : trains) {
            String destinations = train.source + " to " + train.destination;
            System.out.printf("%-8d%-25s%-30sTK.%-10.2f%-10s\n", train.number, train.name, destinations, train.charge, train.departureTime);
        }
    }

    public static void reservation() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Your Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Number of seats: ");
        int numOfSeats = sc.nextInt();
        viewDetails();
        System.out.print("Enter train number: ");
        int trainNum = sc.nextInt();

        Train selectedTrain = null;
        for (Train train : trains) {
            if (train.number == trainNum) {
                selectedTrain = train;
                break;
            }
        }

        if (selectedTrain == null) {
            System.out.println("Invalid train number!");
            return;
        }

        float charges = calculateCharge(selectedTrain, numOfSeats);
        printTicket(name, numOfSeats, selectedTrain, charges);

        System.out.print("Confirm Ticket (y/n): ");
        char confirm = sc.next().charAt(0);
        if (confirm == 'y') {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(reservationsFile, true))) {
                bw.write(name + "\t\t" + numOfSeats + "\t\t" + trainNum + "\t\t" + charges + "\n");
                System.out.println("Reservation Done");
            } catch (IOException e) {
                System.out.println("Error in reservation: " + e.getMessage());
            }
        } else {
            System.out.println("Reservation Not Done");
        }
    }

    public static void printTicket(String name, int numOfSeats, Train train, float charges) {
        System.out.println("--------------------------------------");
        System.out.println("|               TICKET               |");
        System.out.println("--------------------------------------");
        System.out.println("Name             :\t" + name);
        System.out.println("Number Of Seats  :\t" + numOfSeats);
        System.out.println("Train Number     :\t" + train.number);
        System.out.println("Train            :\t" + train.name);
        System.out.println("Destination      :\t" + train.source + " to " + train.destination);
        System.out.println("Departure        :\t" + train.departureTime);
        System.out.println("Charges          :\t" + charges +" TK");
    }


    public static float calculateCharge(Train train, int numOfSeats) {
        return train.charge * numOfSeats;
    }

    public static void cancelReservation() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter your name: ");
        String name = sc.nextLine();
        System.out.print("Enter the train number to cancel: ");
        int trainNum = sc.nextInt();

        List<String> reservations = new ArrayList<>();
        boolean reservationFound = false;

        // Read all reservations and store the ones that don't match the cancellation criteria
        try (BufferedReader br = new BufferedReader(new FileReader(reservationsFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\t\t");
                if (parts.length >= 3) {
                    String reservedName = parts[0];
                    int reservedTrainNum = Integer.parseInt(parts[2]);
                    if (reservedName.equals(name) && reservedTrainNum == trainNum) {
                        reservationFound = true;
                    } else {
                        reservations.add(line);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading reservations: " + e.getMessage());
            return;
        }

        if (reservationFound) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(reservationsFile))) {
                for (String reservation : reservations) {
                    bw.write(reservation + "\n");
                }
                System.out.println("Reservation for " + name + " on train number " + trainNum + " has been cancelled.");
            } catch (IOException e) {
                System.out.println("Error writing reservations: " + e.getMessage());
            }
        } else {
            System.out.println("No reservation found for " + name + " on train number " + trainNum);
        }
    }

    public static void viewReservations() {
        System.out.println("-----------------------------------------------------------------------------");
        System.out.printf("%-15s%-10s%-15s%-10s\n", "Name", "Seats", "Train Number", "Charges");
        System.out.println("-----------------------------------------------------------------------------");
        try (BufferedReader br = new BufferedReader(new FileReader(reservationsFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\t\t");
                if (parts.length >= 4) {
                    String name = parts[0];
                    String seats = parts[1];
                    String trainNum = parts[2];
                    String charges = parts[3];
                    System.out.printf("%-15s%-10s%-15s%-10sTK\n", name, seats, trainNum, charges);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading reservations: " + e.getMessage());
        }
    }
}
