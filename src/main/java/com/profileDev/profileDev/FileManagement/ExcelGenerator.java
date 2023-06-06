package com.profileDev.profileDev.FileManagement;

import com.profileDev.profileDev.controller.ProfileDevController;
import com.profileDev.profileDev.dto.CredentialDTO;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
@NoArgsConstructor
public class ExcelGenerator {

    Logger logger = LoggerFactory.getLogger(ProfileDevController.class);
    private List<CredentialDTO> credentialDTOList;
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;

    /*public ExcelGenerator(List < CredentialDTO > credentialDTOList) {
        this.credentialDTOList = credentialDTOList;
        workbook = new XSSFWorkbook();
        logger.info("ExcelGenerator >> ExcelGenerator()");
    }*/

    public void generator1(List < CredentialDTO > credentialDTOList) {
        this.credentialDTOList = credentialDTOList;
        workbook = new XSSFWorkbook();
        logger.info("ExcelGenerator >> ExcelGenerator()");
    }

    public void generateExcelFile(HttpServletResponse httpServletResponse) throws IOException {
        writeHeader();
        write();

        String currentDate = (new SimpleDateFormat("ssmmHH_ddMMyyyy")).format(new Date());
        String path ="D:\\programming software\\intellij\\IntelliJ_workspace\\profileDev\\src\\main\\java\\com\\profileDev\\profileDev\\FileManagement\\ExcelDump\\"+currentDate+".xlsx";
        FileOutputStream fileOutputStream = new FileOutputStream(path);
        //ServletOutputStream outputStream = httpServletResponse.getOutputStream(); //used in direct download by API
        //workbook.write(outputStream);
        workbook.write(fileOutputStream);
        workbook.close();
        //outputStream.close();
        logger.info("ExcelGenerator >> generateExcelFile()");

    }

    private void writeHeader() {
        sheet = workbook.createSheet("Student");
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
        createCell(row, 0, "Name", style);
        createCell(row, 1, "Password", style);
        createCell(row, 2, "Role", style);
        logger.info("ExcelGenerator >> writeHeader()");
    }

    private void createCell(Row row, int columnCount, Object valueOfCell, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (valueOfCell instanceof Integer) {
            cell.setCellValue((Integer) valueOfCell);
        } else if (valueOfCell instanceof Long) {
            cell.setCellValue((Long) valueOfCell);
        } else if (valueOfCell instanceof String) {
            cell.setCellValue((String) valueOfCell);
        } else {
            cell.setCellValue((Boolean) valueOfCell);
        }
        cell.setCellStyle(style);
        //logger.info("ExcelGenerator >> createCell()");
    }

    private void write() {
        int rowCount = 1;
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
        for (CredentialDTO credentialDTO: credentialDTOList) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, credentialDTO.getName(), style);
            createCell(row, columnCount++, credentialDTO.getPassword(), style);
            createCell(row, columnCount++, credentialDTO.getRole(), style);
        }
        logger.info("ExcelGenerator >> write()");
    }


}
