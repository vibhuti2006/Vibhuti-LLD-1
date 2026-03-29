package com.example.parking;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public class ParkingLot {
    private final List<ParkingSlot> slots;
    private final Map<String, ParkingTicket> activeTickets;
    private final Map<SlotType, Double> hourlyRates;
    private int ticketCounter;
    private final Map<Integer, Map<Integer, Integer>> gateDistances;

    public ParkingLot() {
        this.slots = new ArrayList<>();
        this.activeTickets = new HashMap<>();
        this.hourlyRates = new HashMap<>();
        this.gateDistances = new HashMap<>();
        this.ticketCounter = 0;
    }

    public void addSlot(ParkingSlot slot) {
        slots.add(slot);
    }

    public void setHourlyRate(SlotType type, double rate) {
        hourlyRates.put(type, rate);
    }

    public void setGateDistance(int gateId, int slotNumber, int distance) {
        gateDistances.computeIfAbsent(gateId, k -> new HashMap<>()).put(slotNumber, distance);
    }

    private List<SlotType> getCompatibleSlotTypes(VehicleType vehicleType) {
        switch (vehicleType) {
            case TWO_WHEELER: return Arrays.asList(SlotType.SMALL, SlotType.MEDIUM, SlotType.LARGE);
            case CAR:         return Arrays.asList(SlotType.MEDIUM, SlotType.LARGE);
            case BUS:         return Arrays.asList(SlotType.LARGE);
            default:          return Collections.emptyList();
        }
    }

    public ParkingTicket park(Vehicle vehicle, LocalDateTime entryTime, SlotType requestedSlotType, int entryGateId) {
        List<SlotType> compatible = getCompatibleSlotTypes(vehicle.getType());

        if (!compatible.contains(requestedSlotType)) {
            throw new IllegalArgumentException(vehicle.getType() + " cannot park in " + requestedSlotType + " slot.");
        }

        Map<Integer, Integer> distances = gateDistances.getOrDefault(entryGateId, Collections.emptyMap());

        ParkingSlot best = null;
        int bestDistance = Integer.MAX_VALUE;

        for (ParkingSlot slot : slots) {
            if (slot.isOccupied() || slot.getType() != requestedSlotType) continue;
            int dist = distances.getOrDefault(slot.getSlotNumber(), slot.getDistanceFromGate());
            if (dist < bestDistance) {
                bestDistance = dist;
                best = slot;
            }
        }

        if (best == null) {
            throw new RuntimeException("No available " + requestedSlotType + " slot.");
        }

        best.occupy();
        String ticketId = "TKT-" + (++ticketCounter);
        ParkingTicket ticket = new ParkingTicket(ticketId, vehicle, best, entryTime);
        activeTickets.put(ticketId, ticket);
        return ticket;
    }

    public Map<SlotType, int[]> status() {
        Map<SlotType, int[]> result = new LinkedHashMap<>();
        for (SlotType type : SlotType.values()) {
            result.put(type, new int[]{0, 0});
        }
        for (ParkingSlot slot : slots) {
            int[] counts = result.get(slot.getType());
            counts[0]++;
            if (!slot.isOccupied()) counts[1]++;
        }
        return result;
    }

    public double exit(ParkingTicket ticket, LocalDateTime exitTime) {
        if (!activeTickets.containsKey(ticket.getTicketId())) {
            throw new IllegalArgumentException("Ticket not found: " + ticket.getTicketId());
        }

        long hours = Duration.between(ticket.getEntryTime(), exitTime).toHours();
        if (Duration.between(ticket.getEntryTime(), exitTime).toMinutes() % 60 > 0) {
            hours++;
        }
        if (hours == 0) hours = 1;

        SlotType slotType = ticket.getSlot().getType();
        double rate = hourlyRates.getOrDefault(slotType, 0.0);
        double totalAmount = hours * rate;

        ticket.getSlot().free();
        activeTickets.remove(ticket.getTicketId());

        return totalAmount;
    }
}
