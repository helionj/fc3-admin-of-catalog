package com.helion.admin.catalog.domain.category;

import com.helion.admin.catalog.domain.Identifier;
import com.helion.admin.catalog.domain.utils.IdUtils;

import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

public class CategoryID extends Identifier {
    private final String value;

    private CategoryID(String value) {
        Objects.requireNonNull(value);
        this.value = value;
    }

   public static CategoryID unique(){
        return CategoryID.from(IdUtils.uuid());
   }

   public static CategoryID from(final String anId) {
        return new CategoryID(anId);
   }



    @Override
    public String getValue(){
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryID that = (CategoryID) o;
        return getValue().equals(that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }
}
