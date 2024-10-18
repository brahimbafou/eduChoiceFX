package com.university.model;

import javax.persistence.*;

@Entity
@Table(name = "choices")
public class Choice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "choice_id")
    private Long choiceId;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "specialty_id")
    private Specialty specialty;

    @Column(name = "choice_order")
    private int choiceOrder;

    public String getStudentFullName() {
        return student != null ? student.getFirstName() + " " + student.getLastName() : "";
    }
    public long getStudentId() {
        return student != null ? student.getStudentId() : 0;
    }
    public String getSpecialtyName() {
        return specialty != null ? specialty.getSpecialtyName() : "";
    }

    public Choice() {
    }

    public Choice(Student student, Specialty specialty, int choiceOrder) {
        this.student = student;
        this.specialty = specialty;
        this.choiceOrder = choiceOrder;
    }

    public Long getChoiceId() {
        return choiceId;
    }

    public void setChoiceId(Long choiceId) {
        this.choiceId = choiceId;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Specialty getSpecialty() {
        return specialty;
    }

    public void setSpecialty(Specialty specialty) {
        this.specialty = specialty;
    }

    public int getChoiceOrder() {
        return choiceOrder;
    }

    public void setChoiceOrder(int choiceOrder) {
        this.choiceOrder = choiceOrder;
    }
}
