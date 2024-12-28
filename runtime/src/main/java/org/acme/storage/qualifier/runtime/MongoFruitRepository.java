package org.acme.storage.qualifier.runtime;

import java.util.Objects;

public class MongoFruitRepository implements FruitRepository {
    private final String name;

    public MongoFruitRepository(final String name) {
        this.name = Objects.requireNonNull(name);
    }

    @Override
    public String name() {
        return name;
    }
}
