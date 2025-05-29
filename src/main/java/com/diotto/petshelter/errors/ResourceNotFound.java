package com.diotto.petshelter.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFound extends Throwable {

    private String resourceName;
    private String nameField;
    private Object valueField;

    public ResourceNotFound(String resourceName, String nameField, Object valueField) {
        super(String.format("%s not found with: %s= '%s", resourceName, nameField, valueField));
        this.resourceName = resourceName;
        this.nameField = nameField;
        this.valueField = valueField;
    }

    public ResourceNotFound(String resourceName) {
        super(String.format("There are no registers of %s in the system.", resourceName));
        this.resourceName = resourceName;
    }


}
