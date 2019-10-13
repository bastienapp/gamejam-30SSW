import controllers.ChoicesLoader;
import models.Choice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static int TIMER_MAX = 30;
    private static Scanner input;
    private static List<Choice> choices;
    private static int timer = TIMER_MAX;

    public static void main(String[] args) {
        input = new Scanner(System.in);
        ChoicesLoader loader = new ChoicesLoader();
        choices = loader.getChoices();
        play(choices.get(0));
    }

    private static void play(Choice choice) {
        if (choice.isDone() && choice.isOnce()) {
            manageFailure(choice);
            return;
        }
        if (choice.getId() != 1
                && choice.getId() != 6
        ) {
            if (timer <= 0) {
                play(choices.get(5));
                return;
            } else if (timer == 1){
                System.out.printf("%n[%d seconde]", timer);
            } else {
                System.out.printf("%n[%d secondes]", timer);
            }
            timer--;
        } else if (choice.getId() != 1) {
            timer = TIMER_MAX;
        } else {
            timer--;
        }
        choice.setDone(true);
        System.out.printf(String.format("%n%s%%n%n", choice.getText()));
        List<Integer> nextChoices = new ArrayList<>();
        if (choice.getChoices() != null) {
            nextChoices = Arrays.asList(choice.getChoices());
        }
        List<String> nextKeys = new ArrayList<>();
        if (choice.getChoicesKeys() != null) {
            nextKeys = Arrays.asList(choice.getChoicesKeys());
        }

        if (choice.getExtinct() != null) {
            for (int key : choice.getExtinct()) {
                Choice extinct = choices.get(key - 1);
                extinct.setDone(true);
                extinct.setFailed(true);
            }
        }

        if (choice.isInput()) {
            String answer = input.nextLine();
            if (nextKeys.contains(answer.toLowerCase())) {
                int key = nextKeys.indexOf(answer.toLowerCase());
                play(choices.get(nextChoices.get(key) - 1));
            } else {
                manageFailure(choice);
            }
        } else {
            boolean stop = false;
            do {
                int i = 0;
                List<Integer> realChoices = new ArrayList<>();
                for (int id : nextChoices) {
                    Choice next = choices.get(id - 1);
                    if (!next.isOnce() || !next.isDone()) {
                        System.out.printf("[%d] %s%n", ++i, next.getTitle());
                        realChoices.add(id);
                    }
                }
                nextChoices = realChoices;
                if (i > 0) {
                    System.out.printf("[%d] Abandonner%n", 9);
                    System.out.printf("%nQuel est mon choix : ");
                    String answer = input.nextLine();
                    try {
                        int c = Integer.parseInt(answer);
                        if (c == 9) {
                            System.out.printf("%nLa bombe explose, déclenchant une réaction en chaîne provoquant une guerre nucléaire totale.%nL'humanité disparaît en quelques années.%n%n");
                            return;
                        } else if (c > 0 && c <= nextChoices.size()) {
                            stop = true;
                            play(choices.get(nextChoices.get(c - 1) - 1));
                            return;
                        } else {
                            System.out.printf("%nChoix incorrect...%n%n");
                        }
                    } catch (Exception e) {
                        //e.printStackTrace();
                        if (choice.getId() != 1) {
                            System.out.printf("%nChoix incorrect...%n%n");
                        }
                    }
                } else {
                    manageFailure(choice);
                    return;
                }
            } while (!stop);
        }
    }

    private static void manageFailure(Choice choice) {
        Integer[] failures = choice.getFailures();
        if (failures.length > 1 && choice.isFailed()) {
            play(choices.get(failures[1] - 1));
            return;
        }
        choice.setFailed(true);
        play(choices.get(failures[0] - 1));
    }
}
