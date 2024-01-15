package org.example;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.example.exceptions.CloneException;
import org.example.exceptions.ValueException;

import java.io.Serializable;
import java.util.ResourceBundle;

public class SudokuField implements Serializable, Cloneable, Comparable<SudokuField> {
    private int value;

    private boolean isSet = true;

    public SudokuField() {

    }

    public SudokuField(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    /*public int getValue() {
        return value;
    }*/

    public void setValue(int value) {
        this.value = value;
    }

    /*public void setValue(int value) {
        this.value = value;
    }*/

    public boolean isSet() {
        return isSet;
    }

    public void setSet(boolean set) {
        isSet = set;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("value",value)
                .toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }

        if (obj == this) {
            return true;
        }

        SudokuField other = (SudokuField) obj;
        return new EqualsBuilder()
                .append(value, other.value)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(value)
                .toHashCode();
    }

    @Override
    public int compareTo(SudokuField o) {
        if (o == null) {
            ResourceBundle bundle = ResourceBundle.getBundle("ExceptionMessages");
            String message = bundle.getString("org.example.exceptions.ValueException");
            throw new ValueException(message);
        }
        if (this.getValue() == o.getValue()) {
            return 0;
        } else if (this.getValue() > o.getValue()) {
            return 1;
        } else {
            return -1;
        }
    }

    @Override
    protected Object clone() throws CloneException {
        try {
            SudokuField sudokuField = (SudokuField) super.clone();
            sudokuField.value = this.value;

            return sudokuField;
        } catch (CloneNotSupportedException e) {
            ResourceBundle bundle = ResourceBundle.getBundle("ExceptionMessages");
            String message = bundle.getString("org.example.CloneException");
            throw new CloneException(message);
        }
    }
}
