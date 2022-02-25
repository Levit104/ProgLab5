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
     * Возвращает строковое представление значений enum'а через запятую
     * 
     * @return строковое представление значение enum'а
     */

    public static String valuesToString() {
        String valuesList = "";
        for (TicketType type : values()) {
            valuesList += type.name() + ", ";
        }
        return valuesList.substring(0, valuesList.length() - 2);
    }
}
