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
	
	@Override
	public ResponseEntity<Response> executeAnalyze(@NotNull @Valid Map<String, String> toParse) {
		// Array to count the instances of A-Z and a-z
		int[] characters = new int[58];
		// Length of the string to parse
		int textLength = toParse.get("text").length();
		// Count the white spaces		
		int whiteSpaces = 0;
		// Count the words (every combination of different letters and other characters except for white spaces)
		int wordCount = 0;
		// Convert the string to an array of char, to be able to work with the ASCII values 
		char[] toParseCharacters = toParse.get("text").toCharArray();
		
		for(int i = 0; i < textLength; i++)
		{
			if(toParseCharacters[i] == ' ')
				whiteSpaces ++;
			// Characters from A to Z
			else if(toParseCharacters[i] >= 65 && toParseCharacters[i] <= 90)
				characters[toParseCharacters[i]-'A']++;
			// Characters from a to z
			else if(toParseCharacters[i] >= 97 && toParseCharacters[i] <= 122)
				characters[toParseCharacters[i]-'A']++;
		}
		
		// Get an array of substrings splitting the original string by its white spaces
		String[] words = toParse.get("text").split(" ");
		for(String w: words)
			if(!w.equals(""))
				wordCount++;
		
		// Generate a list of maps, where each map is a single key/value element (<letter>/<instances>) having the letters with one or more instances
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

		// Text and Response are two convenient ways to stores the values, helpful to generate the JSON response 
		
		Text text = new Text();
		text.setWithSpaces(textLength);
		text.setWithoutSpaces(textLength - whiteSpaces);
		
		Response response = new Response();
		response.setTextLength(text);
		response.setWordCount(wordCount);
		response.setCharacterCount(list);
		
		HttpHeaders headers = new HttpHeaders();
		return new ResponseEntity<>(response, headers, HttpStatus.OK);
		
	}
	
	
}
