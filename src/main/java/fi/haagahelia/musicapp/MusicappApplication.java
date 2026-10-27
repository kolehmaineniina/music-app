package fi.haagahelia.musicapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


// - DOMAIN PAKETTI, JOSSA SONG ENTITY (KENTÄT: ID, SONGNAME, ARTIST, YEAR)
// - DOMAIN PAKETTIIN CRUDREPOSITORY
// - KAIKKI TARVITTAVAT DEPENDENCYT THYMELEAF, TIETOKANTA MYSQL
// - KONTROLLERI SongController
//      - samat kuin bookstoressa

//
//      - login, kirjautuminen, password rest
//
// candidate: like toiminto
// 


@SpringBootApplication
public class MusicappApplication {

	public static void main(String[] args) {
		SpringApplication.run(MusicappApplication.class, args);
	}

}
