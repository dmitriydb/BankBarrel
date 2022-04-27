package ru.shanalotte.bankbarrel.core;


import java.util.stream.Stream;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static ru.shanalotte.bankbarrel.core.CustomerCreationData.validName;
import static ru.shanalotte.bankbarrel.core.CustomerCreationData.validSurname;
import ru.shanalotte.bankbarrel.core.domain.BankClient;

public class CustomerCreationTest {


  @ParameterizedTest
  @MethodSource("invalidNames")
  public void should_ThrowException_WhenClientCreatedWithInvalidName(String invalidName){
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      BankClient.Builder customer = new BankClient.Builder(invalidName, validSurname);
    });
    assertThat(exception.getMessage()).contains("givenName");
  }

  @ParameterizedTest
  @MethodSource("invalidSurnames")
  public void should_ThrowException_WhenClientCreatedWithInvalidSurname(String invalidSurname){
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      BankClient.Builder customer = new BankClient.Builder(validName, invalidSurname);
    });
    assertThat(exception.getMessage()).contains("familyName");
  }

  @ParameterizedTest
  @MethodSource("validNames")
  public void should_CreateCustomer_WithValidName(String validName){
    BankClient.Builder customer = new BankClient.Builder(validName, validSurname);
  }

  @ParameterizedTest
  @MethodSource("validSurnames")
  public void should_CreateCustomer_WithValidSurname(String validSurname){
    BankClient.Builder customer = new BankClient.Builder(validName, validSurname);
  }

  @Test
  public void should_NotCreateCustomer_WithoutEmailAndTelephone(){
    Exception exception = assertThrows(Exception.class, () -> {
      BankClient bankClient = new BankClient.Builder(validName, validSurname).build();
    });
  }

  @Test
  public void should_CreateCustomerWithOnlyEmailFilled(){
    BankClient bankClient = new BankClient.Builder(validName, validSurname)
        .withEmail("abc@xyz")
        .build();
  }

  @Test
  public void should_CreateCustomerWithOnlyTelephoneFilled(){
    BankClient bankClient = new BankClient.Builder(validName, validSurname)
        .withTelephone("+7849238402384")
        .build();
  }

  @Test
  public void should_CreateCustomerWithBothTelephoneAndEmailFilled(){
    BankClient bankClient = new BankClient.Builder(validName, validSurname)
        .withTelephone("+74283642834")
        .withEmail("dadaad@djafj.xfjkdf")
        .build();
  }

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

}