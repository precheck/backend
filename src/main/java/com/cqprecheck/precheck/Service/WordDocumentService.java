package com.cqprecheck.precheck.Service;


import com.cqprecheck.precheck.Models.EntityHolder;
import com.cqprecheck.precheck.Models.Organization;
import com.cqprecheck.precheck.Repositories.EntityRepository;
import com.cqprecheck.precheck.Storage.StorageService;
import com.google.cloud.language.v1beta2.Entity;
import com.google.cloud.language.v1beta2.EntityMention;
import org.apache.poi.POIXMLDocumentPart;
import org.apache.poi.openxml4j.opc.PackagePart;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.xmlbeans.XmlOptions;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTComments;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CommentsDocument;

import javax.xml.namespace.QName;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.apache.poi.POIXMLTypeLoader.DEFAULT_XML_OPTIONS;


public class WordDocumentService {

    private final StorageService storageService;
    private EntityRepository entityRepository;
    private Organization organization;

    private String location = "upload-dir";
    private final Path rootLocation;

    public WordDocumentService(StorageService storageService, EntityRepository entityRepository, Organization organization) {
        this.storageService = storageService;
        this.entityRepository = entityRepository;
        this.organization = organization;
        this.rootLocation = Paths.get(location);
    }

    public void processFile(String filename){
        try {
            XWPFDocument docx = new XWPFDocument(new FileInputStream(storageService.load(filename).toFile()));
            List<String> paragraphs = new ArrayList<>();
            List<Integer> paragraphLengths = new ArrayList<>();
            StringBuilder googleDocumentString = new StringBuilder();
            GoogleApiService googleApiService = new GoogleApiService();
            List<XWPFParagraph> paragraphList = docx.getParagraphs();
            for(XWPFParagraph paragraph : paragraphList){
                paragraphs.add(paragraph.getText());
                paragraphLengths.add(paragraph.getText().length());
                googleDocumentString.append(paragraph.getText());
            }
            List<Entity> entities = googleApiService.analyzeDocument(googleDocumentString.toString());
            List<com.cqprecheck.precheck.Models.Entity> parsedEntites = entities.stream()
                    .filter((Entity e) -> e.getMentionsList()
                            .stream()
                            .filter((m) ->m.getType() == EntityMention.Type.PROPER).collect(Collectors.toList()).size() > 0)
                    .map(com.cqprecheck.precheck.Models.Entity::new)
                    .collect(Collectors.toList());

            Map<String, EntityHolder> entityMap = new HashMap<>();
            for(com.cqprecheck.precheck.Models.Entity entity : parsedEntites){
                EntityHolder holder = new EntityHolder(entityRepository.findByNameAndOrganization(entity.getName(), organization), entity.getLocations());
                entityMap.put(entity.getName(), holder);
            }

            XWPFDocument document = new XWPFDocument();

            for(String p : paragraphs){
                //create Paragraph
                XWPFParagraph paragraph = document.createParagraph();
                XWPFRun run = paragraph.createRun();
                run.setText(p);
            }

            XWPFParagraph blankParagraph = document.createParagraph();
            XWPFRun blankRun = blankParagraph.createRun();
            blankRun.setText("\n\n\n");


            XWPFParagraph paragraph = document.createParagraph();

            XWPFRun run = paragraph.createRun();
            run.setText("\n\nPossible CQs:");

            for (Map.Entry<String,EntityHolder> pair : entityMap.entrySet()){
                List<com.cqprecheck.precheck.Models.Entity> entitiesInSet = pair.getValue().getEntities();
                System.out.println(pair.getKey());
                if(entitiesInSet.size() > 0){
                    for(com.cqprecheck.precheck.Models.Entity entity : entitiesInSet){
                        XWPFParagraph innerParagraph = document.createParagraph();
                        XWPFRun innerRun = innerParagraph.createRun();
                        innerRun.setText(entity.getName() + ": " + entity.getUrl());
                    }
                }else{
                    XWPFParagraph innerParagraph = document.createParagraph();
                    XWPFRun innerRun = innerParagraph.createRun();
                    innerRun.setText(pair.getKey() + ": Not found in system");
                }
            }

            FileOutputStream out = new FileOutputStream(this.rootLocation.resolve("test-cq.docx").toFile());
            document.write(out);
            out.close();
            System.out.println("Document written successfully");



        } catch (IOException e) {
            e.printStackTrace();
        }
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
