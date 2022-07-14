package ru.shanalotte.bankbarrel.webapp.service.listing;

import ru.shanalotte.bankbarrel.core.dto.CodeAndValuesPairsListWrapper;

/**
 * Интерфейс для получения CodeAndValuesPairsListWrapper.
 */

public interface ListingService {
  CodeAndValuesPairsListWrapper getListingDto();
}
