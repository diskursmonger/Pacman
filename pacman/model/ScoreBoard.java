package pacman.model;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class ScoreBoard {
    private static final String fileName = "scores.txt";

    public static ArrayList<Entry> getScoresList() {
        Scanner inputScanner;
        var result = new ArrayList<Entry>();

        try {
            inputScanner = new Scanner(new FileInputStream(ScoreBoard.fileName));
        } catch (FileNotFoundException e) {
            return result;
        }

        while (inputScanner.hasNextLine()) {
            result.add(Entry.fromString(inputScanner.nextLine()));
        }

        result.sort(Comparator.comparingInt(e -> e.score));

        return result;
    }

    public static void addScoreEntry(Entry newScoreEntry) throws IOException {
        var outputStream = new PrintStream(new FileOutputStream(ScoreBoard.fileName, true));
        outputStream.println(newScoreEntry.toString());
    }

    public static class Entry {
        public final String playerName;
        public final int score;

        public Entry(String playerName, int score) {
            this.playerName = playerName.isBlank() ? "Player" : playerName;
            this.score = score;
        }

        @Override
        public String toString() {
            return String.format("%s %d", this.playerName, this.score);
        }

        public static Entry fromString(String stringRepr) {
            var tokenizer = new Scanner(stringRepr);

            String playerName = tokenizer.hasNext() ? tokenizer.next() : "";
            int score = tokenizer.hasNextInt() ? tokenizer.nextInt() : -1;

            return new Entry(playerName, score);
        }
    }

}
