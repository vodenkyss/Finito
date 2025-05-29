package model;

import java.io.Serializable;

public enum Priority implements Serializable {
    LOW("Low"),MEDIUM("Medium"),HIGH("High");

    private String label;


    Priority(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }

    /**
     * Returns the priority level associated with enum.
     * The level is represented as an integer value
     * @return integer level of the priority
     */
    public int getLevel() {
        return switch (this) {
            case HIGH -> 3;
            case MEDIUM -> 2;
            case LOW -> 1;
        };
    }

}
