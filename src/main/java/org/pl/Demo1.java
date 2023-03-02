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
        // 创建一个PDF编辑器
        PdfWriter pdfWriter = new PdfWriter(path);
        // 初始化一个PDF文档（没有页）
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        // 创建一个PDF页
        Document document = new Document(pdfDocument);
        // 添加一个段落
        document.add(new Paragraph("Hello world!"));
        // 关闭页
        document.close();
    }

    public void 示例2(String path) throws IOException {
        PdfWriter writer = new PdfWriter(path);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        // 使用TimesRoMan字体
        PdfFont font = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
        // 创建一个段落使用设置字体
        document.add(new Paragraph("iText is:").setFont(font));
        // 创建一个列表
        List list = new List()
                .setSymbolIndent(12) // 设置偏移量
                .setListSymbol("•") // 设置列头
                .setFont(font); // 设置列表字体
        // 在列表里添加列内容
        list.add(new ListItem("Never gonna give you up"))
                .add(new ListItem("Never gonna let you down"))
                .add(new ListItem("Never gonna run around and desert you"))
                .add(new ListItem("Never gonna make you cry"))
                .add(new ListItem("Never gonna say goodbye"))
                .add(new ListItem("Never gonna tell a lie and hurt you"));
        document.add(list);
        document.close();
    }

    public void 示例3(String path) throws FileNotFoundException, MalformedURLException {
        PdfWriter writer = new PdfWriter(path);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);
        // 引用一个图像
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

        PdfWriter writer = new PdfWriter(path);
        PdfDocument pdf = new PdfDocument(writer);
        // 创建PDF页 A4大小（横向）
        Document document = new Document(pdf, PageSize.A4.rotate());
        // 设置页边距
        document.setMargins(20, 20, 20, 20);
        // 黑体字
        PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
        // 黑体字加粗
        PdfFont bold = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
        // 创建一个表格(表格的列和列宽使用数组的长度和百分比)
        Table table = new Table(UnitValue.createPercentArray(new float[]{4, 1, 3, 4, 3, 3, 3, 3, 1}))
                .useAllAvailableWidth(); // 百分百宽度
        BufferedReader br = new BufferedReader(new FileReader("src/main/resources/csv/united_states.csv"));
        String line = br.readLine();
        // 编写表头
        process(table, line, bold, true);
        // 读表内容
        while ((line = br.readLine()) != null) {
            // 循环里编写表内容
            process(table, line, font, false);
        }
        br.close();
        document.add(table);
        document.close();
    }

    /**
     * PDF的表格写入
     * @param table PDF的表格
     * @param line 每行内容
     * @param font 字体
     * @param isHeader 是否为表头
     */
    public static void process(Table table, String line, PdfFont font, boolean isHeader) {
        // 对行内容使用;进行分组
        StringTokenizer tokenizer = new StringTokenizer(line, ";");
        // 类似于迭代器的用法
        while (tokenizer.hasMoreTokens()) {
            if (isHeader) {
                table.addHeaderCell(new Cell().add(new Paragraph(tokenizer.nextToken()).setFont(font)));
            } else {
                table.addCell(new Cell().add(new Paragraph(tokenizer.nextToken()).setFont(font)));
            }
        }
    }
}
