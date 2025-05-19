package domain.enums;

public enum PetType {

    CAT(1), DOG(2);

    private final int value;

    PetType(int value) {
        this.value = value;
    }

    public static PetType fromValue(int value) {
        for (PetType type : PetType.values()) {
            if (type.value == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid value: " + value);
    }

    public static PetType fromString(String value) {
        if (value == null) return null;
        try {
            return PetType.valueOf(value.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    @Override
    public String toString() {
        return this.name().charAt(0) + this.name().substring(1).toLowerCase();
    }
}
