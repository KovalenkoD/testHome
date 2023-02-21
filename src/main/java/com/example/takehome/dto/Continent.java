package com.example.takehome.dto;

import java.util.List;

import lombok.Getter;

@Getter
public class Continent {
	private String name;
	private String code;
	private List<Country> countries;
}