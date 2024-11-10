package dev.italofernandes.desafio.sangiorgio.enumeration;

public enum PaymentStatusEnum {
    PARTIAL("Parcial"),
    FULL("Total"),
    EXCESS("Excedente");

    private final String description;

    PaymentStatusEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static PaymentStatusEnum fromDescription(String description) {
        for (PaymentStatusEnum type : PaymentStatusEnum.values()) {
            if (type.getDescription().equalsIgnoreCase(description)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Description not found: " + description);
    }
}
