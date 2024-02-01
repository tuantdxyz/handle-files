package com.bezkoder.spring.files.upload.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.CompletableFuture;

@Service
public class ExcelProcessingServiceImpl {

    @Async
    public CompletableFuture<Integer> processExcelData(MultipartFile excelFile) {

        //
        return CompletableFuture.completedFuture(check(excelFile));
    }

    private Integer check(MultipartFile excelFile) {
        System.out.println("1");
        return 1;

    }
}
