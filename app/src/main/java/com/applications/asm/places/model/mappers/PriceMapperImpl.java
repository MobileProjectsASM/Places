package com.applications.asm.places.model.mappers;

import com.applications.asm.domain.entities.Price;
import com.applications.asm.places.model.PriceVM;

import java.util.ArrayList;
import java.util.List;

public class PriceMapperImpl implements PriceMapper {
    @Override
    public PriceVM getPriceVM(Price price) {
        return new PriceVM(price.getId(), price.getName());
    }

    @Override
    public Price getPrice(PriceVM priceVM) {
        return new Price(priceVM.getId(), priceVM.getName());
    }

    @Override
    public List<PriceVM> getPricesVM(List<Price> prices) {
        List<PriceVM> pricesVM = new ArrayList<>();
        for(Price price: prices)
            pricesVM.add(getPriceVM(price));
        return pricesVM;
    }

    @Override
    public List<Price> getPrices(List<PriceVM> pricesVM) {
        List<Price> prices = new ArrayList<>();
        for(PriceVM priceVM: pricesVM)
            prices.add(getPrice(priceVM));
        return prices;
    }
}
