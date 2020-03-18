package model;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * (c) JBay Solutions 2010-2012 All rights reserved.
 * <p>
 * User: RuiP
 * Date: 28-01-2020
 * Time: 12:33
 */
@Entity
public class EpochResult extends Model {

    @Id
    public long epochResultId;

    public int epochNumber;

    public double score;

    public double f1;

    public double accuracy;

    public double prec;

    public double recall;

    @ManyToOne
    public TrainingStrategyResult training;

    private static final Finder<Long, EpochResult> find = new Finder<>(EpochResult.class);

    public static EpochResult createEpochResult(
            TrainingStrategyResult training,
            int epochNumber,
            double score,
            double f1,
            double accuracy,
            double precision,
            double recall
    ) {
        EpochResult res = new EpochResult();
        res.training = training;
        res.epochNumber = epochNumber;
        res.score = score;
        res.f1 = f1;
        res.accuracy = accuracy;
        res.prec = precision;
        res.recall = recall;
        res.save();
        return res;
    }

}
