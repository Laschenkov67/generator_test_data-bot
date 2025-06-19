package com.example.javabot.command;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BotCommand {
    INN("ИНН", "inn"),
    INN_FL("ИНН ФЛ", "inn_fl"),
    OGRN("ОГРН", "ogrn"),
    OGRN_IP("ОГРН ИП", "ogrn_ip"),
    OKPO("ОКПО", "okpo"),
    OKPO_IP("ОКПО ИП", "okpo_ip"),
    SNILS("СНИЛС", "snils"),
    SNILS_GOSKEY("СНИЛС ГОСКЛЮЧ", "snils_goskey"),
    ENP_OMS("ЕНП ОМС", "enp_oms"),
    PASSPORT("Серия и номер паспорта РФ", "passport"),
    FULL_NAME("ФИО", "full_name"),
    BIRTH_DATE("Дата рождения", "birth_date"),
    LOGIN("Логин", "login"),
    EMAIL("E-mail", "email"),
    PHONE("Телефон", "phone"),
    GUID("GUID", "guid"),
    GUID_LOWER("GUID LOWER", "guid_lower"),
    UUID("UUID", "uuid");

    private final String displayName;
    private final String commandCode;

    public static BotCommand fromDisplayName(String displayName) {
        for (BotCommand command : values()) {
            if (command.getDisplayName().equals(displayName)) {
                return command;
            }
        }
        return null;
    }
}
