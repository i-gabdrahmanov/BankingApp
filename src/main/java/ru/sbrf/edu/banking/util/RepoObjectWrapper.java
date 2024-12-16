package ru.sbrf.edu.banking.util;

import lombok.Data;

@Data
public class RepoObjectWrapper<T> {

    private T value;
}
