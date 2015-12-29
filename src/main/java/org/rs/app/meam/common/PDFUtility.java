package org.rs.app.meam.common;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.PrettyXmlSerializer;
import org.htmlcleaner.TagNode;
import org.w3c.dom.DOMException;
import org.xhtmlrenderer.layout.SharedContext;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.xhtmlrenderer.pdf.ITextUserAgent;
import org.xhtmlrenderer.resource.XMLResource;

/**
 *
 * @author ra2ldip
 */
public class PDFUtility {

    private static PDFUtility reportUtils;

    public PDFUtility() {
    }

    public static PDFUtility getInstance() {
        if (reportUtils == null) {
            reportUtils = new PDFUtility();
        }
        return reportUtils;
    }

    public byte[] createPDF(String pdfBuffer) {
        pdfBuffer = sanitize(pdfBuffer);
        HtmlCleaner cleaner = new HtmlCleaner();
        CleanerProperties cp = cleaner.getProperties();
        cp.setUseEmptyElementTags(false);
        cp.setTranslateSpecialEntities(true);
        cp.setTransResCharsToNCR(true);
        cp.setOmitComments(true);
        cp.setOmitComments(true);
        cp.setOmitDoctypeDeclaration(true);
        cp.setNamespacesAware(false);
        TagNode node = cleaner.clean(pdfBuffer);
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            new PrettyXmlSerializer(cp).writeToStream(node, baos);
            ByteArrayInputStream finalXhtmlStream = new ByteArrayInputStream(baos.toByteArray());
            org.w3c.dom.Document document = XMLResource.load(finalXhtmlStream).getDocument();
            document.setStrictErrorChecking(false);
            document.setXmlStandalone(true);
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocument(document, null);
            ITextUserAgent callback = new ITextUserAgent(renderer.getOutputDevice());
            SharedContext scontext = renderer.getSharedContext();
            callback.setSharedContext(scontext);
            scontext.setUserAgentCallback(callback);
            scontext.setDotsPerPixel(16);
            renderer.layout();
            renderer.createPDF(os);
            PdfReader reader = new PdfReader(os.toByteArray());
            ByteArrayOutputStream modifiedPdfBaos = new ByteArrayOutputStream();
            PdfStamper stamper = new PdfStamper(reader, modifiedPdfBaos);
            BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
            PdfImportedPage page;
            int pageNo = 0;
            int totalPages = reader.getNumberOfPages();
            while (pageNo < totalPages) {
                pageNo++;
                PdfContentByte cb = stamper.getOverContent(pageNo);
                page = stamper.getWriter().getImportedPage(reader, pageNo);
                PdfDocument pd = cb.getPdfDocument();
                pd.setPageSize(PageSize.A4);
                pd.setMargins(50, 50, 50, 50);
                cb.beginText();
                cb.setFontAndSize(bf, 10);
                cb.showTextAligned(Element.ALIGN_LEFT, "Page " + pageNo + " of " + totalPages, 520, 10, 0);
                cb.setColorFill(BaseColor.GREEN);
                cb.endText();
    }
            stamper.setViewerPreferences(PdfWriter.PageLayoutSinglePage | PdfWriter.PageModeUseThumbs);
            stamper.close();
            reader.close();
            System.out.println("done");
            return modifiedPdfBaos.toByteArray();
        } catch (DocumentException | com.lowagie.text.DocumentException | IOException | DOMException e) {
            throw new RuntimeException(e);
        }
    }

    private static StringBuilder addPdfHeader() {
        StringBuilder sb = new StringBuilder();
        String img = "http://localhost:8084/images/nlclogo.png";
        sb.append("<table width='100%;border-width: 0px !important;' border='0' cellspacing='0' cellpadding='0'>");
        sb.append("<tr>");
        sb.append("<td style='text-align:center !important;vertical-align: bottom !important;'>");
        sb.append("<img src='").append(img).append("' height='100%' width='100%'/>");
        sb.append("</td>");
        sb.append("<td width='80%' style='text-align:center !important;'>");
        sb.append("<span style='text-align:center !important;font-size:25px !important;color:#1e7d4b !important;'>NEYVELI LIGNITE CORPORATION LIMITED</span><br/>"
                + "<span style='text-align:center !important;font-size:20px !important;color:#1e7d4b !important;'>(“Navaratna” Government of India Enterprise)</span><br/>"
                + "<span style='text-align:center !important;font-size:20px !important;color:#1e7d4b !important;'>OFFICE OF THE EXECUTIVE DIRECTOR/PBD &CONTRACTS</span><br/>"
                + "<span style='text-align:center !important;font-size:18px !important;color:#1e7d4b !important;'>CORPORATE OFFICE, NEYVELI-607801.</span>");
        sb.append("</td>");
        sb.append("<td style='text-align:center !important;vertical-align: bottom !important;'>");
        sb.append("<img src='").append(img).append("' height='100%' width='100%'/>");
        sb.append("</td>");
        sb.append("</tr>");
        sb.append("</table>");
        sb.append("<br><br><hr>");
        return sb;
    }

    private String htmlTags() {
        StringBuilder sb = new StringBuilder();
        sb.append("<(?!\\/?html");
        sb.append("|\\/?head|\\/?style|\\/?body");
        sb.append("|\\/?table|\\/?thead|\\/?tbody|\\/?tr|\\/?td|\\/?th");
        sb.append("|\\/?br|\\/?a|\\/?img|\\/?h1|\\/?h2|\\/?h3|\\/?h4|\\/?b|\\/?span|\\/?pre |\\/?p |\\/?hr|\\/?strong");
        sb.append("|\\/?ol|\\/?ul|\\/?li");
        sb.append("|\\/?span|\\/?label|\\/?font");
        sb.append(")[^>]+>");
        return sb.toString();
    }

    private String sanitize(String htmlContent) {
        String messageBody = addStyleShit(htmlContent);
        messageBody = messageBody.replaceAll(htmlTags(), "");
        messageBody = messageBody.replaceAll("&nbsp;", " ");
        messageBody = messageBody.replaceAll("[\t ]+", " ");
        messageBody = messageBody.trim();
        messageBody = messageBody.replaceAll("(\n )", "\n");
        messageBody = messageBody.replaceAll("&aacute;", "?");
        messageBody = messageBody.replaceAll("&Aacute;", "?");
        messageBody = messageBody.replaceAll("&eacute;", "?");
        messageBody = messageBody.replaceAll("&Eacute;", "?");
        messageBody = messageBody.replaceAll("&iacute;", "?");
        messageBody = messageBody.replaceAll("&Iacute;", "?");
        messageBody = messageBody.replaceAll("&oacute;", "?");
        messageBody = messageBody.replaceAll("&Oacute;", "?");
        messageBody = messageBody.replaceAll("&uacute;", "?");
        messageBody = messageBody.replaceAll("&Uacute;", "?");
        messageBody = messageBody.replaceAll("&ntilde;", "?");
        messageBody = messageBody.replaceAll("&Ntilde;", "?");
        messageBody = messageBody.replaceAll("Loading...", "");
        return messageBody;
    }

    private static String addStyleShit(String pdfBuffer) {
        StringBuilder sb = new StringBuilder();
        sb.append("<html>");
        sb.append("<head>");
        sb.append("<style type='css/text'>");
        sb.append("html{font-family:sans-serif;-ms-text-size-adjust:100%;-webkit-text-size-adjust:100%}\n"
                + "body{margin:0}article,aside,details,figcaption,figure,footer,header,hgroup,main,nav,section,summary{display:block}\n"
                + "audio,canvas,progress,video{display:inline-block;vertical-align:baseline}\n"
                + "audio:not([controls]){display:none;height:0}[hidden],\n"
                + "template{display:none}\n"
                + "a{background:0 0}\n"
                + "a:active,\n"
                + "a:hover{outline:0}\n"
                + "A:link, A:visited {color: rgb(35, 86, 157);font-weight: normal;text-decoration: none;}\n"
                + "abbr[title]{border-bottom:1px dotted}\n"
                + "b,strong{font-weight:700}\n"
                + "dfn{font-style:italic}\n"
                + "h1{font-size:2em;margin:.67em 0}\n"
                + "mark{background:#ff0;color:#000}\n"
                + "small{font-size:80%}\n"
                + "sub,sup{font-size:75%;line-height:0;position:relative;vertical-align:baseline}\n"
                + "sup{top:-.5em}\n"
                + "sub{bottom:-.25em}\n"
                + "img{border:0}\n"
                + "svg:not(:root){overflow:hidden}\n"
                + "figure{margin:1em 40px}\n"
                + "hr{-moz-box-sizing:content-box;box-sizing:content-box;height:0}\n"
                + "pre{overflow:auto}");
        sb.append("/*Grid*/\n"
                + ".ui-jqgrid {position: relative; font-size:12px;}\n"
                + ".ui-jqgrid .ui-jqgrid-view {position: relative;left:0px; top: 0px; padding: .0em;}\n"
                + "/* caption*/\n"
                + ".ui-jqgrid .ui-jqgrid-titlebar {padding: .5em .5em .5em .5em;border-left: 0px none;border-right: 0px none; border-top: 0px none;}\n"
                + ".ui-jqgrid .ui-jqgrid-title {margin: .1em 0 .2em; font-family: ABeeZee ;font-size: 16px;}\n"
                + ".ui-jqgrid .ui-jqgrid-titlebar-close { position: absolute;top: 50%; width: 19px; margin: -10px 0 0 0; padding: 1px; height:18px;}\n"
                + ".ui-jqgrid .ui-jqgrid-titlebar-close span { display: block; margin: 1px; }\n"
                + ".ui-jqgrid .ui-jqgrid-titlebar-close:hover { padding: 0; }\n"
                + "/* header*/\n"
                + ".ui-jqgrid .ui-jqgrid-hdiv {position: relative; margin: 0em;padding: 0em; overflow-x: hidden; overflow-y: auto; border-left: 0px none !important; border-top : 0px none !important; border-right : 0px none !important;}\n"
                + ".ui-jqgrid .ui-jqgrid-hbox {float: left; padding-right: 20px; overflow: hidden;}\n"
                + ".ui-jqgrid .ui-jqgrid-htable {width:100%;table-layout:fixed;margin:0em;}\n"
                + ".ui-jqgrid .ui-jqgrid-htable th {height:25px;padding: 0 2px 0 2px;border:1px solid #a8da7f;}\n"
                + ".ui-jqgrid .ui-jqgrid-htable th div {overflow: hidden; position:relative; height:25px;border:1px solid #a8da7f;}\n"
                + ".ui-th-column, .ui-jqgrid .ui-jqgrid-htable th.ui-th-column {overflow: hidden;white-space: nowrap;text-align:center;border-top : 0px none;border-bottom : 0px none;}\n"
                + ".ui-th-ltr, .ui-jqgrid .ui-jqgrid-htable th.ui-th-ltr {border-left : 0px none;font-size: 15px;background: #e4fad0;font-family: ABeeZee}\n"
                + ".ui-th-rtl, .ui-jqgrid .ui-jqgrid-htable th.ui-th-rtl {border-right : 0px none;}\n"
                + ".ui-jqgrid .ui-th-div-ie {white-space: nowrap; zoom :1; height:17px;}\n"
                + ".ui-jqgrid .ui-jqgrid-resize {height:20px !important;position: relative; cursor :e-resize;display: inline;overflow: hidden;}\n"
                + ".ui-jqgrid .ui-grid-ico-sort {overflow:hidden;position:absolute;display:inline; cursor: pointer !important;}\n"
                + ".ui-jqgrid .ui-icon-asc {margin-top:-3px; height:12px;}\n"
                + ".ui-jqgrid .ui-icon-desc {margin-top:3px;height:12px;}\n"
                + ".ui-jqgrid .ui-i-asc {margin-top:0px;height:16px;}\n"
                + ".ui-jqgrid .ui-i-desc {margin-top:0px;margin-left:13px;height:16px;}\n"
                + ".ui-jqgrid .ui-jqgrid-sortable {cursor:pointer;}\n"
                + ".ui-jqgrid tr.ui-search-toolbar th { border-top-width: 1px !important; border-top-color: inherit !important; border-top-style: ridge !important }\n"
                + "tr.ui-search-toolbar input {margin: 1px 0px 0px 0px}\n"
                + "tr.ui-search-toolbar select {margin: 1px 0px 0px 0px}\n"
                + "/* body */ \n"
                + ".ui-jqgrid .ui-jqgrid-bdiv {background-color: #FFFFFF;position: relative; margin: 0em; padding:0; overflow: visible; text-align:left;}\n"
                + ".ui-jqgrid .ui-jqgrid-btable {table-layout:fixed; margin:0em;}\n"
                + ".ui-jqgrid tr.jqgrow td {\n"
                + "    word-wrap: break-word;   \n"
                + "    white-space: pre-wrap;   \n"
                + "    overflow: hidden;\n"
                + "    font-weight: normal;\n"
                + "    height: auto;\n"
                + "    padding-left: 5px;\n"
                + "    font-family: ABeeZee, Helvetica;\n"
                + "    vertical-align: text-top;\n"
                + "    vertical-align: middle;\n"
                + "    padding-top: 2px;\n"
                + "    padding-bottom: 2px;\n"
                + "    border-bottom-width: thin; border-bottom-color: inherit; border-bottom-style: solid;    \n"
                + "}\n"
                + ".ui-jqgrid tr.jqgfirstrow td {padding: 0 2px 0 2px;border-right-width: thin; border-right-style: solid;}\n"
                + ".ui-jqgrid tr.jqgroup td {font-weight: normal; overflow: hidden; white-space: pre; height: 22px;padding: 0 2px 0 2px;border-bottom-width: 1px; border-bottom-color: inherit; border-bottom-style: solid;}\n"
                + ".ui-jqgrid tr.jqfoot td {font-weight: bold; overflow: hidden; white-space: pre; height: 22px;padding: 0 2px 0 2px;border-bottom-width: 1px; border-bottom-color: inherit; border-bottom-style: solid;}\n"
                + ".ui-jqgrid tr.ui-row-ltr td {text-align:left;border-right-width: 1px; border-right-color: inherit; border-right-style: solid;}\n"
                + ".ui-jqgrid tr.ui-row-rtl td {text-align:right;border-left-width: 1px; border-left-color: inherit; border-left-style: solid;}\n"
                + ".ui-jqgrid td.jqgrid-rownum { padding: 0 2px 0 2px; margin: 0px; border: 0px none;}\n"
                + ".ui-jqgrid .ui-jqgrid-resize-mark { width:2px; left:0; background-color:#777; cursor: e-resize; cursor: col-resize; position:absolute; top:0; height:100px; overflow:hidden; display:none;	border:0 none;}\n"
                + "/*subgrid*/\n"
                + ".ui-jqgrid .ui-jqgrid-btable .ui-sgcollapsed span {display: block;}\n"
                + ".ui-jqgrid .ui-subgrid {margin:0em;padding:0em; width:100%;}\n"
                + ".ui-jqgrid .ui-subgrid table {table-layout: fixed;}\n"
                + ".ui-jqgrid .ui-subgrid tr.ui-subtblcell td {height:18px;border-right-width: 1px; border-right-color: inherit; border-right-style: solid;border-bottom-width: 1px; border-bottom-color: inherit; border-bottom-style: solid;}\n"
                + ".ui-jqgrid .ui-subgrid td.subgrid-data {border-top:  0px none !important;}\n"
                + ".ui-jqgrid .ui-subgrid td.subgrid-cell {border-width: 0px 0px 1px 0px;}\n"
                + ".ui-jqgrid .ui-th-subgrid {height:20px;}\n"
                + "");
        sb.append(".ui-widget-content {\n"
                + "    border: 1px solid #dddddd;\n"
                + "    color: #333333;\n"
                + "}\n"
                + ".ui-widget-content a {\n"
                + "    color: #333333;\n"
                + "}\n"
                + ".ui-widget-header {\n"
                + "    border: 1px solid ;\n"
                + "    color: #ffffff;\n"
                + "    font-weight: bold;\n"
                + "    text-align: center;\n"
                + "    min-height: 20px;\n"
                + "}\n"
                + ".ui-widget-header a {\n"
                + "    color: #ffffff;\n"
                + "}\n"
                + "\n"
                + "/* Interaction states\n"
                + "----------------------------------*/\n"
                + ".ui-state-default,\n"
                + ".ui-widget-content .ui-state-default,\n"
                + ".ui-widget-header .ui-state-default {\n"
                + "    border: 1px solid #cccccc;\n"
                + "    font-weight: bold;\n"
                + "    color: #1c94c4;\n"
                + "}\n"
        );
        sb.append(".cvteste {text-align: center !important;color: white !important;background-color: green !important;}");
        sb.append("table{width:100% !important;border:'1px';}table th {1px solid #dddddd; border-bottom:2px solid #a8da7f; min-height:30px; text-align:center; vertical-align: middle; background:#e4fad0;}table td {1px solid #dddddd; text-align: left; vertical-align: top;}");
        sb.append("</style>");
        sb.append("</head>");
        sb.append("<body>");
        sb.append("<table width='100%'><tr><td>");
        sb.append(addPdfHeader());
        sb.append(pdfBuffer);
        sb.append("</td></tr></table>");
        sb.append("</body>");
        sb.append("</html>");
        return sb.toString();
    }
}
