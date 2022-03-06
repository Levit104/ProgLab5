package ru.itmo.lab5.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

import ru.itmo.lab5.collection.CollectionManager;
import ru.itmo.lab5.data.Coordinates;
import ru.itmo.lab5.data.Event;
import ru.itmo.lab5.data.EventType;
import ru.itmo.lab5.data.Ticket;
import ru.itmo.lab5.data.TicketType;
import ru.itmo.lab5.exceptions.NotUniqueValueException;

/**
 * Класс для работы с файлом (менеджер файла)
 */

public class FileManager {
    private int row;
    private Map<Integer, Ticket> ticketMap;
    private boolean noErrors;
    private Scanner stringScanner;
    private FileWriter fileWriter;

    /**
     * Конструктор, задающий параметры класса
     * 
     * @param file путь до файла
     */

    public FileManager(String file) {
        Reader inputReader = null;
        row = 2;
        ticketMap = new LinkedHashMap<>();
        noErrors = true;
        try {
            inputReader = new InputStreamReader(new FileInputStream(file), "UTF8");
            StringBuilder stringBuilder = new StringBuilder();
            int symbol = 0;
            
            while ((symbol = inputReader.read()) != -1) {
                stringBuilder.append((char) symbol);
            }

            stringScanner = new Scanner(stringBuilder.toString());
            
            if (!stringScanner.nextLine().equals(CollectionManager.csvString())) {
                throw new IllegalArgumentException();
            }

        } catch (UnsupportedEncodingException e1) {
            System.out.println("Неподдерживаемая кодировка\n");
            noErrors = false;
        } catch (FileNotFoundException | NullPointerException e2) {
            System.out.println("Файл не найден\n");
            noErrors = false;
        } catch (IOException e3) {
            System.out.println("Ошибка при чтении файла\n");
            noErrors = false;
        } catch (NoSuchElementException e4) {
            System.out.println("Файл пустой\n");
            noErrors = false;
        } catch (IllegalArgumentException e5) {
            System.out.println("Ошибка в заголовке файла/Некорректные данные\n");
            noErrors = false;
        } finally {
            try {
                if (inputReader != null) {
                    inputReader.close();
                }
            } catch (IOException e) {
                System.out.println("Ошибка при чтении файла\n");
            }
        }
    }

    /**
     * Возвращает коллекцию, содержащую объекты класса Ticket
     * 
     * @return коллекция
     */

    public Map<Integer, Ticket> getTicketMap() {
        return this.ticketMap;
    }

    /**
     * Считывает информацию из csv файла
     * 
     * @return коллекцию, содержащую объекты класса Ticket
     * @see Ticket
     */

