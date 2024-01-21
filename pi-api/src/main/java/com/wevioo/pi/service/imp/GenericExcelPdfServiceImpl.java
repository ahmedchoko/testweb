package com.wevioo.pi.service.imp;

import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.wevioo.pi.common.ApplicationConstants;
import com.wevioo.pi.annotation.ExcelPdfHeader;
import com.wevioo.pi.domain.entity.referential.Parameter;
import com.wevioo.pi.exception.DataNotFoundException;
import com.wevioo.pi.exception.DocumentExportException;
import com.wevioo.pi.rest.dto.DownloadDocumentDto;
import com.wevioo.pi.service.GenericExcelPdfService;
import com.wevioo.pi.service.ParameterService;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Date;
import java.util.HashMap;
import java.util.Base64;
import java.util.ArrayList;
import java.util.stream.Collectors;


@Service
public class GenericExcelPdfServiceImpl implements GenericExcelPdfService {

    @Autowired
    private ParameterService parameterService;

    @Override
    public <T> DownloadDocumentDto saveExcelSheet(List<T> data) throws IOException {
        
        if(data.isEmpty()){
            throw new DataNotFoundException(ApplicationConstants.NO_DATA_FOUND , "Data Not found");
        }
        /// Instanciate workbook
        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Sheet1");


        // Create header row
        Row headerRow = sheet.createRow(0);
        CellStyle headerStyle = workbook.createCellStyle();


        // get the fields names ( DTO attributes names )
        Field[]  fields =  getAllFields( data.get(0).getClass());
        // set attributes names as column headers except serialVersionID attribute
        String[] columnHeaders = Arrays.stream(fields)
                .map(Field::getName)
                .filter(name -> !name.equals("serialVersionUID"))
                .filter(name -> !name.equals("step"))
                .toArray(String[]::new);



        List<Map<String, Object>> data1 = processData(data ,columnHeaders );

        ///  Make Header  style
        XSSFFont font = workbook.createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 8);
        font.setBold(true);
        headerStyle.setFont(font);

        String[] columnHeadersMapped =  Arrays.stream(fields)
                .filter(field -> !field.getName().equals("serialVersionUID"))
                .filter(field -> !field.getName().equals("step"))
                .filter(field -> field.isAnnotationPresent(ExcelPdfHeader.class))
                .map(field -> field.getAnnotation(ExcelPdfHeader.class).value())
                .toArray(String[]::new);

        // Create header cells
        for (int i = 0; i < columnHeadersMapped.length; i++) {
            Cell headerCell = headerRow.createCell(i);
            headerCell.setCellValue(columnHeadersMapped[i]);
            headerCell.setCellStyle(headerStyle);
        }

        // Create data rows cells
        int rowIndex = 1;

        // for each ligne of data we will create cell ( header , value )
        for (Map<String, Object> rowData : data1) {
            Row dataRow = sheet.createRow(rowIndex++);
            int cellIndex = 0;
            for (String header : columnHeaders) {
                // cell index in excel table
                Cell dataCell = dataRow.createCell(cellIndex++);
                Object cellValue = rowData.get(header);
                if (cellValue != null) {
                    // handle cellValue based on data Type
                    if (cellValue instanceof String) {
                        dataCell.setCellValue((String) cellValue);
                    } else if (cellValue instanceof Number) {
                        dataCell.setCellValue(((Number) cellValue).doubleValue());
                    } else if (cellValue instanceof Boolean) {
                        dataCell.setCellValue((Boolean) cellValue);
                    }
                    else if (cellValue instanceof Enum<?>) {
                        // Handle enums as strings
                        dataCell.setCellValue(((Enum<?>) cellValue).name());
                    }
                    else if (cellValue instanceof Date) {
                        // Set the cell value as a date
                        CellStyle dateStyle = workbook.createCellStyle();
                        dateStyle.setDataFormat(workbook.getCreationHelper().createDataFormat().getFormat("yyyy-MM-dd"));
                        dataCell.setCellStyle(dateStyle);
                        dataCell.setCellValue((Date) cellValue);
                    }
                }
                // Adjust column width
                int columnIndex = dataCell.getColumnIndex();
                sheet.setColumnWidth(columnIndex, 270 * 20);
            }
        }

        // create pdf file
        String fileLocation = createFile("excel.xlsx");

