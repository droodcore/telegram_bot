package ru.kortukov.service.enums;

public enum ServiceCommand {
    HELP("/help"),
    REGISTRATION("/registration"),
    CANCEL("/cancel"),
    START("/start");

    private final String value;


    ServiceCommand(String value) {
        this.value = value;
    }


    @Override
    public String toString() {
        return value;
    }

    public static ServiceCommand fromValue(String value) {
        for (ServiceCommand c :
                ServiceCommand.values()) {
            if (c.value.equals(value))
                return c;
        }
        return null;
    }
}
