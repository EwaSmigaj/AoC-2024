package org.aoc2024;

import lombok.NoArgsConstructor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


@NoArgsConstructor
public class Day4 extends Day{
    @Override
    public void part1() {
        int sum = 0;

//        horizontal
        var input = this.readFile("day4.txt");
        var lines = input.split("\n");

        for (String line : lines) {
            Pattern pattern = Pattern.compile("XMAS");
            Matcher matcher = pattern.matcher(line);
            while(matcher.find())
            {
                sum++;
            }
        }

//        written backwards
        for (String line : lines) {
            Pattern pattern = Pattern.compile("SAMX");
            Matcher matcher = pattern.matcher(line);
            while(matcher.find())
            {
                sum++;
            }
        }

        String[] linesDiagonal = new String[lines.length];

        for (int i = 0; i < lines.length; i++) {
            StringBuilder newLine = new StringBuilder();
            for(String line: lines) {
                newLine.append(line.charAt(i));
            }
            System.out.println(newLine.toString());
            linesDiagonal[i] = newLine.toString();
        }

        }

    @Override
    public void part2() {
    }
}
