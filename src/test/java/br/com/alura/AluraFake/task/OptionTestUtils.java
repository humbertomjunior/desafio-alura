package br.com.alura.AluraFake.task;

import br.com.alura.AluraFake.task.request.OptionRequest;

import java.util.ArrayList;
import java.util.List;

public class OptionTestUtils {

    public static List<Option> createMockedOptions(int optionsQuantity, int correctAnswers) {
        List<Option> options = new ArrayList<>();
        for (int i = 0; i < optionsQuantity; i++) {
            final var isCorrect = options.stream().filter(Option::isCorrect).count() >= correctAnswers;
            options.add(Option.builder().option("Opção %s".formatted(i)).isCorrect(isCorrect).build());
        }
        return options;
    }

    public static List<OptionRequest> createMockedOptionRequests(int optionsQuantity, int correctAnswers) {
        List<OptionRequest> options = new ArrayList<>();
        for (int i = 0; i < optionsQuantity; i++) {
            final var isCorrect = options.stream().filter(OptionRequest::isCorrect).count() >= correctAnswers;
            options.add(OptionRequest.builder().option("Opção %s".formatted(i)).isCorrect(isCorrect).build());
        }
        return options;
    }

}
