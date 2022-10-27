package ar.com.llegolaslutz.atencionpsicologica.auth;

public class JwtCinfig {
	
	public final static String CLAVE_SECRETA = "Dor3don$aladon";
	
	public final static String CLAVE_PRIVADA = "-----BEGIN OPENSSH PRIVATE KEY-----\r\n"
			+ "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\r\n"
			+ "-----END OPENSSH PRIVATE KEY-----";
	
	public final static String CLAVE_PUBLICA ="-----BEGIN OPENSSH PUBLIC KEY-----x3uKtE4OuAAdfbL0Xkdic9BG9qm9wlX8NbpY9+VC967hU2E3U6WoOMvPkuv0meA+j5DD0+HWTeO8bbcfPUpzhTmBwtYsJ648rbLgwCf+M8oIdbWg53ljOh4B8bEVTKNwwVSuhr4IfaG2OuoUAXN9KCyzB0NOzSvGfIuMOEYyTA2teP4uRTp94DdR6GYR0XtC3XaSlIebRcSY4leubLSMs6GOdC7Gm65MhSJMq9pL2oSmUUqES0QzFyNTdysq3oklu9oXq75qxuspHgrwbhiiiI/YLHs1TNMRGTAQGyV4RM2Q2rQ+pWlbJa1hNlMJOwEf8b3+SJTbGfgTrI5qPazs= gael@LAPTOP-LSGDDRTB-----END OPENSSH PUBLIC KEY-----";
	
	
}
