package com.kata.secretSanta;

import com.google.common.base.Throwables;
import com.google.common.collect.Lists;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.SynchronousQueue;

import static java.util.stream.Collectors.toList;

/**
 * Created by bec on 15/06/15.
 * Implementation of kata Secret Santa
 */
public class SecretSanta {
    int FIRSTNAME = 0;
    int LASTNAME = 1;
    int EMAIL = 2;
    private Map<String, List<String>> santaAttendants = new HashMap<>();

    public SecretSanta on(String santaList) {
        try {
            Files.lines(Paths.get(santaList))
                    .map(s -> s.split("\\s+"))
                    .forEach(this::addPerson);

            return this;
        } catch (IOException e) {
            throw Throwables.propagate(e);
        }
    }

    private void addPerson(String[] person) {
        if (santaAttendants.containsKey(person[LASTNAME])){
            santaAttendants.get(person[LASTNAME]).add(person[FIRSTNAME]+ " " + person[LASTNAME]);
        }
        else {
            List<String> familyList = new ArrayList();
            familyList.add(person[FIRSTNAME]+ " " + person[LASTNAME]);
            santaAttendants.put(person[LASTNAME], familyList);
        }
    }

    public List<String> drawSanta(){
        List<String> secretSantaList = new ArrayList();

        String lastDrawnFamily = "";
        Random rand = new Random();
        while (numberOfSantas()>0){
            if (santaAttendants.get(largestFamily()).size() * 2 >= numberOfSantas()){
                lastDrawnFamily = largestFamily();

                secretSantaList.add(
                        santaAttendants.get(lastDrawnFamily)
                        .remove(rand.nextInt(santaAttendants.get(lastDrawnFamily).size())));
            }
            else {
                List<String> elligibleFamilies = santaAttendants.keySet().stream().collect(toList());
                elligibleFamilies.remove(lastDrawnFamily);
                lastDrawnFamily = elligibleFamilies.get(rand.nextInt(elligibleFamilies.size()));

                secretSantaList.add(
                        santaAttendants.get(lastDrawnFamily)
                                .remove(rand.nextInt(santaAttendants.get(lastDrawnFamily).size())));
            }
            if (santaAttendants.get(lastDrawnFamily).size() == 0) {
                santaAttendants.remove(lastDrawnFamily);
            }
        }
        return secretSantaList;
    }

    public List<String> drawSanta_() {
        List<String> secretSantaList = new ArrayList();

        while (numberOfSantas() > 0) {

        }
        return secretSantaList;
    }

    private String largestFamily(){
        return santaAttendants.entrySet().stream()
                .reduce((f1, f2) -> f1.getValue().size() > f2.getValue().size()? f1 : f2)
                .map(s -> s.getKey())
                .get();
    }

    private int numberOfSantas(){
        if (santaAttendants.entrySet().isEmpty()) return 0;
        return santaAttendants.entrySet().stream()
                .map(s -> s.getValue().size())
                .reduce((s1, s2) -> s1 + s2)
                .get();
    }
}
