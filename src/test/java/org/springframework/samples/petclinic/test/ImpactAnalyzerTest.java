package org.springframework.samples.petclinic.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;


import org.springframework.samples.petclinic.ImpactAnalyzer.connect.Connect;
import org.springframework.samples.petclinic.ImpactAnalyzer.models.HTMLElement;
import org.springframework.samples.petclinic.ImpactAnalyzer.models.HTMLJSONFileParser;
import org.springframework.samples.petclinic.ImpactAnalyzer.models.TestFileParser;
import org.springframework.samples.petclinic.test.DependencyAnalyzer;

public class ImpactAnalyzerTest {
	private static final String FILE_PATH = "./src/test/java/org/springframework/samples/petclinic/test/SeleniumTest.java";
	private static final String JSON_PATH = "./json/aHR0cDovL2xvY2FsaG9zdDo4MDgwL293bmVycy8xL3BldHMvbmV3.json";



	@org.junit.Test
	public void Test() throws FileNotFoundException {
		Connect.connect();
		try {
			 File folder = new File("./json");
			 File[] listOfFiles = folder.listFiles();

			DependencyAnalyzer dependencyAnalyzer = new DependencyAnalyzer();

			CompilationUnit testCompUnit = StaticJavaParser.parse(new File(FILE_PATH));
			var testElements = TestFileParser.parse(FILE_PATH);
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			Connect.addVersion(timestamp);

			List<HTMLElement> htmlElements = new ArrayList<>();



			for (int i = 0; i < listOfFiles.length ; i++) {
				String currentPage = listOfFiles[i].toString();
				HTMLJSONFileParser htmlJsonFileParser = new HTMLJSONFileParser(currentPage);
				htmlJsonFileParser.setElements();
				htmlElements.addAll(htmlJsonFileParser.getHTMLElements());




			}
			dependencyAnalyzer.dependencyAnalyzer(htmlElements, testElements);



		}catch(Exception e) {
			e.printStackTrace();
		}
		Connect.close();
	}
}
