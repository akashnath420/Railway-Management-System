import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Train Reservation System!");

        // Login or sign-up
        boolean loggedIn = false;
        while (!loggedIn) {
            System.out.println("\n1. Login\n2. Sign-up\n3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    loggedIn = Login.login();
                    break;
                case 2:
                    Login.signUp();
                    break;
                case 3:
                    System.out.println("Exiting...");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice!");
            }
        }

        // Proceed with reservation system
        while (true) {
            System.out.println("\n=========================");
            System.out.println("   TRAIN RESERVATION SYSTEM");
            System.out.println("=========================");
            System.out.println("1. Reserve A Ticket");
            System.out.println("2. View All Available Trains");
            System.out.println("3. Cancel Reservation");
            System.out.println("4. View All Reservations");
            System.out.println("5. Logout");
            System.out.print("Enter your choice: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (option) {
                case 1:
                    TrainReservationSystem.reservation();
                    break;
                case 2:
                    TrainReservationSystem.viewDetails();
                    break;
                case 3:
                    TrainReservationSystem.cancelReservation();
                    break;
                case 4:
                    TrainReservationSystem.viewReservations();
                    break;
                case 5:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid option! Please try again.");
            }
        }
    }
}
