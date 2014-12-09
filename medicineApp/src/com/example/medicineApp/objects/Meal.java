package com.example.medicineApp.objects;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Created by Sabina on 2014-12-05.
 */
public class Meal {

    private String id;
    private String name;
    private String consumptionDay;

    public Meal(String id, String name, String consumptionDay) {
        this.id = id;
        this.name = name;
        this.consumptionDay = consumptionDay;
    }

    public String getId() {
        return id;
    }

    public String getConsumptionDay() {
        return consumptionDay;
    }

    public String getName() {
        return name;
    }


    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 31).
                append(id).
                toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Meal))
            return false;
        if (obj == this)
            return true;

        Meal rhs = (Meal) obj;
        return new EqualsBuilder().
                append(id, rhs.id).
                isEquals();
    }


}
