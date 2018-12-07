package assignment;

import org.apache.tomcat.util.http.fileupload.FileUpload;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import Model.Record;

import java.util.*;

import javax.xml.parsers.*;
import org.xml.sax.InputSource;
import org.w3c.dom.*;
import java.io.*;

@SpringBootApplication
@RestController
public class MainActivity {
	
	MainActivity() {
	}

	@GetMapping(path="/backend-csv/upload")
	public Response upload(@RequestParam("limit") String limit) {
		HashMap<String, Record> map = new HashMap<>();
		List<Record> response = new ArrayList<>();
		String[] array = limit.split("\\n");
		for (int i = 1; i < array.length; i++) {
			String[] elements = array[i].split(",");
			// issue count file should have only 6 columns
			if (elements.length == 6) {
				Record record = new Record((elements[0]), elements[1], elements[2],
						Float.parseFloat(elements[3]), Float.parseFloat(elements[4]), Float.parseFloat(elements[5]));
				String reference = String.valueOf(elements[0]);
				
				// if the transaction already exists in map then its a duplicate entry
				if (map.containsKey(reference)) {
					map.get(reference).setInvalid("Duplicate Transaction");
					response.add(map.get(reference));
					record.setInvalid("Duplicate transaction");
					response.add(record);

				} else {
					map.put(reference, record);
				}

				// if the balance is negative then its invalid transaction
				if (Float.parseFloat(elements[5]) < 0.0) {
					record.setInvalid("Invalid balance");
					response.add(record);
					System.out.println(elements[0]);
				}
			} else {
				return new Response("Fail", response);
			}
		}
		
		return new Response("Done", response);
	}
	
	@GetMapping(path="/backend-xml/upload")
	public Response validateXml(@RequestParam("limit") String limit) {
		String xmlRecords = limit;
		HashMap<String, Record> map = new HashMap<>();
        List<Record> response = new ArrayList<>();
        
		try {
	        DocumentBuilderFactory dbf =
	            DocumentBuilderFactory.newInstance();
	        DocumentBuilder db = dbf.newDocumentBuilder();
	        InputSource is = new InputSource();
	        is.setCharacterStream(new StringReader(xmlRecords));

	        Document doc = db.parse(is);
	        NodeList nodes = doc.getElementsByTagName("record");

	        NodeList nodeList = doc.getDocumentElement().getChildNodes();
	        for (int i = 0; i < nodeList.getLength(); i++) {
	        	Node node = nodeList.item(i);
	        	if (node.getNodeType() == Node.ELEMENT_NODE) {
	        		Element element = (Element) node;
	        		
	        		String reference = (element.getAttribute("reference"));
	        		String accountNumber = element.getElementsByTagName("accountNumber").item(0).getTextContent();
	        		String description = element.getElementsByTagName("description").item(0).getTextContent();
	        		float startBalance = Float.parseFloat(element.getElementsByTagName("startBalance").item(0).getTextContent());
	        		float mutation = Float.parseFloat(element.getElementsByTagName("mutation").item(0).getTextContent());
	        		float endBalance = Float.parseFloat(element.getElementsByTagName("endBalance").item(0).getTextContent());
	        		
	        		if (reference == null || reference.equalsIgnoreCase("") || accountNumber == null ||
	        				accountNumber.equalsIgnoreCase("") || description == null || description.equals("")) {
	        			System.out.println("Invalid data");
	        			return new Response("Fail", response);
	        		}
	        		
	        		Record record = new Record(reference, accountNumber, description, startBalance, mutation, endBalance);
	        		System.out.println(record);
	        		if (map.containsKey(reference)) {
	        			map.get(reference).setInvalid("Duplicate transaction");
	        			record.setInvalid("Duplicate transaction");
	        			response.add(map.get(reference));
	        			response.add(record);
	        			System.out.println("Duplicate transaction " + record.getTrasactionReference());
	        		} else {
	        			map.put(reference, record);
	        		}
	        		response.add(record);
	        		if (endBalance < 0.0) {
	        			record.setInvalid("Invalid balance");
	        			response.add(record);
	        			System.out.println("invalid balance " + record.getTrasactionReference());
	        		}
	        	}
	        }
	        
	    }
	    catch (Exception e) {
	    	System.out.println("exception happened");
	        return new Response("Fail", response);
	    }
		
		return new Response("Done", response);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SpringApplication.run(MainActivity.class, args);

	}

}
