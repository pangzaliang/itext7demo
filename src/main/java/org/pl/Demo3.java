package org.pl;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceCmyk;
import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.ColumnDocumentRenderer;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.*;
import com.itextpdf.layout.renderer.CellRenderer;
import com.itextpdf.layout.renderer.DrawContext;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.StringTokenizer;

public class Demo3 {

    public static final String APPLE_IMG = "src/main/resources/img/ny_times_apple.jpg";
    public static final String APPLE_TXT = "src/main/resources/data/ny_times_apple.txt";
    public static final String FACEBOOK_IMG = "src/main/resources/img/ny_times_fb.jpg";
    public static final String FACEBOOK_TXT = "src/main/resources/data/ny_times_fb.txt";
    public static final String INST_IMG = "src/main/resources/img/ny_times_inst.jpg";
    public static final String INST_TXT = "src/main/resources/data/ny_times_inst.txt";



    public void 示例1 (String path) throws IOException {

        PdfDocument pdf = new PdfDocument(new PdfWriter(path));

        PageSize ps = PageSize.A5;

        Document document = new Document(pdf, ps);

        float offSet = 36;
        float columnWidth = (ps.getWidth() - offSet * 2 + 10) / 3;
        float columnHeight = ps.getHeight() - offSet * 2;

        Rectangle[] columns = {new Rectangle(offSet - 5, offSet, columnWidth, columnHeight),
                new Rectangle(offSet + columnWidth, offSet, columnWidth, columnHeight),
                new Rectangle(offSet + columnWidth * 2 + 5, offSet, columnWidth, columnHeight)};
        document.setRenderer(new ColumnDocumentRenderer(document, columns));

        Image apple = new Image(ImageDataFactory.create(APPLE_IMG)).setWidth(columnWidth);
        String articleApple = new String(Files.readAllBytes(Paths.get(APPLE_TXT)), StandardCharsets.UTF_8);
        Demo3.addArticle(document, "Apple Encryption Engineers, if Ordered to Unlock iPhone, Might Resist", "By JOHN MARKOFF MARCH 18, 2016", apple, articleApple);
        Image facebook = new Image(ImageDataFactory.create(FACEBOOK_IMG)).setWidth(columnWidth);
        String articleFB = new String(Files.readAllBytes(Paths.get(FACEBOOK_TXT)), StandardCharsets.UTF_8);
        Demo3.addArticle(document, "With \"Smog Jog\" Through Beijing, Zuckerberg Stirs Debate on Air Pollution", "By PAUL MOZUR MARCH 18, 2016", facebook, articleFB);
        Image inst = new Image(ImageDataFactory.create(INST_IMG)).setWidth(columnWidth);
        String articleInstagram = new String(Files.readAllBytes(Paths.get(INST_TXT)), StandardCharsets.UTF_8);
        Demo3.addArticle(document, "Instagram May Change Your Feed, Personalizing It With an Algorithm","By MIKE ISAAC MARCH 15, 2016", inst, articleInstagram);

        document.close();
    }

