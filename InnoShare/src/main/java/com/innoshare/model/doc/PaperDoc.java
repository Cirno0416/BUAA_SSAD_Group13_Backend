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
@Document(indexName = "papers")
@AllArgsConstructor
@NoArgsConstructor
public class PaperDoc {
    @Id
    @Field(type = FieldType.Keyword)
    private Integer paper_id;

    @Field(type = FieldType.Keyword, index = false)
    private Integer user_id;

    @Field(type = FieldType.Text, analyzer = "english", copyTo = "info")
    private String title;

    @Field(type = FieldType.Text, analyzer = "english")
    private String author;

    @Field(type = FieldType.Text, analyzer = "english", copyTo = "info")
    private String abstract_text;

    @Field(type = FieldType.Keyword)
    private List<String> subject1;

    @Field(type = FieldType.Keyword)
    private List<String> subject2;

    @Field(type = FieldType.Keyword, index = false)
    private String file_path;

    @Field(type = FieldType.Date)
    private Date published_at;

    @Field(type = FieldType.Date)
    private Date created_at;

    @Field(type = FieldType.Date)
    private Date updated_at;

    @Field(type = FieldType.Keyword, index = false)
    private String doi;

    @Field(type = FieldType.Keyword, index = false)
    private String download_url;

    @Field(type = FieldType.Text, analyzer = "english")
    private String info;
}
