package org.acme.storage.qualifier.test;

import io.agroal.api.AgroalDataSource;
import io.quarkus.agroal.DataSource;
import io.quarkus.test.QuarkusUnitTest;
import jakarta.inject.Inject;
import org.acme.storage.qualifier.runtime.FruitRepository;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

public class DataSourceMasterSampleTest {
    @RegisterExtension
    static final QuarkusUnitTest unitTest = new QuarkusUnitTest()
            .setArchiveProducer(() -> ShrinkWrap.create(JavaArchive.class)
                    .addAsResource(
                            // language=properties
                            new StringAsset("quarkus.datasource.\"master\".devservices.enabled=true"),
                            "application.properties"));

    @Inject
    @DataSource("master")
    AgroalDataSource agroalDataSource;

    @Inject
    @DataSource("master")
    FruitRepository fruitRepository;

    @Test
    void shouldReturnDataSourceName() {
        Assertions.assertEquals("master", fruitRepository.name());
    }
}
