package org.pl;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import java.io.FileNotFoundException;

public class Main1 {
    public static void main(String[] args) throws FileNotFoundException {
        PdfWriter pdfWriter = new PdfWriter("C:/Users/Liang/Desktop/1.pdf");
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        Document document = new Document(pdfDocument);
        document.add(new Paragraph("Hello world!"));
        document.close();
    }
}