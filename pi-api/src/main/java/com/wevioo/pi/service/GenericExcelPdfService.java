package com.wevioo.pi.service;

import com.itextpdf.text.DocumentException;
import com.wevioo.pi.rest.dto.DownloadDocumentDto;

import java.io.IOException;
import java.util.List;

/**
 * It's a generic EXCEL , PDF service aims to download files
 */
public interface GenericExcelPdfService {

    /**
     * @param data  exce
     * @param <T>
     * @return DownloadDocumentDto file
     * @throws IOException
     */
    <T> DownloadDocumentDto saveExcelSheet(List<T> data) throws IOException;

    /**
     * @param data
     * @param <T>
     * @return
     * @throws IOException
     * @throws DocumentException
     */
    <T> DownloadDocumentDto savePdfTable(List<T> data) throws IOException, DocumentException;
}
