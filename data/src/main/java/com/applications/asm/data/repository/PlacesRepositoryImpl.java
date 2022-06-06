package com.applications.asm.data.repository;

import com.applications.asm.data.exception.PlacesDataSourceWSError;
import com.applications.asm.data.exception.PlacesDataSourceWSException;
import com.applications.asm.data.model.CategoryModel;
import com.applications.asm.data.model.PlaceModel;
import com.applications.asm.data.model.ReviewModel;
import com.applications.asm.data.model.SuggestedPlaceModel;
import com.applications.asm.data.model.mapper.CategoryModelMapper;
import com.applications.asm.data.model.mapper.PlaceDetailsModelMapper;
import com.applications.asm.data.model.mapper.PlaceModelMapper;
import com.applications.asm.data.model.mapper.ReviewModelMapper;
import com.applications.asm.data.model.mapper.SuggestedPlaceModelMapper;
import com.applications.asm.data.sources.PlacesDataSourceWS;
import com.applications.asm.domain.entities.Category;
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

import io.reactivex.rxjava3.core.Single;

public class PlacesRepositoryImpl implements PlacesRepository {
    private final PlacesDataSourceWS placeDataSourceWs;
    private final PlaceModelMapper placeModelMapper;
    private final PlaceDetailsModelMapper placeDetailsModelMapper;
    private final ReviewModelMapper reviewModelMapper;
    private final SuggestedPlaceModelMapper suggestedPlaceModelMapper;
    private final CategoryModelMapper categoryModelMapper;
    private final static Integer DEFAULT_AMOUNT = 10;
    private Integer totalPages;
    private final Logger log = Logger.getLogger("com.applications.asm.data.repository.PlacesRepositoryImpl");

    public PlacesRepositoryImpl(
        PlacesDataSourceWS placesDataSourceWs,
        PlaceModelMapper placeModelMapper,
        PlaceDetailsModelMapper placeDetailsModelMapper,
        ReviewModelMapper reviewModelMapper,
        SuggestedPlaceModelMapper suggestedPlaceModelMapper,
        CategoryModelMapper categoryModelMapper
    ) {
        this.placeDataSourceWs = placesDataSourceWs;
        this.placeModelMapper = placeModelMapper;
        this.placeDetailsModelMapper = placeDetailsModelMapper;
        this.reviewModelMapper = reviewModelMapper;
        this.suggestedPlaceModelMapper = suggestedPlaceModelMapper;
        this.categoryModelMapper = categoryModelMapper;
    }

    @Override
    public Single<PlaceDetails> getPlaceDetails(String placeId) {
        return Single.fromCallable(() -> {
            if(placeId == null) throw new PlacesRepositoryException(PlacesRepositoryError.ANY_VALUE_IS_NULL);
            return placeId;
        }).flatMap(
            placeDataSourceWs::getPlaceDetailsModel
        ).map(placeDetailsModel -> {
            if(placeDetailsModel != null)
                return placeDetailsModelMapper.getPlaceDetailsFromPlaceDetailsModel(placeDetailsModel);
            throw new PlacesRepositoryException(PlacesRepositoryError.RESPONSE_NULL);
        })
        .doOnError(throwable -> {
            Exception exception = (Exception) throwable;
            if(exception instanceof IOException) {
                log.info(getClass().getName() + ": " + exception.getMessage());
                throw new PlacesRepositoryException(PlacesRepositoryError.CONNECTION_WITH_SERVER_ERROR);
            } else if(exception instanceof RuntimeException) {
                log.info(getClass().getName() + ": " + exception.getMessage());
                throw new PlacesRepositoryException(PlacesRepositoryError.DECODING_RESPONSE_ERROR);
            } else if(exception instanceof PlacesDataSourceWSException) {
                PlacesDataSourceWSError error = ((PlacesDataSourceWSException) exception).getError();
                log.info(getClass().getName() + ": " + error.getMessage());
                switch (error) {
                    case REDIRECTIONS:
                        throw new PlacesRepositoryException(PlacesRepositoryError.CONNECTION_WITH_SERVER_ERROR);
                    case CLIENT_ERROR:
                        throw new PlacesRepositoryException(PlacesRepositoryError.DO_REQUEST_ERROR);
                    default:
                        throw new PlacesRepositoryException(PlacesRepositoryError.SERVER_ERROR);
                }
            } else throw exception;
        });
    }

