package parking;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class VipParkingStrategyTest {

	@Test
    public void testPark_givenAVipCarAndAFullParkingLog_thenGiveAReceiptWithCarNameAndParkingLotName() {
        Car vipCar = new Car("ACar");
        ParkingLot parkingLot = new ParkingLot("parking lot", 1);
        parkingLot.getParkedCars().add(new Car("not important car"));
        List<ParkingLot> parkingLots = Arrays.asList(parkingLot);

        VipParkingStrategy spyVipParkingStrategy = spy(new VipParkingStrategy());

        doReturn(true).when(spyVipParkingStrategy).isAllowOverPark(vipCar);

        Receipt receipt = spyVipParkingStrategy.park(parkingLots, vipCar);

        assertEquals("ACar", receipt.getCarName());
        assertEquals("parking lot", receipt.getParkingLotName());

        /* Exercise 4, Write a test case on VipParkingStrategy.park()
	    * With using Mockito spy, verify and doReturn */

    }

    @Test
    public void testPark_givenCarIsNotVipAndAFullParkingLog_thenGiveNoSpaceReceipt() {
        Car car = new Car("Car");
        ParkingLot parkingLot = new ParkingLot("parking lot", 1);
        parkingLot.getParkedCars().add(new Car("not important car"));
        List<ParkingLot> parkingLots = Arrays.asList(parkingLot);

        VipParkingStrategy spyVipParkingStrategy = spy(new VipParkingStrategy());

        doReturn(false).when(spyVipParkingStrategy).isAllowOverPark(car);

        Receipt receipt = spyVipParkingStrategy.park(parkingLots, car);

        verify(spyVipParkingStrategy, times(0)).createReceipt(parkingLot, car);

        assertEquals("Car", receipt.getCarName());
        assertEquals("No Parking Lot", receipt.getParkingLotName());

        /* Exercise 4, Write a test case on VipParkingStrategy.park()
         * With using Mockito spy, verify and doReturn */
    }

    @Test
    public void testIsAllowOverPark_givenCarNameContainsCharacterAAndIsVipCar_thenReturnTrue(){
        Car leoCar = new Car("LeoCarA");
        VipParkingStrategy spyVipParkingStrategy = spy(new VipParkingStrategy());

        CarDao mockCarDao = mock(CarDao.class);
        when(mockCarDao.isVip("LeoCarA")).thenReturn(true);
        doReturn(mockCarDao).when(spyVipParkingStrategy).getCarDao();

        boolean allowOverPark = spyVipParkingStrategy.isAllowOverPark(leoCar);

        assertTrue(allowOverPark);


        /* Exercise 5, Write a test case on VipParkingStrategy.isAllowOverPark()
         * You may refactor the code, or try to use
         * use @RunWith(MockitoJUnitRunner.class), @Mock (use Mockito, not JMockit) and @InjectMocks
         */
    }

    @Test
    public void testIsAllowOverPark_givenCarNameDoesNotContainsCharacterAAndIsVipCar_thenReturnFalse(){
        Car leoCar = new Car("LeoCar");
        VipParkingStrategy spyVipParkingStrategy = spy(new VipParkingStrategy());

        CarDao mockCarDao = mock(CarDao.class);
        when(mockCarDao.isVip("LeoCar")).thenReturn(true);
        doReturn(mockCarDao).when(spyVipParkingStrategy).getCarDao();

        boolean allowOverPark = spyVipParkingStrategy.isAllowOverPark(leoCar);

        assertFalse(allowOverPark);
        /* Exercise 5, Write a test case on VipParkingStrategy.isAllowOverPark()
         * You may refactor the code, or try to use
         * use @RunWith(MockitoJUnitRunner.class), @Mock (use Mockito, not JMockit) and @InjectMocks
         */
    }

    @Test
    public void testIsAllowOverPark_givenCarNameContainsCharacterAAndIsNotVipCar_thenReturnFalse(){
        Car leoCar = new Car("LeoCarA");
        VipParkingStrategy spyVipParkingStrategy = spy(new VipParkingStrategy());

        CarDao mockCarDao = mock(CarDao.class);
        when(mockCarDao.isVip("LeoCarA")).thenReturn(false);
        doReturn(mockCarDao).when(spyVipParkingStrategy).getCarDao();

        boolean allowOverPark = spyVipParkingStrategy.isAllowOverPark(leoCar);

        assertFalse(allowOverPark);
        /* Exercise 5, Write a test case on VipParkingStrategy.isAllowOverPark()
         * You may refactor the code, or try to use
         * use @RunWith(MockitoJUnitRunner.class), @Mock (use Mockito, not JMockit) and @InjectMocks
         */
    }

    @Test
    public void testIsAllowOverPark_givenCarNameDoesNotContainsCharacterAAndIsNotVipCar_thenReturnFalse() {
        Car leoCar = new Car("LeoCar");
        VipParkingStrategy spyVipParkingStrategy = spy(new VipParkingStrategy());

        CarDao mockCarDao = mock(CarDao.class);
        when(mockCarDao.isVip("LeoCar")).thenReturn(false);
        doReturn(mockCarDao).when(spyVipParkingStrategy).getCarDao();

        boolean allowOverPark = spyVipParkingStrategy.isAllowOverPark(leoCar);

        assertFalse(allowOverPark);
        /* Exercise 5, Write a test case on VipParkingStrategy.isAllowOverPark()
         * You may refactor the code, or try to use
         * use @RunWith(MockitoJUnitRunner.class), @Mock (use Mockito, not JMockit) and @InjectMocks
         */
    }

    private Car createMockCar(String carName) {
        Car car = mock(Car.class);
        when(car.getName()).thenReturn(carName);
        return car;
    }
}
