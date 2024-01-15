package org.example;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

public abstract class SudokuFieldGroup implements Cloneable, Serializable {
    private SudokuField[] sudokuFields;

    public SudokuFieldGroup(SudokuField[] sudokuFields) {
        this.sudokuFields = sudokuFields;
    }

    public boolean verify() {
        for (int i = 0; i < 9; i++) {
            for (int j = i + 1; j < 9; j++) {
                if (sudokuFields[i].equals(sudokuFields[j])
                        || sudokuFields[i].hashCode() == sudokuFields[j].hashCode()) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("sudokuFields",sudokuFields)
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

        SudokuFieldGroup other = (SudokuFieldGroup) obj;
        return new EqualsBuilder()
                .append(sudokuFields, other.sudokuFields)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(sudokuFields)
                .toHashCode();
    }
}
