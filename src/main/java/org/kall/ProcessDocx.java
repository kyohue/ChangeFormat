package org.kall;


import jakarta.xml.bind.JAXBElement;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.docx4j.TextUtils;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class ProcessDocx {
    private static final Log logger = LogFactory.getLog(ProcessDocx.class);
    private static Integer examQC = 0;
    private static Integer examPartCount = 0;

    public List<Object> readDocx(String filePath) {
        File file = new File(filePath);
        try (InputStream inputStream = new FileInputStream(file)) {
            if (!file.exists()) {
                logger.error("文件不存在");
            }
            WordprocessingMLPackage mlPackage = WordprocessingMLPackage.load(inputStream);
            return mlPackage.getMainDocumentPart().getContent();
        } catch (IOException | Docx4JException e) {
            logger.error("读取docx文件失败", e);
        }
        return null;
    }

    public void writeDocx(String filePath, List<Object> content) {
        File file = new File(filePath);
        try (OutputStream outputStream = new FileOutputStream(file)) {
            if (!file.exists()) {
                file.createNewFile();
            }
            WordprocessingMLPackage mlPackage = WordprocessingMLPackage.createPackage();
            MainDocumentPart part = mlPackage.getMainDocumentPart();
            for (Object obj : content) {
                if (obj != null) {
                    part.addObject(obj);
                }
            }
            mlPackage.save(outputStream);
        } catch (IOException | Docx4JException e) {
            logger.error("写入docx文件失败", e);
        }
    }

    public List<Object> convert(List<Object> inputContent, List<Object> plateContent) {
        List<Object> outputContent = new ArrayList<>();
        //将plate文件的开头添加到outputContent中
        for (Object obj : plateContent) {
            if (obj instanceof JAXBElement) {
                JAXBElement<?> jaxbElement = (JAXBElement<?>) obj;
                Object value = jaxbElement.getValue();
                if (value instanceof Tbl) {
                    Tbl tbl = (Tbl) value;
                    Tr firstRow = (Tr) tbl.getContent().get(0);
                    JAXBElement<?> element = (JAXBElement<?>) firstRow.getContent().get(1);
                    Tc cell = (Tc) element.getValue();
                    List<Object> content = cell.getContent();
                    for (Object o : content) {
                        if (o instanceof P) {
                            P p = (P) o;
                            String text = TextUtils.getText(p);
                            logger.info(text);
                        }
                    }
                }
            }
            //到达模板开头的结尾，退出循环
            if (obj instanceof P && !((P) obj).getContent().isEmpty()) {
                P p = (P) obj;
                String text = TextUtils.getText(p);
                if (text.equals(KeyWord.RegPlateExamPart.getName())) {
                    break;
                }
                logger.info(text);
            }
            outputContent.add(obj);
        }
        return outputContent;
    }

    private Tc insertCell(Tc cell, String content) {
        ObjectFactory factory = new ObjectFactory();
        cell.getContent().clear();
        P paragraph = factory.createP();
        Text text = new Text();
        text.setValue(content);
        paragraph.getContent().add(text);
        cell.getContent().add(paragraph);
        return cell;
    }

    private void checkForTable(Object obj) {
        if (obj instanceof List) {
            List<?> list = (List<?>) obj;
            for (Object o : list) {
                if (o instanceof Tbl) {
                    Tbl tbl = (Tbl) o;
                    Tr firstRow = (Tr) tbl.getContent().get(0);
                    Tc cell = (Tc) firstRow.getContent().get(0);
                    System.out.println(cell.getContent());
                } else {
                    checkForTable(o);
                }
            }
        }
    }
}
