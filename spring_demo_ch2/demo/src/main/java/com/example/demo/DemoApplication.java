/*
package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
*/
package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootApplication
@RestController
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@GetMapping("/hello")
	public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
		return String.format("Hello %s!", name);
	}
}

interface CoffeeRepository extends CrudRepository<Coffee, String> {}

@Entity
class Coffee {
	@Id
	private String id;
	private String name;

	public Coffee(String id, String name) {
		this.id = id;
		this.name = name;
	}
	public Coffee(String name) {
		this(UUID.randomUUID().toString(), name);
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}

@RestController
class RestApiDemoController {
	//private List<Coffee> coffees = new ArrayList<>();

    private final CoffeeRepository coffeeRepository;

    public RestApiDemoController(CoffeeRepository coffeeRepository) {
        this.coffeeRepository = coffeeRepository;

        this.coffeeRepository.saveAll(List.of(
                new Coffee("Cafe Cereze"),
                new Coffee("Cafe Ganador"),
                new Coffee("Cafe Lareno"),
                new Coffee("Cafe Tres Pontas")
        ));
    }
    /*
	public RestApiDemoController() {
		coffees.addAll(List.of(
			new Coffee("Cafe Cereze"),
			new Coffee("Cafe Ganador"),
			new Coffee("Cafe Lareno"),
			new Coffee("Cafe Tres Pontas")
		));
	}
	*/

	@RequestMapping(value = "/coffees", method = RequestMethod.GET)
	Iterable<Coffee> getCoffees() {
		//return coffees;
        return coffeeRepository.findAll();
	}

	@GetMapping("/coffees/{id}")
	Optional<Coffee> getCoffeeById(@PathVariable String id) {
	    return coffeeRepository.findById(id);
	    /*
		for (Coffee c: coffees) {
			if (c.getId().equals(id)) {
				return Optional.of(c);
			}
		}
		return Optional.empty();
	     */
	}

	@PostMapping("/coffees")
	Coffee postCoffee(@RequestBody Coffee coffee) {
		/*
	    coffees.add(coffee);
		return coffee;
		*/
        return coffeeRepository.save(coffee);
	}
    /*
	@PutMapping("/coffees/{id}")
	Coffee putCoffee(@PathVariable String id, @RequestBody Coffee coffee) {

		int coffeeIndex = -1;
		for (Coffee c: coffees) {
			if (c.getId().equals((id))) {
				coffeeIndex = coffees.indexOf(c);
				coffees.set(coffeeIndex, coffee);
			}
		}
		return (coffeeIndex == -1) ? postCoffee(coffee) : coffee;

        return (coffeeRepository.existsById(id))
                ? new ResponseEntity<>(coffeeRepository.save(coffee), HttpStatus.OK)
                : new ResponseEntity<>(coffeeRepository.save(coffee), HttpStatus.CREATED);

	}
    */
	@DeleteMapping("/coffees/{id}")
	void deleteCoffee(@PathVariable String id) {
		//coffees.removeIf(c -> c.getId().equals(id));
        coffeeRepository.deleteById(id);
	}
}