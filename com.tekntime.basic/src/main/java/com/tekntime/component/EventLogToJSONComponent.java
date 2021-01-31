package com.tekntime.component;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.tekntime.model.EventLog;
import com.tekntime.model.LoginErrorTraining;
import com.tekntime.repository.EventLogRepository;

@Component
public class EventLogToJSONComponent {
	
	Logger logger = LoggerFactory.getLogger(EventLogToJSONComponent.class);
	
	@Autowired
	 EventLogRepository repository;
	
	@Value("${log.file.dir}")
	private String LOGFILEDIR;
	
	@Value("${json.out.dir}")
	private String JSONOUTPUTDIR;
	
	public  void loadEvent () {
		
		File path = new File(LOGFILEDIR); 
		for(File f: path.listFiles()) { 
			if(f.isFile()) {
					try {
						
						BufferedReader userLog = new BufferedReader (new FileReader (f.getAbsolutePath()));
						String logContent = userLog.readLine();
						int i = 0;
						File file = extractedLog();
						
						while (logContent != null) {
							if ("".equals(logContent.trim())) {
								logContent = userLog.readLine();
								continue;
							}
							
							System.out.println("line:" + i+":" + logContent);
							
							//split logic
							EventLog eventLog = new EventLog();
						
							String line = logContent;
							String[] parts = line.split(" ");
							if (parts.length <= 5) {  //Do we need to change the value of the length method here to >=5 perhaps it would handle any unknown line of log that might be appended wrongly?
								logContent = userLog.readLine();
								continue;
							}
				            String date =parts[0];
				            String time =parts[1];
				            String thread =parts[2];
				            String event =parts[3]; //event should be classname & classname should be event vice versa//done      
				            String classname = parts[5];  //everyline should have a thread part[4] is empty string & part[6] starts with only "-"
				            String msgbgns = parts [7]; 
				            int msgindex = logContent.indexOf(msgbgns);
				            String message = new String (logContent.substring(msgindex));
				            //String thread = new String(logContent.substring(thrdindex));
				            //int msgindex= logContent.indexOf(msgbegins);
				            //String message = logContent.indexOf(msgbegins);    //remove thread information from message
				            String errorstack = "";
				            String errormsg = "";
				           
				            if (message.endsWith("with root cause") || message.startsWith("Application run failed") || message.endsWith("Application run failed")) {
				        	 errormsg = userLog.readLine();
				        	 String stacktrace = userLog.readLine();
				        	         	 
				        	//
				        	while(stacktrace != null && stacktrace.trim().startsWith("at")){ 		///use trim to remove space and then compare with 'at' 
				        		 errorstack = errorstack + stacktrace;
				        		 stacktrace = userLog.readLine();
				         	 }
				         	} 
							eventLog.setDate(date);
							eventLog.setClassname(classname);
							eventLog.setEvent(event);
							eventLog.setTime(time);
							eventLog.setThread(thread);
							eventLog.setMessage(message);
							eventLog.setStacktrace(errorstack);
							eventLog.setErrormsg(errormsg);
							
							           
							//writefile(jsonTrainingPojo, file);
							
							repository.save(eventLog);
							
				            logContent = userLog.readLine();
							i++;    
							
						}
			
						
					}catch (IOException e) {
						logger.error("Error occurred {}" , e);
					}
			} 
	}
}
	
	// Write a function to create a new file
	public  File extractedLog () {
		File extractedUserLog = new File(JSONOUTPUTDIR);
		try {	      
	      if (extractedUserLog.createNewFile()) {
	    	  System.out.println("File created: " + extractedUserLog.getName());
	      
	      }else {
	    	  System.out.println("File already exists.");
	      }
	    }catch (IOException e) {
	    	System.out.println("An error occurred.");
	    	e.printStackTrace();
	    }
	    return extractedUserLog;
	  }
	
	//function to write to file
	public static void writefile(String convertedjson2, File file) {
	    try {
	      FileWriter userLogextract = new FileWriter(file, true);
	      //userLogextract.append((CharSequence) jsonPojo);
	      userLogextract.append("\n");
	      userLogextract.close();
	      
	      System.out.println("Successfully wrote to the file.");
	    } catch (IOException e) {
	      System.out.println("An error occurred.");
	      e.printStackTrace();
	    }
	  }
	
	public static void writefile(LoginErrorTraining trainingJson, File file) {
		Gson gson = new Gson();
		String convertedjson = gson.toJson(trainingJson);
		try {
	      FileWriter userLogextract = new FileWriter(file, true);
	     userLogextract.append(convertedjson);
	      userLogextract.append("\n");
	      userLogextract.close();
	      
	      System.out.println("Successfully wrote to the file.");
	    } catch (IOException e) {
	      System.out.println("An error occurred.");
	      e.printStackTrace();
	    }
		
	
		
	  }
	// Write another function converting to JSON
	

	
}
	
	


					

