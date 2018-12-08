package assignment;

import static org.junit.Assert.*;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import Model.Record;

public class MainActivityTest {
	
	// valid issues
	String validIssues = 
			"First name,Sur name,Issue count,Date of birth\n" +
			"Theo,Jansen,5,1978-01-02T00:00:00\n" +
			"Fiona,de Vries,7,1950-11-12T00:00:00\n" +
			"Petra,Boersma,1,2001-04-20T00:00:00";
	
	
	// invalid issues containing more fields
	String invalidIssues = 
			"First name,Sur name,Issue count,Date of birth\n" +
			"Theo,Jansen,5,wheytheextrafield,1978-01-02T00:00:00\n" +
			"Fiona,de Vries,7,1950-11-12T00:00:00\n" +
			"Petra,Boersma,1,2001-04-20T00:00:00";
	
	// test that we have 4 rows after splitting
	@Test
	public void testValidIssuesCount() {
		String[] issuesArray = validIssues.split("\\n");
		assertEquals(4, issuesArray.length);
	}
	
	// test that we have exactly 4 columns in each row
	@Test
	public void testValidIssuesRowCount() {
		String[] issuesArray = validIssues.split("\\n");
		for (String string : issuesArray) {
			String[] rowArray = string.split(",");
			assertEquals(4, rowArray.length);
		}
	}
	
	// test that we have exactly 4 columns in each row
	@Test
	public void testInvalidIssuesRowCount() {
		boolean flag = false;
		String[] invalid = invalidIssues.split("\\n");
		for (String string : invalid) {
			String[] rowArray = string.split(",");
			if (rowArray.length != 4) {
				flag = true;
			}
		}
		assertTrue(flag);
	}
	
	@Test
	public void testSortIssueCount() {
		String[] valid = validIssues.split("\\n");
		int[] issueCount = new int[valid.length - 1];
		int i = 0;
		for (String string : valid) {
			String[] rowArray = string.split(",");
			if (i == 0) {
				i++;
				continue;
			} else {
				issueCount[i-1] = Integer.parseInt(rowArray[2]);
			}
			i++;
		}
		
		Arrays.sort(issueCount);
		assertEquals(1, issueCount[0]);
	}
	
	String records =
			"194261,NL91RABO0315273637,Clothes from Jan Bakker,21.6,-41.83,-20.23\n" +
			"112806,NL27SNSB0917829871,Clothes for Willem Dekker,91.23,+15.57,106.8\n" +
			"183049,NL69ABNA0433647324,Clothes for Jan King,86.66,+44.5,131.16\n" +
			"183356,NL74ABNA0248990274,Subscription for Peter de Vries,92.98,-46.65,46.33\n" +
			"112806,NL69ABNA0433647324,Clothes for Richard de Vries,90.83,-10.91,79.92\n" +
			"112806,NL93ABNA0585619023,Tickets from Richard Bakker,102.12,+45.87,147.99\n" +
			"139524,NL43AEGO0773393871,Flowers from Jan Bakker,99.44,+41.23,140.67\n" +
			"179430,NL93ABNA0585619023,Clothes for Vincent Bakker,23.96,-27.43,-3.47\n" +
			"141223,NL93ABNA0585619023,Clothes from Erik Bakker,94.25,+41.6,135.85\n" +
			"195446,NL74ABNA0248990274,Flowers for Willem Dekker,26.32,+48.98,75.3\n";
	
	// test for duplicate transactions
	@Test
	public void testDuplicateTransactions() {
		HashSet<String> set = new HashSet<>();
		int count = 0;
		String[] recordArray = records.split("\\n");
		for (String string : recordArray) {
			String[] rowArray = string.split(",");
			if (set.contains(rowArray[0])) {
				count++;
			} else {
				set.add(rowArray[0]);
			}
		}
		
		assertEquals(2, count);
	}
	
	// test for invalid end balance
	@Test
	public void testInvalidEndBalance() {
		int count = 0;
		String[] recordArray = records.split("\\n");
		for (String string : recordArray) {
			String[] rowArray = string.split(",");
			if (Float.parseFloat(rowArray[5]) < 0.0) {
				count++;
			}
		}
		
		assertEquals(2, count);
	}
	
