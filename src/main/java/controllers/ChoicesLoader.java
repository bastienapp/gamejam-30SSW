package controllers;

import models.Choice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ChoicesLoader {

    private static final String CHOICES_FILE = "choices - export.tsv";
    private static final String DELIMITER = "\t";

    public List<Choice> getChoices() {
        List<Choice> choices = new ArrayList<>();

        InputStream inputStream = getClass()
                .getClassLoader().getResourceAsStream(CHOICES_FILE);

        if (inputStream != null) {
            boolean first = true;
            try (BufferedReader br
                         = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                while ((line = br.readLine()) != null) {
                    if (!first) {
                        String[] values = line.split(DELIMITER);
                        choices.add(new Choice(values));
                    } else {
                        first = false;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return choices;
    }
}