    public Map<Integer, Ticket> parseFile() {
        if (noErrors) {
            System.out.println("Файл успешно загружен. Проверка данных...\n");

            Scanner dataScanner = null;
            String line = null;
            int index = 0;

            while (stringScanner.hasNextLine()) {
                Ticket ticket = null;
                Integer key = null;
                Integer id = null;
                String name = null;
                Double coordinateX = null;
                Double coordinateY = null;
                Date creationDate = null;
                double price = 0;
                TicketType type = null;
                Long eventID = null;
                String eventName = null;
                LocalDateTime eventDate = null;
                EventType eventType = null;

                line = stringScanner.nextLine();
                dataScanner = new Scanner(line);
                dataScanner.useDelimiter(",");
                noErrors = true;

                if (line.split(",").length != 12) {
                    System.out.println("Некорректные данные в строке " + row);
                    System.out.printf("Ошибка: элемент в строке %d не был добавлен%n%n", row);
                    row++;
                    continue;
                }

                if (line.charAt(0) == ',') {
                    System.out.printf("Значение поля key в строке %d не может быть пустым, " +
                                      "т.к. элемент не может существовать без ключа%n", row);
                    row++;
                    System.out.printf("Ошибка: элемент в строке %d не был добавлен%n%n", row);
                    continue;
                }

                while (dataScanner.hasNext()) {
                    String data = dataScanner.next();

                    if (index == 0) {
                        key = parseKey(data, "key");
                    } else if (index == 1) {
                        id = parseID(data, "ID");
                    } else if (index == 2) {
                        name = parseName(data, "name");
                    } else if (index == 3) {
                        coordinateX = parseCoordinate(data, "coordinateX");
                    } else if (index == 4) {
                        coordinateY = parseCoordinate(data, "coordinateY");
                    } else if (index == 5) {
                        creationDate = parseTicketDate(data, "creationDate");
                    } else if (index == 6) {
                        price = parsePrice(data, "price");
                    } else if (index == 7) {
                        type = parseTicketType(data, "ticketType");
                    } else if (index == 8) {
                        eventID = parseEventID(data, "eventID");
                    } else if (index == 9) {
                        eventName = parseName(data, "eventName");
                    } else if (index == 10) {
                        eventDate = parseEventDate(data, "eventDate");
                    } else if (index == 11) {
                        eventType = parseEventType(data, "eventType");
                    } else {
                        System.out.println("Некорректные данные " + data);
                    }

                    index++;
                }

                if (line.charAt(line.length() - 1) == ',') {
                    System.out.printf("Значение поля eventType в строке %d не может быть пустым%n", row);
                    noErrors = false;
                }

                ticket = new Ticket(key, id, name, new Coordinates(coordinateX, coordinateY), creationDate, price, type,
                                    new Event(eventID, eventName, eventDate, eventType));

                if (noErrors) {
                    ticketMap.put(ticket.getKey(), ticket);
                    System.out.printf("Элемент в строке %d был успешно добавлен%n%n", row);
                } else {
                    System.out.printf("Ошибка: элемент в строке %d не был добавлен%n%n", row);
                }

                row++;
                index = 0;
            }
            System.out.println("Проверка данных завершена\n");
        }
        return ticketMap;
    }

    /**
     * Сохраняет коллекцию в файл
     * 
     * @param file путь до файла
     * @param collectionManager менеджер коллекции
     */

    public void saveCollection(String file, CollectionManager collectionManager) {
        String csvString = collectionManager.toString();

        try {
            fileWriter = new FileWriter(file);
            fileWriter.write(csvString);
            System.out.println("Коллекция успешно сохранена");
        } catch (IOException e1) {
            System.out.println("Не удалось записать данные в файл");
        } catch (NullPointerException e2) {
            System.out.println("При запуске программы путь до файла не был указан или был указан неверно. " +
                                "Укажите его как аргумент команды save");
        } finally {
            try {
                if (fileWriter != null) {
                    fileWriter.close();
                }
            } catch (IOException e) {
                System.out.println("Не удалось записать данные в файл");
            }
        }
    }

    /**
     * Проверяет расширение файла
     * 
     * @param file путь до файла
     * @return {@code true} если файл имеет расширение .csv, иначе {@code false} 
     */

    public static boolean checkFileExtension(String file) {
        String extension = "";
        int i = file.lastIndexOf('.');
        
        if (i > 0) {
            extension = file.substring(i + 1);
        }

        if (extension.equals("csv")) {
            return true;
        } else {
            System.out.println("Файл должен иметь расширение .csv");
            return false;
        }
    }

    /**
     * Проверяет существует ли файл
     * 
     * @param file путь до файла
     * @return {@code true} если файл существует, иначе {@code false} 
     */
    public static boolean checkIfFileExists(String file) {
        return new File(file).isFile();
    }

    private Integer parseKey(String data, String mode) {
        Integer key = null;
        try {
            key = Integer.parseInt(data);
            if (!CollectionManager.checkTicketKey(key, ticketMap)) {
                throw new NotUniqueValueException();
            }
        } catch (NumberFormatException e1) {
            System.out.printf("Значение поля %s в строке %d должно быть числом и не содержать пробелов%n", mode, row);
            noErrors = false;
        } catch (NotUniqueValueException e2) {
            System.out.printf("Значение поля %s в строке %d, должно быть уникальным%n", mode, row);
            noErrors = false;
        }
        return key;
    }

