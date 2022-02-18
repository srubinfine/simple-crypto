package com.adgarsolutions.shared.repository;

import java.util.Map;

public interface Identifiable<ID> {
    ID getId();
    Map<String, Object> getFieldMappings();
}
