package ru.shanalotte.bankbarrel.webapp.service;

import org.springframework.stereotype.Service;
import ru.shanalotte.bankbarrel.webapp.dto.ListingDto;

/**
 * Интерфейс для получения ListingDto.
 */

public interface ListingService {
  ListingDto getListingDto();
}
