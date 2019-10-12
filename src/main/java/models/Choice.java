package models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Choice {

    private int id;
    private String key;
    private String title;
    private String text;
    private boolean once;
    private boolean input;
    private Integer[] choices;
    private String[] choicesKeys;
    private Integer[] failures;
    private Integer[] extinct;
    private boolean done;
    private boolean failed;

    public Choice(String[] str) {
        this.id = Integer.parseInt(str[0]);

        try {
            this.key = str[1];
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.title = str[2];
        this.text = str[3];
        if (str.length > 4 && !str[4].isEmpty()) {
            this.once = Boolean.parseBoolean(str[4]);
        }
        if (str.length > 5 && !str[5].isEmpty()) {
            this.input = Boolean.parseBoolean(str[5]);
        }
        if (str.length > 6 && !str[6].isEmpty()) {
            List<String> cKeys = new ArrayList<>();
            List<Integer> cValues = new ArrayList<>();
            for (String v : str[6].split(",")) {
                String[] k = v.split("=");
                if (k.length > 1) {
                    cKeys.add(k[0]);
                    cValues.add(Integer.parseInt(k[1]));
                } else if (!k[0].isEmpty()) {
                    cValues.add(Integer.parseInt(k[0]));
                }
            }
            this.choices = cValues.toArray(Integer[]::new);
            this.choicesKeys = cKeys.toArray(String[]::new);
        }
        if (str.length > 7 && !str[7].isEmpty()) {
            this.failures = Arrays.stream(str[7].split(","))
                    .map(Integer::parseInt)
                    .toArray(Integer[]::new);
        }
        if (str.length > 8 && !str[8].isEmpty()) {
            this.extinct = Arrays.stream(str[8].split(","))
                    .map(Integer::parseInt)
                    .toArray(Integer[]::new);
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer[] getChoices() {
        return choices;
    }

    public void setChoices(Integer[] choices) {
        this.choices = choices;
    }

    public Integer[] getFailures() {
        return failures;
    }

    public void setFailures(Integer[] failures) {
        this.failures = failures;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isOnce() {
        return once;
    }

    public void setOnce(boolean once) {
        this.once = once;
    }

    public boolean isInput() {
        return input;
    }

    public void setInput(boolean input) {
        this.input = input;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public boolean isFailed() {
        return failed;
    }

    public void setFailed(boolean failed) {
        this.failed = failed;
    }

    public String[] getChoicesKeys() {
        return choicesKeys;
    }

    public void setChoicesKeys(String[] choicesKeys) {
        this.choicesKeys = choicesKeys;
    }

    public Integer[] getExtinct() {
        return extinct;
    }

    public void setExtinct(Integer[] extinct) {
        this.extinct = extinct;
    }
}
