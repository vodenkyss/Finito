package model;

import java.io.Serializable;

public enum Priority implements Serializable {
    LOW("Low"),MEDIUM("Medium"),HIGH("High");

    private final String label;

    Priority(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
