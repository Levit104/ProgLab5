package ru.itmo.lab5.data;

/**
 * Класс, описывающий координаты x и y
 */

public class Coordinates {
    private Double x; // Максимальное значение поля: 606, Поле не может быть null
    private Double y; // Максимальное значение поля: 483, Поле не может быть null

    /**
     * Конструктор, задающий координаты
     * 
     * @param x координата x
     * @param y координата y
     */

    public Coordinates(Double x, Double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return String.format("%s,%s", Double.toString(x), Double.toString(y));
    }
}
