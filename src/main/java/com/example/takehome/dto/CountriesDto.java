package com.example.takehome.dto;

import java.util.List;

import lombok.Getter;

@Getter
public class CountriesDto
{
	private CountriesData data;

	@Getter
	public class CountriesData {
		private List<Country> countries;
	}
}