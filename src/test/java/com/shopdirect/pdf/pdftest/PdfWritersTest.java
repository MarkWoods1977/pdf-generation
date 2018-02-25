package com.shopdirect.pdf.pdftest;

import org.junit.Test;

public class PdfWritersTest {

    @Test
    public void iTextWrite() throws Exception {

        PdfWriters pdfWriters = new PdfWriters();
        pdfWriters.iTextWrite();
    }

    @Test
    public void iTextTableWrite() throws Exception {

        PdfWriters pdfWriters = new PdfWriters();
        pdfWriters.iTextTableWrite();
    }

    @Test
    public void iTextEncryptedWrite() throws Exception {

        PdfWriters pdfWriters = new PdfWriters();
        pdfWriters.iTextEncryptedWrite();
    }
}