package com.applications.asm.data.model.mapper;

import com.applications.asm.data.model.PriceModel;
import com.applications.asm.domain.entities.Price;

import java.util.ArrayList;
import java.util.List;

public class PriceModelMapperImpl implements PriceModelMapper {
    @Override
    public PriceModel getPriceModel(Price price) {
        return new PriceModel(price.getId(), price.getName());
    }

    @Override
    public Price getPrice(PriceModel priceModel) {
        return new Price(priceModel.getId(), priceModel.getName());
    }

    @Override
    public List<Price> getPrices(List<PriceModel> pricesModel) {
        List<Price> prices = new ArrayList<>();
        for(PriceModel priceModel: pricesModel)
            prices.add(getPrice(priceModel));
        return prices;
    }

    @Override
    public List<PriceModel> getPricesModel(List<Price> prices) {
        List<PriceModel> pricesModel = new ArrayList<>();
        for(Price price: prices)
            pricesModel.add(getPriceModel(price));
        return pricesModel;
    }
}
