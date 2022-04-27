package ru.shanalotte.bankbarrel.webapp.controller;

import java.util.Arrays;
import static org.assertj.core.api.Assertions.assertThat;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import ru.shanalotte.bankbarrel.core.dto.ListingDtoItem;
import ru.shanalotte.bankbarrel.core.dto.ListingDto;
import ru.shanalotte.bankbarrel.webapp.service.ListingService;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountOpeningTest {

  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private EnrollingHelper enrollingHelper;
  @Autowired
  private ListingService accountTypeListingService;
  @Autowired
  private ListingService accountAdditionalTypesListingService;
  @Autowired
  private ListingService accountOpeningCurrenciesListingService;

  @Test
  public void userPageModelShouldContainAccountOpeningDto() throws Exception {
    enrollingHelper.enrollTestUser();

    mockMvc.perform(MockMvcRequestBuilders.get("/user/testuser"))
        .andExpect(model().attributeExists("accountOpeningDto"));
  }


  @Test
  public void whenOpeningAccountUserShouldSeeAllPossibleAccountTypesAndCurrencies() throws Exception {
    enrollingHelper.enrollTestUser();

    ListingDto accountTypesDto = accountTypeListingService.getListingDto();
    ListingDto accountAdditionalTypesDto = accountAdditionalTypesListingService.getListingDto();
    ListingDto accountOpeningCurrencies = accountOpeningCurrenciesListingService.getListingDto();
    mockMvc.perform(MockMvcRequestBuilders.get("/user/testuser"))
        .andExpect(model().attribute("accountTypesDto", Matchers.equalTo(accountTypesDto)))
        .andExpect(model().attribute("accountAdditionalTypesDto", Matchers.equalTo(accountAdditionalTypesDto)))
        .andExpect(model().attribute("accountOpeningCurrenciesDto", Matchers.equalTo(accountOpeningCurrencies)));
    MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/user/testuser"))
        .andReturn();
    String content = mvcResult.getResponse().getContentAsString();
    System.out.println(content);
    for (ListingDto dto : Arrays.asList(accountTypesDto, accountAdditionalTypesDto, accountOpeningCurrencies)) {
      for (ListingDtoItem dtoItem : dto.getItems()) {
        assertThat(content).contains(dtoItem.getCode());
        assertThat(content).contains(dtoItem.getValue());
      }
    }
  }
}
