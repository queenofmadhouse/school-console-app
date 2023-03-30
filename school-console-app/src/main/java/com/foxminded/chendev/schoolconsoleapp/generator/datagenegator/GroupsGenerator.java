package com.foxminded.chendev.schoolconsoleapp.generator.datagenegator;

import com.foxminded.chendev.schoolconsoleapp.dao.impl.GroupDaoImpl;
import com.foxminded.chendev.schoolconsoleapp.entity.Group;

import java.util.Random;

public class GroupsGenerator implements DataGenerator {
    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int ALPHABET_SIZE = ALPHABET.length();
    private Random random = new Random();
    private static GroupDaoImpl groupDao;
    private static long amountOfGroups;

    public GroupsGenerator(GroupDaoImpl groupDao, long amountOfGroups) {
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
