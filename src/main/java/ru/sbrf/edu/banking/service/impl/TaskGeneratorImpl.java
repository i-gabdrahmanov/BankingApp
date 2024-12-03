package ru.sbrf.edu.banking.service.impl;

import org.springframework.stereotype.Service;
import ru.sbrf.edu.banking.model.Department;
import ru.sbrf.edu.banking.service.TaskGenerator;

import java.util.List;

@Service
public class TaskGeneratorImpl implements TaskGenerator {
    @Override
    public void generateDailyTaskForArch(List<Department> permitted, Department initiator) {
        System.out.println(permitted);
    }
}
