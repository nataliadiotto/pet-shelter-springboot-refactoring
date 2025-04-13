package domain.enums;

public enum AnimalType {

    CAT(1), DOG(2);

    private final int value;

    AnimalType(int value) {
        this.value = value;
    }

    public static AnimalType fromValue(int value) {
        for (AnimalType type : AnimalType.values()) {
            if (type.value == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid value: " + value);
    }

    public static AnimalType fromString(String value) {
        if (value == null) return null;
        try {
            return AnimalType.valueOf(value.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    @Override
    public String toString() {
        return this.name().charAt(0) + this.name().substring(1).toLowerCase();
    }
}
