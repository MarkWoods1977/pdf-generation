package com.shopdirect.pdf.pdftest;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class PdfWriters {

    public static final String PDF_LOCATION_ROOT = "src/main/resources/";
    public static final String ITEXT_HELLO_WORLD_PDF_LOCATION = PDF_LOCATION_ROOT + "iTextHelloWorld.pdf";
    public static final String ITEXT_ENCRYPTED_HELLO_WORLD_PDF_LOCATION = PDF_LOCATION_ROOT + "encryptedPdf.pdf";
    public static final String ITEXT_TABLE_PDF_LOCATION = PDF_LOCATION_ROOT + "iTextTable.pdf";
    public static final String PDF_BOX_HELLO_WORLD_PDF_LOCATION = PDF_LOCATION_ROOT + "pdfBoxHelloWorld.pdf";

    public void iTextWrite() throws FileNotFoundException, DocumentException {

        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(ITEXT_HELLO_WORLD_PDF_LOCATION));

        document.open();
        Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
        Chunk chunk = new Chunk("Hello World", font);

        document.add(chunk);
        document.close();
    }

    public void iTextTableWrite() throws DocumentException, IOException, URISyntaxException {

        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(ITEXT_TABLE_PDF_LOCATION));

        document.open();

        PdfPTable table = new PdfPTable(3);
        addTableHeader(table);
        addRows(table);
        addCustomRows(table);

        document.add(table);
        document.close();

    }

    public void iTextEncryptedWrite() throws IOException, DocumentException {

        PdfReader pdfReader = new PdfReader(PDF_LOCATION_ROOT);
        PdfStamper pdfStamper
                = new PdfStamper(pdfReader, new FileOutputStream(ITEXT_ENCRYPTED_HELLO_WORLD_PDF_LOCATION));

        pdfStamper.setEncryption(
                "userpass".getBytes(),
                "".getBytes(),
                PdfWriter.ALLOW_PRINTING | PdfWriter.ALLOW_COPY,
                PdfWriter.ENCRYPTION_AES_256
        );

        pdfStamper.close();
    }

    private void addCustomRows(PdfPTable table)
            throws URISyntaxException, BadElementException, IOException {

        Path path = Paths.get(ClassLoader.getSystemResource("Java_logo.png").toURI());
        Image img = Image.getInstance(path.toAbsolutePath().toString());
        img.scalePercent(10);

        PdfPCell imageCell = new PdfPCell(img);
        table.addCell(imageCell);

        PdfPCell horizontalAlignCell = new PdfPCell(new Phrase("row 2, col 2"));
        horizontalAlignCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(horizontalAlignCell);

        PdfPCell verticalAlignCell = new PdfPCell(new Phrase("row 2, col 3"));
        verticalAlignCell.setVerticalAlignment(Element.ALIGN_BOTTOM);
        table.addCell(verticalAlignCell);
    }

    private void addRows(PdfPTable table) {

        table.addCell("row 1, col 1");
        table.addCell("row 1, col 2");
        table.addCell("row 1, col 3");
    }

    private void addTableHeader(PdfPTable table) {

        Stream.of("column header 1", "column header 2", "column header 3")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
    }




}