        // Save the workbook to a file
        try (FileOutputStream outputStream = new FileOutputStream(fileLocation)) {
            workbook.write(outputStream);
        } finally {
            workbook.close();
        }
        try{
            // get base64Content to be send to front
            String base64Content = getBase64Content(fileLocation);

            //delete file
            Path filePath = Paths.get(fileLocation);
            Files.delete(filePath);
            // Add timestamp to the file name
            String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            String fileName = timestamp+".xlsx";

            return DownloadDocumentDto.builder().contentType("application/vnd.ms-excel").content(base64Content)
                    .fileName(fileName).build();
        }
        catch (IOException e) {
            throw new DataNotFoundException(ApplicationConstants.ERROR_NO_FILE_FOUND,
                    ApplicationConstants.ERROR_NO_FILE_FOUND);
        }
    }

    @Override
    public <T> DownloadDocumentDto savePdfTable(List<T> data) throws IOException, DocumentException {

        if(data.isEmpty()){
            throw new DataNotFoundException(ApplicationConstants.NO_DATA_FOUND , "Data Not found");
        }
        // create pdf file
        String fileLocation = createFile("pdf.pdf");

        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(fileLocation));
        document.open();

        // Add logo image
        // read path file from referentiel
        Parameter param = parameterService.getByKey(ApplicationConstants.TEMPLATE_INVESTMENT);
        String basePath = param.getParameterValue();
        String logoUrl = basePath + "/" + ApplicationConstants.IMG_LOGO;


        Image logoImage = Image.getInstance(logoUrl);


        // Add the center logo
        logoImage.scaleToFit(110, 110); // Adjust the size
        logoImage.setAbsolutePosition(230, 750); // Adjust the position for the right side
        document.add(logoImage);

        Field[] fields =  getAllFields( data.get(0).getClass());

        // Filter fields to exclude "serialVersionUID"
        Field[] filteredFields = Arrays.stream(fields)
                .filter(field -> !field.getName().equals("serialVersionUID"))
                .filter(field -> !field.getName().equals("step"))
                .toArray(Field[]::new);

        // Exclude the serialVersionUID field
        List<String>  columnHeadersMapped =  Arrays.stream(filteredFields)
                .filter(field -> !field.getName().equals("serialVersionUID"))
                .filter(field -> !field.getName().equals("step"))
                .filter(field -> field.isAnnotationPresent(ExcelPdfHeader.class))
                .map(field -> field.getAnnotation(ExcelPdfHeader.class).value())
                .collect(Collectors.toList());

        // Add spacing
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));

        // Creating a table dynamically based on data
        PdfPTable table = new PdfPTable(columnHeadersMapped.size());
        table.setWidthPercentage(100);

        // Adding headers to the table with bold style and font size
        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        headerFont.setSize(8);


        // Adding headers to the table
        for (String header : columnHeadersMapped) {
            PdfPCell headerCell = new PdfPCell(new Phrase(header, headerFont));
            headerCell.setPadding(5); // Adjust the padding
            table.addCell(headerCell);
        }

        // Create a Font for the data cells
        Font dataFont = FontFactory.getFont(FontFactory.HELVETICA);
        dataFont.setSize(5); // Set the desired font size for data cells

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        // Adding data to the table dynamically
        for (T obj : data) {
            for (Field field : filteredFields) {
                String fieldName = field.getName();
                try {
                        Method getterMethod = findGetterMethod(obj.getClass(), fieldName);
                    // Check if the field is a date field
                    if (field.getType().equals(Date.class)) {

                        Date dateValue = (Date) getterMethod.invoke(obj);
                        String formattedDate = (dateValue != null) ? dateFormat.format(dateValue) : "";

                        PdfPCell dataCell = new PdfPCell(new Phrase(formattedDate, dataFont));
                        dataCell.setPadding(4); // Adjust the padding as needed
                        table.addCell(dataCell);
                    } else {
                        Object value = getterMethod.invoke(obj);
                        PdfPCell dataCell = new PdfPCell(new Phrase(value != null ? value.toString() : "" , dataFont));
                        dataCell.setPadding(4); // Adjust the padding as needed
                        table.addCell(dataCell);
                    }
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    throw new DocumentExportException(e.getMessage(),ApplicationConstants.DOCUMENT_ERROR);
                }
            }
        }

        // Adding the table to the document
        document.add(table);

        // Closing the document
        document.close();


        String base64Content = getBase64Content(fileLocation);

        //delete file
        Path path = Paths.get(fileLocation);
        Files.delete(path);

        // Add timestamp to the file name
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String fileName = timestamp + ".pdf";

        return DownloadDocumentDto.builder().contentType("application/pdf").content(base64Content)
                .fileName(fileName).build();
    }


    /**
     * this function utility is to set handle data in needed format to be fetched in excel table then
     * @param data
     * @param columnHeaders
     * @param <T>
     * @return
     */
    public <T> List<Map<String, Object>> processData(List<T> data , String[] columnHeaders) {
        return data.stream()
                .map(investor -> mapObjectToRowData(investor, columnHeaders))
                .collect(Collectors.toList());
    }

    /**
     * this function utility is to set handle data in needed format to be fetched in excel table then
     * @param getDto
     * @param columnHeaders
     * @param <T>
     * @return
     */
    private <T> Map<String, Object> mapObjectToRowData(T getDto, String[] columnHeaders) {
        Map<String, Object> rowData = new HashMap<>();
        // for each header  we will set its value
        for (String header : columnHeaders) {
            try {

                /// get the value of that field
                Method getterMethod = findGetterMethod(getDto.getClass(), header);
                Object value = getterMethod.invoke(getDto);
                rowData.put(header, value);

            } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                throw new DocumentExportException(e.getMessage(),ApplicationConstants.DOCUMENT_ERROR);
            }
        }
        return rowData;
    }

    /**
     * @param clazz this methed will return getAttribute value for each class
     * @param fieldName
     * @return
     */
    private Method findGetterMethod(Class<?> clazz, String fieldName) throws NoSuchMethodException {
        String getterMethodName = "get" + StringUtils.capitalize(fieldName);
        return clazz.getMethod(getterMethodName);
    }


    /**
     * create file
     * @param fileName
     * @return
     */
    private String createFile( String fileName){
        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        return path.substring(0, path.length() - 1) + fileName;
    }

    /**
     * get base64Content to be sent to Client
     * @param fileLocation
     * @return
     * @throws IOException
     */
    private String getBase64Content(String fileLocation) throws IOException {

        ByteArrayResource resource = null;
        Path path = Paths.get(fileLocation);
        resource = new ByteArrayResource(Files.readAllBytes(
                path));
        return Base64.getEncoder().encodeToString(resource.getByteArray());
    }
    private static Field[] getAllFields(Class<?> type) {
        List<Field> fieldsList = new ArrayList<>();

        // Loop through the class hierarchy
        while (type != null) {
            // Add the field to the list
            fieldsList.addAll(Arrays.asList(type.getDeclaredFields()));
            // Move up to the superclass
            type = type.getSuperclass();
        }
        // Convert the list to an array
        Field[] fieldsArray = new Field[fieldsList.size()];
        return fieldsList.toArray(fieldsArray);
    }

}
