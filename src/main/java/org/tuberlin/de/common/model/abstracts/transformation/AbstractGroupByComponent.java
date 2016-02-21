package org.tuberlin.de.common.model.abstracts.transformation;

import org.tuberlin.de.common.model.interfaces.JobGraph;
import org.tuberlin.de.common.model.interfaces.transorfmation.TransformationGroupBy;
import org.tuberlin.de.common.model.types.ComponentTypes;

import java.util.Map;

/**
 * Created by oxid on 1/4/16.
 */
public abstract class AbstractGroupByComponent implements TransformationGroupBy{

    // TODO Only copied from AbstractJobComponent to make test not fail
    protected Map<String, Object> parameters;
    protected boolean initialized = false;
    protected JobGraph jobGraph;

    @Override
    public boolean init(JobGraph jobGraph, Map<String, Object> parameters) throws IllegalArgumentException {
        if(parameters == null || jobGraph == null) throw new IllegalArgumentException("Arguments must not be null!");
        this.parameters = parameters;
        this.initialized = true;
        this.jobGraph = jobGraph;
        return true;
    }

    @Override
    public String getJobSource() throws IllegalStateException {
        return null;
    }

    @Override
    public String[] getJobImports() throws IllegalStateException {
        return new String[0];
    }

    @Override
    public boolean verify() throws IllegalStateException {
        return this.initialized;
    }

    @Override
    public ComponentTypes getType() throws IllegalStateException {
        return null;
    }

    @Override
    public Object getParameter(String key) throws IllegalStateException {
        return null;
    }
}
