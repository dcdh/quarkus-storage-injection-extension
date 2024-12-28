package org.acme.storage.qualifier.deployment;

import io.quarkus.agroal.DataSource;
import io.quarkus.arc.deployment.SyntheticBeanBuildItem;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.annotations.ExecutionTime;
import io.quarkus.deployment.annotations.Record;
import io.quarkus.deployment.builditem.CombinedIndexBuildItem;
import io.quarkus.deployment.builditem.FeatureBuildItem;
import io.quarkus.mongodb.MongoClientName;
import jakarta.inject.Singleton;
import org.acme.storage.qualifier.runtime.FruitRepository;
import org.acme.storage.qualifier.runtime.FruitRepositoryRecorder;
import org.acme.storage.qualifier.runtime.JdbcFruitRepository;
import org.acme.storage.qualifier.runtime.MongoFruitRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

class StorageQualifierProcessor {

    private static final String FEATURE = "storage-qualifier";

    @BuildStep
    FeatureBuildItem feature() {
        return new FeatureBuildItem(FEATURE);
    }

    @BuildStep
    @Record(ExecutionTime.RUNTIME_INIT)
    List<SyntheticBeanBuildItem> registerJdbcFruitRepository(
            final CombinedIndexBuildItem combinedIndexBuildItem,
            final FruitRepositoryRecorder fruitRepositoryRecorder) {
        final Set<String> dataSourcesName = combinedIndexBuildItem.getIndex()
                .getAnnotations(DataSource.class)
                .stream()
                .map(name -> name.value().asString())
                .collect(Collectors.toSet());

        return dataSourcesName.stream()
                .map(dataSourceName -> SyntheticBeanBuildItem.configure(JdbcFruitRepository.class)
                        .types(FruitRepository.class, JdbcFruitRepository.class)
                        .scope(Singleton.class)
                        .createWith(fruitRepositoryRecorder.jdbcFruitRepositorySupplier(dataSourceName))
                        .addQualifier().annotation(DataSource.class).addValue("value", dataSourceName).done()
                        .unremovable()
                        .setRuntimeInit()
                        .done())
                .toList();
    }

    @BuildStep
    @Record(ExecutionTime.RUNTIME_INIT)
    List<SyntheticBeanBuildItem> registerMongoFruitRepository(
            final CombinedIndexBuildItem combinedIndexBuildItem,
            final FruitRepositoryRecorder fruitRepositoryRecorder) {
        final Set<String> mongoClientsName = combinedIndexBuildItem.getComputingIndex()
                .getAnnotations(MongoClientName.class)
                .stream()
                .map(name -> name.value().asString())
                .collect(Collectors.toSet());

        return mongoClientsName.stream()
                .map(mongoClientName ->
                        SyntheticBeanBuildItem.configure(MongoFruitRepository.class)
                                .types(FruitRepository.class, MongoFruitRepository.class)
                                .scope(Singleton.class)
                                .createWith(fruitRepositoryRecorder.mongoFruitRepositorySupplier(mongoClientName))
                                .addQualifier().annotation(MongoClientName.class).addValue("value", mongoClientName).done()
                                .unremovable()
                                .setRuntimeInit()
                                .done())
                .toList();
    }

}
