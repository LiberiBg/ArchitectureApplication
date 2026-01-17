package esiea.yangnguyen.architectureapplication;

import esiea.yangnguyen.architectureapplication.config.TestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(TestConfig.class)
class ArchitectureApplicationTests {

    @Test
    void contextLoads() {
    }

}
