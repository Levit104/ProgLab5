package ru.itmo.lab5.data;

/**
 * Enum, содержащий типы спортивных событий
 */

public enum EventType {
    E_SPORTS,
    FOOTBALL,
    BASKETBALL,
    EXPOSITION;

    /**
     * Возвращает значения enum'а в строковом представлении через запятую
     * 
     * @return значения enum'a через запятую
     */

    public static String valuesToString() {
        String valuesList = "";
        for (EventType type : values()) {
            valuesList += type.name() + ", ";
        }
        return valuesList.substring(0, valuesList.length() - 2);
    }
}
