package com.example.payments;

public class SafeCashClient {
    public SafeCashPayment createPayment(int amount, String user) {
        return new SafeCashPayment(amount, user);
    }
}

class SafeCashAdapter implements PaymentGateway {
    private final SafeCashClient client;

    SafeCashAdapter(SafeCashClient client) {
        this.client = java.util.Objects.requireNonNull(client);
    }

    @Override
    public String charge(String customerId, int amountCents) {
        return client.createPayment(amountCents, customerId).confirm();
    }
}
