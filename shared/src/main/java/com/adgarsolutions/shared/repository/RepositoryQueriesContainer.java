package com.adgarsolutions.shared.repository;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RepositoryQueriesContainer {
    private String findAll;
    private String findById;
    private String count;
    private String save;
    private List<String> storedProcs = new ArrayList<>();
}
