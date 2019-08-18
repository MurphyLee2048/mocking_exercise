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
		doReturn("SalesActivity").when(spySalesReportData).getType();
		doReturn(true).when(spySalesReportData).isConfidential();
		doReturn(spySalesActivityReport).when(spySalesApp).generateReport(anyListOf(String.class), anyListOf(SalesReportData.class));
		doReturn("").when(spySalesActivityReport).toXml();

		Calendar yesterday = Calendar.getInstance();
		yesterday.set(Calendar.DATE, yesterday.get(Calendar.DATE) - 1);
		when(spySales.getEffectiveFrom()).thenReturn(yesterday.getTime());

		Calendar tomorrow = Calendar.getInstance();
		tomorrow.set(Calendar.DATE, tomorrow.get(Calendar.DATE) + 1);
		when(spySales.getEffectiveTo()).thenReturn(tomorrow.getTime());

		spySalesApp.generateSalesActivityReport("DUMMY", 1000, false, false);

		// then
        verify(spyEcmService).uploadDocument(anyString());



//		SalesApp salesApp = new SalesApp();
//		salesApp.generateSalesActivityReport("DUMMY", 1000, false, false);

	}
}
