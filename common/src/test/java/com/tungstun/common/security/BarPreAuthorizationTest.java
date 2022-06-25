package com.tungstun.common.security;


import com.tungstun.common.security.annotation.BarPreAuthorization;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcBuilderCustomizer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.ConfigurableMockMvcBuilder;

/**
 * Test class tests the {@code BarPreAuthorization} annotation and the logic of it from it's {@code BarPreAuthorizationAspect}
 * */
@SpringBootTest
class BarPreAuthorizationTest implements MockMvcBuilderCustomizer {
    @Override
    public void customize(ConfigurableMockMvcBuilder<?> builder) {
        RequestBuilder apiKeyRequestBuilder = MockMvcRequestBuilders.get("any")
                .header("access_token", "jwt");
        builder.defaultRequest(apiKeyRequestBuilder);
    }

    private class TestService {
        @BarPreAuthorization(id = "123")
        public void staticIdMethod() {
        }

        @BarPreAuthorization(id = "#id")
        public void parameterIdMethod(String id) {
        }

        @BarPreAuthorization(id = "#dto.id")
        public void parameterFieldIdMethod(TestDTO dto) {
        }
    }

    private class TestDTO {
        private String id;

        public TestDTO(String id) {
            this.id = id;
        }
    }

    @BeforeEach
    void setup() {

    }

    private final TestService testService = new TestService();

//    @Test
//    void doTest() {
//        testService.staticIdMethod();
//    }
}
