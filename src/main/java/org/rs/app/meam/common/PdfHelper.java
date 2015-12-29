package org.rs.app.meam.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.http.HttpServletResponse;
import org.docx4j.Docx4J;
import org.docx4j.convert.out.FOSettings;
import org.docx4j.fonts.IdentityPlusMapper;
import org.docx4j.fonts.Mapper;
import org.docx4j.fonts.PhysicalFont;
import org.docx4j.fonts.PhysicalFonts;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;

/**
 *
 * @author Suresh
 */
public class PdfHelper {

    /**
     * @param pdfContnt
     * @param filename
     * @param key
     * @param response
     */
    public static void downloadPdf(byte[] pdfContent, String filename, HttpServletResponse response) {
        try {
            if (pdfContent.length > 0) {
                initiateFile(response, filename);
                response.setContentType("application/pdf");
                try (OutputStream os = response.getOutputStream()) {
                    os.write(pdfContent);
                    os.flush();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void initiateFile(HttpServletResponse response, String filename) {
        response.setHeader("Expires", "0");
        response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
        response.setHeader("Pragma", "public");
        response.setHeader("Accept-Ranges", "none");
        response.addHeader("Content-Disposition", "inline; filename=" + filename);//add inline
    }

    public static byte[] createPDF(byte[] docxContent) {
        try {
            WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(new ByteArrayInputStream(docxContent));
            FOSettings foSettings = Docx4J.createFOSettings();
            Mapper fontMapper = new IdentityPlusMapper();
            wordMLPackage.setFontMapper(fontMapper);
            PhysicalFont font = PhysicalFonts.getPhysicalFonts().get("Arial Unicode MS");
            if (font != null) {
                fontMapper.getFontMappings().put("Times New Roman", font);
                fontMapper.getFontMappings().put("Arial", font);
            }
            fontMapper.getFontMappings().put("Libian SC Regular", PhysicalFonts.getPhysicalFonts().get("SimSun"));

            fontMapper.getFontMappings().put("Rupee Foradian", PhysicalFonts.getPhysicalFonts().get("Rupee Foradian"));
            foSettings.setWmlPackage(wordMLPackage);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            Docx4J.toFO(foSettings, out, Docx4J.FLAG_EXPORT_PREFER_XSL);
            return out.toByteArray();

        } catch (Exception ex) {
        }
        return null;
    }
}
