package ru.shanalotte.bankbarrel.rest.infomodule.beanscreation;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;
import ru.shanalotte.bankbarrel.core.dto.CodeAndValuePair;
import ru.shanalotte.bankbarrel.core.service.EnumToCodeAndValuePairConverter;


@SpringBootTest
@ActiveProfiles("dev")
public class BeansCreationInDevProfileTest {

  @Autowired
  private EnumToCodeAndValuePairConverter enumToCodeAndValuePairConverter;

  @Autowired
  private RestTemplate restTemplate;

  @Test
  public void enumToCodeAndValuePairConverterIsWorkingCorrectly() {
    String enumElementName = "SOMEENUMVALUE";
    CodeAndValuePair codeAndValuePair = enumToCodeAndValuePairConverter.convert(enumElementName);
    assertThat(codeAndValuePair.getCode()).isEqualTo(enumElementName);
    assertThat(codeAndValuePair.getValue()).isNotNull();
  }

  @Test
  public void restTemplateCreatingTest() {
    assertThat(restTemplate).isNotNull();
  }
}