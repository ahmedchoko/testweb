package com.wevioo.pi.domain.entity.config;


import lombok.Getter;
import lombok.Setter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Email notification template
 */
@Entity
@Table(name = "PI031T_EMAIL_TEMPLATE")
@Getter
@Setter
public class EmailTemplate {


    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PI030T_ID", updatable = false, nullable = false)
    private Long id;

    /**
     * label
     */
    @Column(name = "PI030T_LABEL", updatable = false, nullable = false)
    private String label;

    /**
     * objectFR
     */
    @Column(name = "PI030T_OBJECT_FR", updatable = false, nullable = false)
    private String objectFR;

    /**
     * objectEN
     */
    @Column(name = "PI030T_OBJECT_EN", updatable = false, nullable = false)
    private String objectEN;

    /**
     * contentHtmlFr
     */
    @Lob
    @Column(name = "PI030T_CONTENT_HTML_FR", updatable = false, nullable = false, columnDefinition = "CLOB")
    private String contentHtmlFr;

    /**
     * contentHtmlEn
     */
    @Lob
    @Column(name = "PI030T_CONTENT_HTML_EN", updatable = false, nullable = false, columnDefinition = "CLOB")
    private String contentHtmlEn;

    /**
     * contentTextFr
     */
    @Column(name = "PI030T_CONTENT_TEXT_FR", updatable = false)
    private String contentTextFr;

    /**
     * contentTextEn
     */
    @Column(name = "PI030T_CONTENT_TEXT_EN", updatable = false)
    private String contentTextEn;


}
