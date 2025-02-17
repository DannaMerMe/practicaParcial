package co.edu.uptc.animals_rest.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.stereotype.Service;

import co.edu.uptc.animals_rest.exception.InvalidRangeException;
import co.edu.uptc.animals_rest.models.Animal;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
@Service
public class AnimalService {
     private static final Logger logger = LoggerFactory.getLogger(AnimalService.class);
    @Value("${animal.file.path}")
    private String filePath;

    
    public List<Animal> getAnimalInRange(int from, int to) throws IOException {
        List<String> listAnimal = Files.readAllLines(Paths.get(filePath));
        List<Animal> animales = new ArrayList<>();
        
        if (from < 0 || to >= listAnimal.size() || from > to) {
            logger.warn("Invalid range: Please check the provided indices. Range: 0 to {}",listAnimal.size());
             throw new InvalidRangeException("Invalid range: Please check the provided indices.");
        }

        for (String line : listAnimal) {
            String[] parts = line.split(",");
            if (parts.length == 2) {
                String categoria = parts[0].trim();
                String nombre = parts[1].trim();                
                animales.add(new Animal(nombre, categoria));
            }
        }
    
        return animales.subList(from, to + 1);
    }

    public List<Animal> getAnimalAll() throws IOException {
        List<String> listAnimal = Files.readAllLines(Paths.get(filePath));
        List<Animal> animales = new ArrayList<>();
        

        for (String line : listAnimal) {
            String[] parts = line.split(",");
            if (parts.length == 2) {
                String category = parts[0].trim();
                String name = parts[1].trim();                
                animales.add(new Animal(name, category));
            }
        }
        return animales;
    }
    public List<Animal> getAnimalCategory(String category) throws IOException {
        List<String> listAnimal = Files.readAllLines(Paths.get(filePath));
        List<Animal> categoryAnimal = new ArrayList<Animal>();
        for(String categoryAnim:listAnimal){
            String[] parts = categoryAnim.split(",");
            if(parts.length == 2) {
                String categoria = parts[0].trim();
                String nombre = parts[1].trim();
                if (categoria.equals(category)) {
                    categoryAnimal.add(new Animal(nombre, categoria));
                }
            }
        }
        return categoryAnimal;
    }
    public List<Animal> getLettersWord(int numberLetters) throws IOException {
        List<String> listAnimal = Files.readAllLines(Paths.get(filePath));
        List<Animal> numberWord = new ArrayList<Animal>();
        for(String letter: listAnimal) {
            String[] parts = letter.split(",");
            if (parts.length == 2) {
                String categoria = parts[0].trim();
                String nombre = parts[1].trim();
                if(nombre.length()<numberLetters){
                    numberWord.add(new Animal(nombre,categoria));
                    }
                }
            }
        return numberWord;
    }
}

