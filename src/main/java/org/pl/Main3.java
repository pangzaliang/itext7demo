package org.pl;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;

public class Main3 {

    public static void main(String[] args) throws FileNotFoundException, MalformedURLException {
        PdfWriter writer = new PdfWriter("C:/Users/Liang/Desktop/3.pdf");
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);
        Image fox = new Image(ImageDataFactory.create("src/main/resources/img/fox.bmp"));
        Image dog = new Image(ImageDataFactory.create("src/main/resources/img/dog.bmp"));
        Paragraph p = new Paragraph("The quick brown ")
                .add(fox)
                .add(" jumps over the lazy ")
                .add(dog);
        document.add(p);
        document.close();
    }
}
