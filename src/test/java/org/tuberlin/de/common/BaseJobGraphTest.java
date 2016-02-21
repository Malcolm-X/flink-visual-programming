package org.tuberlin.de.common;

import org.junit.Before;
import org.junit.Test;
import org.tuberlin.de.common.base.BaseDataSinkPrint;
import org.tuberlin.de.common.base.BaseDataSourceComponentText;
import org.tuberlin.de.common.base.BaseGroupBy;
import org.tuberlin.de.common.base.BaseJobGraph;
import org.tuberlin.de.common.base.BaseTransformationAggregate;
import org.tuberlin.de.common.base.BaseTransformationFlatMap;
import org.tuberlin.de.common.codegenerator.CodeGenerator;
import org.tuberlin.de.common.model.Constants;
import org.tuberlin.de.common.model.interfaces.CompilationUnitComponent;
import org.tuberlin.de.common.model.interfaces.JobGraph;
import org.tuberlin.de.common.model.interfaces.datasink.DataSink;
import org.tuberlin.de.common.model.interfaces.datasources.DataSource;
import org.tuberlin.de.common.model.interfaces.transorfmation.TransformationAggregate;
import org.tuberlin.de.common.model.interfaces.transorfmation.TransformationFlatMap;
import org.tuberlin.de.common.model.interfaces.transorfmation.TransformationGroupBy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BaseJobGraphTest {



  /**  public static final String COMPONENT_TYPE_JSON = "COMPONENT_TYPE";
    public static final String TRANSFORMATION_TYPE = "TRANSFORMATION_TYPE";
    public static final String DATA_SOURCE_TYPE = "DATA_SOURCE_TYPE";
    public static final String DATA_SINK_TYPE = "DATA_SINK_TYPE";
    public static final String JOB_COMPONENT_IMPORTS_JSON = "JOB_COMPONENT_IMPORTS_JSON";
    public static final String COMPONENT_JOB_SOURCE_JSON = "COMPONENT_JOB_SOURCE_JSON";
    public static final String COMPONENT_SOURCE_JSON = "COMPONENT_SOURCE_JSON";
    public static final String JOB_COMPONENT_CHILDREN = "COMPONENT_CHILDREN_JSON";
    public static final String JOB_COMPONENT_PARENT = "COMPONENT_PARENT_JSON";
    public static final String JOB_COMPONENT_INPUT_TYPE = "COMPONENT_INPUT_TYPE_JSON";
    public static final String JOB_COMPONENT_OUTPUT_TYPE = "COMPONENT_OUTPUT_TYPE_JSON";
    public static final String JOB_COMPOENT_KEY = "COMPONENT_KEY_JSON";
    public static final String COMPONENT_PATH_JSON = "COMPONENT_CLASS_JSON"; */



    JobGraph jobGraph;

    @Before
    public void setUp() throws Exception {

        Map<String, Object> jGraphParamters = new HashMap<String, Object>();
        Map<String, Object> dSourceCompParameters = new HashMap<String, Object>();
        Map<String, Object> fMapPrarameters = new HashMap<String, Object>();
        Map<String, Object> gByParameters = new HashMap<String, Object>();
        Map<String, Object> aggParameters = new HashMap<String, Object>();

        ArrayList<String> alParents, alChildren;



        //DataSink
        Map<String, Object> dSinkParameters = new HashMap<String, Object>();
        String componentKeyDataSourceTest = "dataSourceTest";
        String componentKeyFlatMapTest = "flatMapTest";
        String componentKeyGroupBy = "groupByTest";
        String componentKeyAggregate = "aggregateTest";
        String componentKeyDataSink = "dataSinkTest";


        jGraphParamters.put(Constants.JOB_COMPONENT_IMPORTS_JSON, "test.job.graph.import");

        //DataSourceParam

        alChildren= new ArrayList<String>();
        alChildren.add(componentKeyFlatMapTest);
        dSourceCompParameters.put(Constants.JOB_COMPONENT_CHILDREN, alChildren);
        dSourceCompParameters.put(Constants.JOB_COMPONENT_PARENT, null);
        dSourceCompParameters.put(Constants.JOB_COMPONENT_INPUT_TYPE, "String");
        dSourceCompParameters.put(Constants.JOB_COMPONENT_OUTPUT_TYPE, "String");
        dSourceCompParameters.put(Constants.JOB_COMPOENT_KEY, componentKeyDataSourceTest);
        dSourceCompParameters.put(Constants.COMPONENT_PATH_JSON, "/a/path");
        //TODO COMPONENT_JOB_SOURCE_JSON

        //FlatMapParam
        alChildren= new ArrayList<String>();
        alChildren.add(componentKeyGroupBy);
        alParents= new ArrayList<String>();
        alParents.add(componentKeyDataSourceTest);
        fMapPrarameters.put(Constants.JOB_COMPONENT_CHILDREN, alChildren);
        fMapPrarameters.put(Constants.JOB_COMPONENT_PARENT, alParents);
        fMapPrarameters.put(CompilationUnitComponent.PACKAGE_NAME_KEY, "test.compilation.unit.package");
        fMapPrarameters.put(CompilationUnitComponent.FUNCTION_NAME_KEY, "LineSplitter");
        fMapPrarameters.put(CompilationUnitComponent.COMPONENT_SOURCE_JSON,    "public class LineSplitter implements FlatMapFunction<String, Tuple2<String, Integer>> {\n" +
                "@Override+\n" +
                "public void flatMap(String value, Collector<Tuple2<String, Integer>> out) {+\n" +
                "// normalize and split the line into words+\n" +
                "String[] tokens = value.toLowerCase().split(\"\\\\W+\");\n" +
                "// emit the pairs\n" +
                "for (String token : tokens) {\n" +
                "if (token.length() > 0) {\n" +
                "out.collect(new Tuple2<String, Integer>(token, 1));\n" +
                "}\n" +
                "}\n" +
                "}\n" +
                "}");
        fMapPrarameters.put(Constants.JOB_COMPONENT_INPUT_TYPE, "Tuple2<String, Integer>");
        fMapPrarameters.put(Constants.JOB_COMPONENT_OUTPUT_TYPE, "Tuple2<String, Integer>");
        fMapPrarameters.put(Constants.JOB_COMPOENT_KEY, componentKeyFlatMapTest);
        fMapPrarameters.put(Constants.COMPONENT_PATH_JSON, "/a/path");
        //TODO COMPONENT_JOB_SOURCE_JSON


        //GroupByParam
        //TODO add field to GoupByComponent interface
        alChildren= new ArrayList<String>();
        alChildren.add(componentKeyAggregate);
        alParents= new ArrayList<String>();
        alParents.add(componentKeyFlatMapTest);
        gByParameters.put(Constants.JOB_COMPONENT_CHILDREN, alChildren);
        gByParameters.put(Constants.JOB_COMPONENT_PARENT, alParents);
        gByParameters.put(Constants.JOB_COMPONENT_INPUT_TYPE, "Tuple2<String, Integer>");
        gByParameters.put(Constants.JOB_COMPONENT_OUTPUT_TYPE, "Tuple2<String, Integer>");
        gByParameters.put(Constants.JOB_COMPOENT_KEY, componentKeyGroupBy);
        gByParameters.put(TransformationGroupBy.COMPONENT_GROUP_BY_FIELD, "0");
        //TODO COMPONENT_JOB_SOURCE_JSON

        //AggregateParam
        //TODO sum und 1
        alChildren= new ArrayList<String>();
        alChildren.add(componentKeyDataSink);
        alParents= new ArrayList<String>();
        alParents.add(componentKeyGroupBy);
        aggParameters.put(Constants.JOB_COMPONENT_CHILDREN, alChildren);
        aggParameters.put(Constants.JOB_COMPONENT_PARENT, alParents);
        aggParameters.put(Constants.JOB_COMPONENT_INPUT_TYPE, "Tuple2<String, Integer>");
        aggParameters.put(Constants.JOB_COMPONENT_OUTPUT_TYPE, "Tuple2<String, Integer>");
        aggParameters.put(Constants.JOB_COMPOENT_KEY, componentKeyAggregate);
        aggParameters.put(TransformationAggregate.FUNCTION_KEY, "SUM");
        aggParameters.put(TransformationAggregate.FIELD_KEY, "1");
        //TODO COMPONENT_JOB_SOURCE_JSON

        //DatSinkPara

        alParents= new ArrayList<String>();
        alParents.add(componentKeyAggregate);
        dSinkParameters.put(Constants.JOB_COMPONENT_CHILDREN, null);
        dSinkParameters.put(Constants.JOB_COMPONENT_PARENT, alParents);
        dSinkParameters.put(Constants.JOB_COMPONENT_INPUT_TYPE, aggParameters.get(Constants.JOB_COMPONENT_OUTPUT_TYPE));
        dSinkParameters.put(Constants.JOB_COMPONENT_OUTPUT_TYPE, null);
        dSinkParameters.put(Constants.JOB_COMPOENT_KEY, componentKeyDataSink);


        //JobGraph
        jobGraph = new BaseJobGraph("testkey", "testname", null, jGraphParamters);

        aggParameters.put(TransformationAggregate.FIELD_KEY, 1);
        aggParameters.put(TransformationAggregate.FUNCTION_KEY, TransformationAggregate.FUNCTION_TYPES.SUM);
        TransformationAggregate aggregateComponent = new BaseTransformationAggregate(jobGraph, aggParameters);

        //DataSource
        DataSource dataSourceComponent = new BaseDataSourceComponentText(jobGraph,  dSourceCompParameters);

        //FlatMap
        TransformationFlatMap transformationFlatMapComponent = new BaseTransformationFlatMap(jobGraph, fMapPrarameters);


        //GroupBy
        TransformationGroupBy transformationGroupByComponent = new BaseGroupBy(jobGraph, gByParameters);

        //Aggregate
        TransformationAggregate transformationAggregate = new BaseTransformationAggregate(jobGraph, aggParameters);

        //DataSink
        DataSink dataSink = new BaseDataSinkPrint(jobGraph, dSinkParameters);



        //JobGraphParam

        //TODO COMPONENT_JOB_SOURCE_JSON



        jobGraph.addComponent(dataSourceComponent);
        jobGraph.addComponent(transformationFlatMapComponent);
        jobGraph.addComponent(transformationGroupByComponent);
        jobGraph.addComponent(transformationAggregate);
        jobGraph.addComponent(dataSink);



    }

    @Test
    public void testGetJobSource() throws Exception {
        CodeGenerator baseCodeGenerator = new CodeGenerator();
//        baseCodeGenerator.generateCode(jobGraph);

        System.out.println(org.tuberlin.de.common.codegenerator.CodeGenerator.generateCode(jobGraph));
    }
}
