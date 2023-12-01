package com.arash.example.springsandbox.scheduled;

import com.arash.example.springsandbox.service.TranslationService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
@Component
public class TranslationScheduler {

    private static final Random RANDOM = new Random();

    private static final List<String> DICTIONARY = List.of(
            "Doom",
            "Disciples",
            "Diablo",
            "Warcraft",
            "Celeste",
            "Cuphead"
    );

    private final TranslationService translationService;

    @Scheduled(fixedDelay = 3_000)
    public void translateRandomWord() {
        System.out.println(translationService.translate(getRandomWord()));
    }

    private String getRandomWord() {
        return DICTIONARY.get(RANDOM.nextInt(DICTIONARY.size()));
    }
}
