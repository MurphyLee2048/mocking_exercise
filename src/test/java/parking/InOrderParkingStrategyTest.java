package parking;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class InOrderParkingStrategyTest {

	@Test
    public void testCreateReceipt_givenACarAndAParkingLog_thenGiveAReceiptWithCarNameAndParkingLotName() {

	    Car leoCar = new Car("LeoCar");
	    ParkingLot parkingLot = new ParkingLot("Parking Lot 1", 1);

	    Car mLeoCar = mock(Car.class);
	    when(mLeoCar.getName()).thenReturn("LeoCar2");

	    ParkingLot mParkingLot = mock(ParkingLot.class);
	    when(mParkingLot.getName()).thenReturn("Parking Lot 2");

	    InOrderParkingStrategy inOrderParkingStrategy = new InOrderParkingStrategy();
	    Receipt receipt = inOrderParkingStrategy.createReceipt(mParkingLot, mLeoCar);

	    verify(mLeoCar, times(1)).getName();  // 放在调用方法后


	    assertEquals("LeoCar2", receipt.getCarName());
        assertEquals("Parking Lot 2", receipt.getParkingLotName());


	    /* Exercise 1, Write a test case on InOrderParkingStrategy.createReceipt()
	    * With using Mockito to mock the input parameter */

    }

    @Test
    public void testCreateNoSpaceReceipt_givenACar_thenGiveANoSpaceReceipt() {
        Car mLeoCar = mock(Car.class);
        when(mLeoCar.getName()).thenReturn("mLeoCar");

        InOrderParkingStrategy inOrderParkingStrategy = new InOrderParkingStrategy();
        Receipt receipt = inOrderParkingStrategy.createNoSpaceReceipt(mLeoCar);

        verify(mLeoCar, times(1)).getName();

        assertEquals("mLeoCar", receipt.getCarName());



        /* Exercise 1, Write a test case on InOrderParkingStrategy.createNoSpaceReceipt()
         * With using Mockito to mock the input parameter */

    }

    @Test
    public void testPark_givenNoAvailableParkingLot_thenCreateNoSpaceReceipt(){
        Car leoCar = new Car("LeoCar");

        InOrderParkingStrategy spyInOrderParkingStrategy = spy(new InOrderParkingStrategy());

//        Receipt receipt = spyInOrderParkingStrategy.park(null, leoCar);

        verify(spyInOrderParkingStrategy).createNoSpaceReceipt(leoCar);

        /* Exercise 2: Test park() method. Use Mockito.spy and Mockito.verify to test the situation for no available parking lot */

    }

    @Test
    public void testPark_givenThereIsOneParkingLotWithSpace_thenCreateReceipt(){
	    Car leoCar = new Car("LeoCar");
	    ParkingLot parkingLot = new ParkingLot("parking lot 1", 1);
        List<ParkingLot> parkingLots = Arrays.asList(parkingLot);
        InOrderParkingStrategy spyInOrderParkingStrategy = spy(new InOrderParkingStrategy());

        Receipt receipt = spyInOrderParkingStrategy.park(parkingLots, leoCar);

        verify(spyInOrderParkingStrategy).createReceipt(parkingLot, leoCar);  // 默认1次
        /* Exercise 2: Test park() method. Use Mockito.spy and Mockito.verify to test the situation for one available parking lot */

    }

    @Test
    public void testPark_givenThereIsOneFullParkingLot_thenCreateReceipt(){
        Car leoCar = new Car("LeoCar");
        ParkingLot parkingLot = new ParkingLot("parking lot 1", 1);
        parkingLot.getParkedCars().add(new Car("otherCar"));
        List<ParkingLot> parkingLots = Arrays.asList(parkingLot);
        InOrderParkingStrategy spyInOrderParkingStrategy = spy(new InOrderParkingStrategy());

        spyInOrderParkingStrategy.park(parkingLots, leoCar);

        verify(spyInOrderParkingStrategy, times(0)).createReceipt(parkingLot, leoCar);
        verify(spyInOrderParkingStrategy).createNoSpaceReceipt(leoCar);


        /* Exercise 2: Test park() method. Use Mockito.spy and Mockito.verify to test the situation for one available parking lot but it is full */

    }

    @Test
    public void testPark_givenThereIsMultipleParkingLotAndFirstOneIsFull_thenCreateReceiptWithUnfullParkingLot(){
        Car leoCar = new Car("LeoCar");
	    ParkingLot parkingLotA = new ParkingLot("parking lot 1", 1);
        parkingLotA.getParkedCars().add(new Car("otherCar"));
        ParkingLot parkingLotB = new ParkingLot("parking lot 2", 1);
        List<ParkingLot> parkingLots = Arrays.asList(parkingLotA, parkingLotB);

        InOrderParkingStrategy spyInOrderParkingStrategy = spy(new InOrderParkingStrategy());
        spyInOrderParkingStrategy.park(parkingLots, leoCar);

        verify(spyInOrderParkingStrategy, times(0)).createReceipt(parkingLotA, leoCar);
        verify(spyInOrderParkingStrategy).createReceipt(parkingLotB, leoCar);

        /* Exercise 3: Test park() method. Use Mockito.spy and Mockito.verify to test the situation for multiple parking lot situation */

    }


}
