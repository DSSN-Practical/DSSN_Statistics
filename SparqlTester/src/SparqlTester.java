import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.util.FileManager;
import org.apache.jena.query.*;
import org.apache.log4j.varia.NullAppender;

public class SparqlTester {
	public static void main (String args[]) {
		if(args == null || args.length < 3){
			throw new IllegalArgumentException("Please use java -jar cubeFile observationsFile queryFile");
		}
		String cubeFile = args[0];
		String observationsFile = args[1];
		String queryFile = args[2];
    	//deactivate log4j logging
		org.apache.log4j.BasicConfigurator.configure(new NullAppender());
        // create an empty model
        Model model = ModelFactory.createDefaultModel();
        InputStream cube = FileManager.get().open(cubeFile);
        if (cube == null) {
            throw new IllegalArgumentException( "CubeFile not found");
        }        
        // read the RDF/XML file
        model.read(cube, "");
        InputStream observations = FileManager.get().open(observationsFile);
        if (observations == null) {
            throw new IllegalArgumentException( "Observations File not found");
        }  
        model.read(observations, "");
        //read in query file
        String queryString = "";
		try {
			queryString = new Scanner(new File(queryFile)).useDelimiter("\\Z").next();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
        //querying all full names
        Query query = QueryFactory.create(queryString);
        try (QueryExecution qexec = QueryExecutionFactory.create(query, model)){
        	ResultSet results = qexec.execSelect();
        	//print results
        	ResultSetFormatter.out(System.out, results, query);
        	//System.out.println(results.getResultVars());
        	//Model answerModel = RDFOutput.encodeAsModel(results);
        	//answerModel.write(System.out);
        	
        	qexec.close();
        	//answerModel.write(System.out);
        }
	}
}