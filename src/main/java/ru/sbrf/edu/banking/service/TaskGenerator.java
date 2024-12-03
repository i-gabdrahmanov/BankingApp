package ru.sbrf.edu.banking.service;

import ru.sbrf.edu.banking.model.Department;

import java.util.List;

public interface TaskGenerator {
    void generateDailyTaskForArch(List<Department> permitted, Department initiator);
}
