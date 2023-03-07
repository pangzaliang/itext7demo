package org.pl;

import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.pdfocr.OcrPdfCreator;
import com.itextpdf.pdfocr.tesseract4.Tesseract4LibOcrEngine;
import com.itextpdf.pdfocr.tesseract4.Tesseract4OcrEngineProperties;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class DemoOCR {

    static final Tesseract4OcrEngineProperties TESSERACT_4_OCR_ENGINE_PROPERTIES = new Tesseract4OcrEngineProperties();

    public void PDFOCR(String jpgPath, String pdfPath) {

        Tesseract4LibOcrEngine tesseract4LibOcrEngine = new Tesseract4LibOcrEngine(TESSERACT_4_OCR_ENGINE_PROPERTIES);

        List<File> files = Arrays.asList(new File(jpgPath));
        TESSERACT_4_OCR_ENGINE_PROPERTIES.setPathToTessData(new File("E:/tessdata_best-4.1.0/tessdata_best-4.1.0"));

        OcrPdfCreator ocrPdfCreator = new OcrPdfCreator(tesseract4LibOcrEngine);
        try (PdfWriter writer = new PdfWriter(pdfPath)) {
            ocrPdfCreator.createPdf(files, writer).close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
