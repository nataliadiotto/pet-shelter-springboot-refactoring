package com.diotto.petshelter.errors;

public class ResourceNotFound extends RuntimeException {

    private String resourceName;
    private String nameField;
    private Object valueField;

    public ResourceNotFound(String resourceName, String nameField, Object valueField) {
        super(String.format("%s not found with: %s = '%s'", resourceName, nameField, valueField));
        this.resourceName = resourceName;
        this.nameField = nameField;
        this.valueField = valueField;
    }

    public ResourceNotFound(String resourceName) {
        super(String.format("There are no registers of %s in the system.", resourceName));
        this.resourceName = resourceName;
    }


}
