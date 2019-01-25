package com.epam.training.sportsbetting.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "result")
public class ResultEntity extends AbstractEntity {

    @OneToMany(mappedBy = "resultId")
    private List<OutcomeEntity> outcomes;
}
