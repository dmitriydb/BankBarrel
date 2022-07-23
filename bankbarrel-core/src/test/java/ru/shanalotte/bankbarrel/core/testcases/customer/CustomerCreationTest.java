package ru.shanalotte.bankbarrel.core.testcases.customer;


import java.util.stream.Stream;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static ru.shanalotte.bankbarrel.core.service.DummyTestingEntitiesConstants.VALID_BANK_CLIENT_EMAIL;
import static ru.shanalotte.bankbarrel.core.service.DummyTestingEntitiesConstants.VALID_BANK_CLIENT_NAME;
import static ru.shanalotte.bankbarrel.core.service.DummyTestingEntitiesConstants.VALID_BANK_CLIENT_SURNAME;
import ru.shanalotte.bankbarrel.core.domain.BankClient;

public class CustomerCreationTest {

  public static Stream<Arguments> invalidNames(){
    return Stream.of(
        Arguments.of(""),
        Arguments.of("    "),
        null
    );
  }

  public static Stream<Arguments> invalidSurnames(){
    return Stream.of(
        Arguments.of(""),
        Arguments.of("    "),
        null
    );
  }

  public static Stream<Arguments> validNames(){
    return Stream.of(
        Arguments.of("Jina"),
        Arguments.of("Jack")
    );
  }

  public static Stream<Arguments> validSurnames(){
    return Stream.of(
        Arguments.of("Goldberg"),
        Arguments.of("Isaac"),
        Arguments.of("Goldstein"),
        Arguments.of("Schindler")
    );
  }


  @ParameterizedTest
  @MethodSource("invalidNames")
  public void should_ThrowException_WhenClientCreatedWithInvalidName(String invalidName){
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
       new BankClient.Builder(invalidName, VALID_BANK_CLIENT_SURNAME).build();
    });
    assertThat(exception.getMessage()).contains("givenName");
  }

  @ParameterizedTest
  @MethodSource("invalidSurnames")
  public void should_ThrowException_WhenClientCreatedWithInvalidSurname(String invalidSurname){
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      new BankClient.Builder(VALID_BANK_CLIENT_NAME, invalidSurname).build();
    });
    assertThat(exception.getMessage()).contains("familyName");
  }

  @ParameterizedTest
  @MethodSource("validNames")
  public void should_CreateCustomer_WithValidName(String validName){
    new BankClient.Builder(validName, VALID_BANK_CLIENT_SURNAME)
        .withEmail(VALID_BANK_CLIENT_EMAIL)
        .build();
  }

  @ParameterizedTest
  @MethodSource("validSurnames")
  public void should_CreateCustomer_WithValidSurname(String validSurname){
    new BankClient.Builder(VALID_BANK_CLIENT_NAME, validSurname)
        .withEmail(VALID_BANK_CLIENT_EMAIL)
        .build();
  }

  @Test
  public void should_NotCreateCustomer_WithoutEmailAndTelephone(){
    assertThrows(Exception.class, () -> {
       new BankClient.Builder(VALID_BANK_CLIENT_NAME, VALID_BANK_CLIENT_SURNAME).build();
    });
  }

  @Test
  public void should_CreateCustomerWithOnlyEmailFilled(){
    new BankClient.Builder(VALID_BANK_CLIENT_NAME, VALID_BANK_CLIENT_SURNAME)
        .withEmail(VALID_BANK_CLIENT_EMAIL)
        .build();
  }

  @Test
  public void should_CreateCustomerWithOnlyTelephoneFilled(){
    new BankClient.Builder(VALID_BANK_CLIENT_NAME, VALID_BANK_CLIENT_SURNAME)
        .withTelephone("+7849238402384")
        .build();
  }

  @Test
  public void should_CreateCustomerWithBothTelephoneAndEmailFilled(){
    new BankClient.Builder(VALID_BANK_CLIENT_NAME, VALID_BANK_CLIENT_SURNAME)
        .withTelephone("+74283642834")
        .withEmail("dadaad@djafj.xfjkdf")
        .build();
  }

}