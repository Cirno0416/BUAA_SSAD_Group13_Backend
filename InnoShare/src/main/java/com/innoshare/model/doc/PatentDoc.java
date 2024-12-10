package com.innoshare.model.doc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import java.util.Date;
import java.util.List;

@Data
@Document(indexName = "patents")
@AllArgsConstructor
@NoArgsConstructor
public class PatentDoc {
    @Id
    @Field(type = FieldType.Keyword)
    private String id;

    @Field(type = FieldType.Text, analyzer = "english")
    private String title;

    @Field(type = FieldType.Keyword)
    private String assignee;

    @Field(type = FieldType.Text, analyzer = "english")
    private String author;

    @Field(type = FieldType.Date)
    private Date creation_date;

    @Field(type = FieldType.Date)
    private Date publication_date;

    @Field(type = FieldType.Keyword, index = false)
    private String result_url;

    @Field(type = FieldType.Keyword, index = false)
    private String pdf_url;

    @Field(type = FieldType.Keyword)
    private List<String> classification;

}
