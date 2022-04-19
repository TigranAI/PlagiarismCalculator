package ru.tigran.PlagiarismCalculator.database.entity;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "solution_data")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class SolutionData {
    @Id
    @Type(type="pg-uuid")
    @Column(name = "id", nullable = false)
    private UUID id;

    @Type(type="pg-uuid")
    private UUID taskId;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private List<Long> astHashList;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getTaskId() {
        return taskId;
    }

    public void setTaskId(UUID taskId) {
        this.taskId = taskId;
    }

    public List<Long> getAstHashList() {
        return astHashList;
    }

    public void setAstHashList(List<Long> astHashList) {
        this.astHashList = astHashList;
    }
}