package com.profolio.controller;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
@Service
public class PdfToJpgService {

    public void convertPdf(String fileName, String itemImgLocation){
        File file = new File(itemImgLocation+fileName);
        try {
            PDDocument document = PDDocument.load(file);
            int pageCount = document.getNumberOfPages();//pdf의 페이지 수
            PDFRenderer pdfRenderer = new PDFRenderer(document);
            // 0 페이지를 JPG파일로 저장
            BufferedImage imageObj = pdfRenderer.renderImageWithDPI(0, 100, ImageType.RGB);
            var fileOriName = fileName.substring(0,fileName.lastIndexOf(".")); // 확장자 추출
            File outputfile = new File(itemImgLocation+fileOriName+".jpg");
            ImageIO.write(imageObj, "jpg", outputfile);
            document.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteFile(String filePath, String itemImgLocation) throws Exception{
        var fileOriName = filePath.substring(0,filePath.lastIndexOf(".")); // 확장자 추출
        File deleteFile = new File(itemImgLocation+fileOriName+".jpg");
        if(deleteFile.exists()) {
            deleteFile.delete();
        }
    }
}