	String xmlRecords = 
			"<records>\n" + 
			"  <record reference=\"130498\">\n" + 
			"    <accountNumber>NL69ABNA0433647324</accountNumber>\n" + 
			"    <description>Tickets for Peter Theuß</description>\n" + 
			"    <startBalance>26.9</startBalance>\n" + 
			"    <mutation>-18.78</mutation>\n" + 
			"    <endBalance>8.12</endBalance>\n" + 
			"  </record>\n" + 
			"  <record reference=\"167875\">\n" + 
			"    <accountNumber>NL93ABNA0585619023</accountNumber>\n" + 
			"    <description>Tickets from Erik de Vries</description>\n" + 
			"    <startBalance>5429</startBalance>\n" + 
			"    <mutation>-939</mutation>\n" + 
			"    <endBalance>6368</endBalance>\n" + 
			"  </record>\n" + 
			"  <record reference=\"147674\">\n" + 
			"    <accountNumber>NL93ABNA0585619023</accountNumber>\n" + 
			"    <description>Subscription from Peter Dekker</description>\n" + 
			"    <startBalance>74.69</startBalance>\n" + 
			"    <mutation>-44.91</mutation>\n" + 
			"    <endBalance>-29.78</endBalance>\n" + 		// =============== invalid
			"  </record>\n" + 
			"  <record reference=\"130498\">\n" +    // ============== duplicate ================
			"    <accountNumber>NL27SNSB0917829871</accountNumber>\n" + 
			"    <description>Subscription from Peter Theuß</description>\n" + 
			"    <startBalance>70.42</startBalance>\n" + 
			"    <mutation>+34.42</mutation>\n" + 
			"    <endBalance>104.84</endBalance>\n" + 
			"  </record>\n" + 
			"  <record reference=\"169639\">\n" + 
			"    <accountNumber>NL43AEGO0773393871</accountNumber>\n" + 
			"    <description>Tickets for Rik de Vries</description>\n" + 
			"    <startBalance>31.78</startBalance>\n" + 
			"    <mutation>-6.14</mutation>\n" + 
			"    <endBalance>25.64</endBalance>\n" + 
			"  </record>\n" + 
			"  <record reference=\"167875\">\n" + 		// ============== duplicate ================
			"    <accountNumber>NL43AEGO0773393871</accountNumber>\n" + 
			"    <description>Flowers from Peter de Vries</description>\n" + 
			"    <startBalance>105.75</startBalance>\n" + 
			"    <mutation>-26.17</mutation>\n" + 
			"    <endBalance>79.58</endBalance>\n" + 
			"  </record>\n" + 
			"  <record reference=\"150438\">\n" + 
			"    <accountNumber>NL74ABNA0248990274</accountNumber>\n" + 
			"    <description>Tickets from Richard de Vries</description>\n" + 
			"    <startBalance>10.1</startBalance>\n" + 
			"    <mutation>-0.3</mutation>\n" + 
			"    <endBalance>9.8</endBalance>\n" + 
			"  </record>\n" + 
			"  <record reference=\"172833\">\n" + 
			"    <accountNumber>NL69ABNA0433647324</accountNumber>\n" + 
			"    <description>Tickets for Willem Theuß</description>\n" + 
			"    <startBalance>66.72</startBalance>\n" + 
			"    <mutation>-41.74</mutation>\n" + 
			"    <endBalance>-24.98</endBalance>\n" + 			// =============== invalid
			"  </record>\n" + 
			"  <record reference=\"165102\">\n" + 
			"    <accountNumber>NL93ABNA0585619023</accountNumber>\n" + 
			"    <description>Tickets for Rik Theuß</description>\n" + 
			"    <startBalance>3980</startBalance>\n" + 
			"    <mutation>+1000</mutation>\n" + 
			"    <endBalance>4981</endBalance>\n" + 
			"  </record>\n" + 
			"  <record reference=\"170148\">\n" + 
			"    <accountNumber>NL43AEGO0773393871</accountNumber>\n" + 
			"    <description>Flowers for Jan Theuß</description>\n" + 
			"    <startBalance>16.52</startBalance>\n" + 
			"    <mutation>+43.09</mutation>\n" + 
			"    <endBalance>59.61</endBalance>\n" + 
			"  </record>\n" + 
			"</records>";
	
	HashMap<String, Record> map = new HashMap<>();
    List<Record> duplicates = new ArrayList<>();
    List<Record> invalidBalance = new ArrayList<>();
	@Test
	public void testTrasactionXml() {
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
	        		
	        		Record record = new Record(reference, accountNumber, description, startBalance, mutation, endBalance);

	        		if (map.containsKey(reference)) {
	        			duplicates.add(map.get(reference));
	        		} else {
	        			map.put(reference, record);
	        		}

	        		if (endBalance < 0.0) {
	        			record.setInvalid("Invalid balance");
	        			invalidBalance.add(record);
	        		}
	        	}
	        }
	        
	    }
	    catch (Exception e) {
	    	System.out.println("exception happened");
	        
	    }
	}
	
	// test for duplicate transactions in xml record
	@Test
	public void testDuplicateTransactionXml() {
		testTrasactionXml();
		assertEquals(2, duplicates.size());
	}
	
	// test for invalid balance in xml record
	@Test
	public void testInvalidBalanceXml() {
		testTrasactionXml();
		assertEquals(2, invalidBalance.size());
	}
	
	
	String invalidXmlRecords = 
			"<records>\n" + 
			"  <record ref=\"130498\">\n" + 		// ============== invalid : it should "reference"
			"    <accountNumber>NL69ABNA0433647324</accountNumber>\n" + 
			"    <description>Tickets for Peter Theuß</description>\n" + 
			"    <startBalance>26.9</startBalance>\n" + 
			"    <mutation>-18.78</mutation>\n" + 
			"    <endBalance>8.12</endBalance>\n" + 
			"  </record>\n" + 
			"</records>";
	
	// test if xml data can be parsed or not
	@Test
	public void testInvalidXmlData() {
		HashMap<String, Record> map = new HashMap<>();
        List<Record> response = new ArrayList<>();
        int count = 0;
		try {
	        DocumentBuilderFactory dbf =
	            DocumentBuilderFactory.newInstance();
	        DocumentBuilder db = dbf.newDocumentBuilder();
	        InputSource is = new InputSource();
	        is.setCharacterStream(new StringReader(invalidXmlRecords));

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

	        		if (reference == null || reference.equalsIgnoreCase("") || accountNumber == null ||
	        				accountNumber.equalsIgnoreCase("") || description == null || description.equals("")) {
	        			count++;
	        		}
	        		
	        	}
	        }
	        
	    }
	    catch (Exception e) {
	    	System.out.println("exception happened");
	    }
		assertEquals(1, count);
	}
}