    @Override
    public Single<List<Place>> getPlaces(String placeToFind, Double longitude, Double latitude, Integer radius, List<String> categories, Integer page) {
        return Single.fromCallable(() -> {
            if(placeToFind == null || longitude == null || latitude == null || radius == null || categories == null || page == null)
                throw new PlacesRepositoryException(PlacesRepositoryError.ANY_VALUE_IS_NULL);
            return true;
        }).flatMap(params -> {
            if(page == 0) {
                return placeDataSourceWs.getPlacesModel(placeToFind, longitude, latitude, radius, getCategories(categories), 0, DEFAULT_AMOUNT)
                    .flatMap(responsePlacesModel -> Single.fromCallable(() -> {
                        if(responsePlacesModel != null) {
                            int total = responsePlacesModel.getTotal();
                            totalPages = total % DEFAULT_AMOUNT == 0 ? total / 10 : total / 10 + 1;
                            return responsePlacesModel;
                        }
                        throw new PlacesRepositoryException(PlacesRepositoryError.RESPONSE_NULL);
                    }));
            } else if(page > 0 && page < totalPages)
                return placeDataSourceWs.getPlacesModel(placeToFind, longitude, latitude, radius, getCategories(categories), (page - 1) * DEFAULT_AMOUNT , DEFAULT_AMOUNT);
            throw new PlacesRepositoryException(PlacesRepositoryError.PAGE_OUT_OF_RANGE);
        }).map(responsePlacesModel -> {
            if(responsePlacesModel != null) {
                List<Place> places = new ArrayList<>();
                for (PlaceModel placeModel : responsePlacesModel.getPlaces())
                    places.add(placeModelMapper.getPlaceFromPlaceModel(placeModel));
                return places;
            } throw new PlacesRepositoryException(PlacesRepositoryError.RESPONSE_NULL);
        }).doOnError(throwable -> {
            Exception exception = (Exception) throwable;
            if(exception instanceof IOException) {
                log.info(getClass().getName() + ": " + exception.getMessage());
                throw new PlacesRepositoryException(PlacesRepositoryError.CONNECTION_WITH_SERVER_ERROR);
            } else if(exception instanceof RuntimeException) {
                log.info(getClass().getName() + ": " + exception.getMessage());
                throw new PlacesRepositoryException(PlacesRepositoryError.DECODING_RESPONSE_ERROR);
            } else if(exception instanceof PlacesDataSourceWSException) {
                PlacesDataSourceWSError error = ((PlacesDataSourceWSException) exception).getError();
                log.info(getClass().getName() + ": " + error.getMessage());
                switch (error) {
                    case REDIRECTIONS:
                        throw new PlacesRepositoryException(PlacesRepositoryError.CONNECTION_WITH_SERVER_ERROR);
                    case CLIENT_ERROR:
                        throw new PlacesRepositoryException(PlacesRepositoryError.DO_REQUEST_ERROR);
                    default:
                        throw new PlacesRepositoryException(PlacesRepositoryError.SERVER_ERROR);
                }
            } else throw exception;
        });
    }

    @Override
    public Single<List<Review>> getReviews(String placeId) {
        return Single.fromCallable(() -> {
            if(placeId == null) throw new PlacesRepositoryException(PlacesRepositoryError.ANY_VALUE_IS_NULL);
            return placeId;
        }).flatMap(
            placeDataSourceWs::getReviewsModel
        ).map(responseReviewsModel -> {
            if(responseReviewsModel != null) {
                List<Review> reviews = new ArrayList<>();
                for (ReviewModel reviewModel : responseReviewsModel.getReviewModels())
                    reviews.add(reviewModelMapper.getReviewFromReviewModel(reviewModel));
                return reviews;
            } throw new PlacesRepositoryException(PlacesRepositoryError.RESPONSE_NULL);
        }).doOnError(throwable -> {
            Exception exception = (Exception) throwable;
            if(exception instanceof IOException) {
                log.info(getClass().getName() + ": " + exception.getMessage());
                throw new PlacesRepositoryException(PlacesRepositoryError.CONNECTION_WITH_SERVER_ERROR);
            } else if(exception instanceof RuntimeException) {
                log.info(getClass().getName() + ": " + exception.getMessage());
                throw new PlacesRepositoryException(PlacesRepositoryError.DECODING_RESPONSE_ERROR);
            } else if(exception instanceof PlacesDataSourceWSException) {
                PlacesDataSourceWSError error = ((PlacesDataSourceWSException) exception).getError();
                log.info(getClass().getName() + ": " + error.getMessage());
                switch (error) {
                    case REDIRECTIONS:
                        throw new PlacesRepositoryException(PlacesRepositoryError.CONNECTION_WITH_SERVER_ERROR);
                    case CLIENT_ERROR:
                        throw new PlacesRepositoryException(PlacesRepositoryError.DO_REQUEST_ERROR);
                    default:
                        throw new PlacesRepositoryException(PlacesRepositoryError.SERVER_ERROR);
                }
            } else throw exception;
        });

    }

