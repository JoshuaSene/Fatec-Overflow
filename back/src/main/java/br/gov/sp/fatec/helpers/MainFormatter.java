package br.gov.sp.fatec.helpers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MainFormatter {

	public static LocalDate parseDateString(String date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

		LocalDate localDate = LocalDate.parse(date, formatter);
		return localDate;
	}
	
	public static String parseStringDate(LocalDate date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

		return date.format(formatter);
	}
}
