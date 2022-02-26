package ru.itmo.lab5.data;

/**
 * Enum, содержащий типы билетов
 */

public enum TicketType {
    VIP,
    USUAL,
    BUDGETARY,
    CHEAP;

    /**
     * Возвращает значения enum'а в строковом представлении через запятую
     * 
     * @return значения enum'a через запятую
     */

    public static String valuesToString() {
        String valuesList = "";
        for (TicketType type : values()) {
            valuesList += type.name() + ", ";
        }
        return valuesList.substring(0, valuesList.length() - 2);
    }
}
