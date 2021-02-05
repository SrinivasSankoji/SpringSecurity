package com.chary.bhaumik.jwt.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class Person implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String name;
    private String email;

}
