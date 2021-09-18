package com.donlaiq.api.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.donlaiq.api.AnalyzeApi;
import com.donlaiq.api.model.Response;
import com.donlaiq.api.model.Text;

@RestController
public class AnalyzeController implements AnalyzeApi{
	
	private static final Logger log = LoggerFactory.getLogger(AnalyzeController.class);

	@Override
	public ResponseEntity<Response> executeAnalyze(@NotNull @Valid Map<String, String> toParse) {
		int[] characters = new int[58];
		int textLength = toParse.get("text").length();
		int whiteSpaces = 0;
		int wordCount = 0;
		char[] toParseCharacters = toParse.get("text").toCharArray();
		for(int i = 0; i < textLength; i++)
		{
			if(toParseCharacters[i] == ' ')
				whiteSpaces ++;
			else if(toParseCharacters[i] >= 65 && toParseCharacters[i] <= 90)
				characters[toParseCharacters[i]-'A']++;
			else if(toParseCharacters[i] >= 97 && toParseCharacters[i] <= 122)
				characters[toParseCharacters[i]-'A']++;
		}
		
		String[] words = toParse.get("text").split(" ");
		for(String w: words)
			if(!w.equals(""))
				wordCount++;
		
		List<Map<String, Integer>> list = new ArrayList<>();
		for(char i = 0; i < 58; i++)
		{
			if(characters[i] != 0)
			{
				Map<String, Integer> characterCount = new HashMap<String, Integer>();
				characterCount.put(Character.toString(i+'A'), characters[i]);
				list.add(characterCount);
			}
		}
		//characterCount = characterCount.substring(0, characterCount.length()-1);
		
		Text text = new Text();
		text.setWithSpaces(textLength);
		text.setWithoutSpaces(textLength - whiteSpaces);
		
		Response response = new Response();
		response.setTextLength(text);
		response.setWordCount(wordCount);
		response.setCharacterCount(list);
		
		/*response.setLength(textLength);
		response.setWhiteSpaces(whiteSpaces);
		response.setWords(wordCount);
		response.setCharacterCount(characterCount);*/
		
		HttpHeaders headers = new HttpHeaders();
		return new ResponseEntity<>(response, headers, HttpStatus.OK);
		
		// TODO Auto-generated method stub
		//return AnalyzeApi.super.executeAnalyze(toParse);
	}
	
	
}