    @Override
    public Single<List<SuggestedPlace>> getSuggestedPlaces(String word, Double longitude, Double latitude) {
        return Single.fromCallable(() -> {
            if(word == null || latitude == null || longitude == null)
                throw new PlacesRepositoryException(PlacesRepositoryError.ANY_VALUE_IS_NULL);
            return true;
        }).flatMap(
            value -> placeDataSourceWs.getSuggestedPlaces(word, longitude, latitude)
        ).map(responseSuggestedPlacesModel -> {
            if(responseSuggestedPlacesModel != null) {
                List<SuggestedPlace> suggestedPlaces = new ArrayList<>();
                for(SuggestedPlaceModel suggestedPlaceModel: responseSuggestedPlacesModel.getSuggestPlacesModel())
                    suggestedPlaces.add(suggestedPlaceModelMapper.getSuggestedPlaceFromSuggestedPlaceModel(suggestedPlaceModel));
                return suggestedPlaces;
            }
            throw new PlacesRepositoryException(PlacesRepositoryError.RESPONSE_NULL);
        }).doOnError(throwable -> {
            Exception exception = (Exception) throwable;
            if(exception instanceof IOException) {
                log.info(getClass().getName() + ": " + exception.getMessage());
                throw new PlacesRepositoryException(PlacesRepositoryError.CONNECTION_WITH_SERVER_ERROR);
            } else if(exception instanceof RuntimeException) {
                log.info(getClass().getName() + ": " + exception.getMessage());
                throw new PlacesRepositoryException(PlacesRepositoryError.DECODING_RESPONSE_ERROR);
            } else if(exception instanceof PlacesDataSourceWSException) {
                PlacesDataSourceWSError error = ((PlacesDataSourceWSException) exception).getError();
                log.info(getClass().getName() + ": " + error.getMessage());
                switch (error) {
                    case REDIRECTIONS:
                        throw new PlacesRepositoryException(PlacesRepositoryError.CONNECTION_WITH_SERVER_ERROR);
                    case CLIENT_ERROR:
                        throw new PlacesRepositoryException(PlacesRepositoryError.DO_REQUEST_ERROR);
                    default:
                        throw new PlacesRepositoryException(PlacesRepositoryError.SERVER_ERROR);
                }
            } else throw exception;
        });
    }

    @Override
    public Single<List<Category>> getCategories(String word, Double longitude, Double latitude, String locale) {
        return Single.fromCallable(() -> {
            if(locale == null) throw new PlacesRepositoryException(PlacesRepositoryError.RESPONSE_NULL);
            return true;
        }).flatMap(
            value -> placeDataSourceWs.getCategoriesModel(word, longitude, latitude, locale)
        ).map(responseCategoriesModel -> {
            if(responseCategoriesModel != null) {
                List<Category> categories = new ArrayList<>();
                for(CategoryModel categoryModel: responseCategoriesModel.getCategoryModelList())
                    categories.add(categoryModelMapper.getCategoryFromCategoryModel(categoryModel));
                return categories;
            } throw new PlacesRepositoryException(PlacesRepositoryError.RESPONSE_NULL);
        }).doOnError(throwable -> {
            Exception exception = (Exception) throwable;
            if(exception instanceof IOException) {
                log.info(getClass().getName() + ": " + exception.getMessage());
                throw new PlacesRepositoryException(PlacesRepositoryError.CONNECTION_WITH_SERVER_ERROR);
            } else if(exception instanceof RuntimeException) {
                log.info(getClass().getName() + ": " + exception.getMessage());
                throw new PlacesRepositoryException(PlacesRepositoryError.DECODING_RESPONSE_ERROR);
            } else if(exception instanceof PlacesDataSourceWSException) {
                PlacesDataSourceWSError error = ((PlacesDataSourceWSException) exception).getError();
                log.info(getClass().getName() + ": " + error.getMessage());
                switch (error) {
                    case REDIRECTIONS:
                        throw new PlacesRepositoryException(PlacesRepositoryError.CONNECTION_WITH_SERVER_ERROR);
                    case CLIENT_ERROR:
                        throw new PlacesRepositoryException(PlacesRepositoryError.DO_REQUEST_ERROR);
                    default:
                        throw new PlacesRepositoryException(PlacesRepositoryError.SERVER_ERROR);
                }
            } else throw exception;
        });
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
