package src.main.java.domain.enums;

public enum BiologicalSex {

    FEMALE(1), MALE(2);

    private final int value;
    BiologicalSex(int value) {
        this.value = value;
    }

    public static BiologicalSex fromValue(int value) {
        for (BiologicalSex sex : BiologicalSex.values()) {
            if (sex.value == value) {
                return sex;
            }
        }
        throw new IllegalArgumentException("Invalid value: " + value);
    }

    public static BiologicalSex fromString(String value) {
        if (value == null) return null;
        try {
            return BiologicalSex.valueOf(value.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    @Override
    public String toString() {
        return this.name().charAt(0) + this.name().substring(1).toLowerCase();
    }
}
