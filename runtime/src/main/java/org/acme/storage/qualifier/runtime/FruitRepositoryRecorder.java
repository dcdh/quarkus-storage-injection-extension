package org.acme.storage.qualifier.runtime;

import io.quarkus.arc.SyntheticCreationalContext;
import io.quarkus.runtime.annotations.Recorder;

import java.util.function.Function;

@Recorder
public class FruitRepositoryRecorder {
    public Function<SyntheticCreationalContext<MongoFruitRepository>, MongoFruitRepository> mongoFruitRepositorySupplier(
            final String mongoClientName) {
        return new Function<SyntheticCreationalContext<MongoFruitRepository>, MongoFruitRepository>() {
            @Override
            public MongoFruitRepository apply(final SyntheticCreationalContext<MongoFruitRepository> context) {
                return new MongoFruitRepository(
                        mongoClientName
                );
            }
        };
    }


    public Function<SyntheticCreationalContext<JdbcFruitRepository>, JdbcFruitRepository> jdbcFruitRepositorySupplier(
            final String dataSourceName) {
        return new Function<SyntheticCreationalContext<JdbcFruitRepository>, JdbcFruitRepository>() {
            @Override
            public JdbcFruitRepository apply(final SyntheticCreationalContext<JdbcFruitRepository> context) {
                return new JdbcFruitRepository(dataSourceName);
            }
        };
    }
}
