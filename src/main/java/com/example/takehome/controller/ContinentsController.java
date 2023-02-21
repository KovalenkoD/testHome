package com.example.takehome.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.takehome.dto.Continent;
import com.example.takehome.dto.ContinentDetails;
import com.example.takehome.dto.ContinentDto;
import com.example.takehome.dto.ContinentResponse;
import com.example.takehome.dto.CountriesDto;
import com.example.takehome.dto.Country;
import com.example.takehome.services.CountryClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * The controller to search continent details by contries
 */
@RestController
@RequestMapping("/api")
@Slf4j
@RequiredArgsConstructor
class ContinentsController
{
  private final CountryClient client;

  @GetMapping("/continents")
  public ContinentResponse getContinents(@RequestParam List<String> countries) throws IOException
  {
    log.warn("getContinents with countries: {}", countries);
    CountriesDto countryDto = client.getCountryDetails(countries);

    List<String> continentCodes =
        countryDto.getData().getCountries()
            .stream().map(Country::getContinent).map(Continent::getCode).toList();
    ContinentDto continentDetails = client.getContinentDetails(continentCodes);

    List<Continent> continents = continentDetails.getData().getContinents();

    List<ContinentDetails> continentDetailsList = continents.stream().map(continent -> {
      List<String> countryCodes = continent.getCountries().stream().map(Country::getCode).toList();

      List<String> countriesInContinent = countries.stream().filter(countryCodes::contains).toList();
      List<String> otherCountriesInContinent = countryCodes.stream().filter(country -> !countries.contains(country)).toList();
      return ContinentDetails.builder()
          .name(continent.getName())
          .countries(countriesInContinent)
          .otherCountries(otherCountriesInContinent)
          .build();
    }).toList();

    return new ContinentResponse(continentDetailsList);
  }
}
