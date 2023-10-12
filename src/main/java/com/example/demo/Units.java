package com.example.demo;
// Abstract class for units
abstract class Unit<T> {
    private String name;
    private T value;

    public Unit(String name, T value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public T getValue() {
        return value;
    }

    // Abstract method for converting a value to this unit
    public abstract T convertToUnit(T value, Unit<T> targetUnit);
}

class Foot extends Unit<Double> {
    public Foot() {
        super("Foot", 1.0);
    }

    @Override
    public Double convertToUnit(Double value, Unit<Double> targetUnit) {
        return value * getValue() / targetUnit.getValue();
    }
}

class Inch extends Unit<Double> {
    public Inch() {
        super("Inch", 12.0);
    }

    @Override
    public Double convertToUnit(Double value, Unit<Double> targetUnit) {
        return value * getValue() / targetUnit.getValue();
    }
}

class Meter extends Unit<Double> {
    public Meter() {
        super("Meter", 0.3048);
    }

    @Override
    public Double convertToUnit(Double value, Unit<Double> targetUnit) {
        return value * getValue() / targetUnit.getValue();
    }
}

class Yard extends Unit<Double> {
    public Yard() {
        super("Yard", 0.3333);
    }

    @Override
    public Double convertToUnit(Double value, Unit<Double> targetUnit) {
        return value * getValue() / targetUnit.getValue();
    }
}