package domain.utils;

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
}
