package com.foxminded.chendev.schoolconsoleapp.datagenerator.impl;

import com.foxminded.chendev.schoolconsoleapp.dao.GroupDao;
import com.foxminded.chendev.schoolconsoleapp.datagenerator.DataGenerator;
import com.foxminded.chendev.schoolconsoleapp.entity.Group;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class GroupDataGenerator implements DataGenerator {
    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int ALPHABET_SIZE = ALPHABET.length();
    private final Random random = new Random();
    private final GroupDao groupDao;
    private final int amountOfGroups;

    public GroupDataGenerator(GroupDao groupDao, int amountOfGroups) {

        this.groupDao = groupDao;
        this.amountOfGroups = amountOfGroups;
    }

    @Override
    public void generateData() {

        for (int i = 0; i < amountOfGroups; i++) {

            String groupName = generateGroupName();
            Group group = Group.builder()
                    .withGroupName(groupName)
                    .build();

            groupDao.save(group);
        }

    }

    private String generateGroupName() {

        char character1 = ALPHABET.charAt(random.nextInt(ALPHABET_SIZE));
        char character2 = ALPHABET.charAt(random.nextInt(ALPHABET_SIZE));
        int number1 = random.nextInt(10);
        int number2 = random.nextInt(10);

        return String.format("%c%c-%d%d", character1, character2, number1, number2);
    }
}
