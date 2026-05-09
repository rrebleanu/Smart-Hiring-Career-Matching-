package com.project.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "cvs")
public class CV {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCv;

    private String fileName;
    private String fileType;

    @Lob
    @Column(name = "data", columnDefinition = "LONGBLOB")
    private byte[] data;

    @ManyToOne
    @JoinColumn(name = "id_candidat")
    private Candidat candidate;

    @OneToOne(mappedBy = "cv")

    public Integer getIdCv() {
        return idCv;
    }

    public void setIdCv(Integer idCv) {
        this.idCv = idCv;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() { return fileType; }
    public void setFileType(String fileType) { this.fileType = fileType; }

    public Candidat getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidat candidate) {
        this.candidate = candidate;
    }

    public byte[] getData() { return data; }
    public void setData(byte[] data) { this.data = data; }

}
