package com.example.takehome.dto;

import java.util.List;

import lombok.Getter;

@Getter
public class ContinentDto
{
	private ContinentData data;

	@Getter
	public class ContinentData {
		private List<Continent> continents;
	}
}