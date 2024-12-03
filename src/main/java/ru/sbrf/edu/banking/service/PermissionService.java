package ru.sbrf.edu.banking.service;

import ru.sbrf.edu.banking.model.Department;

import java.util.List;

public interface PermissionService {

    List<Department> getPermissionFor(Department department);
}
