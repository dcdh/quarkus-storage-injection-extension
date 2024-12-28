package org.acme.storage.qualifier.runtime;

import java.util.Objects;

public class JdbcFruitRepository implements FruitRepository {
    private final String name;

    public JdbcFruitRepository(final String name) {
        this.name = Objects.requireNonNull(name);
    }

    @Override
    public String name() {
        return name;
    }
}
