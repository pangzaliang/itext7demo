package org.pl;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

//        Demo1 demo1 = new Demo1();
//
//        demo1.示例1("C:/Users/Liang/Desktop/1.pdf");
//        demo1.示例2("C:/Users/Liang/Desktop/2.pdf");
//        demo1.示例3("C:/Users/Liang/Desktop/3.pdf");
//        demo1.示例4("C:/Users/Liang/Desktop/4.pdf");

//        Demo2 demo2 = new Demo2();
//
//        demo2.示例1("C:/Users/dell/Desktop/1.pdf");

//        Demo3 demo3 = new Demo3();
//        demo3.示例1("C:/Users/dell/Desktop/1.pdf");
//        demo3.示例2("C:/Users/dell/Desktop/2.pdf");
//        demo3.示例3("C:/Users/dell/Desktop/3.pdf");

//        Demo4 demo4 = new Demo4();
//        demo4.示例1("C:/Users/dell/Desktop/1.pdf");
//        demo4.示例2("C:/Users/dell/Desktop/2.pdf");
//        demo4.示例3("C:/Users/dell/Desktop/3.pdf");

        DemoOCR demoOCR = new DemoOCR();
        demoOCR.PDFOCR("C:\\Users\\dell\\Desktop\\微信截图_20230307170551.png",
                "C:/Users/dell/Desktop/999.pdf");
    }
}
