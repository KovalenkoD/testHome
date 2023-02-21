package com.example.takehome.services.impl;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.takehome.dto.ContinentDto;
import com.example.takehome.dto.CountriesDto;
import com.example.takehome.graphql.GraphqlRequestBody;
import com.example.takehome.services.CountryClient;
import com.example.takehome.util.GraphqlSchemaReaderUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CountryClientImpl implements CountryClient
{
	private final WebClient webClient = WebClient.builder().build();

	private final String url;
	private final String countryDetailsQuery;
	private final String continentDetailsQuery;


	public CountryClientImpl(@Value("https://countries.trevorblades.com/") String url) throws IOException
	{
		this.url = url;
		countryDetailsQuery = GraphqlSchemaReaderUtil.getSchemaFromFileName("getCountriesByCodes");
		continentDetailsQuery = GraphqlSchemaReaderUtil.getSchemaFromFileName("getContinentsByCountryCodes");

	}

	public CountriesDto getCountryDetails(List<String> countries)
	{
		GraphqlRequestBody graphQLRequestBody = new GraphqlRequestBody();
		graphQLRequestBody.setQuery(countryDetailsQuery);
		graphQLRequestBody.setVariables(Map.of("countryCodes", countries));

		log.info("getCountryDetails: countries: {}", countries);

		return webClient.post()
			.uri(url)
			.bodyValue(graphQLRequestBody)
			.retrieve()
			.bodyToMono(CountriesDto.class)
			.block();
	}

	public ContinentDto getContinentDetails(List<String> continentCodes)
	{
		GraphqlRequestBody graphQLRequestBody = new GraphqlRequestBody();
		graphQLRequestBody.setQuery(continentDetailsQuery);
		graphQLRequestBody.setVariables(Map.of("continentCodes", continentCodes));

		log.info("getContinentDetails: continentCodes: {}", continentCodes);

		return webClient.post()
			.uri(url)
			.bodyValue(graphQLRequestBody)
			.retrieve()
			.bodyToMono(ContinentDto.class)
			.block();
	}
}