    private Integer parseID(String data, String mode) {
        Integer ID = null;
        try {
            ID = Integer.parseInt(data);
            if (ID <= 0) {
                throw new NumberFormatException();
            }
            if (!CollectionManager.checkTicketID(ID, ticketMap)) {
                throw new NotUniqueValueException();
            }
        } catch (NumberFormatException e1) {
            System.out.printf("Значение поля %s в строке %d должно быть числом больше нуля и не содержать пробелов%n", mode, row);
            noErrors = false;
        } catch (NotUniqueValueException e2) {
            System.out.printf("Значение поля %s в строке %d, должно быть уникальным%n", mode, row);
            noErrors = false;
        }
        return ID;
    }

    private Long parseEventID(String data, String mode) {
        Long eventID = null;
        try {
            eventID = Long.parseLong(data);
            if (eventID <= 0) {
                throw new NumberFormatException();
            }
            if (!CollectionManager.checkEventID(eventID, ticketMap)) {
                throw new NotUniqueValueException();
            }
        } catch (NumberFormatException e1) {
            System.out.printf("Значение поля %s в строке %d должно быть числом больше нуля и не содержать пробелов%n", mode, row);
            noErrors = false;
        } catch (NotUniqueValueException e2) {
            System.out.printf("Значение поля %s в строке %d, должно быть уникальным%n", mode, row);
            noErrors = false;
        }
        return eventID;
    }

    private String parseName(String data, String mode) {
        String name = null;
        try {
            if (data.isEmpty() || data.trim().length() == 0 || data.trim().length() != data.length()) {
                throw new IllegalArgumentException();
            }
            name = data;
        } catch (IllegalArgumentException e) {
            System.out.printf("Значение поля %s в строке %d не может быть пустым и не должно содержать пробелов%n", mode, row);
            noErrors = false;
        }
        return name;
    }

    private Double parseCoordinate(String data, String mode) {
        Double coordinate = null;
        int maxValue = 0;
        try {
            coordinate = Double.parseDouble(data);
            if (mode.equals("coordinateX") && coordinate > 606) {
                maxValue = 606;
                throw new NumberFormatException();
            } else if (mode.equals("coordinateY") && coordinate > 483) {
                maxValue = 483;
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            System.out.printf("Значение поля %s в строке %d должно быть числом не больше %d и не содержать пробелов%n", mode, row, maxValue);
            noErrors = false;
        }
        return coordinate;
    }

    private double parsePrice(String data, String mode) {
        double price = 0;
        try {
            price = Double.parseDouble(data);
            if (price <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            System.out.printf("Значение поля %s в строке %d должно быть числом больше нуля и не содержать пробелов%n", mode, row);
            noErrors = false;
        }
        return price;
    }

    private Date parseTicketDate(String data, String mode) {
        Date date = null;
        try {
            date = new SimpleDateFormat("HH:mm:ss dd.MM.yyyy").parse(data);
        } catch (ParseException e) {
            System.out.printf("Значение поля %s в строке %d содержит неверный формат даты и/или лишние пробелы%n", mode, row);
            noErrors = false;
        }
        return date;
    }

    private LocalDateTime parseEventDate(String data, String mode) {
        LocalDateTime date = null;
        try {
            date = LocalDateTime.parse(data, DateTimeFormatter.ofPattern("HH:mm:ss dd.MM.yyyy"));
        } catch (DateTimeParseException e) {
            System.out.printf("Значение поля %s в строке %d содержит неверный формат даты и/или лишние пробелы%n", mode, row);
            noErrors = false;
            System.out.println(e.getMessage());
        }
        return date;
    }

    private TicketType parseTicketType(String data, String mode) {
        TicketType ticketType = null;
        try {
            ticketType = TicketType.valueOf(data);
        } catch (IllegalArgumentException e) {
            System.out.printf("Значение поля %s в строке %d содержит недопустимое значение и/или пробелы%n", mode, row);
            noErrors = false;
        }
        return ticketType;
    }

    private EventType parseEventType(String data, String mode) {
        EventType eventType = null;
        try {
            eventType = EventType.valueOf(data);
        } catch (IllegalArgumentException e1) {
            System.out.printf("Значение поля %s в строке %d содержит недопустимое значение и/или пробелы%n", mode, row);
            noErrors = false;
        }
        return eventType;
    }
}
