package com.cqprecheck.precheck.Service;

import org.apache.poi.POIXMLDocumentPart;
import org.apache.poi.openxml4j.opc.PackagePart;
import org.apache.xmlbeans.XmlOptions;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTComments;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CommentsDocument;

import javax.xml.namespace.QName;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.apache.poi.POIXMLTypeLoader.DEFAULT_XML_OPTIONS;

public class WordDocumentService {



    private String readFile(String filename){
        return "";
    }








    private static class MyXWPFCommentsDocument extends POIXMLDocumentPart {

        private CTComments comments;

        private MyXWPFCommentsDocument(PackagePart part) throws Exception {
            super(part);
            comments = CommentsDocument.Factory.newInstance().addNewComments();
        }

        private CTComments getComments() {
            return comments;
        }

        @Override
        protected void commit() throws IOException {
            XmlOptions xmlOptions = new XmlOptions(DEFAULT_XML_OPTIONS);
            xmlOptions.setSaveSyntheticDocumentElement(new QName(CTComments.type.getName().getNamespaceURI(), "comments"));
            PackagePart part = getPackagePart();
            OutputStream out = part.getOutputStream();
            comments.save(out, xmlOptions);
            out.close();
        }

    }
}
