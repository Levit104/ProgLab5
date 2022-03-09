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

    public static String valuesList() {
        StringBuilder valuesListBuilder = new StringBuilder();
        for (TicketType type : values()) {
            valuesListBuilder.append(type).append(", ");
        }
        return valuesListBuilder.substring(0, valuesListBuilder.length() - 2);
    }
}
