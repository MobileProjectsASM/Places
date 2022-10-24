package com.applications.asm.data.repository;

import android.location.Location;

import com.applications.asm.data.framework.local.hardware.GpsClient;
import com.applications.asm.data.framework.local.shared_preferences.LocalPersistenceClient;
import com.applications.asm.data.framework.local.shared_preferences.model.CoordinatesSP;
import com.applications.asm.data.mapper.CoordinatesMapper;
import com.applications.asm.domain.entities.Coordinates;
import com.applications.asm.domain.entities.Response;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.observers.TestObserver;
import io.reactivex.rxjava3.schedulers.TestScheduler;
import io.reactivex.rxjava3.subscribers.TestSubscriber;

public class AllCoordinatesImplTest {
    @InjectMocks
    private AllCoordinatesImpl allCoordinates;

    @Mock
    private LocalPersistenceClient localPersistenceClient = null;

    @Mock
    private GpsClient gpsClient = null;

    @Mock
    private Location location = null;

    @Mock
    private CoordinatesMapper coordinatesMapper = null;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void myLocationSavedTest() {
        //Arrange
        CoordinatesSP coordinatesSaved = new CoordinatesSP(90, 160);
        Coordinates coordinates = new Coordinates((double) 90, (double) 160);
        Mockito.when(localPersistenceClient.getCoordinates()).thenReturn(Single.just(coordinatesSaved));
        Mockito.when(gpsClient.getCurrentLocation()).thenReturn(Single.just(location));
        Mockito.when(coordinatesMapper.coordinatesSpToCoordinates(coordinatesSaved)).thenReturn(coordinates);

        Coordinates.State savedState = Coordinates.State.SAVED;
        TestScheduler testScheduler = new TestScheduler();
        TestObserver<Response<Coordinates>> testObserver = new TestObserver<>();

        //Act
        allCoordinates
                .myLocation(savedState)
                .subscribeOn(testScheduler)
                .subscribe(testObserver);

        //Assert
        //testObserver.assertValue(coordinatesResponse -> coordinatesResponse.getData() != null);
    }

    @Test
    public void myCurrentLocationTest() {
        //Arrange
        Coordinates.State currentState = Coordinates.State.CURRENT;

        //Act
        allCoordinates.myLocation(currentState);

        //Assert
        Mockito.verify(gpsClient).getCurrentLocation();
    }
}