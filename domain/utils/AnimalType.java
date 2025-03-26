package domain.utils;

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
}
