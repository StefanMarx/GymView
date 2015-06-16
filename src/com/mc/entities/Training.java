package com.mc.entities;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * represents the trainings data on a machine
 * 
 * @author stefan
 *
 */
public class Training {

	final static int ALL_TRAININGS = 197;
	final static int ALL_NAMES = 13;
	
	private String machineName;
	private LocalDate trainDate;
	private int trainLbs;
	private int trainSecs;
	private static Training[] theRecords = new Training[ALL_TRAININGS];

	Training() {
		LocalDate localDate;;
		localDate = LocalDate.MIN;
		this.machineName = "";
		this.trainDate =  localDate;
		this.trainLbs = 0;
		this.trainSecs = 0;
	    for (int i = 0; i < theRecords.length; i++) {
			theRecords[i] = new Training();
		}
	}

	public String getMachineName() {
		return machineName;
	}

	public void setMachineName(String machineName) {
		this.machineName = machineName;
	}

	Training(final LocalDate tDate, final String name, final int tLbs, final int tSecs) {
		this.trainDate = tDate;
		this.machineName = name;
		this.trainLbs = tLbs;
		this.trainSecs = tSecs;
	}

	public LocalDate getTrainDaten() {
		return this.trainDate;
	}

	public void setTrainDaten(LocalDate trainDaten) {
		this.trainDate = trainDaten;
	}

	public Integer getTrainWeight() {
		return this.trainLbs;
	}

	public void setTrainWeight(int trainWeight) {
		this.trainLbs = trainWeight;
	}

	public Integer getTrainSecs() {
		return this.trainSecs;
	}

	public void setTrainSecs(int trainSecs) {
		this.trainSecs = trainSecs;
	}

	public static Training makeOneA4Train(String dateString, String machineName, int tLbs, int tSecs) {
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
	    LocalDate date = LocalDate.parse(dateString, formatter);
	    Training mockRecord = new Training(date, machineName, tLbs,	tSecs);
		return mockRecord;
	}
	
	public static Training[] getTheRecords() {
		return theRecords;
	}

	public static void setTheRecords(Training[] theRecords) {
		Training.theRecords = theRecords;
	}

}
