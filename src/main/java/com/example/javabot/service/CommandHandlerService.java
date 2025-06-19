package com.example.javabot.service;

import com.example.javabot.command.BotCommand;
import com.example.javabot.command.CommandHandler;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommandHandlerService implements CommandHandler {

    private final BusinessDataGeneratorService businessDataGeneratorService;
    private final PersonDataGeneratorService personDataGeneratorService;
    private final GuidUuidGeneratorService guidUuidGeneratorService;

    @Override
    public String handle(BotCommand command) {
        return switch (command) {
            case INN -> businessDataGeneratorService.generateJuridicalInn();
            case INN_FL -> businessDataGeneratorService.generateIndividualInn();
            case OGRN -> businessDataGeneratorService.generateOgrn();
            case OGRN_IP -> businessDataGeneratorService.generateOgrnIp();
            case OKPO -> businessDataGeneratorService.generateOkpo();
            case OKPO_IP -> businessDataGeneratorService.generateOkpoIp();
            case ENP_OMS -> businessDataGeneratorService.generateEnpOms();
            case PASSPORT -> businessDataGeneratorService.generatePassportNumber();
            case SNILS_GOSKEY -> businessDataGeneratorService.generateSnilsGosKey();
            case SNILS -> businessDataGeneratorService.generateSnils();
            case FULL_NAME -> personDataGeneratorService.generateFullName();
            case BIRTH_DATE -> personDataGeneratorService.generateBirthDate();
            case LOGIN -> personDataGeneratorService.generateLogin();
            case EMAIL -> personDataGeneratorService.generateEmail();
            case PHONE -> personDataGeneratorService.generatePhoneNumber();
            case GUID -> guidUuidGeneratorService.generateGuid();
            case GUID_LOWER -> guidUuidGeneratorService.generateGuidLower();
            case UUID -> guidUuidGeneratorService.generateUuid();
        };
    }
}
