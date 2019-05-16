package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Table(name = "Likes")
@NamedQueries({
    @NamedQuery(
            name = "checkHasLiked",//指定された社員と日報のいいね情報が存在するか
            query = "SELECT l FROM Like AS l WHERE l.employee = :employee AND l.report = :report"
            ),
    @NamedQuery(
            name = "getLikesCount",//1つの日報についているいいねの数
            query = "SELECT COUNT(l) FROM Like AS l WHERE l.report = :report"
            )
})

@Entity
public class Like {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "report_id", nullable = false)
    private Report report;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }

}
