# Multilevel Parking Lot Design

## Class Diagram

```
+----------------+       +---------------+       +--------------+
|   ParkingLot   |------>|  ParkingSlot  |       |   SlotType   |
|----------------|       |---------------|       |--------------|
| slots          |       | slotNumber    |       | SMALL        |
| activeTickets  |       | type: SlotType|<------| MEDIUM       |
| hourlyRates    |       | floor         |       | LARGE        |
| gateDistances  |       | occupied      |       +--------------+
|----------------|       | distFromGate  |
| park()         |       +---------------+       +--------------+
| status()       |                               | VehicleType  |
| exit()         |       +---------------+       |--------------|
+----------------+------>| ParkingTicket |       | TWO_WHEELER  |
                         |---------------|       | CAR          |
                         | ticketId      |       | BUS          |
                         | vehicle       |       +--------------+
                         | slot          |            ^
                         | entryTime     |            |
                         +---------------+       +---------+
                              |                  | Vehicle |
                              +----------------->|---------|
                                                 | plate   |
                         +------------+          | type    |
                         | EntryGate  |          +---------+
                         |------------|
                         | gateId     |
                         +------------+
```

## Design Approach

### Slot Compatibility

A smaller vehicle can park in a larger slot if needed:

| Vehicle Type | Allowed Slot Types |
|---|---|
| TWO_WHEELER | SMALL, MEDIUM, LARGE |
| CAR | MEDIUM, LARGE |
| BUS | LARGE |

This is handled by `getCompatibleSlotTypes()` in `ParkingLot`.

### Nearest Slot Assignment

Each entry gate has a distance map to every slot. When `park()` is called, it picks the closest free slot of the requested type from the given gate.

### Billing

- Billing is based on the **allocated slot type**, not the vehicle type.
- A bike parked in a MEDIUM slot pays the MEDIUM hourly rate.
- Partial hours are rounded up (e.g. 2.5h is billed as 3h).

### APIs

| Method | Description |
|---|---|
| `park(vehicle, entryTime, slotType, gateId)` | Assigns nearest compatible slot, returns a `ParkingTicket` |
| `status()` | Returns available/total count per `SlotType` |
| `exit(ticket, exitTime)` | Frees the slot, calculates bill based on slot rate, returns amount |

### Classes Overview

| Class | Responsibility |
|---|---|
| `ParkingLot` | Core class — manages slots, tickets, rates, and all 3 APIs |
| `ParkingSlot` | Represents a single slot with type, floor, and occupancy state |
| `ParkingTicket` | Stores vehicle, slot, and entry time for a parked vehicle |
| `Vehicle` | Holds license plate and vehicle type |
| `EntryGate` | Represents an entry gate with an ID |
| `SlotType` | Enum: SMALL, MEDIUM, LARGE |
| `VehicleType` | Enum: TWO_WHEELER, CAR, BUS |
