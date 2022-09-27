package com.applications.asm.places.view_model;

import android.util.ArrayMap;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.ViewModel;

import com.applications.asm.domain.entities.Criterion;
import com.applications.asm.domain.entities.Response;
import com.applications.asm.domain.use_cases.GetCriteriaUc;
import com.applications.asm.places.model.CriterionVM;
import com.applications.asm.places.model.Resource;
import com.applications.asm.places.model.ResourceStatus;
import com.applications.asm.places.model.mappers.CriterionMapper;
import com.applications.asm.places.view_model.exception.ErrorUtils;

import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public class AdvancedSearchViewModel extends ViewModel {
    private final GetCriteriaUc getCriteriaUc;
    private final CriterionMapper criterionMapper;
    public static final String SORT_CRITERIA_LIST = "sort_criteria_list";
    public static final String PRICES_LIST = "prices_criteria_list";

    public AdvancedSearchViewModel(GetCriteriaUc getCriteriaUc, CriterionMapper criterionMapper) {
        this.getCriteriaUc = getCriteriaUc;
        this.criterionMapper = criterionMapper;
    }

    public LiveData<Resource<Map<String, Object>>> loadData() {
        Single<Resource<List<CriterionVM>>> sortCriteriaSingle = getCriteriaUc.execute(criterionMapper.getType(CriterionVM.Type.SORT))
            .map(this::getCriteria)
            .onErrorReturn(throwable -> ErrorUtils.resolveError(throwable, AdvancedSearchViewModel.class));
        Single<Resource<List<CriterionVM>>> pricesCriteriaSingle = getCriteriaUc.execute(criterionMapper.getType(CriterionVM.Type.PRICE))
            .map(this::getCriteria)
            .onErrorReturn(throwable -> ErrorUtils.resolveError(throwable, AdvancedSearchViewModel.class));
        Flowable<Resource<Map<String, Object>>> dataFlowable = Single.zip(sortCriteriaSingle, pricesCriteriaSingle, ((sortCriteria, pricesCriteria) -> {
            Resource<Map<String, Object>> criteriaResource;
            if(sortCriteria.getStatus() == ResourceStatus.SUCCESS && pricesCriteria.getStatus() == ResourceStatus.SUCCESS) {
                Map<String, Object> criteria = new ArrayMap<>();
                criteria.put(SORT_CRITERIA_LIST, sortCriteria);
                criteria.put(PRICES_LIST, pricesCriteria);
                criteriaResource = Resource.success(criteria);
            } else {
                if(sortCriteria.getStatus() == ResourceStatus.SUCCESS) {
                    if(pricesCriteria.getStatus() == ResourceStatus.WARNING) criteriaResource = Resource.warning(pricesCriteria.getWarning());
                    else criteriaResource = Resource.error(pricesCriteria.getErrorMessage());
                } else {
                    if(sortCriteria.getStatus() == ResourceStatus.WARNING) criteriaResource = Resource.warning(sortCriteria.getWarning());
                    else criteriaResource = Resource.error(sortCriteria.getErrorMessage());
                }
            }
            return criteriaResource;
        }))
        .toFlowable();
        return LiveDataReactiveStreams.fromPublisher(dataFlowable);
    }

    private Resource<List<CriterionVM>> getCriteria(Response<List<Criterion>> response) {
        Resource<List<CriterionVM>> resource;
        if(response.getError() == null || response.getError().isEmpty())
            resource = Resource.success(criterionMapper.getCriteriaVM(response.getData()));
        else
            resource = Resource.warning(response.getError());
        return resource;
    }
}
