
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.StringTokenizer;

/**
 * represents the trainings data on an A4 machine
 * 
 * @author stefan
 *
 */
public class __Machine {

	private LocalDate trainDate;
	private int trainLbs;
	private int trainSecs;
	private static __Machine[] mockRecords = new __Machine[171];
	private static __Machine[] histRecords = new __Machine[171];

	__Machine() {
		LocalDate localDate;;
		localDate = LocalDate.MIN;
		this.trainDate =  localDate;
		this.trainLbs = 0;
		this.trainSecs = 0;
	    for (int i = 0; i < mockRecords.length; i++) {
			mockRecords[i] = new __Machine();
		}
	}

	__Machine(final LocalDate tDate, final int tLbs, final int tSecs) {
		this.trainDate = tDate;
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


	protected static __Machine makeOneA4Train(String dateString, int tLbs, int tSecs) {
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
	    LocalDate date = LocalDate.parse(dateString, formatter);
	    __Machine mockRecord = new __Machine(date, tLbs,	tSecs);
	   // System.out.println("In der Anlage " + mockRecord.getTrainDaten().toString());
		return mockRecord;
	}
	
	public static __Machine[] getMockData() {
		mockRecords[0] = __Machine.makeOneA4Train("18.12.2012", 120, 93);
		mockRecords[1] = __Machine.makeOneA4Train("22.12.2012", 126, 91);
		mockRecords[2] = __Machine.makeOneA4Train("24.12.2012", 132, 91);
		mockRecords[3] = makeOneA4Train("27.12.2012", 138, 94);
		mockRecords[4] = makeOneA4Train("29.12.2012", 120, 99);
		mockRecords[5] = makeOneA4Train("01.01.2013", 130, 94);
		mockRecords[6] = makeOneA4Train("05.01.2013", 130, 98);
		mockRecords[7] = makeOneA4Train("09.01.2013", 130, 97);
		mockRecords[8] = makeOneA4Train("12.01.2013", 130, 140);
		mockRecords[9] = makeOneA4Train("15.01.2013", 130, 91);
		mockRecords[10] = makeOneA4Train("17.01.2013", 130, 99);
		mockRecords[11] = makeOneA4Train("21.01.2013", 130, 92);
		mockRecords[12] = makeOneA4Train("25.01.2013", 130, 93);
		mockRecords[13] = makeOneA4Train("28.01.2013", 130, 98);
		mockRecords[14] = makeOneA4Train("01.02.2013", 130, 94);
		mockRecords[15] = makeOneA4Train("05.02.2013", 130, 91);
		mockRecords[16] = makeOneA4Train("08.02.2013", 130, 91);
		
		mockRecords[17] = makeOneA4Train("10.02.2013", 130, 91);
		mockRecords[18] = makeOneA4Train("11.02.2013", 130, 91);
		mockRecords[19] = makeOneA4Train("14.02.2013", 130, 92);
		mockRecords[20] = makeOneA4Train("18.02.2013", 130, 92);
		mockRecords[21] = makeOneA4Train("21.02.2013", 130, 93);
		mockRecords[22] = makeOneA4Train("25.02.2013", 130, 92);
		mockRecords[23] = makeOneA4Train("28.02.2013", 130, 92);
		mockRecords[24] = makeOneA4Train("04.03.2013", 130, 103);
		mockRecords[25] = makeOneA4Train("07.03.2013", 130, 93);
		mockRecords[26] = makeOneA4Train("11.03.2013", 130, 93);
		mockRecords[27] = makeOneA4Train("18.03.2013", 130, 91);
		mockRecords[28] = makeOneA4Train("21.03.2013", 130, 93);
		mockRecords[29] = makeOneA4Train("25.03.2013", 130, 95);
		mockRecords[30] = makeOneA4Train("30.03.2013", 130, 93);
		mockRecords[31] = makeOneA4Train("04.04.2013", 130, 96);
		mockRecords[32] = makeOneA4Train("08.04.2013", 130, 92);
		mockRecords[33] = makeOneA4Train("11.04.2013", 130, 91);
		mockRecords[34] = makeOneA4Train("15.04.2013", 130, 99);
		mockRecords[35] = makeOneA4Train("18.04.2013", 130, 97);
		mockRecords[36] = makeOneA4Train("22.04.2013", 130, 93);
		mockRecords[37] = makeOneA4Train("25.04.2013", 130, 92);
		mockRecords[38] = makeOneA4Train("29.04.2013", 130, 92);
		mockRecords[39] = makeOneA4Train("07.05.2013", 130, 92);
		mockRecords[40] = makeOneA4Train("10.05.2013", 130, 91);
		mockRecords[41] = makeOneA4Train("13.05.2013", 130, 93);
		mockRecords[42] = makeOneA4Train("16.05.2013", 130, 105);
		mockRecords[43] = makeOneA4Train("21.05.2013", 130, 91);
		mockRecords[44] = makeOneA4Train("23.05.2013", 130, 95);
		mockRecords[45] = makeOneA4Train("27.05.2013", 130, 93);
		mockRecords[46] = makeOneA4Train("01.06.2013", 130, 95);
		mockRecords[47] = makeOneA4Train("03.06.2013", 130, 96);
		mockRecords[48] = makeOneA4Train("06.06.2013", 130, 91);
		mockRecords[49] = makeOneA4Train("10.06.2013", 130, 90);
		mockRecords[50] = makeOneA4Train("13.06.2013", 130, 90);
		mockRecords[51] = makeOneA4Train("17.06.2013", 130, 91);
		mockRecords[52] = makeOneA4Train("20.06.2013", 130, 91);
		mockRecords[53] = makeOneA4Train("24.06.2013", 130, 91);
		mockRecords[54] = makeOneA4Train("27.06.2013", 130, 93);
		mockRecords[55] = makeOneA4Train("01.07.2013", 130, 92);
		mockRecords[56] = makeOneA4Train("04.07.2013", 130, 92);
		mockRecords[57] = makeOneA4Train("08.07.2013", 130, 92);
		mockRecords[58] = makeOneA4Train("11.07.2013", 130, 92);
		mockRecords[59] = makeOneA4Train("15.07.2013", 130, 93);
		mockRecords[60] = makeOneA4Train("22.07.2013", 130, 94);
		mockRecords[61] = makeOneA4Train("29.07.2013", 130, 93);
		mockRecords[62] = makeOneA4Train("01.08.2013", 130, 95);
		mockRecords[63] = makeOneA4Train("05.08.2013", 130, 92);
		mockRecords[64] = makeOneA4Train("08.08.2013", 130, 92);
		mockRecords[65] = makeOneA4Train("17.08.2013", 130, 93);
		mockRecords[66] = makeOneA4Train("19.08.2013", 130, 92);
		mockRecords[67] = makeOneA4Train("21.08.2013", 130, 93);
		mockRecords[68] = makeOneA4Train("26.08.2013", 130, 93);
		mockRecords[69] = makeOneA4Train("04.09.2013", 130, 99);
		mockRecords[70] = makeOneA4Train("06.09.2013", 130, 96);
		mockRecords[71] = makeOneA4Train("09.09.2013", 130, 92);
		mockRecords[72] = makeOneA4Train("12.09.2013", 130, 99);
		mockRecords[73] = makeOneA4Train("16.09.2013", 130, 91);
		mockRecords[74] = makeOneA4Train("23.09.2013", 130, 91);
		mockRecords[75] = makeOneA4Train("26.09.2013", 130, 99);
		mockRecords[76] = makeOneA4Train("30.09.2013", 130, 92);
		mockRecords[77] = makeOneA4Train("07.10.2013", 130, 98);
		mockRecords[78] = makeOneA4Train("14.10.2013", 130, 95);
		mockRecords[79] = makeOneA4Train("17.10.2013", 130, 98);
		mockRecords[80] = makeOneA4Train("21.10.2013", 130, 101);
		mockRecords[81] = makeOneA4Train("24.10.2013", 130, 99);
		mockRecords[82] = makeOneA4Train("02.11.2013", 130, 92);
		mockRecords[83] = makeOneA4Train("04.11.2013", 130, 92);
		mockRecords[84] = makeOneA4Train("06.11.2013", 130, 94);
		mockRecords[85] = makeOneA4Train("11.11.2013", 130, 92);
		mockRecords[86] = makeOneA4Train("25.11.2013", 130, 92);
		mockRecords[87] = makeOneA4Train("02.12.2013", 130, 161);
		mockRecords[88] = makeOneA4Train("05.12.2013", 130, 92);
		mockRecords[89] = makeOneA4Train("07.12.2013", 130, 99);
		mockRecords[90] = makeOneA4Train("09.12.2013", 130, 98);
		mockRecords[91] = makeOneA4Train("16.12.2013", 130, 92);
		mockRecords[92] = makeOneA4Train("19.12.2013", 130, 92);
		mockRecords[93] = makeOneA4Train("21.12.2013", 130, 91);
		mockRecords[94] = makeOneA4Train("23.12.2013", 130, 91);
		mockRecords[95] = makeOneA4Train("30.12.2013", 130, 96);
		mockRecords[96] = makeOneA4Train("03.01.2014", 130, 92);
		mockRecords[97] = makeOneA4Train("06.01.2014", 130, 92);
		mockRecords[98] = makeOneA4Train("09.01.2014", 130, 93);
		mockRecords[99] = makeOneA4Train("13.01.2014", 130, 92);
		mockRecords[100] = makeOneA4Train("18.01.2014", 130, 93);
		mockRecords[101] = makeOneA4Train("20.01.2014", 130, 93);
		mockRecords[102] = makeOneA4Train("27.01.2014", 130, 91);
		mockRecords[103] = makeOneA4Train("01.02.2014", 130, 93);
		mockRecords[104] = makeOneA4Train("03.02.2014", 130, 92);
		mockRecords[105] = makeOneA4Train("06.02.2014", 130, 92);
		mockRecords[106] = makeOneA4Train("10.02.2014", 130, 91);
		mockRecords[107] = makeOneA4Train("13.02.2014", 130, 92);
		mockRecords[108] = makeOneA4Train("17.02.2014", 130, 93);
		mockRecords[109] = makeOneA4Train("22.02.2014", 130, 92);
		mockRecords[110] = makeOneA4Train("24.02.2014", 130, 92);
		mockRecords[111] = makeOneA4Train("08.03.2014", 130, 91);
		mockRecords[112] = makeOneA4Train("10.03.2014", 130, 93);
		mockRecords[113] = makeOneA4Train("13.03.2014", 130, 91);
		mockRecords[114] = makeOneA4Train("17.03.2014", 130, 91);
		mockRecords[115] = makeOneA4Train("29.03.2014", 130, 92);
		mockRecords[116] = makeOneA4Train("31.03.2014", 130, 92);
		mockRecords[117] = makeOneA4Train("06.04.2014", 130, 108);
		mockRecords[118] = makeOneA4Train("07.04.2014", 130, 92);
		mockRecords[119] = makeOneA4Train("10.04.2014", 130, 92);
		mockRecords[120] = makeOneA4Train("12.04.2014", 130, 93);
		mockRecords[121] = makeOneA4Train("17.04.2014", 130, 94);
		mockRecords[122] = makeOneA4Train("19.04.2014", 128, 94);
		mockRecords[123] = makeOneA4Train("24.04.2014", 130, 93);
		mockRecords[124] = makeOneA4Train("26.04.2014", 130, 93);
		mockRecords[125] = makeOneA4Train("28.04.2014", 130, 91);
		mockRecords[126] = makeOneA4Train("01.05.2014", 130, 93);
		mockRecords[127] = makeOneA4Train("05.05.2014", 130, 93);
		mockRecords[128] = makeOneA4Train("10.05.2014", 130, 96);
		mockRecords[129] = makeOneA4Train("12.05.2014", 130, 92);
		mockRecords[130] = makeOneA4Train("17.05.2014", 130, 107);
		mockRecords[131] = makeOneA4Train("19.05.2014", 130, 91);
		mockRecords[132] = makeOneA4Train("24.05.2014", 130, 91);
		mockRecords[133] = makeOneA4Train("31.05.2014", 132, 91);
		mockRecords[134] = makeOneA4Train("07.06.2014", 132, 125);
		mockRecords[135] = makeOneA4Train("14.06.2014", 132, 91);
		mockRecords[136] = makeOneA4Train("16.06.2014", 132, 91);
		mockRecords[137] = makeOneA4Train("23.06.2014", 132, 91);
		mockRecords[138] = makeOneA4Train("30.06.2014", 132, 94);
		mockRecords[139] = makeOneA4Train("03.07.2014", 132, 100);
		mockRecords[140] = makeOneA4Train("07.07.2014", 132, 92);
		mockRecords[141] = makeOneA4Train("14.07.2014", 132, 92);
		mockRecords[142] = makeOneA4Train("16.07.2014", 132, 90);
		mockRecords[143] = makeOneA4Train("24.07.2014", 132, 91);
		mockRecords[144] = makeOneA4Train("28.07.2014", 132, 92);
		mockRecords[145] = makeOneA4Train("02.08.2014", 132, 91);
		mockRecords[146] = makeOneA4Train("04.08.2014", 132, 91);
		mockRecords[147] = makeOneA4Train("11.08.2014", 132, 91);
		mockRecords[148] = makeOneA4Train("14.08.2014", 132, 91);
		mockRecords[149] = makeOneA4Train("18.08.2014", 132, 92);
		mockRecords[150] = makeOneA4Train("25.08.2014", 132, 93);
		mockRecords[151] = makeOneA4Train("08.09.2014", 132, 91);
		mockRecords[152] = makeOneA4Train("13.09.2014", 132, 92);
		mockRecords[153] = makeOneA4Train("15.09.2014", 132, 91);
		mockRecords[154] = makeOneA4Train("25.09.2014", 132, 92);
		mockRecords[155] = makeOneA4Train("29.09.2014", 132, 92);
		mockRecords[156] = makeOneA4Train("13.10.2014", 132, 92);
		mockRecords[157] = makeOneA4Train("20.10.2014", 130, 94);
		mockRecords[158] = makeOneA4Train("25.10.2014", 130, 92);
		mockRecords[159] = makeOneA4Train("27.10.2014", 130, 102);
		mockRecords[160] = makeOneA4Train("01.11.2014", 130, 97);
		mockRecords[161] = makeOneA4Train("03.11.2014", 130, 97);
		mockRecords[162] = makeOneA4Train("10.11.2014", 130, 92);
		mockRecords[163] = makeOneA4Train("17.11.2014", 130, 92);
		mockRecords[164] = makeOneA4Train("20.11.2014", 130, 94);
		mockRecords[165] = makeOneA4Train("24.11.2014", 140, 98);
		mockRecords[166] = makeOneA4Train("29.11.2014", 140, 90);
		mockRecords[167] = makeOneA4Train("15.12.2014", 140, 92);
		mockRecords[168] = makeOneA4Train("18.12.2014", 140, 103);
		mockRecords[169] = makeOneA4Train("24.12.2014", 140, 92);
		mockRecords[170] = makeOneA4Train("31.12.2014", 140, 91);
        
		// Sollte der 12.01.13 sein
		System.out.println("Unmittelbar vor der Zuweisung " + mockRecords[0].getTrainDaten().toString());		
		System.out.println("in der Zuweisung " + mockRecords[0].getTrainDaten().toString());
		System.out.println("in der Zuweisung " + mockRecords[1].getTrainDaten().toString());

		return mockRecords;
	}
	
	public static __Machine[] getA4Data( String[] trainings){
		__Machine[] theTraininigs = new __Machine[trainings.length - 3];
		
		StringTokenizer tok = new StringTokenizer(trainings[3], ";");
		for (int i = 3; i < trainings.length; i++) {
			
			histRecords[i - 3] = null;
		}
		return theTraininigs;
	}

}