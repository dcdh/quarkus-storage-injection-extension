package org.acme.storage.qualifier.test;

import io.agroal.api.AgroalDataSource;
import io.quarkus.agroal.DataSource;
import io.quarkus.datasource.common.runtime.DataSourceUtil;
import io.quarkus.test.QuarkusUnitTest;
import jakarta.inject.Inject;
import org.acme.storage.qualifier.runtime.FruitRepository;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

public class DataSourceDefaultSampleTest {

    @RegisterExtension
    static final QuarkusUnitTest unitTest = new QuarkusUnitTest()
            .setArchiveProducer(() -> ShrinkWrap.create(JavaArchive.class));

    @Inject
    AgroalDataSource agroalDataSource;

    @Inject
    @DataSource(DataSourceUtil.DEFAULT_DATASOURCE_NAME)
    FruitRepository fruitRepository;

    @Test
    void shouldReturnDataSourceName() {
        Assertions.assertEquals("<default>", fruitRepository.name());
    }
}
