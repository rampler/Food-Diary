package com.example.medicineApp;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sabina on 2014-11-20.
 */
public class Medicine implements Serializable {

    private String name;
    private String company;
    private int dosage;
    private BigDecimal price;

    public Medicine (String name, String company, int dosage, BigDecimal price) {
        this.name = name;
        this.company = company;
        this.dosage = dosage;
        this.price = price;
    }

    public int hashCode() {
        return new HashCodeBuilder(2, 31).
                append(getName()).
                append(getCompany()).
                append(getDosage()).
                append(getPrice()).
                toHashCode();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Medicine))
            return false;
        if (obj == this)
            return true;

        Medicine rhs = (Medicine) obj;
        return new EqualsBuilder().
                // if deriving: appendSuper(super.equals(obj)).
                        append(getName(), rhs.getName()).
                append(getCompany(), rhs.getCompany()).
                append(getDosage(), rhs.getDosage()).
                append(getPrice(), rhs.getPrice()).
                isEquals();
    }


    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();
        List medicine = new ArrayList();
        medicine.add(name);
        medicine.add(company);
        medicine.add(dosage);
        medicine.add(price);
        oos.writeObject(medicine);
    }

    private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
        ois.defaultReadObject();
        List medicine = (List)ois.readObject();
        name = (String) medicine.get(0);
        company = (String) medicine.get(1);
        dosage = (Integer) medicine.get(2);
        price = (BigDecimal) medicine.get(3);
    }


    public String getName() {
        return name;
    }

    public String getCompany() {
        return company;
    }

    public int getDosage() {
        return dosage;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
