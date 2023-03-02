package org.pl;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.UnitValue;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.StringTokenizer;

public class Demo1 {

    public void 示例1(String path) throws FileNotFoundException {
        PdfWriter pdfWriter = new PdfWriter(path);
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        Document document = new Document(pdfDocument);
        document.add(new Paragraph("Hello world!"));
        document.close();
    }

    public void 示例2(String path) throws IOException {
        PdfWriter writer = new PdfWriter(path);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        // Create a PdfFont
        PdfFont font = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
        // Add a Paragraph
        document.add(new Paragraph("iText is:").setFont(font));
        // Create a List
        List list = new List()
                .setSymbolIndent(12)
                .setListSymbol("•")
                .setFont(font);
        // Add ListItem objects
        list.add(new ListItem("Never gonna give you up"))
                .add(new ListItem("Never gonna let you down"))
                .add(new ListItem("Never gonna run around and desert you"))
                .add(new ListItem("Never gonna make you cry"))
                .add(new ListItem("Never gonna say goodbye"))
                .add(new ListItem("Never gonna tell a lie and hurt you"));
        // Add the list
        document.add(list);
        document.close();
    }

    public void 示例3(String path) throws FileNotFoundException, MalformedURLException {
        PdfWriter writer = new PdfWriter(path);
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

    public void 示例4(String path) throws IOException {

        //Initialize PDF writer
        PdfWriter writer = new PdfWriter(path);

        //Initialize PDF document
        PdfDocument pdf = new PdfDocument(writer);

        // Initialize document
        Document document = new Document(pdf, PageSize.A4.rotate());
        document.setMargins(20, 20, 20, 20);

        PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
        PdfFont bold = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
        Table table = new Table(UnitValue.createPercentArray(new float[]{4, 1, 3, 4, 3, 3, 3, 3, 1}))
                .useAllAvailableWidth();
        BufferedReader br = new BufferedReader(new FileReader("src/main/resources/csv/united_states.csv"));
        String line = br.readLine();
        process(table, line, bold, true);
        while ((line = br.readLine()) != null) {
            process(table, line, font, false);
        }
        br.close();
        document.add(table);

        //Close document
        document.close();
    }

    public static void process(Table table, String line, PdfFont font, boolean isHeader) {
        StringTokenizer tokenizer = new StringTokenizer(line, ";");
        while (tokenizer.hasMoreTokens()) {
            if (isHeader) {
                table.addHeaderCell(new Cell().add(new Paragraph(tokenizer.nextToken()).setFont(font)));
            } else {
                table.addCell(new Cell().add(new Paragraph(tokenizer.nextToken()).setFont(font)));
            }
        }
    }
}
