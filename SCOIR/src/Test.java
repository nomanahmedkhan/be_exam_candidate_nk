import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.*;

public class Test{
	
	/**
	 * Errors to be written in the error-directory in case of any errors in the input csv file
	 */
	static final String error1 = "ID must not be empty and must be 8 characters long";
	static final String error2 = "First name must not be empty and must be less than 15 characters";
	static final String error3 = "Middle name must be less than 15 characters";
	static final String error4 = "Last name must not be empty and must be less than 15 characters";
	static final String error5 = "phone number must match the pattern ###-###-####";
	static final String error6 = "phone number must not be empty";
	
	public static void main (String[] args) {
		
		while(true) { //to make sure the file application keeps running
			
			//the list will contain names of the processed files
			ArrayList<File> processedFiles = new ArrayList<File>();
		
		/**
		 * to get all the files from directory and store it in an array	
		 */
		File dir = new File("C:\\Users\\noman\\Desktop\\input-directory");
		  File[] directoryListing = dir.listFiles();
		  
		  if (directoryListing != null) { 
			  
			  
			/**
			 * for each file in the directory process the files   
			 */
		    for (File child : directoryListing) {
		    	
		    	//if the file hasnt already been processed 
		    	if(!processedFiles.contains(child)) {
		    		
		    	Pattern pattern = Pattern.compile(",");
		    	
		    	//read the file 
				try (LineNumberReader in = new LineNumberReader(new FileReader(child));){
					 
					//create an error file 
					try(BufferedWriter errorFile = new BufferedWriter(new FileWriter(new File("C:\\Users\\noman\\Desktop\\error-directory\\"+child.getName())))){
						 
						 //headers for the error csv file
						 try {
								errorFile.write("LINE_NUM,ERROR_MSG");
							} catch (IOException e) {
							
								e.printStackTrace();
							}
						 
						 
					List<Person> person = in
				        .lines()
				        .skip(1)
				        .map(line -> {
				                String[] x = pattern.split(line);
				                /**
				                 * Error checks that must be done on the csv files
				                 * if there are errors write them to the file created in the error directory
				                 */
				                if(x[0].isEmpty() || x[0].length() != 8) {
				                	printError(errorFile,error1,in.getLineNumber());
				                }if(x[1].isEmpty() || x[1].length() > 15) {
				                	printError(errorFile,error2,in.getLineNumber());
				                }if(x[2].length() > 15) {
				                	printError(errorFile,error3,in.getLineNumber());
				                }if(x[3].isEmpty() || x[3].length() > 15) {
				                	printError(errorFile,error4,in.getLineNumber());
				                }if(!x[4].matches("(?:\\d{3}-){2}\\d{4}")) {
				                	printError(errorFile,error5,in.getLineNumber());
				                }if(x[4].trim().isEmpty()) {
				                	printError(errorFile,error6,in.getLineNumber());
				                }
				                
				                //return an object that must be written to the JSON file
				                return new Person(Integer.parseInt(x[0]),
				                                  x[1],
				                                  x[2],
				                                  x[3],
				                                  x[4]);
				                
				            })
				        .collect(Collectors.toList());
					
					/**
					 * Write the object derived from the csv file to  Json file
					 */
				    ObjectMapper mapper = new ObjectMapper();
				    mapper.enable(SerializationFeature.INDENT_OUTPUT);
				    mapper.writeValue(new File("C:\\Users\\noman\\Desktop\\output-directory\\"+child.getName().replaceFirst("\\.csv$","")+".json"), person);
				    
				    //add the file to the processed ArrayList
				    processedFiles.add(child);
				    
				    in.close();
				    
				    /**
				     * Delete the file once it has been processed from the input directory
				     */
				    if(child.delete()) {
				    	System.out.println("File is deleted");
				    }else {
				    	System.out.println("File could not be deleted");
				    }
				}catch(IOException io){
					io.printStackTrace();
					
				}}catch(IOException io) {
                	io.printStackTrace();
                }
				
		    	
		    }
		    }
		  
		  
		  } else {
		  
			  System.out.println("Error");
			  
		  }
		  
		}
		
	}
	
	/**
	 * This method takes the parameters listed below and writes the errors to the 
	 * error file that is created.
	 * 
	 * @param errorFile
	 * @param error
	 * @param LineNumber
	 * @void no return value, just writes the errors to the csv file in error-directory
	 */
	public static void printError(BufferedWriter errorFile, String error, int LineNumber) {
		
		try {
			
		errorFile.newLine();
		errorFile.write(LineNumber+","+error);
		
		}catch(IOException io) {
			io.printStackTrace();
		}
	}
}