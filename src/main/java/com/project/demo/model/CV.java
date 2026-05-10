package com.project.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "cvs")
public class CV {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String fileName;
    private String fileType;

    private boolean activ;

    public boolean isActiv() {
        return activ;
    }

    public void setActiv(boolean activ) {
        this.activ = activ;
    }

    @Column(columnDefinition = "bytea")
    private byte[] data;

    @ManyToOne
    @JoinColumn(name = "id_candidat")
    private Candidat candidat;

    public Integer getId() {
        return id;
    }

    public void setId(Integer idCv) {
        this.id = idCv;
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
        return candidat;
    }

    public void setCandidat(Candidat candidat) {
        this.candidat = candidat;
    }

    public byte[] getData() { return data; }
    public void setData(byte[] data) { this.data = data; }

}
