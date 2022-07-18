package ru.job4j.concurrent;

import java.io.*;

public class Testing {
    public static void main(String[] args) throws IOException {
        int best = 0;
        int current = 0;
        int capacity;
        try (BufferedReader reader = new BufferedReader(new FileReader("input.txt"))) {
            try {
                capacity = Integer.parseInt(reader.readLine());
            } catch (NumberFormatException e) {
                capacity = 0;
            }
            for (int b = 0; b <= capacity; b++) {
                int i;
                try {
                    i = Integer.parseInt(reader.readLine());
                } catch (NumberFormatException e) {
                    i = 0;
                }
                if (i == 1) {
                    current++;
                } else if (current > best) {
                    best = current;
                    current = 0;
                } else {
                    current = 0;
                }
            }
        }

        try (FileWriter writer = new FileWriter("output.txt")) {
            writer.write(String.valueOf(Math.max(best, current)));
        }
    }
}
