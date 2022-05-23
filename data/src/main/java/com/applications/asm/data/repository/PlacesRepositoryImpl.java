package com.applications.asm.data.repository;

import com.applications.asm.data.exception.PlacesDataSourceWSError;
import com.applications.asm.data.exception.PlacesDataSourceWSException;
import com.applications.asm.data.model.PlaceDetailsModel;
import com.applications.asm.data.model.PlaceModel;
import com.applications.asm.data.model.ResponsePlacesModel;
import com.applications.asm.data.model.ResponseReviewsModel;
import com.applications.asm.data.model.ResponseSuggestedPlacesModel;
import com.applications.asm.data.model.ReviewModel;
import com.applications.asm.data.model.SuggestedPlaceModel;
import com.applications.asm.data.model.mapper.PlaceDetailsModelMapper;
import com.applications.asm.data.model.mapper.PlaceModelMapper;
import com.applications.asm.data.model.mapper.ReviewModelMapper;
import com.applications.asm.data.model.mapper.SuggestedPlaceModelMapper;
import com.applications.asm.data.sources.PlacesDataSourceWS;
import com.applications.asm.domain.entities.Place;
import com.applications.asm.domain.entities.PlaceDetails;
import com.applications.asm.domain.entities.Review;
import com.applications.asm.domain.entities.SuggestedPlace;
import com.applications.asm.domain.exception.PlacesRepositoryError;
import com.applications.asm.domain.exception.PlacesRepositoryException;
import com.applications.asm.domain.repository.PlacesRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class PlacesRepositoryImpl implements PlacesRepository {
    private final PlacesDataSourceWS placeDataSourceWs;
    private final PlaceModelMapper placeModelMapper;
    private final PlaceDetailsModelMapper placeDetailsModelMapper;
    private final ReviewModelMapper reviewModelMapper;
    private final SuggestedPlaceModelMapper suggestedPlaceModelMapper;
    private final static Integer DEFAULT_AMOUNT = 10;
    private Integer totalPages;
    private final String TAG = "PlacesRepositoryImpl";
    private final Logger log = Logger.getLogger("com.applications.asm.data.repository.PlacesRepositoryImpl");

    public PlacesRepositoryImpl(
        PlacesDataSourceWS placesDataSourceWs,
        PlaceModelMapper placeModelMapper,
        PlaceDetailsModelMapper placeDetailsModelMapper,
        ReviewModelMapper reviewModelMapper,
        SuggestedPlaceModelMapper suggestedPlaceModelMapper
    ) {
        this.placeDataSourceWs = placesDataSourceWs;
        this.placeModelMapper = placeModelMapper;
        this.placeDetailsModelMapper = placeDetailsModelMapper;
        this.reviewModelMapper = reviewModelMapper;
        this.suggestedPlaceModelMapper = suggestedPlaceModelMapper;
    }

    @Override
    public PlaceDetails getPlaceDetails(String placeId) throws PlacesRepositoryException {
        if(placeId == null) throw new PlacesRepositoryException(PlacesRepositoryError.ANY_VALUE_IS_NULL);
        try {
            PlaceDetailsModel placeDetails = placeDataSourceWs.getPlaceDetailsModel(placeId);
            if(placeDetails != null)
                return placeDetailsModelMapper.getPlaceDetailsFromPlaceDetailsModel(placeDetails);
            throw new PlacesRepositoryException(PlacesRepositoryError.RESPONSE_NULL);
        } catch (IOException ioException) {
            log.info(TAG + ": " + ioException.getMessage());
            throw new PlacesRepositoryException(PlacesRepositoryError.CONNECTION_WITH_SERVER_ERROR);
        } catch (RuntimeException runtimeException) {
            log.info(TAG + ": " + runtimeException.getMessage());
            throw new PlacesRepositoryException(PlacesRepositoryError.DECODING_RESPONSE_ERROR);
        } catch (PlacesDataSourceWSException placesDataSourceWSException) {
            PlacesDataSourceWSError error = placesDataSourceWSException.getError();
            log.info(TAG + ": " + error.getMessage());
            switch (error) {
                case REDIRECTIONS:
                    throw new PlacesRepositoryException(PlacesRepositoryError.CONNECTION_WITH_SERVER_ERROR);
                case CLIENT_ERROR:
                    throw new PlacesRepositoryException(PlacesRepositoryError.DO_REQUEST_ERROR);
                default:
                    throw new PlacesRepositoryException(PlacesRepositoryError.SERVER_ERROR);
            }
        }
    }

    @Override
    public List<Place> getPlaces(String placeToFind, Double longitude, Double latitude, Integer radius, List<String> categories, Integer page) throws PlacesRepositoryException {
        if(placeToFind == null || longitude == null || latitude == null || radius == null || categories == null || page == null) throw new PlacesRepositoryException(PlacesRepositoryError.ANY_VALUE_IS_NULL);
        try {
            if(page == 0) {
                ResponsePlacesModel responsePlacesModel = placeDataSourceWs.getPlacesModel(placeToFind, longitude, latitude, radius, getCategories(categories), 0, DEFAULT_AMOUNT);
                List<Place> places = new ArrayList<>();
                if(responsePlacesModel != null)  {
                    int total = responsePlacesModel.getTotal();
                    totalPages = total % DEFAULT_AMOUNT == 0 ? total / 10 : total / 10 + 1;
                    for (PlaceModel placeModel : responsePlacesModel.getPlaces())
                        places.add(placeModelMapper.getPlaceFromPlaceModel(placeModel));
                    return places;
                } else throw new PlacesRepositoryException(PlacesRepositoryError.RESPONSE_NULL);
            } else if(page > 0 && page < totalPages) {
                ResponsePlacesModel responsePlacesModel = placeDataSourceWs.getPlacesModel(placeToFind, longitude, latitude, radius, getCategories(categories), (page - 1) * DEFAULT_AMOUNT , DEFAULT_AMOUNT);
                List<Place> places = new ArrayList<>();
                if(responsePlacesModel != null)  {
                    for (PlaceModel placeModel : responsePlacesModel.getPlaces())
                        places.add(placeModelMapper.getPlaceFromPlaceModel(placeModel));
                    return places;
                } else throw new PlacesRepositoryException(PlacesRepositoryError.RESPONSE_NULL);
            } throw new PlacesRepositoryException(PlacesRepositoryError.PAGE_OUT_OF_RANGE);
        } catch (IOException ioException) {
            log.info(TAG + ": " + ioException.getMessage());
            throw new PlacesRepositoryException(PlacesRepositoryError.CONNECTION_WITH_SERVER_ERROR);
        } catch (RuntimeException runtimeException) {
            log.info(TAG + ": " + runtimeException.getMessage());
            throw new PlacesRepositoryException(PlacesRepositoryError.DECODING_RESPONSE_ERROR);
        } catch (PlacesDataSourceWSException placesDataSourceWSException) {
            PlacesDataSourceWSError error = placesDataSourceWSException.getError();
            log.info(TAG + ": " + error.getMessage());
            switch (error) {
                case REDIRECTIONS:
                    throw new PlacesRepositoryException(PlacesRepositoryError.CONNECTION_WITH_SERVER_ERROR);
                case CLIENT_ERROR:
                    throw new PlacesRepositoryException(PlacesRepositoryError.DO_REQUEST_ERROR);
                default:
                    throw new PlacesRepositoryException(PlacesRepositoryError.SERVER_ERROR);
            }
        }
    }

    @Override
    public List<Review> getReviews(String placeId) throws PlacesRepositoryException {
        if(placeId == null) throw new PlacesRepositoryException(PlacesRepositoryError.ANY_VALUE_IS_NULL);
        try {
            List<Review> reviews = new ArrayList<>();
            ResponseReviewsModel responseReviewsModel = placeDataSourceWs.getReviewsModel(placeId);
            if(responseReviewsModel != null) {
                for (ReviewModel reviewModel : responseReviewsModel.getReviewModels())
                    reviews.add(reviewModelMapper.getReviewFromReviewModel(reviewModel));
                return reviews;
            } throw new PlacesRepositoryException(PlacesRepositoryError.RESPONSE_NULL);
        } catch (IOException ioException) {
            log.info(TAG + ": " + ioException.getMessage());
            throw new PlacesRepositoryException(PlacesRepositoryError.CONNECTION_WITH_SERVER_ERROR);
        } catch (RuntimeException runtimeException) {
            log.info(TAG + ": " + runtimeException.getMessage());
            throw new PlacesRepositoryException(PlacesRepositoryError.DECODING_RESPONSE_ERROR);
        } catch (PlacesDataSourceWSException placesDataSourceWSException) {
            PlacesDataSourceWSError error = placesDataSourceWSException.getError();
            log.info(TAG + ": " + error.getMessage());
            switch (error) {
                case REDIRECTIONS:
                    throw new PlacesRepositoryException(PlacesRepositoryError.CONNECTION_WITH_SERVER_ERROR);
                case CLIENT_ERROR:
                    throw new PlacesRepositoryException(PlacesRepositoryError.DO_REQUEST_ERROR);
                default:
                    throw new PlacesRepositoryException(PlacesRepositoryError.SERVER_ERROR);
            }
        }
    }

    @Override
    public List<SuggestedPlace> getSuggestedPlaces(String word, Double longitude, Double latitude) throws PlacesRepositoryException {
        if(word == null || latitude == null || longitude == null) throw new PlacesRepositoryException(PlacesRepositoryError.ANY_VALUE_IS_NULL);
        try {
            List<SuggestedPlace> suggestedPlaces = new ArrayList<>();
            ResponseSuggestedPlacesModel responseSuggestedPlacesModel = placeDataSourceWs.getSuggestedPlaces(word, latitude, longitude);
            if(responseSuggestedPlacesModel != null) {
                for(SuggestedPlaceModel suggestedPlaceModel: responseSuggestedPlacesModel.getSuggestPlacesModel())
                    suggestedPlaces.add(suggestedPlaceModelMapper.getSuggestedPlaceFromSuggestedPlaceModel(suggestedPlaceModel));
                return suggestedPlaces;
            } throw new PlacesRepositoryException(PlacesRepositoryError.RESPONSE_NULL);
        } catch (IOException ioException) {
            log.info(TAG + ": " + ioException.getMessage());
            throw new PlacesRepositoryException(PlacesRepositoryError.CONNECTION_WITH_SERVER_ERROR);
        } catch (RuntimeException runtimeException) {
            log.info(TAG + ": " + runtimeException.getMessage());
            throw new PlacesRepositoryException(PlacesRepositoryError.DECODING_RESPONSE_ERROR);
        } catch (PlacesDataSourceWSException placesDataSourceWSException) {
            PlacesDataSourceWSError error = placesDataSourceWSException.getError();
            log.info(TAG + ": " + error.getMessage());
            switch (error) {
                case REDIRECTIONS:
                    throw new PlacesRepositoryException(PlacesRepositoryError.CONNECTION_WITH_SERVER_ERROR);
                case CLIENT_ERROR:
                    throw new PlacesRepositoryException(PlacesRepositoryError.DO_REQUEST_ERROR);
                default:
                    throw new PlacesRepositoryException(PlacesRepositoryError.SERVER_ERROR);
            }
        }
    }

    private String getCategories(List<String> categories) {
        if(!categories.isEmpty()) {
            StringBuilder cat = new StringBuilder();
            for(int i = 0; i < categories.size(); i++) {
                if(i < categories.size() - 1)
                    cat.append(categories.get(i)).append(",");
                else cat.append(categories.get(i));
            }
            return cat.toString();
        }
        return "";
    }
}
