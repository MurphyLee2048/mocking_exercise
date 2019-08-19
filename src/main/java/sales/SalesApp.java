package sales;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class SalesApp {

    public void generateSalesActivityReport(String salesId, boolean isNatTrade) {
        List<String> headers = generateHeaders(isNatTrade);
        if (salesId == null) return;
        Sales sales = getSalesDao().getSalesBySalesId(salesId);
        if (!isValidDate(sales)) return;
        List<SalesReportData> reportDataList = getSalesReportDao().getReportData(sales);
        SalesActivityReport report = this.generateReport(headers, reportDataList);
        ecmServiceUploadDocument(report);
    }

    protected List<SalesReportData> getTempList(int maxRow, List<SalesReportData> reportDataList) {
        List<SalesReportData> tempList = new ArrayList<SalesReportData>();
        for (int i = 0; i < reportDataList.size() || i < maxRow; i++) {
            tempList.add(reportDataList.get(i));
        }
        return tempList;
    }

    protected List<SalesReportData> getFilteredReportDataList(List<SalesReportData> reportDataList, boolean isSupervisor) {
        List<SalesReportData> filteredReportDataList = new ArrayList<SalesReportData>();
        for (SalesReportData data : reportDataList) {
            if ("SalesActivity".equalsIgnoreCase(data.getType())) {
                if (data.isConfidential()) {
                    if (isSupervisor) {
                        filteredReportDataList.add(data);
                    }
                } else {
                    filteredReportDataList.add(data);
                }
            }
        }
        return filteredReportDataList;
    }

    protected SalesReportDao getSalesReportDao() {
        return new SalesReportDao();
    }

    protected SalesDao getSalesDao() {
        return new SalesDao();
    }

    protected void ecmServiceUploadDocument(SalesActivityReport report) {
        getEcmService().uploadDocument(report.toXml());
    }

    protected EcmService getEcmService() {
        return new EcmService();
    }

    protected List<String> generateHeaders(boolean isNatTrade) {
        List<String> headers;
        if (isNatTrade) {
            headers = Arrays.asList("Sales ID", "Sales Name", "Activity", "Time");
        } else {
            headers = Arrays.asList("Sales ID", "Sales Name", "Activity", "Local Time");
        }
        return headers;
    }

    protected boolean isValidDate(Sales sales) {
        Date today = new Date();
        return !today.after(sales.getEffectiveTo())
                && !today.before(sales.getEffectiveFrom());
    }

    protected SalesActivityReport generateReport(List<String> headers, List<SalesReportData> reportDataList) {
        // TODO Auto-generated method stub
        return null;
    }
}
