package com.example.parking;

import java.time.LocalDateTime;
import java.util.Map;

public class App {

    public static void main(String[] args) {

        ParkingLot lot = new ParkingLot();

        lot.setHourlyRate(SlotType.SMALL, 10);
        lot.setHourlyRate(SlotType.MEDIUM, 20);
        lot.setHourlyRate(SlotType.LARGE, 50);

        lot.addSlot(new ParkingSlot(1, SlotType.SMALL,  1, 1));
        lot.addSlot(new ParkingSlot(2, SlotType.SMALL,  1, 2));
        lot.addSlot(new ParkingSlot(3, SlotType.SMALL,  1, 3));
        lot.addSlot(new ParkingSlot(4, SlotType.MEDIUM, 1, 4));
        lot.addSlot(new ParkingSlot(5, SlotType.MEDIUM, 1, 5));
        lot.addSlot(new ParkingSlot(6, SlotType.MEDIUM, 2, 6));
        lot.addSlot(new ParkingSlot(7, SlotType.LARGE,  2, 7));
        lot.addSlot(new ParkingSlot(8, SlotType.LARGE,  2, 8));

        for (int i = 1; i <= 8; i++) {
            lot.setGateDistance(1, i, i);
            lot.setGateDistance(2, i, 9 - i);
        }

        System.out.println("=== Initial Status ===");
        printStatus(lot);

        Vehicle bike = new Vehicle("KA-01-1234", VehicleType.TWO_WHEELER);
        LocalDateTime bikeEntry = LocalDateTime.of(2026, 3, 29, 10, 0);
        ParkingTicket bikeTicket = lot.park(bike, bikeEntry, SlotType.SMALL, 1);
        System.out.println("\nParked: " + bikeTicket);

        Vehicle car = new Vehicle("MH-02-5678", VehicleType.CAR);
        LocalDateTime carEntry = LocalDateTime.of(2026, 3, 29, 10, 30);
        ParkingTicket carTicket = lot.park(car, carEntry, SlotType.MEDIUM, 2);
        System.out.println("Parked: " + carTicket);

        Vehicle bus = new Vehicle("DL-03-9999", VehicleType.BUS);
        LocalDateTime busEntry = LocalDateTime.of(2026, 3, 29, 11, 0);
        ParkingTicket busTicket = lot.park(bus, busEntry, SlotType.LARGE, 1);
        System.out.println("Parked: " + busTicket);

        Vehicle bike2 = new Vehicle("KA-04-0001", VehicleType.TWO_WHEELER);
        LocalDateTime bike2Entry = LocalDateTime.of(2026, 3, 29, 11, 15);
        ParkingTicket bike2Ticket = lot.park(bike2, bike2Entry, SlotType.MEDIUM, 1);
        System.out.println("Parked (bike in medium): " + bike2Ticket);

        System.out.println("\n=== Status After Parking ===");
        printStatus(lot);

        LocalDateTime bikeExit = LocalDateTime.of(2026, 3, 29, 13, 0);
        double bikeBill = lot.exit(bikeTicket, bikeExit);
        System.out.println("\nBike exit bill (3h, SMALL rate): Rs " + bikeBill);

        LocalDateTime carExit = LocalDateTime.of(2026, 3, 29, 13, 0);
        double carBill = lot.exit(carTicket, carExit);
        System.out.println("Car exit bill (2.5h -> 3h, MEDIUM rate): Rs " + carBill);

        LocalDateTime bike2Exit = LocalDateTime.of(2026, 3, 29, 12, 15);
        double bike2Bill = lot.exit(bike2Ticket, bike2Exit);
        System.out.println("Bike-in-medium exit bill (1h, MEDIUM rate): Rs " + bike2Bill);

        System.out.println("\n=== Final Status ===");
        printStatus(lot);
    }

    private static void printStatus(ParkingLot lot) {
        Map<SlotType, int[]> status = lot.status();
        for (Map.Entry<SlotType, int[]> entry : status.entrySet()) {
            int[] counts = entry.getValue();
            System.out.println("  " + entry.getKey() + ": " + counts[1] + " available / " + counts[0] + " total");
        }
    }
}
