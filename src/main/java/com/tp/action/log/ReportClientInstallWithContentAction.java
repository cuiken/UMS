package com.tp.action.log;

import com.google.common.collect.Lists;
import com.opensymphony.xwork2.ActionSupport;
import com.tp.entity.log.LogCountClientInstallWithContent;
import com.tp.orm.Page;
import com.tp.orm.PageRequest.Sort;
import com.tp.orm.PropertyFilter;
import com.tp.service.LogCCInstallWithContentService;
import com.tp.utils.ExcelUtils;
import com.tp.utils.ServletUtils;
import com.tp.utils.Struts2Utils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Namespace("/report")
public class ReportClientInstallWithContentAction extends ActionSupport {

    private static final long serialVersionUID = 1L;

    private LogCCInstallWithContentService logCCInstallWithContentService;
    private Page<LogCountClientInstallWithContent> page = new Page<LogCountClientInstallWithContent>();
    private List<Integer> sliders = Lists.newArrayList();

    private int rowIndex = 0;
    private Map<String, CellStyle> styles;

    @RequiresPermissions("report_install_by_content:view")
    public String execute() throws Exception {
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(Struts2Utils.getRequest());
        if (!page.isOrderBySetted()) {
            page.setOrderBy("createTime");
            page.setOrderDir(Sort.DESC);
        }
        page = logCCInstallWithContentService.searchCountClientInstallWithContent(page, filters);
        sliders = page.getSlider(10);
        return SUCCESS;
    }

    public String export() throws Exception {
        String date = Struts2Utils.getParameter("date");
        List<String> markets = logCCInstallWithContentService.getMarketFromLog(date);
        Workbook wb = exportExcelWorkbook(markets, date);
        HttpServletResponse response = Struts2Utils.getResponse();
        response.setContentType(ServletUtils.EXCEL_TYPE);
        ServletUtils.setFileDownloadHeader(response, date + "客户端通过内容安装统计.xls");

        wb.write(response.getOutputStream());
        response.getOutputStream().flush();
        return null;
    }

    private Workbook exportExcelWorkbook(List<String> markets, String date) {
        List<LogCountClientInstallWithContent> logs = logCCInstallWithContentService.getLogs(date);

        Workbook wb = new HSSFWorkbook();
        styles = ExcelUtils.createStyles(wb);
        for (String name : markets) {
            rowIndex = 0;
            if (name == null)
                continue;
            List<LogCountClientInstallWithContent> existLogs = Lists.newArrayList();
            for (LogCountClientInstallWithContent log : logs) {
                if (log.getMarketName() != null && log.getMarketName().equals(name) && !(existLogs.contains(log))) {
                    existLogs.add(log);
                }
            }
            Sheet s = wb.createSheet(name);
            s.createFreezePane(0, 2, 0, 2);
            for (int i = 0; i < 3; i++) {
                s.autoSizeColumn(i);
            }
            generateTitle(s, date);
            generateHeader(s);
            generateContent(s, existLogs);
            generateTotals(s, existLogs.size());
        }
        return wb;
    }

    private void generateTitle(Sheet s, String date) {
        Row r = s.createRow(rowIndex++);
        Cell cl = r.createCell(0);
        cl.setCellValue(date + "客户端通过内容安装统计");
        cl.setCellStyle(styles.get("header"));
        s.addMergedRegion(CellRangeAddress.valueOf("$A$1:$C$1"));
    }

    private void generateHeader(Sheet s) {
        Row r = s.createRow(rowIndex++);
        CellStyle headerStyle = styles.get("header");
        String[] headers = {"序号", "解锁名称", "安装量"};
        for (int i = 0; i < headers.length; i++) {
            Cell cl = r.createCell(i);
            cl.setCellValue(headers[i]);
            cl.setCellStyle(headerStyle);
        }
    }

    private void generateContent(Sheet s, List<LogCountClientInstallWithContent> logs) {
        int count = 0;
        for (LogCountClientInstallWithContent entity : logs) {
            count++;
            Row r = s.createRow(rowIndex++);
            Cell cellNo = r.createCell(0);
            cellNo.setCellValue(count);

            Cell cellLockName = r.createCell(1);
            cellLockName.setCellValue(entity.getAppName());

            Cell cellDownload = r.createCell(2);
            cellDownload.setCellValue(entity.getInstalled());

        }
    }

    private void generateTotals(Sheet s, int count) {

        Row r = s.createRow(rowIndex++);
        CellStyle totalStyle = styles.get("total");

        Cell c1 = r.createCell(0);
        c1.setCellStyle(totalStyle);
        c1.setCellValue("合计");

        //合计公式
        Cell c3 = r.createCell(2);
        c3.setCellStyle(totalStyle);
        c3.setCellFormula("SUM(C3:C" + (count + 2) + ")");
    }

    public Page<LogCountClientInstallWithContent> getPage() {
        return page;
    }

    public List<Integer> getSliders() {
        return sliders;
    }

    @Autowired
    public void setLogCCInstallWithContentService(LogCCInstallWithContentService logCCInstallWithContentService) {
        this.logCCInstallWithContentService = logCCInstallWithContentService;
    }
}