    /**
     * 添加文章
     * @param doc 文档
     * @param title 标题
     * @param author 作者
     * @param img 图像
     * @param text 内容
     */
    public static void addArticle(Document doc, String title, String author, Image img, String text) throws IOException {
        Paragraph p1 = new Paragraph(title)
                .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))
                .setFontSize(14);
        doc.add(p1);
        doc.add(img);
        Paragraph p2 = new Paragraph()
                .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA))
                .setFontSize(6)
                .setFontColor(ColorConstants.GRAY)
                .add(author);
        doc.add(p2);
        Paragraph p3 = new Paragraph()
                .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))
                .setFontSize(9)
                .add(text);
        doc.add(p3);
    }


    Color greenColor = new DeviceCmyk(0.78f, 0, 0.81f, 0.21f);
    Color yellowColor = new DeviceCmyk(0, 0, 0.76f, 0.01f);
    Color redColor = new DeviceCmyk(0, 0.76f, 0.86f, 0.01f);
    Color blueColor = new DeviceCmyk(0.28f, 0.11f, 0, 0);

    public void 示例2 (String path) throws IOException {
        PdfDocument pdf = new PdfDocument(new PdfWriter(path));
        PageSize ps = new PageSize(842, 680);

        Document document = new Document(pdf, ps);

        PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
        PdfFont bold = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
        Table table = new Table(UnitValue.createPercentArray(new float[]{1.5f, 7, 2, 2, 2, 2, 3, 4, 4, 2}));
        table.setTextAlignment(TextAlignment.CENTER)
                .setHorizontalAlignment(HorizontalAlignment.CENTER);
        BufferedReader br = new BufferedReader(new InputStreamReader(Files.newInputStream(Paths.get("src/main/resources/csv/premier_league.csv")), StandardCharsets.UTF_8));
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

    public void process(Table table, String line, PdfFont font, boolean isHeader) {
        StringTokenizer tokenizer = new StringTokenizer(line, ";");
        int columnNumber = 0;
        while (tokenizer.hasMoreTokens()) {
            if (isHeader) {
                Cell cell = new Cell().add(new Paragraph(tokenizer.nextToken()));
                cell.setNextRenderer(new RoundedCornersCellRenderer(cell));
                cell.setPadding(5).setBorder(null);
                table.addHeaderCell(cell);
            } else {
                columnNumber++;
                Cell cell = new Cell().add(new Paragraph(tokenizer.nextToken()));
                cell.setFont(font).setBorder(new SolidBorder(ColorConstants.BLACK, 0.5f));
                switch (columnNumber) {
                    case 4:
                        cell.setBackgroundColor(greenColor);
                        break;
                    case 5:
                        cell.setBackgroundColor(yellowColor);
                        break;
                    case 6:
                        cell.setBackgroundColor(redColor);
                        break;
                    default:
                        cell.setBackgroundColor(blueColor);
                        break;
                }
                table.addCell(cell);
            }
        }

    }

    private static class RoundedCornersCellRenderer extends CellRenderer {
        public RoundedCornersCellRenderer(Cell modelElement) {
            super(modelElement);
        }

        @Override
        public void drawBorder(DrawContext drawContext) {
            Rectangle rectangle = getOccupiedAreaBBox();
            float llx = rectangle.getX() + 1;
            float lly = rectangle.getY() + 1;
            float urx = rectangle.getX() + getOccupiedAreaBBox().getWidth() - 1;
            float ury = rectangle.getY() + getOccupiedAreaBBox().getHeight() - 1;
            PdfCanvas canvas = drawContext.getCanvas();
            float r = 4;
            float b = 0.4477f;
            canvas.moveTo(llx, lly).lineTo(urx, lly).lineTo(urx, ury - r)
                    .curveTo(urx, ury - r * b, urx - r * b, ury, urx - r, ury)
                    .lineTo(llx + r, ury)
                    .curveTo(llx + r * b, ury, llx, ury - r * b, llx, ury - r)
                    .lineTo(llx, lly).stroke();
            super.drawBorder(drawContext);
        }
    }

    public void 示例3(String path) throws IOException {

        //Initialize PDF document
        PdfDocument pdf = new PdfDocument(new PdfWriter(path));
        pdf.addEventHandler(PdfDocumentEvent.END_PAGE, new MyEventHandler());

        // Initialize document
        Document document = new Document(pdf);

        Paragraph p = new Paragraph("List of reported UFO sightings in 20th century")
                .setTextAlignment(TextAlignment.CENTER).setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD)).setFontSize(14);
        document.add(p);



        Table table = new Table(UnitValue.createPercentArray(new float[]{3, 5, 7, 4}));

        BufferedReader br = new BufferedReader(new FileReader("src/main/resources/csv/ufo.csv"));
        String line = br.readLine();
        process3(table, line, PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD), true);
        while ((line = br.readLine()) != null) {
            process3(table, line, PdfFontFactory.createFont(StandardFonts.HELVETICA), false);
        }
        br.close();

        document.add(table);

        document.close();
    }

    public void process3(Table table, String line, PdfFont font, boolean isHeader) {
        StringTokenizer tokenizer = new StringTokenizer(line, ";");
        while (tokenizer.hasMoreTokens()) {
            if (isHeader) {
                table.addHeaderCell(new Cell().add(new Paragraph(tokenizer.nextToken()).setFont(font)).setFontSize(9).setBorder(new SolidBorder(ColorConstants.BLACK, 0.5f)));
            } else {
                table.addCell(new Cell().add(new Paragraph(tokenizer.nextToken()).setFont(font)).setFontSize(9).setBorder(new SolidBorder(ColorConstants.BLACK, 0.5f)));
            }
        }
    }

    protected static class MyEventHandler implements IEventHandler{

        public void handleEvent(Event event) {
            PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
            PdfDocument pdfDoc = docEvent.getDocument();
            PdfPage page = docEvent.getPage();
            int pageNumber = pdfDoc.getPageNumber(page);
            Rectangle pageSize = page.getPageSize();
            PdfCanvas pdfCanvas = new PdfCanvas(page.newContentStreamBefore(), page.getResources(), pdfDoc);

            //Set background
            Color limeColor = new DeviceCmyk(0.208f, 0, 0.584f, 0);
            Color blueColor = new DeviceCmyk(0.445f, 0.0546f, 0, 0.0667f);
            pdfCanvas.saveState()
                    .setFillColor(pageNumber % 2 == 1 ? limeColor : blueColor)
                    .rectangle(pageSize.getLeft(), pageSize.getBottom(), pageSize.getWidth(), pageSize.getHeight())
                    .fill().restoreState();

            //Add header and footer
            try {
                pdfCanvas.beginText()
                        .setFontAndSize(PdfFontFactory.createFont(StandardFonts.HELVETICA), 9)
                        .moveText(pageSize.getWidth() / 2 - 60, pageSize.getTop() - 20)
                        .showText("THE TRUTH IS OUT THERE")
                        .moveText(60, -pageSize.getTop() + 30)
                        .showText(String.valueOf(pageNumber))
                        .endText();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            //Add watermark
            Canvas canvas = new Canvas(pdfCanvas, page.getPageSize());
            canvas.setFontColor(ColorConstants.WHITE);
            canvas.setProperty(Property.FONT_SIZE, UnitValue.createPointValue(60));
            try {
                canvas.setProperty(Property.FONT, PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            canvas.showTextAligned(new Paragraph("CONFIDENTIAL"), 298, 421, pdfDoc.getPageNumber(page),
                    TextAlignment.CENTER, VerticalAlignment.MIDDLE, 45);

            pdfCanvas.release();
        }
    }
}
