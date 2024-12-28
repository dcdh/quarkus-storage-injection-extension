package org.acme.storage.qualifier.test;

import com.mongodb.client.MongoClient;
import io.quarkus.mongodb.MongoClientName;
import io.quarkus.mongodb.runtime.MongoClientBeanUtil;
import io.quarkus.test.QuarkusUnitTest;
import jakarta.inject.Inject;
import org.acme.storage.qualifier.runtime.FruitRepository;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

public class MongoclientDefaultQualifierTest {
    @RegisterExtension
    static final QuarkusUnitTest unitTest = new QuarkusUnitTest()
            .setArchiveProducer(() -> ShrinkWrap.create(JavaArchive.class));

    @Inject
    MongoClient mongoClient;

    @Inject
    @MongoClientName(MongoClientBeanUtil.DEFAULT_MONGOCLIENT_NAME)
    FruitRepository fruitRepository;

    @Test
    void shouldReturnDataSourceName() {
        Assertions.assertEquals("<default>", fruitRepository.name());
    }
}
