package sales;

import org.junit.Test;

import java.util.Arrays;

import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
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

    @Test
    public void should_return_true_when_sales_date_isValid() {
        // given
        SalesApp salesApp = new SalesApp();
        Sales sales = spy(new Sales());

        // when
        Calendar fromDay = Calendar.getInstance();
        fromDay.set(Calendar.DATE, fromDay.get(Calendar.DATE) - 100);  // int field, int value
        doReturn(fromDay.getTime()).when(sales).getEffectiveFrom();
        Calendar toDay = Calendar.getInstance();
        toDay.set(Calendar.DATE, toDay.get(Calendar.DATE) + 100);
        doReturn(toDay.getTime()).when(sales).getEffectiveTo();

        boolean res = salesApp.isValidDate(sales);

        // then
        assertTrue(res);
    }

    @Test
    public void should_return_sales_id_sales_name_activity_time_when_generate_headers_given_is_nat_trade() {
        // given
        SalesApp salesApp = new SalesApp();

        // when
        List<String> res = salesApp.generateHeaders(true);

        // then
        assertEquals(Arrays.asList("Sales ID", "Sales Name", "Activity", "Time"), res);
    }
}
