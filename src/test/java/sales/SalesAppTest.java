package sales;

import org.junit.Test;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import static org.mockito.Mockito.*;

public class SalesAppTest {

	@Test
	public void testGenerateSalesActivityReport() {
		// given
		EcmService spyEcmService = spy(new EcmService());
		Sales spySales = spy(new Sales());
		SalesActivityReport spySalesActivityReport = spy(new SalesActivityReport());
		SalesApp spySalesApp = spy(new SalesApp());
		SalesDao spySalesDao = spy(new SalesDao());
		SalesReportDao spySalesReportDao = spy(new SalesReportDao());
		SalesReportData spySalesReportData = spy(new SalesReportData());
		List<SalesReportData> salesReportDataList = Arrays.asList(spySalesReportData);  // Collections.singletonList(salesReportData);

		// when
		doReturn(spySales).when(spySalesDao).getSalesBySalesId(anyString());
		doReturn(salesReportDataList).when(spySalesReportDao).getReportData(any(Sales.class));
		doReturn(spySalesDao).when(spySalesApp).getSalesDao();
        doReturn(spySalesReportDao).when(spySalesApp).getSalesReportDao();
		doReturn(true).when(spySalesApp).isValidDate(any(Sales.class));
		doReturn(spySalesActivityReport).when(spySalesApp).generateReport(anyListOf(String.class), anyListOf(SalesReportData.class));
		doReturn(spyEcmService).when(spySalesApp).getEcmService();
		doReturn(Arrays.asList("Sales ID", "Sales Name", "Activity", "Local Time")).when(spySalesApp).generateHeaders(anyBoolean());

		spySalesApp.generateSalesActivityReport("DUMMY", 1000, false, false);

		// then
        verify(spySalesApp).ecmServiceUploadDocument(spySalesActivityReport);



//		SalesApp salesApp = new SalesApp();
//		salesApp.generateSalesActivityReport("DUMMY", 1000, false, false);

	}
}
