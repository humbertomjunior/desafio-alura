package br.com.alura.AluraFake.task;

import br.com.alura.AluraFake.task.request.OptionRequest;

import java.util.ArrayList;
import java.util.List;

public class OptionTestUtils {

    public static OptionRequest createOptionRequest(String option, boolean isCorrect) {
        return OptionRequest.builder()
                .option(option)
                .isCorrect(isCorrect)
                .build();
    }

    public static List<Option> createMockedOptions(int optionsQuantity, int correctAnswers) {
        List<Option> options = new ArrayList<>();
        for (int i = 0; i < optionsQuantity; i++) {
            final var isCorrect = options.stream().filter(Option::isCorrect).count() < correctAnswers;
            options.add(Option.builder().option("Opção %s".formatted(i)).isCorrect(isCorrect).build());
        }
        return options;
    }

    public static List<OptionRequest> createMockedOptionRequests(int optionsQuantity, int correctAnswers) {
        List<OptionRequest> options = new ArrayList<>();
        for (int i = 0; i < optionsQuantity; i++) {
            final var isCorrect = options.stream().filter(OptionRequest::isCorrect).count() < correctAnswers;
            options.add(OptionRequest.builder().option("Opção %s".formatted(i)).isCorrect(isCorrect).build());
        }
        return options;
    }

    public static List<Option> copyFromOptionRequest(List<OptionRequest> options) {
        Integer id = 0;
        final List<Option> copiedOptions = new ArrayList<>();
        for (OptionRequest optionRequest : options) {
            id++;
            copiedOptions.add(Option.builder()
                    .id(id.longValue())
                    .isCorrect(optionRequest.isCorrect())
                    .option(optionRequest.getOption())
                    .build());
        }

        return copiedOptions;
    }

}
