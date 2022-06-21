package com.applications.asm.data.model.mapper;

import com.applications.asm.data.model.PriceModel;
import com.applications.asm.domain.entities.Price;

import java.util.List;

public interface PriceMapper {
    PriceModel getPriceModel(Price price);
    Price getPrice(PriceModel priceModel);
    List<Price> getPrices(List<PriceModel> pricesModel);
    List<PriceModel> getPricesModel(List<Price> prices);
}
