package com.example.takehome.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor

@AllArgsConstructor
public class ContinentDetails
{
	private List<String> countries;
	private String name;
	private List<String> otherCountries;
}