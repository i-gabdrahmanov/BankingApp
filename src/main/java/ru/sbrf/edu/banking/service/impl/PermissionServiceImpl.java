package ru.sbrf.edu.banking.service.impl;

import org.springframework.stereotype.Service;
import ru.sbrf.edu.banking.model.Department;
import ru.sbrf.edu.banking.service.PermissionService;

import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {
    @Override
    public List<Department> getPermissionFor(Department department) {
        return List.of(new Department(1L, "Первый"),
                new Department(2L, "Второй"));
    }
}
