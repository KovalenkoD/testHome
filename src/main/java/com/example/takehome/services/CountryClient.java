package com.example.takehome.services;

import java.util.List;

import com.example.takehome.dto.ContinentDto;
import com.example.takehome.dto.CountriesDto;

public interface CountryClient
{
	CountriesDto getCountryDetails(List<String> countries);
	ContinentDto getContinentDetails(List<String> continentCodes);
}
