package com.bezkoder.spring.files.upload.controller;

import com.bezkoder.spring.files.upload.service.ExcelProcessingServiceImpl;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RestController
@CrossOrigin("http://localhost:8081")
public class ExcelController {
    //TODO import, export
    @Autowired
    private ExcelProcessingServiceImpl excelProcessingService;

    @PostMapping("/import-excel")
    public ResponseEntity<String> uploadExcel(@RequestParam("file") MultipartFile excelFile) {
        excelProcessingService.processExcelData(excelFile);
        return ResponseEntity.ok("File is being processed in the background.");
    }

    @GetMapping("/export-excel")
    public ResponseEntity<Resource> exportToExcel() throws IOException {
        System.out.println("TT");
        Resource resource = this.handleExportToExcel();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=exported_data.xlsx")
//            .contentType(MediaType.APPLICATION_OCTET_STREAM)  // khong can phai truyen
                .body(resource);
    }

    public Resource handleExportToExcel() throws IOException {
        // Create a workbook and a sheet
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Sheet1");

        // Create some data for testing
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID");
        headerRow.createCell(1).setCellValue("Name");
        Row dataRow = sheet.createRow(1);
        dataRow.createCell(0).setCellValue(1);  // gia tri truyen vao
        dataRow.createCell(1).setCellValue("John Doe"); // gia tri truyen vao

        // Write the workbook content to a ByteArrayOutputStream
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();

        // Convert the ByteArrayOutputStream to a byte array
        byte[] excelBytes = outputStream.toByteArray();

        // Wrap the byte array in a ByteArrayResource
        ByteArrayResource resource = new ByteArrayResource(excelBytes);

        return resource;
    }

}
