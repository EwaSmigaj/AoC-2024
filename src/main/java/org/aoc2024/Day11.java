package org.aoc2024;

import java.util.*;
import java.util.stream.Collectors;

public class Day11 extends Day {

    List<Long> stones = new LinkedList<>();
    HashMap<Long, Long> stoneCount = new HashMap<>();

    @Override
    public void part1() {
        String input = this.readFile("day11.txt");
        stones = Arrays.stream(input.split(" ")).map(Long::valueOf).collect(Collectors.toCollection(LinkedList::new));

        for(int i = 0; i < 25; i++) {
            blink();
            System.out.println(stones.size());
        }

        System.out.println("___________________________");
    }

    private void blink() {
        List<Long> stonesAfterBlink = new LinkedList<>();
        List<String> newStones;
        Long stone;

        for (Long integer : stones) {
            stone = integer;

            if (stone == 0) {
                newStones = Arrays.asList("1");
            } else if (stone.toString().length() % 2 == 0) {
                String stoneSting = stone.toString();
                newStones = Arrays.asList(stoneSting.substring(0, stoneSting.length() / 2), stoneSting.substring(stoneSting.length() / 2));
            } else {
                newStones = Arrays.asList(String.valueOf(stone * 2024));
            }

            for (String s : newStones) {
                stonesAfterBlink.add(Long.parseLong(s));
            }
        }

        stones = stonesAfterBlink;
    }

    @Override
    public void part2() {
        String input = this.readFile("day11.txt");

        // <Stone number, number of occurrences>

        for(Long stoneNb : Arrays.stream(input.split(" ")).map(Long::valueOf).collect(Collectors.toList())) {
            stoneCount.put(stoneNb, 1L);
        }

        for(int i = 0; i < 75; i++) {
            blinkOptimized();
        }
        System.out.println(stoneCount.values().stream().mapToLong(Long::longValue).sum());

    }

    private void blinkOptimized() {
        Long[] stoneNumbers = stoneCount.keySet().toArray(new Long[0]);
        HashMap<Long, Long> updates = new HashMap<>();
//        System.out.println(stoneCount);
        for (Long stoneNb : stoneNumbers) {
//            System.out.println("analysing stoneNb -> " + stoneNb);
            Long stoneOccurrences = stoneCount.get(stoneNb);
//            System.out.println("it has " + stoneOccurrences + " occurences");

            if (stoneOccurrences > 0) {
                    Long newStone;
                    String stoneNbString = String.valueOf(stoneNb);
                    if (stoneNb == 0) {
                        newStone = 1L;
                    } else if (stoneNbString.length() % 2 == 0) {
                        newStone = Long.parseLong(stoneNbString.substring(0, stoneNbString.length() / 2));
//                        System.out.println("changed " + stoneNb + " to " + newStone + " with occ = " + stoneOccurrences);
                        updates.merge(newStone, stoneOccurrences, Long::sum);
                        newStone = Long.parseLong(stoneNbString.substring(stoneNbString.length() / 2));
                    } else {
                        newStone = stoneNb * 2024;
                    }
                    updates.merge(newStone, stoneOccurrences, Long::sum);
                    updates.merge(stoneNb, -stoneOccurrences, Long::sum);
//                System.out.println("changed " + stoneNb + " to " + newStone);
//                System.out.println(stoneCount);
            }
        }
        for(Long key : updates.keySet()) {
            stoneCount.merge(key, updates.get(key), Long::sum);
        }
//        System.out.println(stoneCount);
    }
}
