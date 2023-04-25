package com.foxminded.chendev.schoolconsoleapp.initializer;

import com.foxminded.chendev.schoolconsoleapp.dao.CrudDao;
import com.foxminded.chendev.schoolconsoleapp.service.DataGeneratorService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class ApplicationInitializerImpl implements ApplicationInitializer {

    private final List<CrudDao<?>> daoList;
    private final DataGeneratorService dataGeneratorService;

    public ApplicationInitializerImpl(List<CrudDao<?>> daoList, DataGeneratorService dataGeneratorService) {
        this.daoList = daoList;
        this.dataGeneratorService = dataGeneratorService;
    }

    @Override
    @PostConstruct
    public void init() {

        if (areAllDatabasesEmpty()) {
            dataGeneratorService.generateData();
        }
    }

    private boolean areAllDatabasesEmpty() {
        return daoList.stream().allMatch(this::isTableEmpty);
    }

    private boolean isTableEmpty(CrudDao<?> dao) {
        List<?> list = dao.findAll();
        return list.isEmpty();
    }
}
