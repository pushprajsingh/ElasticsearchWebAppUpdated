package com.demo.elasticsearch.ESUtils;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.annotate.JsonAutoDetect.Visibility;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.demo.elasticsearch.ESEntities.Task;
import com.demo.elasticsearch.ESModels.FilterCriteriaModel;

public class JSONUtils {

	
	public  static  Task getParseTaskData(String inputjson) throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper().setVisibility(JsonMethod.FIELD, Visibility.ANY);
		mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		Task task2 = null;
		if (inputjson != null && !inputjson.isEmpty()) {
			task2 = mapper.readValue(inputjson, Task.class);
			if (task2 == null) {
				task2 = new Task();
			}
			try {

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return task2;
	}
	
	public static FilterCriteriaModel getParseFilterCriteriaData(String inputjson)
			throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper().setVisibility(JsonMethod.FIELD,
				Visibility.ANY);
		mapper.configure(
				DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		FilterCriteriaModel criteriaModel = null;
		if (inputjson != null && !inputjson.isEmpty()) {
			criteriaModel = mapper.readValue(inputjson,
					FilterCriteriaModel.class);
			if(criteriaModel==null){
				criteriaModel=new FilterCriteriaModel();
			}
			try {
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return criteriaModel;
	}

}
