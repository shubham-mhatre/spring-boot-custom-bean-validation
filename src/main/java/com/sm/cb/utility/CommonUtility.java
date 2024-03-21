package com.sm.cb.utility;

import java.util.function.Predicate;

import org.springframework.stereotype.Component;

@Component
public class CommonUtility {

	public Predicate<String> checkMandatory = value -> (value == null || value.isEmpty());
}
