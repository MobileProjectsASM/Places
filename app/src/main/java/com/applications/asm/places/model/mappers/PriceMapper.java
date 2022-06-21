package com.applications.asm.places.model.mappers;

import com.applications.asm.domain.entities.Price;
import com.applications.asm.places.model.PriceVM;

import java.util.List;

public interface PriceMapper {
    PriceVM getPriceVM(Price price);
    Price getPrice(PriceVM priceVM);
    List<PriceVM> getPricesVM(List<Price> prices);
    List<Price> getPrices(List<PriceVM> pricesVM);
}
